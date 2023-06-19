package com.example.kernlang.db.jdbc;

import com.example.kernlang.codebase_viewer.graph.GraphEdge;
import com.example.kernlang.db.DAOAbstraction.GraphEdgeDAO;
import com.example.kernlang.db.DataAccessException;
import com.example.kernlang.db.EdgeData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class JDBCGraphEdgeDAO implements GraphEdgeDAO {

    private final Connection connection;

    public JDBCGraphEdgeDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public ArrayList<EdgeData> getAllEdges() throws DataAccessException {
        try (PreparedStatement ps = connection.prepareStatement("SELECT startnode, endnode FROM graphedge")) {
            try (ResultSet rs = ps.executeQuery()) {
                ArrayList<EdgeData> out = new ArrayList<>();
                while (rs.next()) {
                    //System.out.println(out);
                    out.add(new EdgeData(rs.getInt("startnode"), rs.getInt("endnode")));
                }
                return out;
            }
        } catch (SQLException ex) {
            throw new DataAccessException("couln't get the edges from the db", ex);
        }
    }

    @Override
    public void addNewEdge(GraphEdge ge) throws DataAccessException {
        if (ge != null) {
            try (PreparedStatement ps = connection.prepareStatement("INSERT INTO graphedge (startnode, endnode) VALUES (?, ?)")) {
                ps.setInt(1, ge.getStartNode().getDatabaseID());
                ps.setInt(2, ge.getEndNode().getDatabaseID());
                ps.execute();
            } catch (SQLException ex) {
                System.out.println("nooooo");
                throw new DataAccessException("couldn't add a new edge in the db", ex);
            }
        }
    }
}
