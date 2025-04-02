package ru.shift.args;

import lombok.*;

import java.nio.file.Path;

@Getter
@NoArgsConstructor
@Setter
public class ApplicationArgs {
    Path fileReadPath;
    Path fileWritePath;
}

