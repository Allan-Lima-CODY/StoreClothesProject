/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BLL;

import BO.BECliente;
import BO.BEEndereco;
import DAL.AccessMethodConnectionFactory;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import javax.swing.JOptionPane;

/**
 *
 * @author allan
 */
public class BLLEndereco {
    public Vector selectByClienteID(int id){
        Vector listaEnderecos = new Vector();
        
        String sql = "SELECT E.EnderecoID, " +
                            "E.CEP, " +
                            "E.Estado, " +
                            "E.Cidade, " +
                            "E.Logradouro, " +
                            "E.Numero " +
                        "FROM endereco E " +
                        "WHERE E.ClienteID = " + id + ";";
        
        try {
            AccessMethodConnectionFactory bllConnction = new AccessMethodConnectionFactory();
            Connection conn = bllConnction.getConnection();
        
            Statement stm = conn.createStatement();
            
            ResultSet enderecos;
            enderecos = stm.executeQuery(sql);
            while(enderecos.next()) {
                int enderecoID = enderecos.getInt("E.EnderecoID");
                String cep = enderecos.getString("E.CEP");
                String estado = enderecos.getString("E.Estado");
                String cidade = enderecos.getString("E.Cidade");
                String logradouro = enderecos.getString("E.Logradouro");
                String numero = enderecos.getString("E.Numero");
                
                String endereco = logradouro + ", " + numero + " - " + cidade + ", " + estado + " - " + cep;
                
                Vector temp = new Vector();
                temp.add(enderecoID);
                temp.add(endereco);
                
                listaEnderecos.addElement(temp);
            }
            
            bllConnction.closeConnection();
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null, "Não foi possível recuperar a lista de clientes!");
        }
        return listaEnderecos;
    }
    
    public BEEndereco findObjectByID(int id){
        BEEndereco objEndereco = new BEEndereco();
        BLLCliente bllCliente = new BLLCliente();
        
        String sql = "SELECT E.EnderecoID, " +
                            "E.ClienteID, " +
                            "E.CEP, " +
                            "E.Estado, " +
                            "E.Cidade, " +
                            "E.Bairro, " +
                            "E.Logradouro, " +
                            "E.Numero, " +
                            "E.Complemento " +
                        "FROM endereco E " +
                        "WHERE E.EnderecoID = " + id + ";";
        
        try {
            AccessMethodConnectionFactory bllConnction = new AccessMethodConnectionFactory();
            Connection conn = bllConnction.getConnection();
        
            Statement stm = conn.createStatement();
            
            ResultSet objectEndereco;
            objectEndereco = stm.executeQuery(sql);
            while(objectEndereco.next()) {
                objEndereco.setEnderecoID(objectEndereco.getInt("E.EnderecoID"));
                objEndereco.setObjCliente(bllCliente.findObjectByID(objectEndereco.getInt("E.ClienteID")));
                objEndereco.setCep(objectEndereco.getString("E.CEP"));
                objEndereco.setEstado(objectEndereco.getString("E.Estado"));
                objEndereco.setCidade(objectEndereco.getString("E.Cidade"));
                objEndereco.setBairro(objectEndereco.getString("E.Bairro"));
                objEndereco.setLogradouro(objectEndereco.getString("E.Logradouro"));
                objEndereco.setNumero(objectEndereco.getInt("E.Numero"));
                objEndereco.setComplemento(objectEndereco.getString("E.Complemento"));
            }
            
            bllConnction.closeConnection();
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null, "Não foi possível recuperar o objeto de endereço!");
        }
        return objEndereco;
    }
    
    public ResultSet searchAddressByClienteID(String field, String argument, int clienteID) throws SQLException {
        String filter = " AND" + field + " " + "LIKE \"%" + argument + "%\"";
        
        String sql = "SELECT E.EnderecoID, " +
                            "CONCAT(E.Logradouro, \", \", E.Numero, \" - \", E.Cidade, \", \", E.Estado, \" - \", E.CEP) Endereco " +
                        "FROM endereco E " +
                        "WHERE E.ClienteID = " + clienteID + filter;
        
        AccessMethodConnectionFactory bllConnction = new AccessMethodConnectionFactory();
        Connection conn = bllConnction.getConnection();
        
        Statement stm = conn.createStatement();
        
        ResultSet result; 
        result = stm.executeQuery(sql);
            
        return result;
    }
    
    public boolean deleteEnderecos(int id){
        String sqlUpdate = "UPDATE pedido SET EnderecoID = 0 WHERE EnderecoID = " + id + ";";
        String sql = "DELETE FROM endereco WHERE EnderecoID = " + id + ";";
        
        try {
            AccessMethodConnectionFactory bllConnction = new AccessMethodConnectionFactory();
            Connection conn = bllConnction.getConnection();
            
            Statement stm = conn.createStatement();
            
            stm.executeUpdate(sqlUpdate);
            stm.executeUpdate(sql);
            
            bllConnction.closeConnection();
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null, "Não foi possível excluir esse pedido!");
            return false;
        }
        return true;
    } 
}
