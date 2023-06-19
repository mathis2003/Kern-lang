package com.example.kernlang.db.jdbc;

import com.example.kernlang.codebase_viewer.graph.GraphEdge;
import com.example.kernlang.codebase_viewer.graph.GraphNode;
import com.example.kernlang.db.DAOAbstraction.GraphNodeDAO;
import com.example.kernlang.db.DataAccessException;
import com.example.kernlang.db.EdgeData;
import com.example.kernlang.db.NodeData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class JDBCGraphNodeDAO extends JDBCAbstractDAO implements GraphNodeDAO {

    private final Connection connection;

    public JDBCGraphNodeDAO(Connection connection) {
        super(connection);
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
        try (PreparedStatement ps = prepare("SELECT * FROM graphnode WHERE id = ?")) {
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
        try (PreparedStatement ps = prepare("SELECT id FROM graphnode")) {
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
    public ArrayList<EdgeData> getAllImports() throws DataAccessException {
        try (PreparedStatement ps = prepare("SELECT * FROM import")) {
            try (ResultSet rs = ps.executeQuery()) {
                ArrayList<EdgeData> out = new ArrayList<>();
                while (rs.next()) {
                    out.add(new EdgeData(
                            rs.getInt("startnode"),
                            rs.getInt("endnode")
                    ));
                }
                return out;
            }
        } catch (SQLException ex) {
            throw new DataAccessException("couldn't get the imports", ex);
        }
    }

    @Override
    public ArrayList<EdgeData> getImportsFromGraphNode(int id) throws DataAccessException {
        return getImportExportFromGraphNode(id, "startnode");
    }

    private ArrayList<EdgeData> getImportExportFromGraphNode(int id, String xxxnode) throws DataAccessException {
        try (PreparedStatement ps = prepare("SELECT * FROM imports WHERE ? = ?")) {
            ps.setString(1, xxxnode);
            ps.setInt(2, id);
            try (ResultSet rs = ps.executeQuery()) {
                ArrayList<EdgeData> out = new ArrayList<>();
                while (rs.next()) {
                    out.add(new EdgeData(
                            rs.getInt("startnode"),
                            rs.getInt("endnode")
                    ));
                }
                return out;
            }
        } catch (SQLException ex) {
            throw new DataAccessException("couldn't get the imports of node with id: " + id, ex);
        }
    }

    @Override
    public ArrayList<EdgeData> getExportsFromGraphNode(int id) throws DataAccessException {
        return getImportExportFromGraphNode(id, "endnode");
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

    @Override
    public void addNewImport(GraphEdge graphEdge) throws DataAccessException {
        /*if (graphEdge != null) {
            try (PreparedStatement ps = connection.prepareStatement("INSERT INTO import (startnode, endnode) VALUES (?, ?)")) {
                ps.setInt(1, graphEdge.getStartNode().getDatabaseID());
                ps.setInt(2, graphEdge.getEndNode().getDatabaseID());
                ps.execute();
            } catch (SQLException ex) {
                throw new DataAccessException("couldn't make new import in db", ex);
            }
        }*/
    }

    @Override
    public void addNewImport(EdgeData edgeData) throws DataAccessException {
        if (edgeData != null) {
            try (PreparedStatement ps = prepare("INSERT INTO import (startnode, endnode) VALUES (?, ?)")) {
                ps.setInt(1, edgeData.startID());
                ps.setInt(2, edgeData.endID());
                ps.execute();
            } catch (SQLException ex) {
                throw new DataAccessException("couldn't make new import in db", ex);
            }
        }
    }

    @Override
    public void updateGraphNode(GraphNode graphNode) throws DataAccessException {
        if (graphNode != null) {
            try (PreparedStatement ps = prepare("UPDATE graphnode SET xpos = ?, ypos = ?, name = ? WHERE id = ?")) {
                ps.setDouble(1, graphNode.getXProperty().get());
                ps.setDouble(2, graphNode.getYProperty().get());
                ps.setString(3, graphNode.getName());
                ps.setInt(4, graphNode.getDatabaseID());
                ps.execute();
            } catch (SQLException ex) {
                throw new DataAccessException("problem with updating values of graphnode with name/id: " + graphNode.getName() + graphNode.getDatabaseID(), ex);
            }
        }
    }

    @Override
    public void deleteImport(EdgeData edgeData) throws DataAccessException {
        if (edgeData != null) {
            try (PreparedStatement ps = prepare("DELETE FROM import WHERE startnode = ? AND endnode = ?")) {
                ps.setInt(1, edgeData.startID());
                ps.setInt(2, edgeData.endID());
                ps.execute();
            } catch (SQLException ex) {
                throw new DataAccessException("couldn't remove import", ex);
            }
        }
    }
}
