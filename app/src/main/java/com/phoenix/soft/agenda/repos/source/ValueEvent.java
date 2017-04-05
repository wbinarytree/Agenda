package com.phoenix.soft.agenda.repos.source;

/**
 * Created by yaoda on 05/04/17.
 */

public class ValueEvent<T> {
    private T value;
    private Notification type;

    public ValueEvent(T value, Notification type) {
        this.value = value;
        this.type = type;
    }

    public Notification getType() {
        return type;
    }

    public void setType(Notification type) {
        this.type = type;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}
