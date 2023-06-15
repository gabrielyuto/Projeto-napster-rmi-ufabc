package org.example.src.services;

import org.example.src.entity.Cliente;
import org.example.src.entity.NewCliente;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServicoServidor extends Remote {
    public NewCliente searchRequest(String file) throws RemoteException;

    public String joinRequest(NewCliente cliente) throws RemoteException;

    public String update(NewCliente cliente) throws RemoteException;
}
