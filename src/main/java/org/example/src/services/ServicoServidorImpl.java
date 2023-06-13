package org.example.src.services;

import org.example.src.db.Conexao;
import org.example.src.entity.Cliente;
import org.example.src.entity.NewCliente;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;

public class ServicoServidorImpl extends UnicastRemoteObject implements ServicoServidor {
    public ServicoServidorImpl() throws RemoteException {
        super();
    }

    @Override
    public NewCliente obterNome(String file) throws RemoteException {
        Conexao conexao = new Conexao();
        Connection connection = conexao.connect_to_db("napster_service", "postgres", "postgres");
        NewCliente clientFiles = conexao.findClientFiles(connection, file);

        return clientFiles;
    }

    @Override
    public String cadastrarNovoUsuario(NewCliente cliente) throws RemoteException {
        Conexao conexao = new Conexao();
        Connection connection = conexao.connect_to_db("napster_service", "postgres", "postgres");
        conexao.insertNewClient(connection, cliente);

        return "JOIN_OK";
    }
}
