package com.epam.rating.pool;

import com.epam.rating.exception.ConnectionPoolException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionPool {

    private static ConnectionPool instance = new ConnectionPool();
    private static final boolean SEMAPHORE_FAIR = true;
    private static final Lock LOCK = new ReentrantLock();

    private Queue<ProxyConnection> connections = new LinkedList<>();
    private List<ProxyConnection> givenConnections = new ArrayList<>();
    private Semaphore semaphore;
    private int maxWaitInSeconds;

    private ConnectionPool(){
        ConnectionFactory connectionFactory = new ConnectionFactory();
        this.maxWaitInSeconds = connectionFactory.getMaxWaitInSeconds();
        int poolSize = connectionFactory.getPoolSize();
        this.semaphore = new Semaphore(poolSize, SEMAPHORE_FAIR);
        for(int i = 0; i < poolSize; i++){
            ProxyConnection proxyConnection = connectionFactory.createConnection();
            connections.add(proxyConnection);
        }
    }

    public static ConnectionPool getInstance(){
        return instance;
    }

    public ProxyConnection getConnection() throws ConnectionPoolException {

        try{
            if(semaphore.tryAcquire(maxWaitInSeconds, TimeUnit.SECONDS)){
                LOCK.lock();
                ProxyConnection givenConnection = connections.poll();
                givenConnections.add(givenConnection);
                return givenConnection;
            } else{
                throw new ConnectionPoolException("Over waiting time..");
            }
        } catch (InterruptedException ex){
            throw new ConnectionPoolException(ex.getMessage(), ex);
        } finally{
            LOCK.unlock();
        }
    }

    public void releaseConnection(ProxyConnection connection) throws ConnectionPoolException{
        if(givenConnections.contains(connection)){
            connections.add(connection);
            givenConnections.remove(connection);
            semaphore.release();
        } else{
            throw new ConnectionPoolException("Invalid connection supplied!");
        }
    }

    public void close() throws ConnectionPoolException {
        try{
            givenConnections.forEach(ProxyConnection::close);
            for(ProxyConnection connection : connections){
                connection.terminate();
            }
        } catch(SQLException ex){
            throw new ConnectionPoolException(ex.getMessage(), ex);
        }
    }

}
