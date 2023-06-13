package org.example.src.services;

import org.example.src.entity.Cliente;
import org.example.src.entity.NewCliente;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServicoServidor extends Remote {
    public NewCliente obterNome(String file) throws RemoteException;

    public String cadastrarNovoUsuario(NewCliente cliente) throws RemoteException;
}
