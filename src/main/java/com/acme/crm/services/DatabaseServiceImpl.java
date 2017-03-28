package com.acme.crm.services;

import java.io.InputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;
import javax.inject.Singleton;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

@Singleton
public class DatabaseServiceImpl implements DatabaseService {
    
    private static Connection connection;
    private final Properties config;
    
    public DatabaseServiceImpl() throws IOException {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        InputStream fileInputStream = loader.getResourceAsStream("database.properties");
        
        config = new Properties();
        config.load(fileInputStream);
    }
    
    @Override
    public Connection getConnection() throws SQLException {
        if (DatabaseServiceImpl.connection == null) {
            this.setConnection();
        }
        
        return DatabaseServiceImpl.connection;
    }
    
    @Override
    public void setConnection() throws SQLException {
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
            
        mysqlDataSource.setServerName(config.getProperty("host"));
        mysqlDataSource.setDatabaseName(config.getProperty("name"));
        mysqlDataSource.setUser(config.getProperty("user"));
        mysqlDataSource.setPassword(config.getProperty("password"));

        DatabaseServiceImpl.connection = (Connection) mysqlDataSource.getConnection();
    }
}
