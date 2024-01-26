/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BLL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author allan
 */
public class BLLHelper {
    public void resizeImage(JLabel label, String path){
        ImageIcon image = new ImageIcon(path);
        image.setImage(image.getImage().getScaledInstance(
            label.getWidth(), 
            label.getHeight(), 
            1));
        label.setIcon(image);
    }
    
    public void alterColorMenu(JLabel select, JLabel outer1, JLabel outer2, JLabel outer3, JLabel outer4){
        select.setBackground(new java.awt.Color(0,0,92));
        outer1.setBackground(new java.awt.Color(0,0,47));
        outer2.setBackground(new java.awt.Color(0,0,47));
        outer3.setBackground(new java.awt.Color(0,0,47));
        outer4.setBackground(new java.awt.Color(0,0,47));
    }
    
    public void visiblePanel(JPanel select, JPanel outer1, JPanel outer2, JPanel outer3, JPanel outer4){
        select.setVisible(true);
        outer1.setVisible(false);
        outer2.setVisible(false);
        outer3.setVisible(false);
        outer4.setVisible(false);
    }
    
    public void disableFieldsCustumers(JTextField Nome, JFormattedTextField CPF, JFormattedTextField Celular){
        Nome.setEnabled(false);
        CPF.setEnabled(false);
        Celular.setEnabled(false);
    }
    
    public void enableFieldsCustumers(JTextField Nome, JFormattedTextField CPF, JFormattedTextField Celular){
        Nome.setEnabled(true);
        CPF.setEnabled(true);
        Celular.setEnabled(true);
    }
    
    public void disableFieldsAdress(JFormattedTextField CEP, 
                                    JTextField Logradouro, 
                                    JTextField Estado, 
                                    JTextField Cidade,
                                    JTextField Bairro,
                                    JFormattedTextField Numero,
                                    JTextField Complemento){
        CEP.setEnabled(false);
        Logradouro.setEnabled(false);
        Estado.setEnabled(false);
        Cidade.setEnabled(false);
        Bairro.setEnabled(false);
        Numero.setEnabled(false);
        Complemento.setEnabled(false);
    }
    
    public void enableFieldsAdress(JFormattedTextField CEP, 
                                   JTextField Logradouro, 
                                   JTextField Estado, 
                                   JTextField Cidade,
                                   JTextField Bairro,
                                   JFormattedTextField Numero,
                                   JTextField Complemento){
        CEP.setEnabled(true);
        Logradouro.setEnabled(true);
        Estado.setEnabled(true);
        Cidade.setEnabled(true);
        Bairro.setEnabled(true);
        Numero.setEnabled(true);
        Complemento.setEnabled(true);
    }
    
    public void disableButtonsAdress(JButton btnGravarEndereco,
                                     JButton btnAlterarEndereco,
                                     JButton btnConsultarEnderecosCliente){
        btnGravarEndereco.setEnabled(false);
        btnAlterarEndereco.setEnabled(false);
        btnConsultarEnderecosCliente.setEnabled(false);
                
    }
    
    public void enableButtonsAdress(JButton btnGravarEndereco,
                                    JButton btnAlterarEndereco,
                                    JButton btnConsultarEndereco){
        btnGravarEndereco.setEnabled(true);
        btnAlterarEndereco.setEnabled(true);
        btnConsultarEndereco.setEnabled(true);
    }
    
    public void clearFieldsCustumers(JTextField Nome, JFormattedTextField CPF, JFormattedTextField Celular, JTextField ID){
        Nome.setText(null);
        CPF.setText(null);
        Celular.setText(null);
        ID.setText(null);
    }
    
    public void clearFieldsAdress(JFormattedTextField CEP, 
                                  JTextField Logradouro, 
                                  JTextField Estado, 
                                  JTextField Cidade,
                                  JTextField Bairro,
                                  JFormattedTextField Numero,
                                  JTextField Complemento,
                                  JTextField ID){
        CEP.setText(null);
        Logradouro.setText(null);
        Estado.setText(null);
        Cidade.setText(null);
        Bairro.setText(null);
        Numero.setText(null);
        Complemento.setText(null);
        ID.setText(null);
    }
    
    public void clearTable(JTable table){
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
    }      
}
