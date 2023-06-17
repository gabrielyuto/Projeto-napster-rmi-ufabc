package org.example.src.entity;

import java.io.Serializable;

public class Client implements Serializable {
    private String name;
    private String ip;
    private String files;
    private String path_to_save;
    private String file_request;
    private int client_port;
    private int destiny_port;

    public Client(String name, String ip, int client_port, String files, String path_to_save) {
        this.name = name;
        this.ip = ip;
        this.client_port = client_port;
        this.files = files;
        this.path_to_save = path_to_save;
    }

    public Client() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String localhost) {
        this.ip = localhost;
    }

    public int getClient_port() {
        return client_port;
    }

    public void setClient_port(int client_port) {
        this.client_port = client_port;
    }

    public String getFiles() {
        return files;
    }

    public void setFiles(String files) {
        this.files = files;
    }

    public int getDestiny_port() {
        return destiny_port;
    }

    public void setDestiny_port(int destiny_port) {
        this.destiny_port = destiny_port;
    }

    public String getPath_to_save() {
        return path_to_save;
    }

    public void setPath_to_save(String path_to_save) {
        this.path_to_save = path_to_save;
    }

    public String getFile_request() {
        return file_request;
    }

    public void setFile_request(String file_request) {
        this.file_request = file_request;
    }
}
