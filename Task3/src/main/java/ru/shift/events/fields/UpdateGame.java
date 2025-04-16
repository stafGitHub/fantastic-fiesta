package ru.shift.events.fields;

import ru.shift.model.GameType;
import ru.shift.events.GameEvent;

public record UpdateGame(GameType gameType) implements GameEvent {
}
