package com.example.kernlang.db.jdbc;

import com.example.kernlang.db.DAOAbstraction.AbstractCreateTables;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public final class CreateTables extends AbstractCreateTables {

    public void createGraphNodeTable(Connection connection) throws SQLException{
        try (Statement statement = connection.createStatement()) {
            statement.execute("""
                    CREATE TABLE graphnode (
                        id INTEGER PRIMARY KEY,
                        xpos DOUBLE PRECISION NOT NULL,
                        ypos DOUBLE PRECISION NOT NULL,
                        name TEXT
                    );
                    """
            );
        }
    }

    public void createImportTable(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            /*statement.execute("""
                    CREATE TABLE import (
                        startnode INTEGER,
                        endnode INTEGER,
                        PRIMARY KEY (startnode, endnode),
                        FOREIGN KEY (startnode) REFERENCES graphnode (id),
                        FOREIGN KEY (endnode) REFERENCES graphnode (id)
                    );
                    """
            );*/
        }
    }
}
