package ru.shift.model.events.fields;

import ru.shift.model.events.GameEvent;

public record TimeUpdate(int time) implements GameEvent {
}
