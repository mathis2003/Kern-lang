package com.example.kernlang.db.DAOAbstraction;

import com.example.kernlang.codebase_viewer.GraphWindowState;
import com.example.kernlang.codebase_viewer.graph.GraphEdge;
import com.example.kernlang.codebase_viewer.graph.GraphNode;
import com.example.kernlang.db.DataAccessException;
import com.example.kernlang.db.ImportData;

import java.util.ArrayList;

public interface GraphNodeDAO {

    ArrayList<GraphNode> getAllGraphNodes(GraphWindowState gws) throws DataAccessException;

    GraphNode getGraphNodeByID(int id, GraphWindowState gws) throws DataAccessException;

    ArrayList<Integer> getIDOfGraphNodes() throws DataAccessException;

    ArrayList<ImportData> getAllImports() throws DataAccessException;

    ArrayList<ImportData> getImportsFromGraphNode(int id) throws DataAccessException;

    ArrayList<ImportData> getExportsFromGraphNode(int id) throws DataAccessException;

    void addNewGraphNode(GraphNode graphNode) throws DataAccessException;

    void addNewImport(GraphEdge graphEdge) throws DataAccessException;

    void addNewImport(ImportData importData) throws DataAccessException;

    void updateGraphNode(GraphNode graphNode) throws DataAccessException;

    void deleteImport(ImportData importData) throws DataAccessException;

    // TODO @mathis add what u need
}
