package org.example;

import org.example.src.model.ServicoCliente;
import org.example.src.model.ServicoClienteImpl;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ServidorPrincipal {
    public static void main(String[] args) throws Exception{
        ServicoCliente servicoCliente = new ServicoClienteImpl();

        LocateRegistry.createRegistry(1099);

        Registry registry = LocateRegistry.getRegistry();

        registry.rebind("rmi://localhost:127.0.0.1/servico", servicoCliente);
        System.out.println("Servidor no ar...");
    }
}
