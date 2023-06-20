package org.example.src;

import org.example.src.services.ServerService;
import org.example.src.services.ServerServiceImpl;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class PrincipalServer {

    //    INICIALIZACAO DO SERVIDOR RMI
    //    -> Captura inicialmente o IP e a porta do Registry
    //    -> Porta default do registry = 1099
    public static void main(String[] args) throws Exception{
        ServerService serverService = new ServerServiceImpl();

        LocateRegistry.createRegistry(1099);

        Registry registry = LocateRegistry.getRegistry();

        registry.rebind("rmi://localhost:127.0.0.1/principalServer", serverService);
    }
}
