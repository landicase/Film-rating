package com.epam.rating.pool;

import com.epam.rating.pool.config.ConfigFactory;
import com.epam.rating.pool.config.DatabaseConfig;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    private static final String DATABASE_PROPERTIES_FILE = "database.properties";

    private DatabaseConfig config;

    public ConnectionFactory(){
        try{
            ConfigFactory factory = new ConfigFactory();
            config = factory.getConfig(DATABASE_PROPERTIES_FILE);
            String driverName = config.getDriverName();
            Class.forName(driverName);
        } catch(ClassNotFoundException | IOException ex) {
            throw new IllegalStateException(ex.getMessage(), ex);
        }
    }

    public ProxyConnection createConnection(){
        try {
            String url = config.getUrl();
            String user = config.getUser();
            String password = config.getPassword();
            Connection connection = DriverManager.getConnection(url, user, password);
            return new ProxyConnection(connection);
        } catch(SQLException ex){
            throw new IllegalStateException(ex.getMessage(), ex);
        }
    }

    public int getMaxWaitInSeconds(){
        return config.getMaxWaitInSeconds();
    }

    public int getPoolSize(){
        return config.getPoolSize();
    }

}