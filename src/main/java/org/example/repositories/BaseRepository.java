package org.example.repositories;

import org.example.config.DatabaseConnection;

import java.sql.Connection;

public class BaseRepository {

    public Connection conn(){
        return DatabaseConnection.getConnection();
    }

}
