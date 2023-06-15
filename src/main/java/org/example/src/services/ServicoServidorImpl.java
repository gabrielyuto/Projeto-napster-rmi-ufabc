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
    public NewCliente searchRequest(String file) throws RemoteException {
        Conexao conexao = new Conexao();
        Connection connection = conexao.connect_to_db("napster_service", "postgres", "postgres");
        NewCliente clientFiles = conexao.findClientFiles(connection, file);

        return clientFiles;
    }

    @Override
    public String joinRequest(NewCliente cliente) throws RemoteException {
        Conexao conexao = new Conexao();
        Connection connection = conexao.connect_to_db("napster_service", "postgres", "postgres");
        String result = conexao.insertNewClient(connection, cliente);

        return result;
    }

    @Override
    public String update(NewCliente cliente) throws RemoteException {
        Conexao conexao = new Conexao();
        Connection connection = conexao.connect_to_db("napster_service", "postgres", "postgres");
        String result = conexao.updateNewRequestDownload(connection, cliente);

        return result;
    }
}
