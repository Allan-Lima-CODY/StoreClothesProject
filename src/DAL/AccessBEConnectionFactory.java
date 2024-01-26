/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAL;

import java.sql.Connection;

/**
 *
 * @author allan
 */
public class AccessBEConnectionFactory {
    private Connection connection = null;
    private String HOSTNAME = "localhost";
    private String DB_NAME = "apptiavo";
    private String USER_NAME = "root";
    private String USER_PASS = "Indied1432";
    private int PORT_NUMBER = 3306;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public String getHOSTNAME() {
        return HOSTNAME;
    }

    public String getDB_NAME() {
        return DB_NAME;
    }

    public String getUSER_NAME() {
        return USER_NAME;
    }

    public String getUSER_PASS() {
        return USER_PASS;
    }

    public int getPORT_NUMBER() {
        return PORT_NUMBER;
    }
}
