package ru.shift.record;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import ru.shift.events.GameEvent;
import ru.shift.events.Observer;
import ru.shift.events.Publisher;
import ru.shift.events.game.result.Won;
import ru.shift.events.record.NewRecord;
import ru.shift.model.GameType;
import ru.shift.timer.Timer;
import ru.shift.view.windows.HighScoresWindow;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Getter
@Slf4j
public class RecordManager extends Observer implements Publisher {
    private static final String FILE_NAME = "stafievskiy_application_record.ser";
    private static final Path RECORDS_FILE_PATH = Paths.get(System.getProperty("user.home"), FILE_NAME);
    private static final String DEFAULT_PLAYER_NAME = "Unknown";
    private static final int DEFAULT_RECORD_TIME = 999;

    private final HighScoresWindow highScoresWindow;
    private final Timer timer;
    private final List<Observer> observers = new ArrayList<>();
    private final Map<GameType, Record> records = new EnumMap<>(GameType.class);
    @Setter
    private String recordName;

    public RecordManager(HighScoresWindow highScoresWindow,
                         Publisher publisher,
                         Timer timer) {
        super(publisher);
        this.highScoresWindow = highScoresWindow;
        this.timer = timer;
        loadRecords();
        updateRecordWindow();
    }

    @Override
    public void addListener(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void notifyListeners(GameEvent gameEvent) {
        observers.forEach(listener -> listener.onGameEvent(gameEvent));
    }

    @Override
    public void onGameEvent(GameEvent gameEvent) {
        if (gameEvent instanceof Won won) {
            int currentTime = timer.getSecondsPassed().get();
            GameType gameType = won.gameType();

            if (isNewRecord(currentTime, gameType)) {
                notifyListeners(new NewRecord(currentTime));
                updateRecord(currentTime, gameType);
                updateRecordWindow();
            }
        }
    }

    private boolean isNewRecord(int time, GameType gameType) {
        Record record = records.get(gameType);
        return record == null || time < record.time();
    }

    private void updateRecord(int time, GameType gameType) {
        records.put(gameType, new Record(time, recordName!=null?recordName:DEFAULT_PLAYER_NAME));
        saveRecords();
        log.info("Рекорд обновлен: {} - {} сек", gameType, time);
    }

    private void loadRecords() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(RECORDS_FILE_PATH.toFile()))) {
            Map<GameType, Record> loadedRecords = (Map<GameType, Record>) ois.readObject();
            records.putAll(loadedRecords);
            log.info("Загружены рекорды из файла: {}", RECORDS_FILE_PATH);
        } catch (IOException | ClassNotFoundException s) {
            log.info("Создана новая таблица рекордов");
            initializeDefaultRecords();
        }
    }

    private void initializeDefaultRecords() {
        for (GameType type : GameType.values()) {
            records.put(type, new Record(DEFAULT_RECORD_TIME,DEFAULT_PLAYER_NAME));
        }
    }

    private void saveRecords() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(RECORDS_FILE_PATH.toFile()))) {
            oos.writeObject(records);
            log.info("Рекорды сохранены в файл: {}", RECORDS_FILE_PATH);
        } catch (IOException e) {
            log.error("Ошибка сохранения рекордов", e);
        }
    }

    private void updateRecordWindow() {
        for (GameType type : GameType.values()) {
            Record record = records.get(type);
            if (record != null) {
                switch (type){
                    case NOVICE -> highScoresWindow.setNoviceRecord(record.name(),record.time());
                    case MEDIUM -> highScoresWindow.setMediumRecord(record.name(),record.time());
                    case EXPERT -> highScoresWindow.setExpertRecord(record.name(),record.time());
                }
            }
        }
    }
}