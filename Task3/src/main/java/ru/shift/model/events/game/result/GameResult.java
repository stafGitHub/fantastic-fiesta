package ru.shift.model.events.game.result;

import ru.shift.model.GameState;
import ru.shift.model.GameType;
import ru.shift.model.dto.CellOutput;
import ru.shift.model.events.GameEvent;

import java.util.List;


public record GameResult(GameState gameState, List<CellOutput> cells, GameType gameType) implements GameEvent {
}
