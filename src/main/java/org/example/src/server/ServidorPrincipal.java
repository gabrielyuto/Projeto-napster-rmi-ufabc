package org.example.src.server;

import org.example.src.services.ServicoServidor;
import org.example.src.services.ServicoServidorImpl;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ServidorPrincipal {
    public static void main(String[] args) throws Exception{
        ServicoServidor servicoServidor = new ServicoServidorImpl();

        LocateRegistry.createRegistry(1099);

        Registry registry = LocateRegistry.getRegistry();

        registry.rebind("rmi://localhost:127.0.0.1/servidorPrincipal", servicoServidor);
        System.out.println("Servidor principal no ar...");
    }
}
