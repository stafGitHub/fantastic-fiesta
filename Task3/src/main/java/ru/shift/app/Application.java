package ru.shift.app;

import ru.shift.controller.MouseController;
import ru.shift.controller.NewGameController;
import ru.shift.controller.RecordController;
import ru.shift.controller.SettingsController;
import ru.shift.model.GameModel;
import ru.shift.model.GameType;
import ru.shift.model.counter.GameCounters;
import ru.shift.model.filed.PlayingField;
import ru.shift.record.RecordManager;
import ru.shift.timer.Timer;
import ru.shift.view.observers.field.BombCountObserver;
import ru.shift.view.observers.field.FlagPlaningObserver;
import ru.shift.view.observers.field.UpdateGameObserver;
import ru.shift.view.observers.field.UpdateTheCellObserver;
import ru.shift.view.observers.game.status.GameLoseObserver;
import ru.shift.view.observers.game.status.GameOverObserver;
import ru.shift.view.observers.game.status.GameWonObserver;
import ru.shift.view.observers.record.GameRecordObserver;
import ru.shift.view.observers.time.TimeObserver;
import ru.shift.view.windows.*;

public class Application {
    //Window
    private static final MainWindow mainWindow = new MainWindow();
    private static final HighScoresWindow highScoresWindow = new HighScoresWindow(mainWindow);
    private static final LoseWindow loseWindow = new LoseWindow(mainWindow);
    private static final WinWindow winWindow = new WinWindow(mainWindow);
    private static final RecordsWindow recordsWindow = new RecordsWindow(mainWindow);
    private static final SettingsWindow settingsWindow = new SettingsWindow(mainWindow);


    //Model
    private static final GameCounters gameCounters = new GameCounters();
    private static final PlayingField playingField = new PlayingField();
    private static final GameModel model = new GameModel(GameType.NOVICE, playingField, gameCounters);

    //Timer
    private static final Timer timer = new Timer(model);
    private static final TimeObserver timeObserver = new TimeObserver(mainWindow, timer);

    //GameRecord
    private static final RecordManager recordManager = new RecordManager(highScoresWindow, model, timer);
    private static final RecordController recordController = new RecordController(recordManager);

    //Observer
    private static final GameOverObserver gameOverObserver = new GameOverObserver(playingField, model);
    private static final BombCountObserver bombCountObserver = new BombCountObserver(mainWindow, model);
    private static final FlagPlaningObserver flagPlaningObserver = new FlagPlaningObserver(mainWindow, model);
    private static final GameLoseObserver gameLoseObserver = new GameLoseObserver(loseWindow, model);
    private static final UpdateTheCellObserver updateTheCellObserver = new UpdateTheCellObserver(mainWindow, model, gameOverObserver);
    private static final GameRecordObserver gameRecordObserver = new GameRecordObserver(recordsWindow, recordManager);
    private static final GameWonObserver gameWonObserver = new GameWonObserver(winWindow, model);
    private static final UpdateGameObserver updateGameObserver = new UpdateGameObserver(mainWindow, model);

    //Controller
    private static final NewGameController newGameController = new NewGameController(model);
    private static final MouseController mouseController = new MouseController(model);
    private static final SettingsController settingsController = new SettingsController(model);

    public static void main(String[] args) {
        //GameRecord
        recordsWindow.setNameListener(recordController);

        //Win
        winWindow.setNewGameListener(e -> newGameController.newGame());

        winWindow.setExitListener(e -> mainWindow.dispose());

        //Lose
        loseWindow.setNewGameListener(e -> newGameController.newGame());
        loseWindow.setExitListener(e -> mainWindow.dispose());

        //Main
        mainWindow.setSettingsMenuAction(e -> settingsWindow.setVisible(true));
        mainWindow.setHighScoresMenuAction(e -> highScoresWindow.setVisible(true));
        mainWindow.setExitMenuAction(e -> mainWindow.dispose());
        mainWindow.setNewGameMenuAction(e -> newGameController.newGame());
        mainWindow.setCellListener(mouseController);

        //Settings
        settingsWindow.setGameTypeListener(settingsController);


        mainWindow.createGameField(9, 9);
        mainWindow.setVisible(true);
    }
}
