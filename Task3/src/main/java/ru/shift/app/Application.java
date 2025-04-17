package ru.shift.app;

import ru.shift.controller.MouseController;
import ru.shift.controller.NewGameController;
import ru.shift.controller.RecordController;
import ru.shift.controller.SettingsController;
import ru.shift.model.GameModel;
import ru.shift.model.GameType;
import ru.shift.record.RecordManager;
import ru.shift.timer.Timer;
import ru.shift.view.observers.*;
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
    private static final GameModel model = new GameModel(GameType.NOVICE);

    //Timer
    private final static TimeObserver timeObserver = new TimeObserver(model, mainWindow);
    private static final Timer timer = new Timer(timeObserver , model);

    //Record
    private static final RecordManager recordManager = new RecordManager(highScoresWindow,model,timer);
    private static final RecordController recordController = new RecordController(recordManager);

    //Observer
    private final static BombCountObserver bombCountObserver = new BombCountObserver(model, mainWindow);
    private final static FlagPlaningObserver flagPlaningObserver = new FlagPlaningObserver(model, mainWindow);
    private final static GameLoseObserver gameLoseObserver = new GameLoseObserver(model, loseWindow);
    private final static UpdateTheCellObserver updateTheCellObserver = new UpdateTheCellObserver(model, mainWindow);
    private final static GameRecordObserver gameRecordObserver = new GameRecordObserver(recordManager, recordsWindow);
    private final static GameWonObserver gameWonObserver = new GameWonObserver(model, winWindow);
    private final static UpdateGameObserver updateGameObserver = new UpdateGameObserver(model, mainWindow);

    //Controller
    private static final NewGameController newGameController = new NewGameController(model);
    private static final MouseController mouseController = new MouseController(model);
    private static final SettingsController settingsController = new SettingsController(model);

    public static void main(String[] args) {
        //Record
        recordsWindow.setNameListener(recordController);

        //Win
        winWindow.setNewGameListener(e -> {
            winWindow.dispose();
            newGameController.newGame();
        });

        winWindow.setExitListener(e -> mainWindow.dispose());

        //Lose
        loseWindow.setNewGameListener(e -> {
            loseWindow.dispose();
            newGameController.newGame();
        });
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
