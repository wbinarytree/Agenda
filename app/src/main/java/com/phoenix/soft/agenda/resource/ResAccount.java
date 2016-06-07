package com.phoenix.soft.agenda.resource;

/**
 * Created by phoenix on 16/6/7.
 */

public class ResAccount {
    public String getName() {
        return name;
    }

    public int getBal() {
        return bal;
    }

    public ResAccount(String name, int bal) {
        this.name = name;
        this.bal = bal;
    }

    private String name;
    private int bal;
}
