package ru_shift.file;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

@Slf4j
public class SystemFileReader {

    public BufferedReader readComputerFile(String fileName) {
        try {
            var bufferedReader = new BufferedReader(new FileReader(fileName));
            log.info("Чтение файла {}", fileName);

            return bufferedReader;
        } catch (IOException e) {
            log.error("Ошибка чтения файла {}", fileName);
            log.error("Подробности {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }
}