package com.example.kernlang.db.DAOAbstraction;

import com.example.kernlang.codebase_viewer.graph.GraphEdge;
import com.example.kernlang.db.DataAccessException;
import com.example.kernlang.db.EdgeData;

import java.util.ArrayList;

public interface GraphEdgeDAO {
    ArrayList<EdgeData> getAllEdges() throws DataAccessException;

    void addNewEdge(GraphEdge ge) throws DataAccessException;
}
