package com.b2.supercoding_prj01.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class jdbc {

    private static final String DB_URL = "jdbc::mysql://localhost:3306/project_01";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "12341234";

    public static void main(String[] args) {

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement statement = connection.createStatement()
        ){
            String stringSQL = "";
            statement.executeQuery(stringSQL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
