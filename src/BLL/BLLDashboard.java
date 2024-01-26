/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BLL;

import DAL.AccessMethodConnectionFactory;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import BO.BEDashboard;

/**
 *
 * @author allan
 */
public class BLLDashboard { 
    public int retornaTotalDeClientes(){
        BEDashboard objDashboard = new BEDashboard();
        
        String sql = "SELECT COUNT(*) AS Clientes FROM cliente;";
       
        try {
            AccessMethodConnectionFactory bllConnction = new AccessMethodConnectionFactory();
            Connection conn = bllConnction.getConnection();
            
            Statement stm = conn.createStatement();
            
            ResultSet numeroDeClientes; 
            numeroDeClientes = stm.executeQuery(sql);
            
            while(numeroDeClientes.next()){
                objDashboard.setTotalDeClientes(numeroDeClientes.getInt("Clientes"));
            }
            
            bllConnction.closeConnection();

        } catch(Exception e) {
            JOptionPane.showMessageDialog(null, "Não foi possível consultar!");
        }
        
        return objDashboard.getTotalDeClientes();
    }
    
    public int retornaTotalDeVendasNoMes(){
        BEDashboard objDashboard = new BEDashboard();
        
        String sql = "SELECT COUNT(*) AS VendasNoUltimoMes "
                   + "FROM pedido "
                   + "WHERE DataCriacao "
                   + "BETWEEN DATE_SUB(CURDATE(), INTERVAL 30 DAY) AND CURDATE();";
       
        try {
            AccessMethodConnectionFactory conexaoBD = new AccessMethodConnectionFactory();  
            Connection conn = conexaoBD.getConnection();
            
            Statement stm = conn.createStatement();
            
            ResultSet numeroDeVendasNoMes; 
            numeroDeVendasNoMes = stm.executeQuery(sql);
            
            while(numeroDeVendasNoMes.next()){
                objDashboard.setVendasNoMes(numeroDeVendasNoMes.getInt("VendasNoUltimoMes"));
            }
            
            conexaoBD.closeConnection();

        } catch(Exception e) {
            JOptionPane.showMessageDialog(null, "Não foi possível consultar!");
        }
        
        return objDashboard.getVendasNoMes();
    }
    
    public double retornaArrecadacaoMensal(){
        BEDashboard objDashboard = new BEDashboard();
        
        String sql = "SELECT SUM(Total) AS ArrecadacaoMes "
                   + "FROM pedido "
                   + "WHERE DataCriacao "
                   + "BETWEEN DATE_SUB(CURDATE(), INTERVAL 30 DAY) AND CURDATE();";
       
        try {
            AccessMethodConnectionFactory conexaoBD = new AccessMethodConnectionFactory();  
            Connection conn = conexaoBD.getConnection();
            
            Statement stm = conn.createStatement();
            
            ResultSet arrecadacaoMes; 
            arrecadacaoMes = stm.executeQuery(sql);
            
            while(arrecadacaoMes.next()){
                objDashboard.setArrecadaoNoMes(arrecadacaoMes.getDouble("ArrecadacaoMes"));
            }
            
            conexaoBD.closeConnection();

        } catch(Exception e) {
            JOptionPane.showMessageDialog(null, "Não foi possível consultar!");
        }
        
        return objDashboard.getArrecadaoNoMes();
    }
}
