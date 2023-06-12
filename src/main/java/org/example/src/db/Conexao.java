package org.example.src.db;

import org.example.src.model.entity.Cliente;
import org.example.src.model.entity.NewCliente;

import java.sql.*;

public class Conexao {
    public Connection connect_to_db(String dbname, String user, String pass) {
        Connection connection = null;

        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/" + dbname, user, pass);

            if (connection != null) {
                System.out.println("Conexao realizada com sucesso!");
            } else {
                System.out.println("Conexao não realizada!");
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return connection;
    }

    public void createTable(Connection connection, String table_name) {
        Statement statement;

        try {
            String query = "create table " + table_name + "(id SERIAL, name VARCHAR(200), file VARCHAR(200));";
            statement = connection.createStatement();
            statement.executeUpdate(query);
            System.out.println("Table created");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void insertNewClient(Connection connection, NewCliente newCliente) {
        Statement statement;

        try {
            String query = String.format("insert into files(name, file) values('%s','%s');", newCliente.getName(), newCliente.getFiles());
            statement = connection.createStatement();
            statement.executeUpdate(query);
            System.out.println("Novo usuario cadastrando com seus arquivos");
        } catch (Exception e) {
            System.out.println(e);
        }
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
            }
        } catch(SQLException ex){
            return null;
        }

        return cliente;
    }
}

