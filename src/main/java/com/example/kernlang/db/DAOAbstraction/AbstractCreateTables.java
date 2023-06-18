package com.example.kernlang.db.DAOAbstraction;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class AbstractCreateTables {

    /**
     * creates all needed tables in a db
     * @param connection to the db
     */
    public void createAllTables(Connection connection) throws SQLException {
        createGraphNodeTable(connection);
        createImportTable(connection);
    }

    public abstract void createGraphNodeTable(Connection connection) throws SQLException;
    public abstract void createImportTable(Connection connection) throws SQLException;
}
