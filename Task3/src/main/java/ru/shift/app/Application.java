package ru.shift.app;

import ru.shift.controller.NewGameController;
import ru.shift.controller.SettingsController;
import ru.shift.model.GameType;
import ru.shift.model.records.RecordController;
import ru.shift.model.records.RecordManager;
import ru.shift.model.Timer;
import ru.shift.view.ViewObserver;

import ru.shift.controller.MouseController;
import ru.shift.model.GameModel;
import ru.shift.view.windows.HighScoresWindow;
import ru.shift.view.windows.MainWindow;
import ru.shift.view.windows.SettingsWindow;

public class Application {
    private static final MainWindow view = new MainWindow();
    private static final RecordManager recordManager = new RecordManager();
    private static final RecordController recordController = new RecordController(recordManager);
    private static final ViewObserver viewObserver = new ViewObserver(view , recordManager , recordController);
    private static final Timer timer = new Timer(viewObserver);
    private static final GameModel model = new GameModel(viewObserver,GameType.NOVICE,recordManager,timer);
    private static final NewGameController newGameController = new NewGameController(model);
    private static final MouseController controller = new MouseController(model);
    private static final SettingsController settingsController = new SettingsController(model);

    public static void main(String[] args) {
        SettingsWindow settingsWindow = new SettingsWindow(view);
        HighScoresWindow highScoresWindow = new HighScoresWindow(view);
        viewObserver.setNewGameController(newGameController);


        view.setHighScoresMenuAction(e -> highScoresWindow.setVisible(true));
        view.setExitMenuAction(e -> view.dispose());
        view.setSettingsMenuAction(e -> settingsWindow.setVisible(true));
        view.setNewGameMenuAction(e -> newGameController.newGame());

        view.setCellListener(controller);
        settingsWindow.setGameTypeListener(settingsController);

        view.createGameField(9, 9);
        view.setVisible(true);
    }
}
