package org.example.src.services;

import org.example.src.bean.FileMessage;
import org.example.src.client.Client;

import java.io.*;
import java.net.Socket;
import java.nio.channels.FileChannel;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.Scanner;

//    SERVICOS DOS CLIENTS
public class ClientService {
    private Scanner scanner = new Scanner(System.in);

    //    ENVIA REQUISICAO JOIN PARA O SERVIDOR RMI
    public void join(Client client) throws Exception{
        Registry registry = LocateRegistry.getRegistry();
        ServerService serverService = (ServerService) registry.lookup("rmi://localhost:127.0.0.1/principalServer");

        String result = serverService.joinRequest(client);

        if(!result.isEmpty()){
            System.out.println("Sou peer " + client.getIp() + ":" + client.getClient_port() + " com arquivos " + client.getFiles());
        }
    }
    //    ENVIA REQUISICAO SEARCH PARA O SERVIDOR RMI
    public List<Client> search(Client client) throws Exception{
        Registry registry = LocateRegistry.getRegistry();
        ServerService serverService = (ServerService) registry.lookup("rmi://localhost:127.0.0.1/principalServer");

        List<Client> resultConsulta = serverService.searchRequest(client);

        if(resultConsulta.isEmpty()){
            System.out.println("File not found");
        }

        resultConsulta.forEach(clientFromList -> {
            System.out.println("Peers com arquivo solicitado: " + clientFromList.getIp() + ":" + clientFromList.getClient_port());
        });

        return resultConsulta;
    }

    //    ENVIA REQUISICAO DOWNLOAD PARA OUTRO PER
    public void download(Client client) throws Exception {
        Socket socket = new Socket(client.getIp(), client.getDestiny_port());
        ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
        output.writeObject(client);

        ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
        FileMessage fileReceived = (FileMessage) input.readObject();

        save(fileReceived, client);
        System.out.println("Arquivo " + fileReceived.getFile() + " baixado com sucesso na pasta " + client.getLocal_path_files());

        update(client);

        input.close();
        output.close();

        socket.close();
    }

    //    METODO SAVE INVOCADO PELO REQUISICAO DOWNLOAD
    private void save(FileMessage message, Client client) throws IOException {
        long time = System.currentTimeMillis();

        FileInputStream fileInputStream = new FileInputStream(message.getFile());
        FileOutputStream fileOutputStream = new FileOutputStream(message.getFile().getName());

        FileChannel fin = fileInputStream.getChannel();
        FileChannel fout = fileOutputStream.getChannel();

        long size = fin.size();

        fin.transferTo(0,size,fout);
    }

    //    ENVIA REQUISICAO UPDATE PARA INFORMAR O SERVIDOR RMI SOBRE O DOWNLOAD
    private void update(Client client) throws Exception {
        Registry registry = LocateRegistry.getRegistry();
        ServerService serverService = (ServerService) registry.lookup("rmi://localhost:127.0.0.1/principalServer");

        String result = serverService.updateRequest(client);

        System.out.println(result);
    }
}