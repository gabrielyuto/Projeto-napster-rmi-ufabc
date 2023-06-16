package org.example.src.db;

import org.example.src.entity.Cliente;

import java.sql.*;

public class ServicesDatabase {
    public Connection connect_to_db(String dbname, String user, String pass) {
        Connection connection = null;

        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/" + dbname, user, pass);

            if (connection != null) {
                System.out.println("Connection success!");
            } else {
                System.out.println("Failed connection!");
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return connection;
    }

    public String insertNewClient(Connection connection, Cliente cliente) {
        Statement statement;

        try {
            String query = String.format("insert into files(name, ip, port, file) values('%s','%s','%s','%s');", cliente.getName(), cliente.getIp(), cliente.getPort(), cliente.getFiles());
            statement = connection.createStatement();
            statement.executeUpdate(query);
            System.out.println("New user registered in database");
        } catch (Exception e) {
            return "NOT_JOIN_OK";
        }
        return "JOIN_OK";
    }

    public Cliente findClientFiles(Connection connection, String file) {
        PreparedStatement preparedStatement;
        ResultSet resultSet;

        Cliente cliente = new Cliente();

        try {
            preparedStatement = connection.prepareStatement("select * from files where file=?");
            preparedStatement.setString(1, file);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                cliente.setName(resultSet.getString("name"));
                cliente.setIp(resultSet.getString("ip"));
                cliente.setPort(resultSet.getInt("port"));
                cliente.setFiles(resultSet.getString("file"));
            }
        } catch(SQLException ex){
            return null;
        }

        return cliente;
    }

    public String updateNewRequestDownload(Connection connection, String name){
        PreparedStatement preparedStatement;

        try {
            preparedStatement = connection.prepareStatement("update files set status = 'DOWNLOADED' where name = ?");
            preparedStatement.setString(1, name);
            preparedStatement.executeQuery();

            System.out.println("UPDATE_OK");
        } catch (Exception e) {
            return "NOT_UPDATED";
        }

        return "UPDATE_OK";
    }
}

