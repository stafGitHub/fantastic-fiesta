package ru.shift.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class Record implements Serializable {
    private String noviceRecordName = "Unknown";
    private String mediumRecordName = "Unknown";
    private String expertRecordName = "Unknown";
    private int noviceTime = 999;
    private int mediumTime = 999;
    private int expertTime = 999;

}
