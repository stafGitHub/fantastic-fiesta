package ru.shift.view.observers.game.result;

import lombok.extern.slf4j.Slf4j;
import ru.shift.events.Observer;
import ru.shift.events.Publisher;
import ru.shift.model.GameState;
import ru.shift.model.dto.CellOutput;
import ru.shift.model.events.GameEvent;
import ru.shift.model.events.fields.UpdateTheCell;
import ru.shift.model.events.game.result.GameResult;
import ru.shift.view.windows.LoseWindow;
import ru.shift.view.windows.WinWindow;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class GameResultObserver extends Observer implements Publisher {
    private final LoseWindow loseWindow;
    private final WinWindow winWindow;
    private final List<Observer> observers = new ArrayList<>();

    public GameResultObserver(LoseWindow loseWindow, WinWindow winWindow, Publisher... publisher) {
        super(publisher);
        this.loseWindow = loseWindow;
        this.winWindow = winWindow;
    }

    @Override
    public void onGameEvent(GameEvent gameEvent) {
        if (gameEvent instanceof GameResult gameResult) {
            notifyListeners(new UpdateTheCell(findAllTheMines(gameResult)));

            if (gameResult.gameState() == GameState.WIN) {
                winWindow.setVisible(true);
            }

            if (gameResult.gameState() == GameState.LOSE) {
                loseWindow.setVisible(true);
            }
        }
    }

    @Override
    public void addListener(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void notifyListeners(GameEvent gameEvent) {
        observers.forEach(observer -> observer.onGameEvent(gameEvent));
    }


    private List<CellOutput> findAllTheMines(GameResult gameResult) {
        log.info("Поиск мин");

        var listMines = gameResult.cells().stream()
                .filter(CellOutput::mine)
                .toList();

        log.info("Поиск мин завершён");
        return listMines;

    }
}
