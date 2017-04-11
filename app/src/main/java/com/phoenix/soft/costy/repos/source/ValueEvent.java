package com.phoenix.soft.costy.repos.source;

/**
 * Created by yaoda on 05/04/17.
 */

public class ValueEvent<T> {
    private T value;
    private EventType type;

    public ValueEvent(T value, EventType type) {
        this.value = value;
        this.type = type;
    }

    public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
        this.type = type;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}
