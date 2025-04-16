package ru.shift.model.publisher;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import ru.shift.model.GameType;
import ru.shift.model.Publisher;
import ru.shift.model.events.GameEvent;
import ru.shift.model.events.GameSettingsListener;
import ru.shift.model.events.game.result.Won;
import ru.shift.model.events.record.NewRecord;
import ru.shift.model.dto.Record;
import ru.shift.view.windows.HighScoresWindow;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Getter
@Slf4j
public class RecordManager extends GameSettingsListener implements Publisher {
    private static final String FILE_NAME = "stafievskiy_application_record.ser";
    private static final Path RECORDS_FILE_PATH = Paths.get(System.getProperty("user.home"), FILE_NAME);
    private final HighScoresWindow highScoresWindow;
    private final Timer timer;
    private final List<GameSettingsListener> gameSettingsListeners = new ArrayList<>();

    @Override
    public void addListener(GameSettingsListener gameSettingsListener) {
        gameSettingsListeners.add(gameSettingsListener);
    }

    @Override
    public void notifyListeners(GameEvent gameEvent) {
        gameSettingsListeners.forEach(listener -> listener.onGameEvent(gameEvent));
    }

    @Setter
    private String recordName;
    private Record currentRecord;

    public RecordManager(HighScoresWindow highScoresWindow ,
                         Publisher publisher,
                         Timer timer) {
        super(publisher);
        this.highScoresWindow = highScoresWindow;
        this.timer = timer;
        loadRecords();
        updateRecordWindow();
    }

    @Override
    public void onGameEvent(GameEvent gameEvent) {
        if (gameEvent instanceof Won won){
            boolean record = checkRecords(timer.getSecondsPassed().get(), won.gameType());
            if (record){
                notifyListeners(new NewRecord(timer.getSecondsPassed().get()));
                updateRecord(timer.getSecondsPassed().get(),won.gameType());
                updateRecordWindow();
            }
        }
    }

    private void updateRecord(int time , GameType gameType) {
        log.info("Обновление рекорда - {} : {}",gameType,time);
        switch (gameType) {
            case NOVICE -> {
                currentRecord.setNoviceTime(time);
                currentRecord.setNoviceRecordName(recordName);
                saveRecords();
                loadRecords();
                updateRecordWindow();
            }
            case MEDIUM -> {
                currentRecord.setMediumTime(time);
                currentRecord.setMediumRecordName(recordName);
                saveRecords();
                loadRecords();
                updateRecordWindow();
            }
            case EXPERT -> {
                currentRecord.setExpertTime(time);
                currentRecord.setExpertRecordName(recordName);
                saveRecords();
                loadRecords();
                updateRecordWindow();
            }
        }
    }

    private boolean checkRecords(int time, GameType gameType) {
        log.info("Проверка рекорда: {}",gameType);
        return switch (gameType) {
            case NOVICE -> time < currentRecord.getNoviceTime();
            case MEDIUM -> time < currentRecord.getMediumTime();
            case EXPERT -> time < currentRecord.getExpertTime();
        };
    }

    private void loadRecords() {
        try {
            if (Files.exists(RECORDS_FILE_PATH)) {
                try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(RECORDS_FILE_PATH.toFile()))) {
                    currentRecord = (Record) ois.readObject();
                    log.info("Чтение файла с рекордами: {}", RECORDS_FILE_PATH);
                }
            } else {
                currentRecord = new Record();
                log.info("Создан новый файл с рекордами");
            }
        } catch (IOException | ClassNotFoundException e) {
            log.error("Ошибка чтения файла с рекордами", e);
            currentRecord = new Record();
        }
    }

    private void saveRecords() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(RECORDS_FILE_PATH.toFile()))) {
            oos.writeObject(currentRecord);
            log.info("Сохранение объекта с рекордами: {}", RECORDS_FILE_PATH);
        } catch (IOException e) {
            log.error("Ошибка сохранения рекордов", e);
        }
    }

    private void updateRecordWindow() {
        highScoresWindow.setNoviceRecord(currentRecord.getNoviceRecordName(), currentRecord.getNoviceTime());
        highScoresWindow.setMediumRecord(currentRecord.getMediumRecordName(), currentRecord.getMediumTime());
        highScoresWindow.setExpertRecord(currentRecord.getExpertRecordName(), currentRecord.getExpertTime());
    }

}
