package com.example.kernlang.db.DAOAbstraction;

import com.example.kernlang.codebase_viewer.graph.GraphEdge;
import com.example.kernlang.codebase_viewer.graph.GraphNode;
import com.example.kernlang.db.DataAccessException;
import com.example.kernlang.db.EdgeData;
import com.example.kernlang.db.NodeData;

import java.util.ArrayList;

public interface GraphNodeDAO {

    ArrayList<NodeData> getAllGraphNodes() throws DataAccessException;

    NodeData getGraphNodeByID(int id) throws DataAccessException;

    ArrayList<Integer> getIDOfGraphNodes() throws DataAccessException;

    ArrayList<EdgeData> getAllImports() throws DataAccessException;

    ArrayList<EdgeData> getImportsFromGraphNode(int id) throws DataAccessException;

    ArrayList<EdgeData> getExportsFromGraphNode(int id) throws DataAccessException;

    void addNewGraphNode(GraphNode graphNode) throws DataAccessException;

    void addNewImport(GraphEdge graphEdge) throws DataAccessException;

    void addNewImport(EdgeData edgeData) throws DataAccessException;

    void updateGraphNode(GraphNode graphNode) throws DataAccessException;

    void deleteImport(EdgeData edgeData) throws DataAccessException;

    // TODO @mathis add what u need
}
