package com.apartmentrental.data;
import com.apartmentrental.data.interfaces.IDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgresDB implements IDB {
    private String host;
    private String username;
    private String password;
    private String dbName;
    private static PostgresDB instance = null;
    private Connection connection;

    private PostgresDB(String host, String username, String password, String dbName) {
        setHost(host);
        setUsername(username);
        setPassword(password);
        setDbName(dbName);
    }


    public static synchronized PostgresDB getInstance() {
        if (instance == null) {
            instance = new PostgresDB("localhost:5432", "postgres", "Nurislam2006", "RentApartments");
        }
        return instance;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    @Override
    public synchronized Connection getConnection() {
        String connectionUrl = "jdbc:postgresql://" + host + "/" + dbName;
        System.out.println("Attempting to connect to: " + connectionUrl);
        try {
            if (connection != null && !connection.isClosed()) {
                System.out.println("Reusing existing connection.");
                return connection;
            }
            System.out.println("Loading JDBC driver...");
            Class.forName("org.postgresql.Driver");
            System.out.println("Creating new connection...");
            connection = DriverManager.getConnection(connectionUrl, username, password);
            System.out.println("Connection established successfully.");
            return connection;

        } catch (ClassNotFoundException e) {
            System.out.println("PostgreSQL JDBC Driver not found in classpath: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console: " + e.getMessage());
            e.printStackTrace();
        }
        System.out.println("Failed to get connection, returning null.");
        return null;
    }
    @Override
    public void close() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.println("Failed to close connection: " + e.getMessage());
            }
        }
    }
}
