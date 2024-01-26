/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BLL;

import DAL.AccessMethodConnectionFactory;
import BO.BECliente;
import BO.BEPagamento;
import BO.BEPagination;
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
public class BLLCliente {
    public Vector selectClientes(BEPagination pagination) {
        Vector listaClientes = new Vector();
        
        String sql = "SELECT C.ClienteID, "
                          + "C.Nome, "
                          + "C.CPF, "
                          + "C.Telefone "
                      + "FROM cliente C "
                      + "ORDER BY 1 "
                      + "DESC LIMIT " + pagination.getPageSize() + 
                        " OFFSET " + pagination.getOffset() + ";";
        
        try {
            AccessMethodConnectionFactory bllConnction = new AccessMethodConnectionFactory();
            Connection conn = bllConnction.getConnection();
        
            Statement stm = conn.createStatement();
            
            ResultSet clientes;
            clientes = stm.executeQuery(sql);
            while(clientes.next()) {
                int clienteID = clientes.getInt("C.ClienteID");
                String nome = clientes.getString("C.nome");
                String cpf = clientes.getString("C.CPF");
                String celular = clientes.getString("C.Telefone");
                
                Vector temp = new Vector();
                
                temp.add(clienteID);
                temp.add(nome);
                temp.add(cpf);
                temp.add(celular);
                
                listaClientes.addElement(temp);
            }
            
            bllConnction.closeConnection();
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null, "Não foi possível recuperar a lista de clientes!");
        }
        return listaClientes;
    }
    
    public boolean deleteClientes(int id){
        String sqlItensPedido = "DELETE \n" +
                                    "FROM itens_pedido\n" +
                                        "WHERE Itens_PedidoID IN\n" +
                                            "(SELECT itens FROM(\n" +
                                                "SELECT ip.Itens_PedidoID itens\n" +
                                                    "FROM itens_pedido ip\n" +
                                                    "LEFT JOIN pedido P ON (ip.PedidoID = P.PedidoID)\n" +
                                                    "LEFT JOIN cliente C ON (P.ClienteID = C.ClienteID)\n" +
                                                    "WHERE C.ClienteID = " + id + ") AS ItensPedido);";
        String sqlPedido = "DELETE FROM pedido WHERE ClienteID = " + id + ";";
        String sqlEndereco = "DELETE FROM endereco WHERE ClienteID = " + id + ";";
        String sql = "DELETE FROM cliente WHERE ClienteID = " + id + ";";
        
        try {
            AccessMethodConnectionFactory bllConnction = new AccessMethodConnectionFactory();
            Connection conn = bllConnction.getConnection();
            
            Statement stm = conn.createStatement();
            
            stm.executeUpdate(sqlItensPedido);
            stm.executeUpdate(sqlPedido);
            stm.executeUpdate(sqlEndereco);
            stm.executeUpdate(sql);
            
            bllConnction.closeConnection();
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null, "Não foi possível excluir esse cliente!");
            return false;
        }
        return true;
    }
    
    public ResultSet searchClientes(String field, String argument) throws SQLException {
        String filter = field + " " + "LIKE \"%" + argument + "%\"";
        
        String sql = "SELECT C.ClienteID, "
                          + "C.Nome, "
                          + "C.CPF, "
                          + "C.Telefone "
                       + "FROM cliente C "
                       + "WHERE " + filter;
        
        AccessMethodConnectionFactory bllConnction = new AccessMethodConnectionFactory();
        Connection conn = bllConnction.getConnection();
        
        Statement stm = conn.createStatement();
        
        ResultSet result; 
        result = stm.executeQuery(sql);
            
        return result;
    }
    
    public BECliente findObjectByID(int id){
        BECliente objCliente = new BECliente();
        
        String sql = "SELECT C.ClienteID, C.Nome, C.CPF, C.Telefone FROM cliente C WHERE C.ClienteID = " + id + ";";
        
        try {
            AccessMethodConnectionFactory bllConnction = new AccessMethodConnectionFactory();
            Connection conn = bllConnction.getConnection();
        
            Statement stm = conn.createStatement();
            
            ResultSet objectCliente;
            objectCliente = stm.executeQuery(sql);
            while(objectCliente.next()) {
                objCliente.setClienteID(objectCliente.getInt("C.ClienteID"));
                objCliente.setNome(objectCliente.getString("C.Nome"));
                objCliente.setCpf(objectCliente.getString("C.CPF"));
                objCliente.setTelCelular(objectCliente.getString("C.Telefone"));
            }
            
            bllConnction.closeConnection();
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null, "Não foi possível recuperar a lista de clientes!");
        }
        return objCliente;
    }
    
    public int totalDeClientes(){
        int totalDeClientes = 0;
        String sql = "SELECT COUNT(*) AS Clientes FROM cliente;";
        
        try {
            AccessMethodConnectionFactory bllConnction = new AccessMethodConnectionFactory();
            Connection conn = bllConnction.getConnection();
        
            Statement stm = conn.createStatement();
            
            ResultSet clientes;
            clientes = stm.executeQuery(sql);
            while(clientes.next()) {
                totalDeClientes = clientes.getInt("Clientes");
            }
            
            bllConnction.closeConnection();
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null, "Não foi possível recuperar a lista de clientes!");
        }
        
        return totalDeClientes;
    }
}
