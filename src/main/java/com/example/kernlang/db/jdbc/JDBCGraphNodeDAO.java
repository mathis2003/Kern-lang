package com.example.kernlang.db.jdbc;

import com.example.kernlang.codebase_viewer.graph.GraphNode;
import com.example.kernlang.db.DAOAbstraction.GraphNodeDAO;
import com.example.kernlang.db.DataAccessException;
import com.example.kernlang.db.NodeData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class JDBCGraphNodeDAO implements GraphNodeDAO {

    private final Connection connection;

    public JDBCGraphNodeDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public ArrayList<NodeData> getAllGraphNodes() throws DataAccessException {
        ArrayList<NodeData> out = new ArrayList<>();
        for (int id : getIDOfGraphNodes()) {
            out.add(getGraphNodeByID(id));
        }
        return out;
    }

    @Override
    public NodeData getGraphNodeByID(int id) throws DataAccessException {
        try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM graphnode WHERE id = ?")) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                return new NodeData(
                        id,
                        rs.getDouble("xpos"),
                        rs.getDouble("ypos"),
                        rs.getString("name"),
                        rs.getString("code")
                );
            }
        } catch (SQLException ex) {
            throw new DataAccessException("couldn't get node with id: " + id, ex);
        }
    }

    @Override
    public ArrayList<Integer> getIDOfGraphNodes() throws DataAccessException {
        try (PreparedStatement ps = connection.prepareStatement("SELECT id FROM graphnode")) {
            try (ResultSet rs = ps.executeQuery()) {
                ArrayList<Integer> out = new ArrayList<>();
                while (rs.next()) {
                    out.add(rs.getInt("id"));
                }
                return out;
            }
        } catch (SQLException ex) {
            throw new DataAccessException("couln't get the ids from the db", ex);
        }
    }

    @Override
    public void addNewGraphNode(GraphNode graphNode) throws DataAccessException {
        if (graphNode != null) {
            try (PreparedStatement ps = connection.prepareStatement("INSERT INTO graphnode (id, xpos, ypos, name, code) VALUES (?, ?, ?, ?, ?)")) {
                ps.setInt(1, graphNode.getDatabaseID());
                ps.setDouble(2, graphNode.getXProperty().get());
                ps.setDouble(3, graphNode.getYProperty().get());
                ps.setString(4, graphNode.getName());
                ps.setString(5, graphNode.getCodeString());
                ps.execute();
            } catch (SQLException ex) {
                System.out.println("nooooo");
                throw new DataAccessException("couldn't add a new node in the db", ex);
            }
        }
    }
}
