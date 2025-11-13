package com.example.clashofclans;

import java.io.Serializable;

public class Quantity implements Serializable {
    private int value;

    public Quantity() {}

    public Quantity(int value) {
        this.value = value;
    }

    public int getValue() { return value; }
    public void setValue(int value) { this.value = value; }
}
