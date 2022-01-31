package com.epam.rating.pool.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigFactory {

    private static final String DRIVER_NAME_PROPERTY = "driver_name";
    private static final String URL_PROPERTY = "url";
    private static final String USER_PROPERTY = "user";
    private static final String PASSWORD_PROPERTY = "password";
    private static final String MAX_WAIT_PROPERTY = "max_wait_in_seconds";
    private static final String POOL_SIZE_PROPERTY = "poolSize";

    public DatabaseConfig getConfig(String propertiesFileName) throws IOException{
        Properties properties = getProperties(propertiesFileName);
        String driverName = properties.getProperty(DRIVER_NAME_PROPERTY);
        String url = properties.getProperty(URL_PROPERTY);
        String user = properties.getProperty(USER_PROPERTY);
        String password = properties.getProperty(PASSWORD_PROPERTY);
        String maxWaitStr = properties.getProperty(MAX_WAIT_PROPERTY);
        String poolSizeStr = properties.getProperty(POOL_SIZE_PROPERTY);
        int maxWaitInSeconds = Integer.parseInt(maxWaitStr);
        int poolSize = Integer.parseInt(poolSizeStr);
        return new DatabaseConfig(driverName, url, user, password, maxWaitInSeconds, poolSize);
    }


    private Properties getProperties(String propertiesFileName) throws IOException {
        ClassLoader loader = getClass().getClassLoader();
        InputStream inputStream = loader.getResourceAsStream(propertiesFileName);
        if(inputStream != null){
            Properties properties = new Properties();
            properties.load(inputStream);
            return properties;
        } else{
            throw new IllegalArgumentException("Database properties file not found!");
        }
    }
}