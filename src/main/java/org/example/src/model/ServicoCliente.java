package org.example.src.model;

import org.example.src.model.entity.Cliente;
import org.example.src.model.entity.NewCliente;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServicoCliente extends Remote {
    public Cliente obterNome(String file) throws RemoteException;

    public String cadastrarNovoUsuario(NewCliente cliente) throws RemoteException;
}
