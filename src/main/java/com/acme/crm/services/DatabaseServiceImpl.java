package com.acme.crm.services;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class DatabaseServiceImpl implements DatabaseService {
    
    private static Connection connection;
    private final Properties config;
    
    public DatabaseServiceImpl() throws IOException {
        FileInputStream fileInputStream = new FileInputStream("database.properties");
        
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
            
        mysqlDataSource.setURL(config.getProperty("url"));
        mysqlDataSource.setDatabaseName(config.getProperty("name"));
        mysqlDataSource.setUser(config.getProperty("user"));
        mysqlDataSource.setPassword(config.getProperty("password"));

        DatabaseServiceImpl.connection = (Connection) mysqlDataSource.getConnection();
    }
    
    @Override
    public ResultSet query(String query) throws SQLException {
        Statement stmt = this.getConnection().createStatement();
        ResultSet rs = stmt.executeQuery(query);
        
        return rs;
    }
}
