package com.example.kernlang.codebase_viewer;

import com.example.kernlang.codebase_viewer.graph.GraphEdge;
import com.example.kernlang.codebase_viewer.graph.GraphNode;
import com.example.kernlang.db.DAOAbstraction.DataAccessContext;
import com.example.kernlang.db.DAOAbstraction.DataAccessProvider;
import com.example.kernlang.db.DAOAbstraction.GraphNodeDAO;
import com.example.kernlang.db.DataAccessException;
import com.example.kernlang.db.jdbc.JDBCDataAccessProvider;

import java.io.File;
import java.sql.SQLException;

public class DBManager {
    private DataAccessProvider dap;
    private final GraphWindowState gws;

    public DBManager(GraphWindowState gws) {
        this.gws = gws;
    }

    public void openDB(File file) throws DataAccessException {
        dap = new JDBCDataAccessProvider(file.getPath());
        // open DB en roep methode aan in codebaseViewerModel om nodes aan te maken
        //for node in resultset:
        //    codeViewerModel.add(new Graphnode...)
        //gws.clearImage();
        for (GraphNode gn : dap.getDataAccessContent().getGraphNodeDAO().getAllGraphNodes(gws)) {
            gws.addNodeFromDB(gn);
        }
    }

    public void newDB(File file) throws DataAccessException {
        try {
            new JDBCDataAccessProvider(file.getPath()).getDataAccessContent().createDB();
        } catch (SQLException | DataAccessException ex) {
            throw new DataAccessException("couldn't make db at path" + file.getPath(), ex);
        }
        openDB(file);
    }

    public void exportToDB(File file) throws DataAccessException {
        try {
            DataAccessProvider dap = new JDBCDataAccessProvider(file.getPath());
            DataAccessContext dac = dap.getDataAccessContent();
            dac.createDB();
            GraphNodeDAO gnDAO = dac.getGraphNodeDAO();
            // NOTE: all graphnodes must be in the database before we start adding edges to the database

            for (GraphNode gn : gws.getGraphNodes()) {
                System.out.println(gnDAO);
                gnDAO.addNewGraphNode(gn);
            }
        } catch (SQLException | DataAccessException ex) {
            throw new DataAccessException("couldn't make db at path" + file.getPath(), ex);
        }

        /*for (GraphNode gn: cvm.getGraphNodes()) {
            for (GraphEdge ge: gn.getImports()) {
                gnDAO.addNewImport(ge);
            }
        }*/
    }
}
