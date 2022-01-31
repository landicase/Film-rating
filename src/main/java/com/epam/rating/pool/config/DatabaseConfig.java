package com.epam.rating.pool.config;

public class DatabaseConfig {

    private String driverName;
    private String url;
    private String user;
    private String password;
    private int poolSize;
    private int maxWaitInSeconds;

    public DatabaseConfig(String driverName, String url, String user, String password,
                          int maxWaitInSeconds, int poolSize) {
        this.driverName = driverName;
        this.url = url;
        this.user = user;
        this.password = password;
        this.maxWaitInSeconds = maxWaitInSeconds;
        this.poolSize = poolSize;
    }

    public String getDriverName(){
        return driverName;
    }

    public String getUrl() {
        return url;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public int getPoolSize() {
        return poolSize;
    }

    public int getMaxWaitInSeconds() {
        return maxWaitInSeconds;
    }
}