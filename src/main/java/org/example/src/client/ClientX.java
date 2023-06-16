package org.example.src.client;

import org.example.src.bean.FileMessage;
import org.example.src.entity.Cliente;
import org.example.src.services.ServerService;
import org.example.src.thread.ListenerThread;

import java.io.*;
import java.net.Socket;
import java.nio.channels.FileChannel;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class ClientX {
    private Scanner scanner = new Scanner(System.in);
    private String name = "Gabriel";
    private String localhost = "localhost";
    private int client_port = 9001;

    public ClientX() throws Exception {
        join(name, localhost, client_port,"Aula.mp4");

        System.out.println("SEARCH: ");
        String fileRequest = scanner.nextLine();

        search(fileRequest);

        System.out.println("1 <- REQUEST FILE | 2 <- CANCELL");
        int condition = scanner.nextInt();

        if(condition == 1){
            System.out.println("Which port do you want to request the file for?");
            int destiny_port = scanner.nextInt();

            download(localhost, destiny_port, fileRequest);

        } else if(condition == 2){
            System.exit(0);
        }
    }

    private static void join(String nameClient, String ip, int port, String files) throws Exception{
        Registry registry = LocateRegistry.getRegistry();
        ServerService serverService = (ServerService) registry.lookup("rmi://localhost:127.0.0.1/principalServer");

        Cliente cliente = new Cliente();
        cliente.setName(nameClient);
        cliente.setFiles(files);
        cliente.setIp(ip);
        cliente.setPort(port);

        serverService.joinRequest(cliente);
    }

    private Cliente search(String fileRequest) throws Exception{
        Registry registry = LocateRegistry.getRegistry();
        ServerService serverService = (ServerService) registry.lookup("rmi://localhost:127.0.0.1/principalServer");

        Cliente resultConsulta = serverService.searchRequest(fileRequest);

        System.out.println("Who owns the file is NAME: " + resultConsulta.getName() + " HOST: " + resultConsulta.getIp() + " IP: " + resultConsulta.getPort());

        return resultConsulta;
    }

    private void download(String host, int destiny_port, String fileRequest) throws IOException, ClassNotFoundException {
        Socket socket = new Socket(host, destiny_port);
        DataOutputStream output = new DataOutputStream(socket.getOutputStream());
        output.writeUTF(fileRequest);
        System.out.println("DOWNLOAD...");

        ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
        FileMessage fileRecived = (FileMessage) input.readObject();
        System.out.println("The file arrived!");

        save(fileRecived);
        System.out.println("Saved!");

        DataInputStream dataInput = new DataInputStream(socket.getInputStream());
        String string = dataInput.readUTF();
        System.out.println(string);

        input.close();
        output.close();

        socket.close();
    }

    private void save(FileMessage message) throws IOException {
        long time = System.currentTimeMillis();

        FileInputStream fileInputStream = new FileInputStream(message.getFile());
        FileOutputStream fileOutputStream = new FileOutputStream("/home/yuto/Documentos/arquivosClienteX/" + time + "_" + message.getFile().getName());

        FileChannel fin = fileInputStream.getChannel();
        FileChannel fout = fileOutputStream.getChannel();

        long size = fin.size();

        fin.transferTo(0,size,fout);
    }

    public static void main(String[] args){
        try{
            new Thread(new ListenerThread(9001)).start();

            new ClientX();

        } catch (IOException e){
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}