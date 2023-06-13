package org.example.src.client;

import org.example.src.entity.NewCliente;
import org.example.src.services.ServicoServidor;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ClientX {
    public static void main(String[] args) throws  Exception{
//        conectaComServidorPrincipal("Pedro", "127.0.0.2", 9000,"arquivo1");
        requisicaoCliente();
    }

    private static void conectaComServidorPrincipal(String nameClient, String ip, int port, String files) throws Exception{
        Registry registry = LocateRegistry.getRegistry();

        ServicoServidor servicoServidor = (ServicoServidor) registry.lookup("rmi://localhost:127.0.0.1/servidorPrincipal");

        NewCliente newCliente = new NewCliente();
        newCliente.setName(nameClient);
        newCliente.setFiles(files);
        newCliente.setIp(ip);
        newCliente.setPort(port);

        String result = servicoServidor.cadastrarNovoUsuario(newCliente);
    }

    private static void requisicaoCliente() throws Exception{
        Registry registry = LocateRegistry.getRegistry();
        ServicoServidor servicoServidor = (ServicoServidor) registry.lookup("rmi://localhost:127.0.0.1/servidorPrincipal");

        BufferedReader entryFile = new BufferedReader(new InputStreamReader(System.in));
        String file = entryFile.readLine(); //BLOCKING

        NewCliente resultConsulta = servicoServidor.obterNome(file);

        System.out.println("Quem possui o arquivo é " + resultConsulta.getName() + " " + resultConsulta.getIp() + " " + resultConsulta.getPort());

        Socket socket = new Socket(resultConsulta.getIp(), resultConsulta.getPort());

        OutputStream outputStream = socket.getOutputStream();
        DataOutputStream writer = new DataOutputStream(outputStream);

        InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());

        BufferedReader reader = new BufferedReader(inputStreamReader);
        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));

        String texto = inFromUser.readLine(); //BLOCKING
        System.out.print("Solicite o arquivo inserindo o nome do arquivo: ");

        writer.writeBytes(texto +"\n");

        String response = reader.readLine(); //BLOCKING
        System.out.println("DoServidor:" + response);
    }
}