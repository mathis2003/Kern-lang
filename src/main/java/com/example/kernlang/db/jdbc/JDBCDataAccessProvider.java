package com.example.kernlang.db.jdbc;

import com.example.kernlang.db.DAOAbstraction.DataAccessContext;
import com.example.kernlang.db.DAOAbstraction.DataAccessProvider;
import com.example.kernlang.db.DataAccessException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCDataAccessProvider implements DataAccessProvider {

    private final String url;

    public JDBCDataAccessProvider(String fileName) {
        url = "jdbc:sqlite:" + fileName;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url);
    }
    @Override
    public DataAccessContext getDataAccessContent() throws DataAccessException {
        try {
            return new JDBCDataAccessContext(getConnection());
        } catch (SQLException ex) {
            throw new DataAccessException("Could not create data access context", ex);
        }
    }
}
