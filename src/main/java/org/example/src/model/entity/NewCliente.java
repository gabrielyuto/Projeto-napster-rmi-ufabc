package org.example.src.model.entity;

import java.io.Serializable;

public class NewCliente implements Serializable {
    public String name;
    public String files;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFiles() {
        return files;
    }

    public void setFiles(String files) {
        this.files = files;
    }
}
