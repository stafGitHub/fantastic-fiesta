package ru.shift.model.events.game.result;

import ru.shift.model.GameType;
import ru.shift.model.events.GameEvent;

public record Won(GameType gameType) implements GameEvent {
}
