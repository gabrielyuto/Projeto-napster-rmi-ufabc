package org.example.src.services;

import org.example.src.bean.FileMessage;
import org.example.src.entity.Client;

import java.io.*;
import java.net.Socket;
import java.nio.channels.FileChannel;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class ClientService {
    private Scanner scanner = new Scanner(System.in);

    public void join(Client client) throws Exception{
        Registry registry = LocateRegistry.getRegistry();
        ServerService serverService = (ServerService) registry.lookup("rmi://localhost:127.0.0.1/principalServer");

        String result = serverService.joinRequest(client);

        if(!result.isEmpty()){
            System.out.println("Sou peer " + client + ":" + client.getClient_port() + " com arquivos " + client.getFiles());
        }
    }

    public void search(Client client) throws Exception{
        Registry registry = LocateRegistry.getRegistry();
        ServerService serverService = (ServerService) registry.lookup("rmi://localhost:127.0.0.1/principalServer");

        Client resultConsulta = serverService.searchRequest(client.getFile_request());

        System.out.println("Peers com arquivo solicitado: " + resultConsulta.getIp() + ":" + resultConsulta.getClient_port());
    }

    public void download(Client client) throws IOException, ClassNotFoundException {
        Socket socket = new Socket(client.getIp(), client.getDestiny_port());
        DataOutputStream output = new DataOutputStream(socket.getOutputStream());
        output.writeUTF(client.getFiles());

        ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
        FileMessage fileRecived = (FileMessage) input.readObject();

        save(fileRecived, client);
        System.out.println("Arquivo " + fileRecived.getFile() + " baixado com sucesso na pasta " + client.getPath_to_save());

        DataInputStream dataInput = new DataInputStream(socket.getInputStream());
        String string = dataInput.readUTF();
        System.out.println(string);

        input.close();
        output.close();

        socket.close();
    }

    private void save(FileMessage message, Client client) throws IOException {
        long time = System.currentTimeMillis();

        FileInputStream fileInputStream = new FileInputStream(message.getFile());
        FileOutputStream fileOutputStream = new FileOutputStream(client.getPath_to_save() + time + "_" + message.getFile().getName());

        FileChannel fin = fileInputStream.getChannel();
        FileChannel fout = fileOutputStream.getChannel();

        long size = fin.size();

        fin.transferTo(0,size,fout);
    }
}