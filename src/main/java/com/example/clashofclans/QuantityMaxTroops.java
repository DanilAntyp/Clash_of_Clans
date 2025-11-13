package com.example.clashofclans;

import java.io.Serializable;

public class QuantityMaxTroops implements Serializable {
    private int maxValue;


    public QuantityMaxTroops() {}

    public QuantityMaxTroops(int maxValue) {
        this.maxValue = maxValue;
    }

    public int getMaxValue() { return maxValue; }
    public void setMaxValue(int maxValue) { this.maxValue = maxValue; }
}
