package org.example.src.client;

import org.example.src.entity.NewCliente;
import org.example.src.services.ServicoServidor;
import sun.rmi.runtime.NewThreadAction;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ClientX {
    public static void main(String[] args) throws  Exception{
        join("Pedro", "127.0.0.1", 9002,"arquivo1");
        NewCliente resultSearch = search();
        requisicaoCliente(resultSearch);
    }

    private static void join(String nameClient, String ip, int port, String files) throws Exception{
        Registry registry = LocateRegistry.getRegistry();

        ServicoServidor servicoServidor = (ServicoServidor) registry.lookup("rmi://localhost:127.0.0.1/servidorPrincipal");

        NewCliente newCliente = new NewCliente();
        newCliente.setName(nameClient);
        newCliente.setFiles(files);
        newCliente.setIp(ip);
        newCliente.setPort(port);

        String result = servicoServidor.joinRequest(newCliente);
    }

    public static NewCliente search() throws Exception{
        Registry registry = LocateRegistry.getRegistry();
        ServicoServidor servicoServidor = (ServicoServidor) registry.lookup("rmi://localhost:127.0.0.1/servidorPrincipal");

        BufferedReader entryFile = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Insira o arquivo que deseja buscar: ");
        String file = entryFile.readLine(); //BLOCKING

        NewCliente resultConsulta = servicoServidor.searchRequest(file);

        System.out.println("Quem possui o arquivo é " + resultConsulta.getName() + " " + resultConsulta.getIp() + " " + resultConsulta.getPort());

        return resultConsulta;
    }

    private static void requisicaoCliente(NewCliente resultConsulta) throws Exception{
        Socket socket = new Socket(resultConsulta.getIp(), resultConsulta.getPort());

        OutputStream outputStream = socket.getOutputStream();
        DataOutputStream writer = new DataOutputStream(outputStream);

        InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());

        BufferedReader reader = new BufferedReader(inputStreamReader);
        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));

        System.out.print("Solicite o arquivo inserindo o nome do arquivo: ");
        String texto = inFromUser.readLine(); //BLOCKING

        writer.writeBytes(texto +"\n");

        Registry registry = LocateRegistry.getRegistry();
        ServicoServidor servicoServidor = (ServicoServidor) registry.lookup("rmi://localhost:127.0.0.1/servidorPrincipal");

        String updateResult = servicoServidor.update(resultConsulta);
        System.out.println(updateResult);

        String response = reader.readLine(); //BLOCKING
        System.out.println("DoServidor:" + response);
    }
}