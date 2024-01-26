/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAL;

import DAL.AccessBEConnectionFactory;
import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

/**
 *
 * @author allan
 */
public class AccessMethodConnectionFactory {
    AccessBEConnectionFactory objConnection = new AccessBEConnectionFactory();
    
    public Connection getConnection(){
        try {
            objConnection.setConnection(DriverManager.getConnection(
                "jdbc:mysql://" + 
                    objConnection.getHOSTNAME() + 
                    ":" + 
                    objConnection.getPORT_NUMBER()+ 
                    "/" + 
                    objConnection.getDB_NAME(), objConnection.getUSER_NAME(), objConnection.getUSER_PASS()));
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null, "Não foi possível conectar no banco!");
            return null;
        }
        return objConnection.getConnection();
    }
    
    public void closeConnection() {
        try {
            if (objConnection.getConnection() != null) {
                objConnection.getConnection().close();
                objConnection.setConnection(null);
            }
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null, "Não foi possível fechar a conexão com o banco!");
        }
    }
}
