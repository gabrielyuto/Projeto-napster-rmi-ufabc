package org.example.src.model.entity;

import java.io.Serializable;

public class Cliente implements Serializable {
    public String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
