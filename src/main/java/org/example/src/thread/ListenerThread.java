package org.example.src.thread;

import org.example.src.bean.FileMessage;
import org.example.src.client.Client;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

// CRIA UMA THREAD PARA CADA CLIENTE
// -> O objetivo é criar um servidor para cada cliente, que escuta todas as requisiçẽs de solicitação de arquivos
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
                // Aguarda a solicitacao de download de um arquivo
                Socket socket = serverSocket.accept(); //BLOCKING

                System.out.println("Client " + socket.getInetAddress().getHostAddress() + " connected");

                ObjectInputStream inputServer = new ObjectInputStream(socket.getInputStream());
                Client clientArrived = (Client) inputServer.readObject();

                // Busca pelo arquivo atraves do método send implementado, e devolve para o cliente.
                FileMessage message = send(clientArrived.getDestiny_path_files(), clientArrived.getFile_request());

                ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
                output.writeObject(message);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private FileMessage send(String path, String fileRequested) throws IOException {
        File file = new File(path + "/" + fileRequested);

        FileMessage fileMessage = new FileMessage(file);

        if(fileMessage == null){
            System.out.println("File not found");
            return null;
        }

        return fileMessage;
    }
}