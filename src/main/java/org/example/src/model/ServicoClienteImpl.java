package org.example.src.model;

import org.example.src.db.Conexao;
import org.example.src.model.entity.Cliente;
import org.example.src.model.entity.NewCliente;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;

public class ServicoClienteImpl extends UnicastRemoteObject implements ServicoCliente {
    public ServicoClienteImpl() throws RemoteException {
        super();
    }

    @Override
    public Cliente obterNome(String file) throws RemoteException {
        Conexao conexao = new Conexao();
        Connection connection = conexao.connect_to_db("napster_service", "postgres", "postgres");
        Cliente clientFiles = conexao.findClientFiles(connection, file);

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
