package org.example.src.services;

import org.example.src.entity.Cliente;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerService extends Remote {
    public Cliente searchRequest(String file) throws RemoteException;

    public String joinRequest(Cliente cliente) throws RemoteException;

    public String updateRequest(String name) throws RemoteException;
}
