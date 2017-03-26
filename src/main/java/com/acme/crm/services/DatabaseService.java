package com.acme.crm.services;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface DatabaseService {
    public Connection getConnection() throws SQLException;
    
    public void setConnection() throws SQLException;
    
    public ResultSet query(String query) throws SQLException;
}
