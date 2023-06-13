package org.example.src.db;

import org.example.src.services.ServicoServidor;
import org.example.src.services.ServicoServidorImpl;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.Statement;

public class PrepareDatabase {
    public static void main(String[] args) throws RemoteException {
        Statement statement;
        Conexao conexao = new Conexao();
        Connection connection = conexao.connect_to_db("napster_service", "postgres", "postgres");

        try {
            String query = "create table files(id SERIAL, name VARCHAR(200), ip VARCHAR(200), port VARCHAR(200), file VARCHAR(200));";
            statement = connection.createStatement();
            statement.executeUpdate(query);
            System.out.println("Table created");
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
