package org.example.src.db;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.Statement;

public class PrepareDatabase {
    public static void main(String[] args) throws RemoteException {
        Statement statement;
        ServicesDatabase servicesDatabase = new ServicesDatabase();
        Connection connection = servicesDatabase.connect_to_db("napster_service", "postgres", "postgres");

        try {
            String query = "create table files(id SERIAL, name VARCHAR(200), ip VARCHAR(200), port VARCHAR(200), file VARCHAR(200), status VARCHAR(200), path VARCHAR(200));";
            statement = connection.createStatement();
            statement.executeUpdate(query);
            System.out.println("Table created");
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
