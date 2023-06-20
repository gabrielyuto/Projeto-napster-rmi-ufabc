package org.example.src.client;

import java.io.Serializable;
import java.util.List;

public class Client implements Serializable {
    private String name;
    private String ip;
    private List<String> files;
    private String file_request;
    private int client_port;
    private int destiny_port;
    private String file_required_return;
    private String destiny_path_files;
    private String local_path_files;


    public Client(String name, String ip, int client_port, List<String> files, String local_path_files) {
        this.name = name;
        this.ip = ip;
        this.client_port = client_port;
        this.files = files;
        this.local_path_files = local_path_files;
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

    public List<String> getFiles() {
        return files;
    }

    public void setFiles(List<String> files) {
        this.files = files;
    }

    public int getDestiny_port() {
        return destiny_port;
    }

    public void setDestiny_port(int destiny_port) {
        this.destiny_port = destiny_port;
    }

    public String getFile_request() {
        return file_request;
    }

    public void setFile_request(String file_request) {
        this.file_request = file_request;
    }

    public String getFile_required_return() {
        return file_required_return;
    }

    public void setFile_required_return(String file_required_return) {
        this.file_required_return = file_required_return;
    }

    public String getDestiny_path_files() {
        return destiny_path_files;
    }

    public void setDestiny_path_files(String destiny_path_files) {
        this.destiny_path_files = destiny_path_files;
    }

    public String getLocal_path_files() {
        return local_path_files;
    }

    public void setLocal_path_files(String local_path_files) {
        this.local_path_files = local_path_files;
    }
}
