package ru.shift.events.game.result;

import ru.shift.model.GameType;
import ru.shift.events.GameEvent;

public record Won(GameType gameType) implements GameEvent {
}
