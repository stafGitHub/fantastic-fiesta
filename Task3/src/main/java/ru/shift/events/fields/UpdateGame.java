package ru.shift.events.fields;

import ru.shift.events.GameEvent;
import ru.shift.model.GameType;

public record UpdateGame(GameType gameType) implements GameEvent {
}
