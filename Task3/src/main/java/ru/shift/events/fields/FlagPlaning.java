package ru.shift.events.fields;

import ru.shift.events.GameEvent;

public record FlagPlaning(int row , int column , boolean flag) implements GameEvent {
}
