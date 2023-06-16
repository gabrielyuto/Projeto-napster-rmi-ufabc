package org.example.src.client;

import org.example.src.bean.FileMessage;
import org.example.src.entity.Cliente;
import org.example.src.services.ServicoServidor;
import org.example.src.thread.ListenerThread;

import java.io.*;
import java.net.Socket;
import java.nio.channels.FileChannel;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class ClientX {
    private String name = "Gabriel";
    private String localhost = "localhost";
    private Scanner scanner = new Scanner(System.in);

    public ClientX() throws Exception {
        this.name = name;

//        join(name, localhost, port,"arquivo1");

        System.out.println("Insira o arquivo que deseja buscar: ");
        String fileRequest = scanner.nextLine();

        Cliente resultSearch = search(fileRequest);

        System.out.println("Deseja: 1 - solicitar arquivo | 2 - cancelar");
        int condition = scanner.nextInt();

        if(condition == 1){
            System.out.println("Deseja solicitar o arquivo para qual porta? ");
            int destiny_port = scanner.nextInt();

            requestFile(localhost, destiny_port, fileRequest);

        } else if(condition == 2){
            System.exit(0);
        }
    }

    private static void join(String nameClient, String ip, int port, String files) throws Exception{
        Registry registry = LocateRegistry.getRegistry();
        ServicoServidor servicoServidor = (ServicoServidor) registry.lookup("rmi://localhost:127.0.0.1/servidorPrincipal");

        Cliente cliente = new Cliente();
        cliente.setName(nameClient);
        cliente.setFiles(files);
        cliente.setIp(ip);
        cliente.setPort(port);

        String result = servicoServidor.joinRequest(cliente);
    }

    private Cliente search(String fileRequest) throws Exception{
        Registry registry = LocateRegistry.getRegistry();
        ServicoServidor servicoServidor = (ServicoServidor) registry.lookup("rmi://localhost:127.0.0.1/servidorPrincipal");

        Cliente resultConsulta = servicoServidor.searchRequest(fileRequest);

        System.out.println("Quem possui o arquivo é " + resultConsulta.getName() + " " + resultConsulta.getIp() + " " + resultConsulta.getPort());

        return resultConsulta;
    }

    private void requestFile(String host, int destiny_port, String fileRequest) throws IOException, ClassNotFoundException {
        Socket socket = new Socket(host, destiny_port);
        DataOutputStream output = new DataOutputStream(socket.getOutputStream());
        output.writeUTF(fileRequest);

        ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
        FileMessage fileRecived = (FileMessage) input.readObject();
        System.out.println("Voce recebeu o arquivo.");

        salvar(fileRecived);

        System.out.println("Arquivo salvo!");

        input.close();
        output.close();

        socket.close();
    }

    private void updatePrincipalServerWithDowload(Cliente resultConsulta) throws Exception{
        Registry registry = LocateRegistry.getRegistry();
        ServicoServidor servicoServidor = (ServicoServidor) registry.lookup("rmi://localhost:127.0.0.1/servidorPrincipal");

        String updateResult = servicoServidor.updateRequest(resultConsulta);
        System.out.println(updateResult);
    }

    private void salvar(FileMessage message) throws IOException {
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