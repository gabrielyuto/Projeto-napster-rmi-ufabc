package org.example.src.server;

import org.example.src.services.ServerService;
import org.example.src.services.ServerServiceImpl;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class PrincipalServer {
    public static void main(String[] args) throws Exception{
        ServerService serverService = new ServerServiceImpl();

        LocateRegistry.createRegistry(1099);

        Registry registry = LocateRegistry.getRegistry();

        registry.rebind("rmi://localhost:127.0.0.1/principalServer", serverService);
        System.out.println("Principal server on...");
    }
}
