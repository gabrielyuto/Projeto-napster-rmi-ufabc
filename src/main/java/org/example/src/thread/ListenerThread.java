package org.example.src.thread;

import org.example.src.bean.FileMessage;
import org.example.src.client.Client;

import javax.swing.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ListenerThread extends Thread {
    private Socket socket = null;
    private int port;

    public ListenerThread(int port){
        this.port = port;
    }

    public void run() {
        try{
            ServerSocket serverSocket = new ServerSocket(port);

            while(true){
                System.out.println("Client server awaiting connection...");
                Socket socket = serverSocket.accept(); //BLOCKING
                System.out.println("Connection accepted!");

                System.out.println("Client " + socket.getInetAddress().getHostAddress() + " connected");

                ObjectInputStream inputServer = new ObjectInputStream(socket.getInputStream());
                Client clientArrived = (Client) inputServer.readObject();

                System.out.println("The client is requesting the file: " + clientArrived.getFile_request());

                FileMessage message = send(clientArrived.getDestiny_path_files(), clientArrived.getFile_request());

                ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
                output.writeObject(message);

                System.out.println("FILE SENT TO CLIENT");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private FileMessage send(String path, String fileRequested) throws IOException {
        File file = new File(path + "/" + fileRequested);

        FileMessage fileMessage = new FileMessage(file);

        return fileMessage;


//        byte[] fileDeserialized = new byte[0];
//
//        if (Files.isDirectory(directory) && Files.exists(file)) {
//            try {
//                fileDeserialized = Files.readAllBytes(file);
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        } else {
//            System.out.println("Diretorio nao existe");
//        }
//
//        return fileDeserialized;
    }
}

//
//    JFileChooser fileChooser = new JFileChooser();
//    File file = fileChooser.getSelectedFile();
//
//
//    int opt = fileChooser.showOpenDialog(null);
//
//        if(opt == JFileChooser.APPROVE_OPTION){
//                File file = fileChooser.getSelectedFile();
//                FileMessage fileMessage = new FileMessage(file);
//
//                return fileMessage;
//                }
//                return null;