package ru.shift.view.observers.game.status;

import ru.shift.events.GameEvent;
import ru.shift.events.Observer;
import ru.shift.events.Publisher;
import ru.shift.model.events.fields.UpdateTheCell;
import ru.shift.model.events.game.status.GameOver;
import ru.shift.model.filed.Field;

import java.util.ArrayList;
import java.util.List;


public class GameOverObserver extends Observer implements Publisher {
    private final Field fields;
    private List<Observer> observers = new ArrayList<>();

    public GameOverObserver(Field fields,Publisher... publisher) {
        super(publisher);
        this.fields = fields;
    }

    @Override
    public void onGameEvent(GameEvent gameEvent) {
        if (gameEvent instanceof GameOver){
            var cellOutput = fields.revealAllMines();
            notifyListeners(new UpdateTheCell(cellOutput));
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
}
