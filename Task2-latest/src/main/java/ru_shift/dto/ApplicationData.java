package ru_shift.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ApplicationData {
    // -r
    private final String readFilePath;
    // -s
    private final String saveFilePath;
}
