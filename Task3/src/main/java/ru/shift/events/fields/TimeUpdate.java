package ru.shift.events.fields;

import ru.shift.events.GameEvent;

public record TimeUpdate(int time) implements GameEvent {
}
