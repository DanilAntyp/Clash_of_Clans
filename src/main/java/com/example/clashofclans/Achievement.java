package com.example.clashofclans;

import java.io.Serializable;

public class Achievement implements Serializable {
    private String name;
    private String description;
    private String type;
    private String reward;

    public Achievement(String name, String description, String type, String reward) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.reward = reward;
    }

    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public String getType() {
        return type;
    }
    public String getReward() {
        return reward;
    }
}
