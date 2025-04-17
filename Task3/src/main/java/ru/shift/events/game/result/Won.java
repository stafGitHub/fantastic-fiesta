package ru.shift.events.game.result;

import ru.shift.events.GameEvent;
import ru.shift.model.GameType;

public record Won(GameType gameType) implements GameEvent {
}
