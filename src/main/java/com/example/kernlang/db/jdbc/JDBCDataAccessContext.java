package com.example.kernlang.db.jdbc;

import com.example.kernlang.db.DAOAbstraction.AbstractCreateTables;
import com.example.kernlang.db.DAOAbstraction.DataAccessContext;
import com.example.kernlang.db.DAOAbstraction.GraphNodeDAO;
import com.example.kernlang.db.DataAccessException;

import java.sql.Connection;
import java.sql.SQLException;

public class JDBCDataAccessContext implements DataAccessContext {

    private final Connection connection;
    private final AbstractCreateTables tableMaker;

    public JDBCDataAccessContext(Connection connection) {
        this.connection = connection;
        tableMaker = new CreateTables();
    }

    @Override
    public GraphNodeDAO getGraphNodeDAO() {
        return new JDBCGraphNodeDAO(connection);
    }

    @Override
    public void createDB() throws SQLException {
        tableMaker.createAllTables(connection);
    }

    @Override
    public void close() throws DataAccessException {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new DataAccessException("Could not close context", e);
        }
    }
}
