package ar.com.juanferrara.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Interfaz que sirve para implementar y utilizar el acceso a una conexion a
 * SQL mediante MySQL
 */
public interface MySQLImplement {

    /**
     * Metodo para instanciar la conexion con la base de datos mysql
     * @return Retorna la Connection instanciada y creada
     */
    default Connection getConnection() {
        Connection connection = null;

        final String DRIVER = "com.mysql.cj.jdbc.Driver";
        final String USER = "root";
        final String PASSWORD = "";
        final String URL = "jdbc:mysql://localhost:3306/peliculas";

        try {
            Class.forName(DRIVER);

            connection = DriverManager.getConnection(URL, USER, PASSWORD);

        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }



        return connection;
    }
}
