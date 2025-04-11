package ru.shift.model.records;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import ru.shift.model.GameType;
import ru.shift.view.windows.HighScoresWindow;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Getter
@Slf4j
public class RecordManager {
    private static final String FILE_NAME = "stafievskiy_application_record.ser";
    private static final Path RECORDS_FILE_PATH = Paths.get(System.getProperty("user.home"), FILE_NAME);
    private final HighScoresWindow highScoresWindow;
    @Setter
    private String recordName;
    private GameType gameType;
    private Record currentRecord;

    public RecordManager(HighScoresWindow highScoresWindow) {
        this.highScoresWindow = highScoresWindow;
        loadRecords();
        updateRecordWindow();
    }

    public void updateRecord(int time) {
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

    public boolean checkRecords(int time, GameType gameType) {
        switch (gameType) {
            case NOVICE -> {
                if (time < currentRecord.getNoviceTime()) {
                    this.gameType = gameType;
                }  return true;
            }
            case MEDIUM -> {
                if (time < currentRecord.getMediumTime()){
                    this.gameType = gameType;
                    return true;
                }
            }
            case EXPERT -> {
                if (time < currentRecord.getExpertTime()){
                    this.gameType = gameType;
                    return true;
                }
            }
        }

        return false;
    }

    private void loadRecords() {
        try {
            if (Files.exists(RECORDS_FILE_PATH)) {
                try (ObjectInputStream ois = new ObjectInputStream(
                        new FileInputStream(RECORDS_FILE_PATH.toFile()))) {
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
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(RECORDS_FILE_PATH.toFile()))) {
            oos.writeObject(currentRecord);
            log.info("Сохранение объекта с рекордами: {}", RECORDS_FILE_PATH);
        } catch (IOException e) {
            log.error("Ошибка сохранения рекордов", e);
        }
    }

    private void updateRecordWindow(){
        highScoresWindow.setNoviceRecord(currentRecord.getNoviceRecordName(),currentRecord.getNoviceTime());
        highScoresWindow.setMediumRecord(currentRecord.getMediumRecordName(),currentRecord.getMediumTime());
        highScoresWindow.setExpertRecord(currentRecord.getExpertRecordName(),currentRecord.getExpertTime());
    }

}
