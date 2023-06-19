package com.example.kernlang.db.DAOAbstraction;



import com.example.kernlang.db.DataAccessException;

import java.sql.SQLException;

public interface DataAccessContext extends AutoCloseable {

    GraphNodeDAO getGraphNodeDAO();

    GraphEdgeDAO getGraphEdgeDAO();

    void createDB() throws SQLException;

    @Override
    void close() throws DataAccessException;
}

