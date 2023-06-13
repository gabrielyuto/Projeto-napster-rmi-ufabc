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

public class ClientY {
    public static void main(String[] args) throws Exception{
        conectaComServidorPrincipal();
        requisicaoCliente();
    }

    private static void conectaComServidorPrincipal() throws Exception{
        Registry registry = LocateRegistry.getRegistry();

        ServicoServidor servicoServidor = (ServicoServidor) registry.lookup("rmi://localhost:127.0.0.1/servico");

        NewCliente newCliente = new NewCliente();
        newCliente.setName("Ana");
        newCliente.setFiles("ArquivoX");

        String result = servicoServidor.cadastrarNovoUsuario(newCliente);
    }

    private static void requisicaoCliente() throws Exception{
        Socket socket = new Socket("127.0.0.3", 9001);

        OutputStream outputStream = socket.getOutputStream();
        DataOutputStream writer = new DataOutputStream(outputStream);

        InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());
        BufferedReader reader = new BufferedReader(inputStreamReader);

        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));

        String texto = inFromUser.readLine(); //BLOCKING

        writer.writeBytes(texto +"\n");

        String response = reader.readLine(); //BLOCKING
        System.out.println("DoServidor:" + response);
    }
}