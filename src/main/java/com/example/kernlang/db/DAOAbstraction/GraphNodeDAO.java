package com.example.kernlang.db.DAOAbstraction;

import com.example.kernlang.codebase_viewer.graph.GraphNode;
import com.example.kernlang.db.DataAccessException;
import com.example.kernlang.db.NodeData;

import java.util.ArrayList;

public interface GraphNodeDAO {

    ArrayList<NodeData> getAllGraphNodes() throws DataAccessException;

    NodeData getGraphNodeByID(int id) throws DataAccessException;

    ArrayList<Integer> getIDOfGraphNodes() throws DataAccessException;

    void addNewGraphNode(GraphNode graphNode) throws DataAccessException;

}
