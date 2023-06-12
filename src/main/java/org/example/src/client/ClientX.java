package org.example.src.client;

import org.example.src.model.ServicoCliente;
import org.example.src.model.entity.NewCliente;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ClientX {
    public static void main(String[] args) throws  Exception{
        Registry registry = LocateRegistry.getRegistry();
        ServicoCliente servicoCliente = (ServicoCliente) registry.lookup("rmi://localhost:127.0.0.1/servico");

        NewCliente newCliente = new NewCliente();
        newCliente.setName("Marcela");
        newCliente.setFiles("ArquivoBB");

        String result = servicoCliente.cadastrarNovoUsuario(newCliente);
//        Cliente cliente = servicoCliente.obterNome("arquivoY");

        System.out.println(result);
    }
}