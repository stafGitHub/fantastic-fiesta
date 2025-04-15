package ru.shift.model.events.fields;

import ru.shift.model.events.GameEvent;

public record FlagPlaning(int row , int column , boolean flag) implements GameEvent {
}
