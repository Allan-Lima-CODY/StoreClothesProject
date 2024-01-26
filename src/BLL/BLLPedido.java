/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BLL;

import DAL.AccessMethodConnectionFactory;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.Vector;
import javax.swing.JOptionPane;

/**
 *
 * @author allan
 */
public class BLLPedido {    
    public Vector selectPedidos() {
        Vector listaPedidos = new Vector();
        
        String sql = "SELECT Pdd.PedidoID,\n" +
                            "CONCAT(Endrc.EnderecoID, \" - \", Endrc.Logradouro, \", \", Endrc.Numero) Endereco,\n" +
                            "CONCAT(Pdd.ClienteID, \" - \", C.Nome) Cliente,\n" +
                            "DATE_FORMAT(Pdd.DataCriacao, '%d/%m/%Y') DataCriacao,\n" +
                            "Entrg.Descricao,\n" +
                            "Pgmnt.Descricao,\n" +
                            "SP.Descricao,\n" +
                            "Pdd.Frete,\n" +
                            "Pdd.Total\n" +
                        "FROM pedido Pdd\n" +
                        "LEFT JOIN endereco Endrc ON (Endrc.EnderecoID = Pdd.EnderecoID)\n" +
                        "LEFT JOIN cliente C ON (C.ClienteID = Pdd.ClienteID)\n" +
                        "LEFT JOIN entrega Entrg ON (Entrg.EntregaID = Pdd.EntregaID)\n" +
                        "LEFT JOIN pagamento Pgmnt ON (Pgmnt.PagamentoID = Pdd.PagamentoID)\n" +
                        "LEFT JOIN status_pedido SP ON (SP.StatusID = Pdd.StatusID);";
        try {
            AccessMethodConnectionFactory bllConnction = new AccessMethodConnectionFactory();
            Connection conn = bllConnction.getConnection();
        
            Statement stm = conn.createStatement();
            
            ResultSet pedidos;
            pedidos = stm.executeQuery(sql);
            while(pedidos.next()) {
                int pedidoID = pedidos.getInt("Pdd.PedidoID");
                String endereco = pedidos.getString("Endereco");
                String nome = pedidos.getString("Cliente");
                String dataCriacao = pedidos.getString("DataCriacao");
                String entrega = pedidos.getString("Entrg.Descricao");
                String pagamento = pedidos.getString("Pgmnt.Descricao");
                String statusPedido = pedidos.getString("SP.Descricao");
                Double doubleFrete = pedidos.getDouble("Pdd.Frete");
                Double doubleTotal = pedidos.getDouble("Pdd.Total");
                
                String frete = "R$" + new DecimalFormat("#,##0.00").format(doubleFrete);
                String total = "R$" + new DecimalFormat("#,##0.00").format(doubleTotal);
                
                Vector temp = new Vector();
                
                temp.add(pedidoID);
                temp.add(endereco);
                temp.add(nome);
                temp.add(dataCriacao);
                temp.add(entrega);
                temp.add(pagamento);
                temp.add(statusPedido);
                temp.add(frete);
                temp.add(total);
                
                listaPedidos.addElement(temp);
            }
            
            bllConnction.closeConnection();
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null, "Não foi possível recuperar a lista de pedidos!");
        }
        return listaPedidos;
    }
    
    public ResultSet searchPedidos(String field, String argument) throws SQLException {
        String filter = field + " " + "LIKE \"%" + argument + "%\"";
        
        String sql = "SELECT P.PedidoID,\n"
                          + "CONCAT(P.EnderecoID, \" - \", E.Logradouro, \", \", E.Numero) Endereco, " 
                          + "CONCAT(P.ClienteID, \" - \", C.Nome) Cliente, " 
                          + "DATE_FORMAT(P.DataCriacao, '%d/%m/%Y') DataCriacao, " 
                          + "ENTRG.Descricao, " 
                          + "PGMNT.Descricao, " 
                          + "S.Descricao, " 
                          + "P.Frete, " 
                          + "P.Total " 
                      + "FROM pedido P " 
                      + "LEFT JOIN endereco E ON (P.EnderecoID = E.EnderecoID) " 
                      + "LEFT JOIN cliente C ON (P.ClienteID = C.ClienteID) " 
                      + "LEFT JOIN entrega ENTRG ON (P.EntregaID = ENTRG.EntregaID) " 
                      + "LEFT JOIN pagamento PGMNT ON (P.PagamentoID = PGMNT.PagamentoID) " 
                      + "LEFT JOIN status_pedido S ON (P.StatusID = S.StatusID) "
                      + "WHERE " + filter;
        
        AccessMethodConnectionFactory bllConnction = new AccessMethodConnectionFactory();
        Connection conn = bllConnction.getConnection();
        
        Statement stm = conn.createStatement();
        
        ResultSet result; 
        result = stm.executeQuery(sql);
            
        return result;
    }
    
    public boolean deletePedidos(int id){
        String sqlItensPedido = "DELETE FROM itens_pedido WHERE PedidoID = " + id;
        String sql = "DELETE FROM pedido WHERE PedidoID = " + id;
        
        try {
            AccessMethodConnectionFactory bllConnction = new AccessMethodConnectionFactory();
            Connection conn = bllConnction.getConnection();
            
            Statement stm = conn.createStatement();
            
            stm.executeUpdate(sqlItensPedido);
            stm.executeUpdate(sql);
            
            bllConnction.closeConnection();
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null, "Não foi possível excluir esse pedido!");
            return false;
        }
        return true;
    }
}
