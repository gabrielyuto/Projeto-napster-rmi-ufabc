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
                System.out.println("Esperando conexão...");
                Socket socket = serverSocket.accept(); //BLOCKING
                System.out.println("Conexão aceita!");

                System.out.println("Cliente " + socket.getInetAddress().getHostAddress() + " conectado");

                DataInputStream inputServer = new DataInputStream(socket.getInputStream());
                String fileRequest = inputServer.readUTF();

                System.out.println("O cliente deseja o arquivo: " + fileRequest);

                FileMessage fileToSend = send();

                ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
                output.writeObject(fileToSend);
            }
        } catch (IOException e) {
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
