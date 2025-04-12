package ru.shift.app;

import ru.shift.controller.MouseViewController;
import ru.shift.controller.NewGameViewController;
import ru.shift.controller.SettingsViewController;
import ru.shift.model.GameModel;
import ru.shift.model.GameType;
import ru.shift.model.Timer;
import ru.shift.model.records.RecordController;
import ru.shift.model.records.RecordManager;
import ru.shift.view.observers.GameRecordObserver;
import ru.shift.view.observers.GameResultObserver;
import ru.shift.view.observers.GameWindowsObserver;
import ru.shift.view.windows.HighScoresWindow;
import ru.shift.view.windows.MainWindow;
import ru.shift.view.windows.SettingsWindow;

public class Application {
    private static final MainWindow view = new MainWindow();

    private static final HighScoresWindow highScoresWindow = new HighScoresWindow(view);

    private static final RecordManager recordManager = new RecordManager(highScoresWindow);

    private static final RecordController recordController = new RecordController(recordManager);


    private static final GameWindowsObserver gameWindowsObserver = new GameWindowsObserver(view);
    private static final GameResultObserver gameResultObserver = new GameResultObserver(view);
    private static final GameRecordObserver gameRecordObserver = new GameRecordObserver(view, recordController);
    private static final Timer timer = new Timer(gameWindowsObserver);
    private static final GameModel model = new GameModel(
            GameType.NOVICE,
            recordManager,
            timer,
            gameWindowsObserver,
            gameResultObserver,
            gameRecordObserver
    );

    private static final NewGameViewController newGameController = new NewGameViewController(model);
    private static final MouseViewController controller = new MouseViewController(model);
    private static final SettingsViewController settingsController = new SettingsViewController(model);

    public static void main(String[] args) {
        SettingsWindow settingsWindow = new SettingsWindow(view);
        gameResultObserver.setNewGameController(newGameController);

        view.setSettingsMenuAction(e -> settingsWindow.setVisible(true));
        view.setHighScoresMenuAction(e -> highScoresWindow.setVisible(true));

        view.setExitMenuAction(e -> view.dispose());
        view.setNewGameMenuAction(e -> newGameController.newGame());
        view.setCellListener(controller);
        settingsWindow.setGameTypeListener(settingsController);


        view.createGameField(9, 9);
        view.setVisible(true);
    }
}
