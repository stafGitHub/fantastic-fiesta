package ru.shift.model.events.fields;

import ru.shift.model.GameType;
import ru.shift.model.events.GameEvent;

public record UpdateGame(GameType gameType) implements GameEvent {
}
