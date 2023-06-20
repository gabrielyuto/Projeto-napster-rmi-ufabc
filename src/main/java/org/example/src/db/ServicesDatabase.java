package org.example.src.db;

import org.example.src.client.Client;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServicesDatabase {
    public Connection connect_to_db(String dbname, String user, String pass) {
        Connection connection = null;

        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/" + dbname, user, pass);
        } catch (Exception e) {
            System.out.println(e);
        }

        return connection;
    }

    public String insertNewClient(Connection connection, Client client) {
        try {
            client.getFiles().forEach(file -> {
                Statement statement;
                String query = String.format("insert into files(name, ip, port, file, path) values('%s','%s','%s','%s','%s');", client.getName(), client.getIp(), client.getClient_port(), file, client.getLocal_path_files());
                try {
                    statement = connection.createStatement();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                try {
                    statement.executeUpdate(query);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (Exception e) {
            return "NOT_JOIN_OK";
        }
        return "JOIN_OK";
    }

    public List<Client> findClientFiles(Connection connection, Client client) {
        PreparedStatement preparedStatement;
        ResultSet resultSet;

        List<Client> listClientWithFile = new ArrayList<>();

        try {
            preparedStatement = connection.prepareStatement("select * from files where file=?");
            preparedStatement.setString(1, client.getFile_request());
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                Client cliente = new Client();

                cliente.setName(resultSet.getString("name"));
                cliente.setIp(resultSet.getString("ip"));
                cliente.setClient_port(resultSet.getInt("port"));
                cliente.setFile_required_return(resultSet.getString("file"));
                cliente.setDestiny_path_files(resultSet.getString("path"));

                listClientWithFile.add(cliente);
            }
        } catch(SQLException ex){
            return null;
        }

        return listClientWithFile;
    }

    public String updateNewRequestDownload(Connection connection, Client client){
        PreparedStatement preparedStatement;

        try {
            preparedStatement = connection.prepareStatement("update files set status='DOWNLOADED' where port = ? AND file=?");
            preparedStatement.setString(1, String.valueOf(client.getDestiny_port()));
            preparedStatement.setString(2, client.getFile_request());
            preparedStatement.execute();

        } catch (SQLException ex) {
            return "UPDATE_NOT_OK";
        }

        System.out.println("UPDATE_OK");
        return "UPDATE_OK";
    }
}

