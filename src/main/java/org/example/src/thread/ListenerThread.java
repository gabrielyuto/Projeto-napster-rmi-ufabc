package org.example.src.thread;

import org.example.src.bean.FileMessage;

import javax.swing.*;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

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

                DataInputStream inputServer = new DataInputStream(socket.getInputStream());
                String fileRequest = inputServer.readUTF();

                System.out.println("The client is requesting the file: " + fileRequest);

                FileMessage fileToSend = send();

                ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
                output.writeObject(fileToSend);

                System.out.println("FILE SENT TO CLIENT");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private FileMessage send() throws IOException {
        JFileChooser fileChooser = new JFileChooser();

        int opt = fileChooser.showOpenDialog(null);

        if(opt == JFileChooser.APPROVE_OPTION){
            File file = fileChooser.getSelectedFile();
            FileMessage fileMessage = new FileMessage(file);

            return fileMessage;
        }
        return null;
    }
}
