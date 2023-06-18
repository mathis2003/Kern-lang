package com.example.kernlang.db.DAOAbstraction;

import com.example.kernlang.db.DataAccessException;

public interface DataAccessProvider {

    DataAccessContext getDataAccessContent() throws DataAccessException;
}
