package ar.com.juanferrara.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public interface MySQLImplement {

    default Connection getConnection() {
        Connection connection = null;

        final String DRIVER = "com.mysql.cj.jdbc.Driver";
        final String USER = "root";
        final String PASSWORD = "";
        final String URL = "jdbc:mysql://localhost:3306/cotizaciones";

        try {
            Class.forName(DRIVER);

            connection = DriverManager.getConnection(URL, USER, PASSWORD);

        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }



        return connection;
    }
}
