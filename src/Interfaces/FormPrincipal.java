/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Interfaces;

import DAL.AccessMethodConnectionFactory;
import BLL.BLLCliente;
import BLL.BLLDashboard;
import BLL.BLLEndereco;
import BLL.BLLHelper;
import BLL.BLLPedido;
import BO.BECliente;
import BO.BEConstantes;
import BO.BEEndereco;
import BO.BEModal;
import BO.BEPagination;
import java.awt.Toolkit;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author allan
 */
public class FormPrincipal extends javax.swing.JFrame {    
    public static BEModal objModal = new BEModal();
    private BLLHelper bllHelper = new BLLHelper();
    private BLLDashboard bllDashboard = new BLLDashboard();
    private BLLCliente bllCliente = new BLLCliente();
    private BLLPedido bllPedido = new BLLPedido();
    private BLLEndereco bllEndereco = new BLLEndereco();
    AccessMethodConnectionFactory bllConnction = new AccessMethodConnectionFactory();
    
    private BEConstantes objConstante = new BEConstantes();
    private BECliente objCliente = new BECliente();
    private BEEndereco objEndereco = new BEEndereco();
    private BEPagination paginationClientes = new BEPagination();
    
    public void disableQueryCustumer(){
        objCliente = new BECliente();
        objEndereco = new BEEndereco();
        
        bllHelper.enableFieldsCustumers(txtNomeCliente, txtTelefone, txtCPF);
        btnGravarCliente.setEnabled(true);
        
        bllHelper.disableFieldsAdress(txtCEP, txtLogradouro, txtEstado, txtCidade, txtBairro, txtNumero, txtComplemento);
        bllHelper.disableButtonsAdress(btnGravarEndereco, btnAlterarEnderecosCliente, btnConsultarEnderecosCliente);
        
        bllHelper.clearFieldsCustumers(txtNomeCliente, txtCPF, txtTelefone, txtIDCliente);
        bllHelper.clearFieldsAdress(txtCEP, txtLogradouro, txtEstado, txtCidade, txtBairro, txtNumero, txtComplemento, txtIDEndereco);
        bllHelper.clearTable(tbEnderecosCliente);
        
        txtFiltroEnderecoCliente.setText("");
        
        txtFiltroEnderecoCliente.setEnabled(false);
        cbFiltroEnderecoCliente.setEnabled(false);
    }
    
    public void selectClientes() {
        Vector colClientes = new Vector();
        
        colClientes.add("Cliente ID");
        colClientes.add("Nome");
        colClientes.add("CPF");
        colClientes.add("Telefone");
        
        DefaultTableModel dataModel = new DefaultTableModel();
        dataModel.setDataVector(bllCliente.selectClientes(paginationClientes), colClientes);
        tbClientes.setModel(dataModel);
    }
    
    public void selectPedidos() {
        Vector colPedidos = new Vector();
        
        colPedidos.add("Pedido ID");
        colPedidos.add("Endereço");
        colPedidos.add("Cliente");
        colPedidos.add("Data de Criação");
        colPedidos.add("Entrega");
        colPedidos.add("Pagamento");
        colPedidos.add("Status");
        colPedidos.add("Frete");
        colPedidos.add("Total");
        
        DefaultTableModel dataModel = new DefaultTableModel();
        dataModel.setDataVector(bllPedido.selectPedidos(), colPedidos);
        tbPedidos.setModel(dataModel);
    }
    
    public void selectAdressByClienteID(int id){
        Vector colEnderecos = new Vector();
        
        colEnderecos.add("ID do Endereço");
        colEnderecos.add("Endereço");
        
        DefaultTableModel dataModel = new DefaultTableModel();
        dataModel.setDataVector(bllEndereco.selectByClienteID(id), colEnderecos);
        tbEnderecosCliente.setModel(dataModel);
    }
    
    public void searchCliente(){
        String choice = cbFiltroClientes.getSelectedItem().toString().trim();
        
        if (choice.equals("")){
            selectClientes();
            return;
        }
        
        String value = txtFiltroClientes.getText();
        String typeColumn = "";
        
        if (choice.equals("ClienteID")) {
            typeColumn = " " + "C.ClienteID";
        }
        if (choice.equals("Nome")) {
            typeColumn = " " + "C.Nome";
        }
        if (choice.equals("CPF")) {
            typeColumn = " " + "C.CPF";
        }
        if (choice.equals("Telefone")) {
            typeColumn = " " + "C.Telefone";
        }
        
        bllHelper.clearTable(tbClientes);
        
        try {
            ResultSet returnSearchClient = bllCliente.searchClientes(typeColumn, value);
            
            DefaultTableModel fillTable = (DefaultTableModel) tbClientes.getModel();
            
            while(returnSearchClient.next()){
                String Coluna0 = returnSearchClient.getString("C.ClienteID").toString().trim();
                String Coluna1 = returnSearchClient.getString("C.Nome").toString().trim();
                String Coluna2 = returnSearchClient.getString("C.CPF").toString().trim();
                String Coluna3 = returnSearchClient.getString("C.Telefone").toString().trim();
                
                fillTable.addRow(new String[]{Coluna0, Coluna1, Coluna2, Coluna3});
            }
            
            bllConnction.closeConnection();
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao preencher a coluna com os dados solicitados!");
        }
    }
    
    public void searchPedido(){
        String choice = cbFiltroPedidos.getSelectedItem().toString().trim();
        
        if (choice.equals("")){
            selectPedidos();
            return;
        }
        
        String value = txtFiltroPedidos.getText();
        String typeColumn = "";
        
        if (choice.equals("Pedido")) {
            typeColumn = " " + "P.PedidoID";
        }
        if (choice.equals("Endereço")) {
            typeColumn = " " + "CONCAT(P.EnderecoID, \" - \", E.Logradouro, \", \", E.Numero)";
        }
        if (choice.equals("Cliente")) {
            typeColumn = " " + "CONCAT(P.ClienteID, \" - \", C.Nome)";
        }
        if (choice.equals("Data de Criação")) {
            typeColumn = " " + "DataCriacao";
        }
        if (choice.equals("Entrega")) {
            typeColumn = " " + "ENTRG.Descricao";
        }
        if (choice.equals("Pagamento")) {
            typeColumn = " " + "PGMNT.Descricao";
        }
        if (choice.equals("Frete")) {
            typeColumn = " " + "P.Frete";
        }
        if (choice.equals("Total")) {
            typeColumn = " " + "P.Total";
        }
        if (choice.equals("Status")) {
            typeColumn = " " + "S.Descricao";
        }
        
        bllHelper.clearTable(tbPedidos);
        
        try {
            ResultSet returnSearchPedidos = bllPedido.searchPedidos(typeColumn, value);
            
            DefaultTableModel fillTable = (DefaultTableModel) tbPedidos.getModel();
            
            while(returnSearchPedidos.next()){
                String coluna0 = returnSearchPedidos.getString("P.PedidoID").toString().trim();
                String coluna1 = returnSearchPedidos.getString("Endereco").toString().trim();
                String coluna2 = returnSearchPedidos.getString("Cliente").toString().trim();
                String coluna3 = returnSearchPedidos.getString("DataCriacao").toString().trim();
                String coluna4 = returnSearchPedidos.getString("ENTRG.Descricao").toString().trim();
                String coluna5 = returnSearchPedidos.getString("PGMNT.Descricao").toString().trim();
                String coluna6 = returnSearchPedidos.getString("S.Descricao").toString().trim();
                Double frete = returnSearchPedidos.getDouble("P.Frete");
                Double total = returnSearchPedidos.getDouble("P.Total");
                String coluna7 = "R$" + new DecimalFormat("#,##0.00").format(frete);
                String coluna8 = "R$" + new DecimalFormat("#,##0.00").format(total);
                
                fillTable.addRow(new String[]{coluna0, coluna1, coluna2, coluna3, coluna4, coluna5, coluna6, coluna7, coluna8});
            }
            
            bllConnction.closeConnection();
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao preencher a coluna com os dados solicitados!");
        }
    }
    
    public void searchAddressByClienteID(){
        String choice = cbFiltroEnderecoCliente.getSelectedItem().toString();
        
        if (choice.equals("")){
            selectAdressByClienteID(objCliente.getClienteID());
            return;
        }
        
        String value = txtFiltroEnderecoCliente.getText();
        String typeColumn = "";
        
        if (choice.equals("Endereço ID")) {
            typeColumn = " " + "E.EnderecoID";
        }
        if (choice.equals("Endereço")) {
            typeColumn = " " + "CONCAT(E.Logradouro, \", \", E.Numero, \" - \", E.Cidade, \", \", E.Estado, \" - \", E.CEP)";
        }
        
        bllHelper.clearTable(tbEnderecosCliente);
        
        try {
            ResultSet returnSearchEnderecos = bllEndereco.searchAddressByClienteID(typeColumn, value, objCliente.getClienteID());
            
            DefaultTableModel fillTable = (DefaultTableModel) tbEnderecosCliente.getModel();
            
            while(returnSearchEnderecos.next()){
                String coluna0 = returnSearchEnderecos.getString("E.EnderecoID").toString().trim();
                String coluna1 = returnSearchEnderecos.getString("Endereco").toString().trim();
                
                fillTable.addRow(new String[]{coluna0, coluna1});
            }
            
            bllConnction.closeConnection();
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao preencher a coluna com os dados solicitados!");
        }
    }
    
    public FormPrincipal() {
        initComponents();
        
        bllHelper.resizeImage(lblImageTesouro, "src/images/logo_tesourinhos.png");
        this.setIconImage(Toolkit.getDefaultToolkit().getImage("src/images/logo_tesourinhos.png"));
        lblDashboard.setBackground(new java.awt.Color(0,0,92));
        
        if(bllDashboard != null) {
            lblNumClientes.setText(Integer.toString(bllDashboard.retornaTotalDeClientes()));
            lblNumVendas.setText(Integer.toString(bllDashboard.retornaTotalDeVendasNoMes()));
            lblNumArrecadacao.setText("R$" + new DecimalFormat("#,##0.00").format(bllDashboard.retornaArrecadacaoMensal()));
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pPrincipal = new javax.swing.JPanel();
        pMenu = new javax.swing.JPanel();
        pPerfil = new javax.swing.JPanel();
        lblImageTesouro = new javax.swing.JLabel();
        lblTituloTesouro = new javax.swing.JLabel();
        lblDashboard = new javax.swing.JLabel();
        lblClientes = new javax.swing.JLabel();
        lblPedidos = new javax.swing.JLabel();
        lblCadastrarClientes = new javax.swing.JLabel();
        lblCadastrarPedidos = new javax.swing.JLabel();
        pTela = new javax.swing.JPanel();
        pDashboard = new javax.swing.JPanel();
        pClientesDados = new javax.swing.JPanel();
        pLogoClientes = new javax.swing.JPanel();
        lblClientesDados = new javax.swing.JLabel();
        lblTituloAbaClientes = new javax.swing.JLabel();
        lblNumClientes = new javax.swing.JLabel();
        pVendasDados = new javax.swing.JPanel();
        pLogoVendas = new javax.swing.JPanel();
        lblLogoVendas = new javax.swing.JLabel();
        lblTituloAbaVendas = new javax.swing.JLabel();
        lblNumVendas = new javax.swing.JLabel();
        pArrecadacaoDados = new javax.swing.JPanel();
        pLogoMoney = new javax.swing.JPanel();
        lblMoneyDados = new javax.swing.JLabel();
        lblTituloAbaArrecadacao = new javax.swing.JLabel();
        lblNumArrecadacao = new javax.swing.JLabel();
        pClientes = new javax.swing.JPanel();
        pTituloClientes = new javax.swing.JPanel();
        lblTituloClientes = new javax.swing.JLabel();
        cbFiltroClientes = new javax.swing.JComboBox<>();
        txtFiltroClientes = new javax.swing.JTextField();
        spClientes = new javax.swing.JScrollPane();
        tbClientes = new javax.swing.JTable();
        btnExcluirCliente = new javax.swing.JButton();
        btnAlterarCliente = new javax.swing.JButton();
        btnConsultaCliente = new javax.swing.JButton();
        btnBackToThePreviousListClientes = new javax.swing.JButton();
        btnGoToTheNextListClientes = new javax.swing.JButton();
        lblTotalCliente = new javax.swing.JLabel();
        pPedidos = new javax.swing.JPanel();
        pTituloPedidos = new javax.swing.JPanel();
        lblTituloPedidos = new javax.swing.JLabel();
        cbFiltroPedidos = new javax.swing.JComboBox<>();
        txtFiltroPedidos = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbPedidos = new javax.swing.JTable();
        btnExcluirPedido = new javax.swing.JButton();
        btnAlterar = new javax.swing.JButton();
        btnConsultarPedido = new javax.swing.JButton();
        pCadastrarClientes = new javax.swing.JPanel();
        pTituloCadastroClientes = new javax.swing.JPanel();
        lblTituloCadastroClientes = new javax.swing.JLabel();
        panelCadastroCliente1 = new javax.swing.JPanel();
        lblCEP = new javax.swing.JLabel();
        txtCEP = new javax.swing.JFormattedTextField();
        lblEstado = new javax.swing.JLabel();
        txtEstado = new javax.swing.JTextField();
        lblCidade = new javax.swing.JLabel();
        txtCidade = new javax.swing.JTextField();
        lblBairro = new javax.swing.JLabel();
        txtBairro = new javax.swing.JTextField();
        lblLogradouro = new javax.swing.JLabel();
        txtLogradouro = new javax.swing.JTextField();
        lblNumero = new javax.swing.JLabel();
        txtNumero = new javax.swing.JFormattedTextField();
        lblComplemento = new javax.swing.JLabel();
        txtComplemento = new javax.swing.JTextField();
        lblCampoCadastroEndereco = new javax.swing.JLabel();
        btnGravarEndereco = new javax.swing.JButton();
        lblIDEndereco = new javax.swing.JLabel();
        txtIDEndereco = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbEnderecosCliente = new javax.swing.JTable();
        lblEnderecosCliente = new javax.swing.JLabel();
        btnAlterarEnderecosCliente = new javax.swing.JButton();
        btnConsultarEnderecosCliente = new javax.swing.JButton();
        panelCadastroCliente = new javax.swing.JPanel();
        lblCampoCadastroCliente = new javax.swing.JLabel();
        lblNomeCliente = new javax.swing.JLabel();
        txtNomeCliente = new javax.swing.JTextField();
        lblCPF = new javax.swing.JLabel();
        txtCPF = new javax.swing.JFormattedTextField();
        lblTelefone = new javax.swing.JLabel();
        txtTelefone = new javax.swing.JFormattedTextField();
        btnGravarCliente = new javax.swing.JButton();
        lblIDCliente = new javax.swing.JLabel();
        txtIDCliente = new javax.swing.JTextField();
        btnCadastrarCliente = new javax.swing.JButton();
        cbFiltroEnderecoCliente = new javax.swing.JComboBox<>();
        txtFiltroEnderecoCliente = new javax.swing.JTextField();
        pCadastrarPedidos = new javax.swing.JPanel();
        pTituloCadastroClientes1 = new javax.swing.JPanel();
        lblTituloCadastroPedidos = new javax.swing.JLabel();
        panelCadastroPedidoItem = new javax.swing.JPanel();
        lblCampoCadastroItem = new javax.swing.JLabel();
        lblItem = new javax.swing.JLabel();
        txtItem = new javax.swing.JTextField();
        lblQuantidadeItem = new javax.swing.JLabel();
        txtQuantidade = new javax.swing.JTextField();
        txtValorItem = new javax.swing.JTextField();
        lblValorItem = new javax.swing.JLabel();
        btnGravarItem = new javax.swing.JButton();
        lblIDItem = new javax.swing.JLabel();
        txtIDItem = new javax.swing.JTextField();
        lblItensPedido = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbItensPedido = new javax.swing.JTable();
        panelCadastroPedido = new javax.swing.JPanel();
        lblCampoCadastroPedido = new javax.swing.JLabel();
        lblCliente = new javax.swing.JLabel();
        cbClientes = new javax.swing.JComboBox<>();
        lblEndereço = new javax.swing.JLabel();
        cbEndereco = new javax.swing.JComboBox<>();
        lblEntrega = new javax.swing.JLabel();
        cbEntrega = new javax.swing.JComboBox<>();
        lblPagamento = new javax.swing.JLabel();
        cbPagamento = new javax.swing.JComboBox<>();
        lblStatusPedido = new javax.swing.JLabel();
        cbStatusPedido = new javax.swing.JComboBox<>();
        lblFrete = new javax.swing.JLabel();
        txtFrete = new javax.swing.JTextField();
        btnGravarPedido = new javax.swing.JButton();
        lblIDPedido = new javax.swing.JLabel();
        txtPedidoID = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setExtendedState(6);
        setMinimumSize(new java.awt.Dimension(1366, 768));
        setSize(new java.awt.Dimension(1366, 768));

        pPrincipal.setBackground(new java.awt.Color(0, 0, 0));
        pPrincipal.setLayout(new java.awt.BorderLayout());

        pMenu.setBackground(new java.awt.Color(0, 0, 47));
        pMenu.setPreferredSize(new java.awt.Dimension(300, 768));

        pPerfil.setBackground(new java.awt.Color(0, 0, 60));

        lblImageTesouro.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        lblTituloTesouro.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        lblTituloTesouro.setForeground(new java.awt.Color(255, 255, 255));
        lblTituloTesouro.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTituloTesouro.setText("Tesourinhos");
        lblTituloTesouro.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);

        javax.swing.GroupLayout pPerfilLayout = new javax.swing.GroupLayout(pPerfil);
        pPerfil.setLayout(pPerfilLayout);
        pPerfilLayout.setHorizontalGroup(
            pPerfilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pPerfilLayout.createSequentialGroup()
                .addGroup(pPerfilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pPerfilLayout.createSequentialGroup()
                        .addGap(73, 73, 73)
                        .addComponent(lblTituloTesouro, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pPerfilLayout.createSequentialGroup()
                        .addGap(84, 84, 84)
                        .addComponent(lblImageTesouro, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(77, Short.MAX_VALUE))
        );
        pPerfilLayout.setVerticalGroup(
            pPerfilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pPerfilLayout.createSequentialGroup()
                .addContainerGap(39, Short.MAX_VALUE)
                .addComponent(lblImageTesouro, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTituloTesouro)
                .addGap(17, 17, 17))
        );

        lblDashboard.setBackground(new java.awt.Color(0, 0, 47));
        lblDashboard.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        lblDashboard.setForeground(new java.awt.Color(255, 255, 255));
        lblDashboard.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblDashboard.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/dashboard_28x28.png"))); // NOI18N
        lblDashboard.setText("Dashboard");
        lblDashboard.setOpaque(true);
        lblDashboard.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblDashboardMouseClicked(evt);
            }
        });

        lblClientes.setBackground(new java.awt.Color(0, 0, 47));
        lblClientes.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        lblClientes.setForeground(new java.awt.Color(255, 255, 255));
        lblClientes.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblClientes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/clientes_28x28.png"))); // NOI18N
        lblClientes.setText("Clientes");
        lblClientes.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        lblClientes.setOpaque(true);
        lblClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblClientesMouseClicked(evt);
            }
        });

        lblPedidos.setBackground(new java.awt.Color(0, 0, 47));
        lblPedidos.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        lblPedidos.setForeground(new java.awt.Color(255, 255, 255));
        lblPedidos.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblPedidos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/pedidos_28x28.png"))); // NOI18N
        lblPedidos.setText("Pedidos");
        lblPedidos.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        lblPedidos.setOpaque(true);
        lblPedidos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblPedidosMouseClicked(evt);
            }
        });

        lblCadastrarClientes.setBackground(new java.awt.Color(0, 0, 47));
        lblCadastrarClientes.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        lblCadastrarClientes.setForeground(new java.awt.Color(255, 255, 255));
        lblCadastrarClientes.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblCadastrarClientes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cadastrar-clientes_48x48.png"))); // NOI18N
        lblCadastrarClientes.setText("Cadastrar Clientes");
        lblCadastrarClientes.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        lblCadastrarClientes.setOpaque(true);
        lblCadastrarClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblCadastrarClientesMouseClicked(evt);
            }
        });

        lblCadastrarPedidos.setBackground(new java.awt.Color(0, 0, 47));
        lblCadastrarPedidos.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        lblCadastrarPedidos.setForeground(new java.awt.Color(255, 255, 255));
        lblCadastrarPedidos.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblCadastrarPedidos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cadastrar-pedidos_48x48.png"))); // NOI18N
        lblCadastrarPedidos.setText("Cadastrar Pedidos");
        lblCadastrarPedidos.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        lblCadastrarPedidos.setOpaque(true);
        lblCadastrarPedidos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblCadastrarPedidosMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout pMenuLayout = new javax.swing.GroupLayout(pMenu);
        pMenu.setLayout(pMenuLayout);
        pMenuLayout.setHorizontalGroup(
            pMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pPerfil, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lblCadastrarPedidos, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lblPedidos, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lblCadastrarClientes, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lblClientes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lblDashboard, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pMenuLayout.setVerticalGroup(
            pMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pMenuLayout.createSequentialGroup()
                .addComponent(pPerfil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(81, 81, 81)
                .addComponent(lblDashboard, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblCadastrarClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblPedidos, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblCadastrarPedidos, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 203, Short.MAX_VALUE))
        );

        pPrincipal.add(pMenu, java.awt.BorderLayout.LINE_START);

        pTela.setBackground(new java.awt.Color(255, 255, 255));
        pTela.setLayout(new java.awt.CardLayout());

        pDashboard.setBackground(new java.awt.Color(226, 226, 226));

        pClientesDados.setBackground(new java.awt.Color(255, 255, 255));

        lblClientesDados.setBackground(new java.awt.Color(0, 204, 255));
        lblClientesDados.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblClientesDados.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/clientes_48x48.png"))); // NOI18N
        lblClientesDados.setOpaque(true);

        javax.swing.GroupLayout pLogoClientesLayout = new javax.swing.GroupLayout(pLogoClientes);
        pLogoClientes.setLayout(pLogoClientesLayout);
        pLogoClientesLayout.setHorizontalGroup(
            pLogoClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblClientesDados, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
        );
        pLogoClientesLayout.setVerticalGroup(
            pLogoClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblClientesDados, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
        );

        lblTituloAbaClientes.setBackground(new java.awt.Color(0, 0, 0));
        lblTituloAbaClientes.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        lblTituloAbaClientes.setForeground(new java.awt.Color(51, 51, 51));
        lblTituloAbaClientes.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTituloAbaClientes.setText("Total de Clientes");

        lblNumClientes.setFont(new java.awt.Font("Arial", 1, 48)); // NOI18N
        lblNumClientes.setForeground(new java.awt.Color(51, 51, 51));
        lblNumClientes.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblNumClientes.setText("0");

        javax.swing.GroupLayout pClientesDadosLayout = new javax.swing.GroupLayout(pClientesDados);
        pClientesDados.setLayout(pClientesDadosLayout);
        pClientesDadosLayout.setHorizontalGroup(
            pClientesDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pClientesDadosLayout.createSequentialGroup()
                .addComponent(pLogoClientes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38)
                .addGroup(pClientesDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblTituloAbaClientes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblNumClientes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 44, Short.MAX_VALUE))
        );
        pClientesDadosLayout.setVerticalGroup(
            pClientesDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pLogoClientes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pClientesDadosLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(lblTituloAbaClientes)
                .addGap(18, 18, 18)
                .addComponent(lblNumClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pVendasDados.setBackground(new java.awt.Color(255, 255, 255));

        pLogoVendas.setBackground(new java.awt.Color(0, 153, 0));

        lblLogoVendas.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblLogoVendas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/vendas_48x48.png"))); // NOI18N

        javax.swing.GroupLayout pLogoVendasLayout = new javax.swing.GroupLayout(pLogoVendas);
        pLogoVendas.setLayout(pLogoVendasLayout);
        pLogoVendasLayout.setHorizontalGroup(
            pLogoVendasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 160, Short.MAX_VALUE)
            .addGroup(pLogoVendasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(lblLogoVendas, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE))
        );
        pLogoVendasLayout.setVerticalGroup(
            pLogoVendasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(pLogoVendasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(lblLogoVendas, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE))
        );

        lblTituloAbaVendas.setBackground(new java.awt.Color(0, 0, 0));
        lblTituloAbaVendas.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        lblTituloAbaVendas.setForeground(new java.awt.Color(51, 51, 51));
        lblTituloAbaVendas.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTituloAbaVendas.setText("Vendas (Mês)");

        lblNumVendas.setFont(new java.awt.Font("Arial", 1, 48)); // NOI18N
        lblNumVendas.setForeground(new java.awt.Color(51, 51, 51));
        lblNumVendas.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblNumVendas.setText("0");

        javax.swing.GroupLayout pVendasDadosLayout = new javax.swing.GroupLayout(pVendasDados);
        pVendasDados.setLayout(pVendasDadosLayout);
        pVendasDadosLayout.setHorizontalGroup(
            pVendasDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pVendasDadosLayout.createSequentialGroup()
                .addComponent(pLogoVendas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(46, 46, 46)
                .addGroup(pVendasDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblTituloAbaVendas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblNumVendas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pVendasDadosLayout.setVerticalGroup(
            pVendasDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pLogoVendas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pVendasDadosLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(lblTituloAbaVendas)
                .addGap(18, 18, 18)
                .addComponent(lblNumVendas, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(24, Short.MAX_VALUE))
        );

        pArrecadacaoDados.setBackground(new java.awt.Color(255, 255, 255));

        pLogoMoney.setBackground(new java.awt.Color(0, 255, 0));

        lblMoneyDados.setBackground(new java.awt.Color(255, 255, 0));
        lblMoneyDados.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblMoneyDados.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/money_48x48.png"))); // NOI18N
        lblMoneyDados.setOpaque(true);

        javax.swing.GroupLayout pLogoMoneyLayout = new javax.swing.GroupLayout(pLogoMoney);
        pLogoMoney.setLayout(pLogoMoneyLayout);
        pLogoMoneyLayout.setHorizontalGroup(
            pLogoMoneyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 160, Short.MAX_VALUE)
            .addGroup(pLogoMoneyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(lblMoneyDados, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE))
        );
        pLogoMoneyLayout.setVerticalGroup(
            pLogoMoneyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 200, Short.MAX_VALUE)
            .addGroup(pLogoMoneyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(lblMoneyDados, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE))
        );

        lblTituloAbaArrecadacao.setBackground(new java.awt.Color(0, 0, 0));
        lblTituloAbaArrecadacao.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        lblTituloAbaArrecadacao.setForeground(new java.awt.Color(51, 51, 51));
        lblTituloAbaArrecadacao.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTituloAbaArrecadacao.setText("Arrecadação (Mês)");

        lblNumArrecadacao.setBackground(new java.awt.Color(51, 51, 51));
        lblNumArrecadacao.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        lblNumArrecadacao.setForeground(new java.awt.Color(51, 51, 51));
        lblNumArrecadacao.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblNumArrecadacao.setText("0");

        javax.swing.GroupLayout pArrecadacaoDadosLayout = new javax.swing.GroupLayout(pArrecadacaoDados);
        pArrecadacaoDados.setLayout(pArrecadacaoDadosLayout);
        pArrecadacaoDadosLayout.setHorizontalGroup(
            pArrecadacaoDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pArrecadacaoDadosLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pArrecadacaoDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblTituloAbaArrecadacao, javax.swing.GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)
                    .addComponent(lblNumArrecadacao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(30, 30, 30))
            .addGroup(pArrecadacaoDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(pArrecadacaoDadosLayout.createSequentialGroup()
                    .addComponent(pLogoMoney, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 200, Short.MAX_VALUE)))
        );
        pArrecadacaoDadosLayout.setVerticalGroup(
            pArrecadacaoDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pArrecadacaoDadosLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(lblTituloAbaArrecadacao)
                .addGap(18, 18, 18)
                .addComponent(lblNumArrecadacao, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(24, Short.MAX_VALUE))
            .addGroup(pArrecadacaoDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(pLogoMoney, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pDashboardLayout = new javax.swing.GroupLayout(pDashboard);
        pDashboard.setLayout(pDashboardLayout);
        pDashboardLayout.setHorizontalGroup(
            pDashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pDashboardLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pDashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pVendasDados, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pArrecadacaoDados, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pClientesDados, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(700, Short.MAX_VALUE))
        );
        pDashboardLayout.setVerticalGroup(
            pDashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pDashboardLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pClientesDados, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pArrecadacaoDados, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pVendasDados, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(149, Short.MAX_VALUE))
        );

        pTela.add(pDashboard, "card2");

        pClientes.setBackground(new java.awt.Color(226, 226, 226));

        pTituloClientes.setBackground(new java.awt.Color(0, 0, 47));

        lblTituloClientes.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        lblTituloClientes.setForeground(new java.awt.Color(255, 255, 255));
        lblTituloClientes.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblTituloClientes.setText("Clientes");

        javax.swing.GroupLayout pTituloClientesLayout = new javax.swing.GroupLayout(pTituloClientes);
        pTituloClientes.setLayout(pTituloClientesLayout);
        pTituloClientesLayout.setHorizontalGroup(
            pTituloClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pTituloClientesLayout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addComponent(lblTituloClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pTituloClientesLayout.setVerticalGroup(
            pTituloClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pTituloClientesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTituloClientes, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
                .addContainerGap())
        );

        cbFiltroClientes.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ClienteID", "Nome", "CPF", "Telefone" }));

        txtFiltroClientes.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtFiltroClientes.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtFiltroClientesKeyReleased(evt);
            }
        });

        tbClientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Cliente ID", "Nome", "CPF", "Telefone"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        spClientes.setViewportView(tbClientes);

        btnExcluirCliente.setText("Excluir");
        btnExcluirCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirClienteActionPerformed(evt);
            }
        });

        btnAlterarCliente.setText("Alterar");
        btnAlterarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAlterarClienteActionPerformed(evt);
            }
        });

        btnConsultaCliente.setText("Consultar Cliente");
        btnConsultaCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConsultaClienteActionPerformed(evt);
            }
        });

        btnBackToThePreviousListClientes.setText("<");
        btnBackToThePreviousListClientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackToThePreviousListClientesActionPerformed(evt);
            }
        });

        btnGoToTheNextListClientes.setText(">");
        btnGoToTheNextListClientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGoToTheNextListClientesActionPerformed(evt);
            }
        });

        lblTotalCliente.setBackground(new java.awt.Color(0, 0, 47));
        lblTotalCliente.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lblTotalCliente.setForeground(new java.awt.Color(255, 255, 255));
        lblTotalCliente.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTotalCliente.setText("0");
        lblTotalCliente.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblTotalCliente.setOpaque(true);

        javax.swing.GroupLayout pClientesLayout = new javax.swing.GroupLayout(pClientes);
        pClientes.setLayout(pClientesLayout);
        pClientesLayout.setHorizontalGroup(
            pClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pTituloClientes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pClientesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(spClientes, javax.swing.GroupLayout.DEFAULT_SIZE, 1054, Short.MAX_VALUE)
                    .addGroup(pClientesLayout.createSequentialGroup()
                        .addComponent(cbFiltroClientes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtFiltroClientes))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pClientesLayout.createSequentialGroup()
                        .addComponent(btnBackToThePreviousListClientes)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblTotalCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnGoToTheNextListClientes)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnExcluirCliente)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAlterarCliente)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnConsultaCliente)))
                .addContainerGap())
        );
        pClientesLayout.setVerticalGroup(
            pClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pClientesLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(pTituloClientes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbFiltroClientes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtFiltroClientes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(spClientes, javax.swing.GroupLayout.DEFAULT_SIZE, 614, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnExcluirCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnAlterarCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnConsultaCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnBackToThePreviousListClientes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnGoToTheNextListClientes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblTotalCliente, javax.swing.GroupLayout.DEFAULT_SIZE, 22, Short.MAX_VALUE))
                .addGap(13, 13, 13))
        );

        pTela.add(pClientes, "card3");

        pPedidos.setBackground(new java.awt.Color(226, 226, 226));

        pTituloPedidos.setBackground(new java.awt.Color(0, 0, 47));

        lblTituloPedidos.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        lblTituloPedidos.setForeground(new java.awt.Color(255, 255, 255));
        lblTituloPedidos.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblTituloPedidos.setText("Pedidos");

        javax.swing.GroupLayout pTituloPedidosLayout = new javax.swing.GroupLayout(pTituloPedidos);
        pTituloPedidos.setLayout(pTituloPedidosLayout);
        pTituloPedidosLayout.setHorizontalGroup(
            pTituloPedidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pTituloPedidosLayout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addComponent(lblTituloPedidos, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(710, Short.MAX_VALUE))
        );
        pTituloPedidosLayout.setVerticalGroup(
            pTituloPedidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pTituloPedidosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTituloPedidos, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
                .addContainerGap())
        );

        cbFiltroPedidos.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pedido", "Endereço", "Cliente", "Data de Criação", "Entrega", "Pagamento", "Status", "Frete", "Total" }));

        txtFiltroPedidos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtFiltroPedidosKeyReleased(evt);
            }
        });

        tbPedidos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Pedido ID", "Endereço", "Cliente", "Data de Criação", "Entrega", "Pagamento", "Status", "Frete", "Total"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tbPedidos);

        btnExcluirPedido.setText("Excluir");
        btnExcluirPedido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirPedidoActionPerformed(evt);
            }
        });

        btnAlterar.setText("Alterar");

        btnConsultarPedido.setText("Consultar Pedido");

        javax.swing.GroupLayout pPedidosLayout = new javax.swing.GroupLayout(pPedidos);
        pPedidos.setLayout(pPedidosLayout);
        pPedidosLayout.setHorizontalGroup(
            pPedidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pTituloPedidos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pPedidosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pPedidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addGroup(pPedidosLayout.createSequentialGroup()
                        .addComponent(cbFiltroPedidos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtFiltroPedidos))
                    .addGroup(pPedidosLayout.createSequentialGroup()
                        .addComponent(btnExcluirPedido)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAlterar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnConsultarPedido)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pPedidosLayout.setVerticalGroup(
            pPedidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pPedidosLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(pTituloPedidos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pPedidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbFiltroPedidos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtFiltroPedidos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 622, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pPedidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnExcluirPedido)
                    .addComponent(btnAlterar)
                    .addComponent(btnConsultarPedido))
                .addContainerGap())
        );

        pTela.add(pPedidos, "card4");

        pCadastrarClientes.setBackground(new java.awt.Color(226, 226, 226));

        pTituloCadastroClientes.setBackground(new java.awt.Color(0, 0, 47));

        lblTituloCadastroClientes.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        lblTituloCadastroClientes.setForeground(new java.awt.Color(255, 255, 255));
        lblTituloCadastroClientes.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblTituloCadastroClientes.setText("Cadastrar Clientes");

        javax.swing.GroupLayout pTituloCadastroClientesLayout = new javax.swing.GroupLayout(pTituloCadastroClientes);
        pTituloCadastroClientes.setLayout(pTituloCadastroClientesLayout);
        pTituloCadastroClientesLayout.setHorizontalGroup(
            pTituloCadastroClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pTituloCadastroClientesLayout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addComponent(lblTituloCadastroClientes)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pTituloCadastroClientesLayout.setVerticalGroup(
            pTituloCadastroClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pTituloCadastroClientesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTituloCadastroClientes, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
                .addContainerGap())
        );

        panelCadastroCliente1.setBackground(new java.awt.Color(213, 217, 223));

        lblCEP.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lblCEP.setForeground(new java.awt.Color(0, 0, 47));
        lblCEP.setText("CEP:");

        try {
            txtCEP.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("#####-###")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        lblEstado.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lblEstado.setForeground(new java.awt.Color(0, 0, 47));
        lblEstado.setText("Estado:");

        lblCidade.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lblCidade.setForeground(new java.awt.Color(0, 0, 47));
        lblCidade.setText("Cidade:");

        lblBairro.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lblBairro.setForeground(new java.awt.Color(0, 0, 47));
        lblBairro.setText("Bairro:");

        lblLogradouro.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lblLogradouro.setForeground(new java.awt.Color(0, 0, 47));
        lblLogradouro.setText("Logradouro:");

        lblNumero.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lblNumero.setForeground(new java.awt.Color(0, 0, 47));
        lblNumero.setText("Número:");

        try {
            txtNumero.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("########")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        lblComplemento.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lblComplemento.setForeground(new java.awt.Color(0, 0, 47));
        lblComplemento.setText("Complemento:");

        lblCampoCadastroEndereco.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        lblCampoCadastroEndereco.setForeground(new java.awt.Color(0, 0, 47));
        lblCampoCadastroEndereco.setText("Cadastrar Endereço");

        btnGravarEndereco.setText("Gravar Endereço");
        btnGravarEndereco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGravarEnderecoActionPerformed(evt);
            }
        });

        lblIDEndereco.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lblIDEndereco.setForeground(new java.awt.Color(0, 0, 47));
        lblIDEndereco.setText("ID:");

        txtIDEndereco.setEnabled(false);

        javax.swing.GroupLayout panelCadastroCliente1Layout = new javax.swing.GroupLayout(panelCadastroCliente1);
        panelCadastroCliente1.setLayout(panelCadastroCliente1Layout);
        panelCadastroCliente1Layout.setHorizontalGroup(
            panelCadastroCliente1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCadastroCliente1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelCadastroCliente1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelCadastroCliente1Layout.createSequentialGroup()
                        .addGroup(panelCadastroCliente1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelCadastroCliente1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(lblCEP, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblEstado, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblBairro, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtEstado, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtBairro, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtCEP, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lblIDEndereco, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtIDEndereco))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelCadastroCliente1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtComplemento, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtLogradouro)
                            .addComponent(txtCidade, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtNumero)
                            .addGroup(panelCadastroCliente1Layout.createSequentialGroup()
                                .addGroup(panelCadastroCliente1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblComplemento, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblLogradouro, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblCidade, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblNumero, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 153, Short.MAX_VALUE))))
                    .addGroup(panelCadastroCliente1Layout.createSequentialGroup()
                        .addGroup(panelCadastroCliente1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblCampoCadastroEndereco)
                            .addComponent(btnGravarEndereco))
                        .addGap(0, 335, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panelCadastroCliente1Layout.setVerticalGroup(
            panelCadastroCliente1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCadastroCliente1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblCampoCadastroEndereco)
                .addGap(18, 18, 18)
                .addGroup(panelCadastroCliente1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelCadastroCliente1Layout.createSequentialGroup()
                        .addGroup(panelCadastroCliente1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(panelCadastroCliente1Layout.createSequentialGroup()
                                .addGroup(panelCadastroCliente1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(panelCadastroCliente1Layout.createSequentialGroup()
                                        .addComponent(lblCEP, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtCEP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(panelCadastroCliente1Layout.createSequentialGroup()
                                        .addComponent(lblLogradouro, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtLogradouro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panelCadastroCliente1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblCidade, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtCidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblBairro, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelCadastroCliente1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtBairro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNumero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(panelCadastroCliente1Layout.createSequentialGroup()
                        .addComponent(lblNumero, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)))
                .addGroup(panelCadastroCliente1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblComplemento, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblIDEndereco, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelCadastroCliente1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtComplemento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtIDEndereco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
                .addComponent(btnGravarEndereco)
                .addContainerGap())
        );

        tbEnderecosCliente.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID do Endereço", "Endereço"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tbEnderecosCliente);
        if (tbEnderecosCliente.getColumnModel().getColumnCount() > 0) {
            tbEnderecosCliente.getColumnModel().getColumn(0).setResizable(false);
        }

        lblEnderecosCliente.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        lblEnderecosCliente.setForeground(new java.awt.Color(0, 0, 47));
        lblEnderecosCliente.setText("Endereços do Cliente");

        btnAlterarEnderecosCliente.setText("Alterar");
        btnAlterarEnderecosCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAlterarEnderecosClienteActionPerformed(evt);
            }
        });

        btnConsultarEnderecosCliente.setText("Consultar");
        btnConsultarEnderecosCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConsultarEnderecosClienteActionPerformed(evt);
            }
        });

        panelCadastroCliente.setBackground(new java.awt.Color(213, 217, 223));

        lblCampoCadastroCliente.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        lblCampoCadastroCliente.setForeground(new java.awt.Color(0, 0, 47));
        lblCampoCadastroCliente.setText("Cadastrar Cliente");

        lblNomeCliente.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lblNomeCliente.setForeground(new java.awt.Color(0, 0, 47));
        lblNomeCliente.setText("Nome:");

        lblCPF.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lblCPF.setForeground(new java.awt.Color(0, 0, 47));
        lblCPF.setText("CPF:");

        try {
            txtCPF.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("###.###.###-##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        lblTelefone.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lblTelefone.setForeground(new java.awt.Color(0, 0, 47));
        lblTelefone.setText("Telefone:");

        try {
            txtTelefone.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("(##)#####-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        btnGravarCliente.setText("Gravar Cliente");

        lblIDCliente.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lblIDCliente.setForeground(new java.awt.Color(0, 0, 47));
        lblIDCliente.setText("ID:");

        txtIDCliente.setEnabled(false);

        javax.swing.GroupLayout panelCadastroClienteLayout = new javax.swing.GroupLayout(panelCadastroCliente);
        panelCadastroCliente.setLayout(panelCadastroClienteLayout);
        panelCadastroClienteLayout.setHorizontalGroup(
            panelCadastroClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCadastroClienteLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelCadastroClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCPF)
                    .addComponent(txtTelefone)
                    .addComponent(txtIDCliente)
                    .addGroup(panelCadastroClienteLayout.createSequentialGroup()
                        .addGroup(panelCadastroClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblCampoCadastroCliente)
                            .addComponent(lblCPF, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnGravarCliente)
                            .addComponent(lblNomeCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblIDCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 353, Short.MAX_VALUE))
                    .addComponent(txtNomeCliente))
                .addContainerGap())
        );
        panelCadastroClienteLayout.setVerticalGroup(
            panelCadastroClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCadastroClienteLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblCampoCadastroCliente)
                .addGap(18, 18, 18)
                .addComponent(lblIDCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtIDCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblNomeCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNomeCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblCPF, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCPF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 42, Short.MAX_VALUE)
                .addComponent(btnGravarCliente)
                .addContainerGap())
        );

        btnCadastrarCliente.setText("Cadastrar Cliente");
        btnCadastrarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCadastrarClienteActionPerformed(evt);
            }
        });

        cbFiltroEnderecoCliente.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Endereço ID", "Endereço" }));

        txtFiltroEnderecoCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtFiltroEnderecoClienteKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout pCadastrarClientesLayout = new javax.swing.GroupLayout(pCadastrarClientes);
        pCadastrarClientes.setLayout(pCadastrarClientesLayout);
        pCadastrarClientesLayout.setHorizontalGroup(
            pCadastrarClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pTituloCadastroClientes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pCadastrarClientesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pCadastrarClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelCadastroCliente1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelCadastroCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pCadastrarClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 561, Short.MAX_VALUE)
                    .addGroup(pCadastrarClientesLayout.createSequentialGroup()
                        .addComponent(btnAlterarEnderecosCliente)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnConsultarEnderecosCliente)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnCadastrarCliente))
                    .addGroup(pCadastrarClientesLayout.createSequentialGroup()
                        .addComponent(lblEnderecosCliente)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(pCadastrarClientesLayout.createSequentialGroup()
                        .addComponent(cbFiltroEnderecoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtFiltroEnderecoCliente)))
                .addContainerGap())
        );
        pCadastrarClientesLayout.setVerticalGroup(
            pCadastrarClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pCadastrarClientesLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(pTituloCadastroClientes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pCadastrarClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pCadastrarClientesLayout.createSequentialGroup()
                        .addComponent(lblEnderecosCliente)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pCadastrarClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbFiltroEnderecoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtFiltroEnderecoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pCadastrarClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnAlterarEnderecosCliente)
                            .addComponent(btnConsultarEnderecosCliente)
                            .addComponent(btnCadastrarCliente)))
                    .addGroup(pCadastrarClientesLayout.createSequentialGroup()
                        .addComponent(panelCadastroCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panelCadastroCliente1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        pTela.add(pCadastrarClientes, "card5");

        pCadastrarPedidos.setBackground(new java.awt.Color(226, 226, 226));

        pTituloCadastroClientes1.setBackground(new java.awt.Color(0, 0, 47));

        lblTituloCadastroPedidos.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        lblTituloCadastroPedidos.setForeground(new java.awt.Color(255, 255, 255));
        lblTituloCadastroPedidos.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblTituloCadastroPedidos.setText("Cadastrar Pedidos");

        javax.swing.GroupLayout pTituloCadastroClientes1Layout = new javax.swing.GroupLayout(pTituloCadastroClientes1);
        pTituloCadastroClientes1.setLayout(pTituloCadastroClientes1Layout);
        pTituloCadastroClientes1Layout.setHorizontalGroup(
            pTituloCadastroClientes1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pTituloCadastroClientes1Layout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addComponent(lblTituloCadastroPedidos)
                .addContainerGap(799, Short.MAX_VALUE))
        );
        pTituloCadastroClientes1Layout.setVerticalGroup(
            pTituloCadastroClientes1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pTituloCadastroClientes1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTituloCadastroPedidos, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
                .addContainerGap())
        );

        panelCadastroPedidoItem.setBackground(new java.awt.Color(213, 217, 223));

        lblCampoCadastroItem.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        lblCampoCadastroItem.setForeground(new java.awt.Color(0, 0, 47));
        lblCampoCadastroItem.setText("Cadastrar Item");

        lblItem.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lblItem.setForeground(new java.awt.Color(0, 0, 47));
        lblItem.setText("Item:");

        lblQuantidadeItem.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lblQuantidadeItem.setForeground(new java.awt.Color(0, 0, 47));
        lblQuantidadeItem.setText("Quantidade:");

        lblValorItem.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lblValorItem.setForeground(new java.awt.Color(0, 0, 47));
        lblValorItem.setText("Valor:");

        btnGravarItem.setText("Gravar Item");

        lblIDItem.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lblIDItem.setForeground(new java.awt.Color(0, 0, 47));
        lblIDItem.setText("ID:");

        txtIDItem.setEnabled(false);

        javax.swing.GroupLayout panelCadastroPedidoItemLayout = new javax.swing.GroupLayout(panelCadastroPedidoItem);
        panelCadastroPedidoItem.setLayout(panelCadastroPedidoItemLayout);
        panelCadastroPedidoItemLayout.setHorizontalGroup(
            panelCadastroPedidoItemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelCadastroPedidoItemLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelCadastroPedidoItemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtValorItem)
                    .addComponent(txtQuantidade, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelCadastroPedidoItemLayout.createSequentialGroup()
                        .addGroup(panelCadastroPedidoItemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelCadastroPedidoItemLayout.createSequentialGroup()
                                .addComponent(lblItem, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(txtItem))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelCadastroPedidoItemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblIDItem, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtIDItem, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelCadastroPedidoItemLayout.createSequentialGroup()
                        .addGroup(panelCadastroPedidoItemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblCampoCadastroItem)
                            .addComponent(lblQuantidadeItem, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnGravarItem)
                            .addComponent(lblValorItem, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panelCadastroPedidoItemLayout.setVerticalGroup(
            panelCadastroPedidoItemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCadastroPedidoItemLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblCampoCadastroItem)
                .addGap(18, 18, 18)
                .addGroup(panelCadastroPedidoItemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelCadastroPedidoItemLayout.createSequentialGroup()
                        .addComponent(lblItem, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtItem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelCadastroPedidoItemLayout.createSequentialGroup()
                        .addComponent(lblIDItem, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtIDItem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblQuantidadeItem, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtQuantidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblValorItem, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtValorItem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44)
                .addComponent(btnGravarItem)
                .addGap(14, 14, 14))
        );

        lblItensPedido.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        lblItensPedido.setForeground(new java.awt.Color(0, 0, 47));
        lblItensPedido.setText("Itens do Pedido");

        tbItensPedido.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID do Item", "Item", "Quantidade", "Valor"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(tbItensPedido);

        panelCadastroPedido.setBackground(new java.awt.Color(213, 217, 223));

        lblCampoCadastroPedido.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        lblCampoCadastroPedido.setForeground(new java.awt.Color(0, 0, 47));
        lblCampoCadastroPedido.setText("Cadastrar Pedido");

        lblCliente.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lblCliente.setForeground(new java.awt.Color(0, 0, 47));
        lblCliente.setText("Cliente:");

        lblEndereço.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lblEndereço.setForeground(new java.awt.Color(0, 0, 47));
        lblEndereço.setText("Endereço:");

        lblEntrega.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lblEntrega.setForeground(new java.awt.Color(0, 0, 47));
        lblEntrega.setText("Entrega:");

        lblPagamento.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lblPagamento.setForeground(new java.awt.Color(0, 0, 47));
        lblPagamento.setText("Pagamento:");

        lblStatusPedido.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lblStatusPedido.setForeground(new java.awt.Color(0, 0, 47));
        lblStatusPedido.setText("Status");

        lblFrete.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lblFrete.setForeground(new java.awt.Color(0, 0, 47));
        lblFrete.setText("Frete:");

        btnGravarPedido.setText("Gravar Pedido");
        btnGravarPedido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGravarPedidoActionPerformed(evt);
            }
        });

        lblIDPedido.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lblIDPedido.setForeground(new java.awt.Color(0, 0, 47));
        lblIDPedido.setText("ID:");

        txtPedidoID.setEnabled(false);

        javax.swing.GroupLayout panelCadastroPedidoLayout = new javax.swing.GroupLayout(panelCadastroPedido);
        panelCadastroPedido.setLayout(panelCadastroPedidoLayout);
        panelCadastroPedidoLayout.setHorizontalGroup(
            panelCadastroPedidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCadastroPedidoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelCadastroPedidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelCadastroPedidoLayout.createSequentialGroup()
                        .addGroup(panelCadastroPedidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblCampoCadastroPedido)
                            .addComponent(lblEndereço, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblEntrega, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblFrete, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnGravarPedido))
                        .addGap(205, 205, 205))
                    .addGroup(panelCadastroPedidoLayout.createSequentialGroup()
                        .addGroup(panelCadastroPedidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbEndereco, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cbEntrega, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(panelCadastroPedidoLayout.createSequentialGroup()
                                .addGroup(panelCadastroPedidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblPagamento, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cbPagamento, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panelCadastroPedidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panelCadastroPedidoLayout.createSequentialGroup()
                                        .addComponent(lblStatusPedido, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addComponent(cbStatusPedido, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addComponent(txtFrete)
                            .addGroup(panelCadastroPedidoLayout.createSequentialGroup()
                                .addGroup(panelCadastroPedidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cbClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panelCadastroPedidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblIDPedido, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtPedidoID, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())))
        );
        panelCadastroPedidoLayout.setVerticalGroup(
            panelCadastroPedidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCadastroPedidoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblCampoCadastroPedido)
                .addGap(18, 18, 18)
                .addGroup(panelCadastroPedidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelCadastroPedidoLayout.createSequentialGroup()
                        .addComponent(lblCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelCadastroPedidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbClientes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtPedidoID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(panelCadastroPedidoLayout.createSequentialGroup()
                        .addComponent(lblIDPedido, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)))
                .addComponent(lblEndereço, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbEndereco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblEntrega, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbEntrega, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelCadastroPedidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelCadastroPedidoLayout.createSequentialGroup()
                        .addComponent(lblPagamento, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbPagamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelCadastroPedidoLayout.createSequentialGroup()
                        .addComponent(lblStatusPedido, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbStatusPedido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblFrete, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtFrete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnGravarPedido)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jButton1.setText("Alterar");

        jButton2.setText("Deletar");

        jButton3.setText("Consulta");

        jButton4.setText("Cadastrar Pedido");

        javax.swing.GroupLayout pCadastrarPedidosLayout = new javax.swing.GroupLayout(pCadastrarPedidos);
        pCadastrarPedidos.setLayout(pCadastrarPedidosLayout);
        pCadastrarPedidosLayout.setHorizontalGroup(
            pCadastrarPedidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pTituloCadastroClientes1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pCadastrarPedidosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pCadastrarPedidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelCadastroPedidoItem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelCadastroPedido, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pCadastrarPedidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3)
                    .addGroup(pCadastrarPedidosLayout.createSequentialGroup()
                        .addComponent(lblItensPedido)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(pCadastrarPedidosLayout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton4)))
                .addContainerGap())
        );
        pCadastrarPedidosLayout.setVerticalGroup(
            pCadastrarPedidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pCadastrarPedidosLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(pTituloCadastroClientes1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pCadastrarPedidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pCadastrarPedidosLayout.createSequentialGroup()
                        .addComponent(lblItensPedido)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pCadastrarPedidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton1)
                            .addComponent(jButton2)
                            .addComponent(jButton3)
                            .addComponent(jButton4)))
                    .addGroup(pCadastrarPedidosLayout.createSequentialGroup()
                        .addComponent(panelCadastroPedido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(panelCadastroPedidoItem, javax.swing.GroupLayout.PREFERRED_SIZE, 290, Short.MAX_VALUE)))
                .addContainerGap())
        );

        pTela.add(pCadastrarPedidos, "card6");

        pPrincipal.add(pTela, java.awt.BorderLayout.CENTER);

        getContentPane().add(pPrincipal, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lblDashboardMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblDashboardMouseClicked
        // TODO add your handling code here:
        bllHelper.alterColorMenu(lblDashboard, lblClientes, lblCadastrarClientes, lblPedidos, lblCadastrarPedidos);
        bllHelper.visiblePanel(pDashboard, pClientes, pPedidos, pCadastrarClientes, pCadastrarPedidos);
        
        if(bllDashboard != null) {
            lblNumClientes.setText(Integer.toString(bllDashboard.retornaTotalDeClientes()));
            lblNumVendas.setText(Integer.toString(bllDashboard.retornaTotalDeVendasNoMes()));
            lblNumArrecadacao.setText("R$" + new DecimalFormat("#,##0.00").format(bllDashboard.retornaArrecadacaoMensal()));
        }
    }//GEN-LAST:event_lblDashboardMouseClicked

    private void lblClientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblClientesMouseClicked
        // TODO add your handling code here:
        bllHelper.alterColorMenu(lblClientes, lblDashboard, lblCadastrarClientes, lblPedidos, lblCadastrarPedidos);
        bllHelper.visiblePanel(pClientes, pDashboard, pPedidos, pCadastrarClientes, pCadastrarPedidos);
        
        disableQueryCustumer();
        
        lblTotalCliente.setText(Integer.toString(bllCliente.totalDeClientes()));
        
        selectClientes();
    }//GEN-LAST:event_lblClientesMouseClicked

    private void lblCadastrarClientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCadastrarClientesMouseClicked
        // TODO add your handling code here:
        bllHelper.alterColorMenu(lblCadastrarClientes, lblDashboard, lblClientes, lblPedidos, lblCadastrarPedidos);
        bllHelper.visiblePanel(pCadastrarClientes, pDashboard, pClientes, pPedidos, pCadastrarPedidos);
        
        disableQueryCustumer();
    }//GEN-LAST:event_lblCadastrarClientesMouseClicked

    private void lblPedidosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblPedidosMouseClicked
        // TODO add your handling code here:
        bllHelper.alterColorMenu(lblPedidos, lblDashboard, lblClientes, lblCadastrarClientes, lblCadastrarPedidos);
        bllHelper.visiblePanel(pPedidos, pDashboard, pClientes, pCadastrarClientes, pCadastrarPedidos);
        
        disableQueryCustumer();
        
        selectPedidos();
    }//GEN-LAST:event_lblPedidosMouseClicked

    private void lblCadastrarPedidosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCadastrarPedidosMouseClicked
        // TODO add your handling code here:
        bllHelper.alterColorMenu(lblCadastrarPedidos, lblDashboard, lblClientes, lblCadastrarClientes, lblPedidos);
        bllHelper.visiblePanel(pCadastrarPedidos, pDashboard, pClientes, pCadastrarClientes, pPedidos);
        
        disableQueryCustumer();
    }//GEN-LAST:event_lblCadastrarPedidosMouseClicked

    private void btnGravarEnderecoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGravarEnderecoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnGravarEnderecoActionPerformed

    private void btnGravarPedidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGravarPedidoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnGravarPedidoActionPerformed

    private void btnExcluirClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirClienteActionPerformed
        objModal.setDeleteID(0);
        objModal.setDesc(null);
        
        if (tbClientes.getSelectedRow() != -1){
            FormPrincipal form = new FormPrincipal();
            
            objModal.setDesc(objConstante.confirmDeleteCliente);
            objModal.setDeleteID(tbClientes.getValueAt(tbClientes.getSelectedRow(), 0).hashCode());
            
            Modal modal = new Modal(form, true);
            modal.setVisible(true);
            
            selectClientes();
        }
        else {
            FormPrincipal form = new FormPrincipal();
            
            objModal.setDesc(objConstante.anyoneItemSelected);
            
            Modal modal = new Modal(form, true);
            modal.setVisible(true);
        }
    }//GEN-LAST:event_btnExcluirClienteActionPerformed

    private void txtFiltroClientesKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFiltroClientesKeyReleased
        searchCliente();
    }//GEN-LAST:event_txtFiltroClientesKeyReleased

    private void btnExcluirPedidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirPedidoActionPerformed
        objModal.setDeleteID(0);
        objModal.setDesc(null);
        
        if (tbPedidos.getSelectedRow() != -1){
            FormPrincipal form = new FormPrincipal();
            
            objModal.setDesc(objConstante.confirmDeletePedido);
            objModal.setDeleteID(tbPedidos.getValueAt(tbPedidos.getSelectedRow(), 0).hashCode());
            
            Modal modal = new Modal(form, true);
            modal.setVisible(true);
            
            selectPedidos();
        }
        else {
            FormPrincipal form = new FormPrincipal();
            
            objModal.setDesc(objConstante.anyoneItemSelected);
            
            Modal modal = new Modal(form, true);
            modal.setVisible(true);
        }
    }//GEN-LAST:event_btnExcluirPedidoActionPerformed

    private void btnConsultaClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultaClienteActionPerformed
        if (tbClientes.getSelectedRow() != -1){
            if(txtFiltroClientes.getText().equals("")){
                objCliente = bllCliente.findObjectByID(tbClientes.getValueAt(tbClientes.getSelectedRow(), 0).hashCode());
            } else {
                Integer clienteID = Integer.valueOf((String) tbClientes.getValueAt(tbClientes.getSelectedRow(), 0));
                objCliente = bllCliente.findObjectByID(clienteID);
            }
            
            selectAdressByClienteID(objCliente.getClienteID());
            
            txtIDCliente.setText(String.valueOf(objCliente.getClienteID()));
            txtNomeCliente.setText(objCliente.getNome());
            txtCPF.setText(objCliente.getCpf());
            txtTelefone.setText(objCliente.getTelCelular());
            
            bllHelper.disableFieldsCustumers(txtNomeCliente, txtCPF, txtTelefone);
            bllHelper.disableFieldsAdress(txtCEP, txtLogradouro, txtEstado, txtCidade, txtBairro, txtNumero, txtComplemento);
            
            btnGravarCliente.setEnabled(false);
            bllHelper.disableButtonsAdress(btnGravarEndereco, btnAlterarEnderecosCliente, btnConsultarEnderecosCliente);
            
            bllHelper.alterColorMenu(lblCadastrarClientes, lblDashboard, lblClientes, lblCadastrarPedidos, lblPedidos);
            bllHelper.visiblePanel(pCadastrarClientes, pDashboard, pClientes, pCadastrarPedidos, pPedidos);
            
            if(tbEnderecosCliente.getRowCount() != 0){
                btnConsultarEnderecosCliente.setEnabled(true);
                txtFiltroEnderecoCliente.setEnabled(true);
                cbFiltroEnderecoCliente.setEnabled(true);
            } else {
                btnConsultarEnderecosCliente.setEnabled(false);
                txtFiltroEnderecoCliente.setEnabled(false);
                cbFiltroEnderecoCliente.setEnabled(false);
            }
        }
        else {
            FormPrincipal form = new FormPrincipal();
            
            objModal.setDesc(objConstante.anyoneItemSelected);
            
            Modal modal = new Modal(form, true);
            modal.setVisible(true);
        }
    }//GEN-LAST:event_btnConsultaClienteActionPerformed

    private void btnConsultarEnderecosClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultarEnderecosClienteActionPerformed
        bllHelper.disableFieldsAdress(txtCEP, txtLogradouro, txtEstado, txtCidade, txtBairro, txtNumero, txtComplemento);
        btnGravarEndereco.setEnabled(false);
        
        if (tbEnderecosCliente.getSelectedRow() != -1){
            if(txtFiltroEnderecoCliente.getText().equals("")){
                objEndereco = bllEndereco.findObjectByID(tbEnderecosCliente.getValueAt(tbEnderecosCliente.getSelectedRow(), 0).hashCode());
            } else {
                Integer enderecoID = Integer.valueOf((String) tbEnderecosCliente.getValueAt(tbEnderecosCliente.getSelectedRow(), 0));
                objEndereco = bllEndereco.findObjectByID(enderecoID);
            }
            
            txtCEP.setText(objEndereco.getCep());
            txtLogradouro.setText(objEndereco.getLogradouro());
            txtEstado.setText(objEndereco.getEstado());
            txtCidade.setText(objEndereco.getCidade());
            txtBairro.setText(objEndereco.getBairro());
            txtNumero.setText(String.valueOf(objEndereco.getNumero()));
            txtIDEndereco.setText(String.valueOf(objEndereco.getEnderecoID()));
            txtComplemento.setText(objEndereco.getComplemento());
        }
        else {
            FormPrincipal form = new FormPrincipal();
            
            objModal.setDesc(objConstante.anyoneItemSelected);
            
            Modal modal = new Modal(form, true);
            modal.setVisible(true);
        }
    }//GEN-LAST:event_btnConsultarEnderecosClienteActionPerformed

    private void btnCadastrarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCadastrarClienteActionPerformed
        disableQueryCustumer();
    }//GEN-LAST:event_btnCadastrarClienteActionPerformed

    private void txtFiltroPedidosKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFiltroPedidosKeyReleased
        searchPedido();
    }//GEN-LAST:event_txtFiltroPedidosKeyReleased

    private void txtFiltroEnderecoClienteKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFiltroEnderecoClienteKeyReleased
        searchAddressByClienteID();
    }//GEN-LAST:event_txtFiltroEnderecoClienteKeyReleased

    private void btnAlterarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAlterarClienteActionPerformed
        bllHelper.disableFieldsAdress(txtCEP, txtLogradouro, txtEstado, txtCidade, txtBairro, txtNumero, txtComplemento);
        
        if (tbClientes.getSelectedRow() != -1){
            if(txtFiltroClientes.getText().equals("")){
                objCliente = bllCliente.findObjectByID(tbClientes.getValueAt(tbClientes.getSelectedRow(), 0).hashCode());
            } else {
                Integer clienteID = Integer.valueOf((String) tbClientes.getValueAt(tbClientes.getSelectedRow(), 0));
                objCliente = bllCliente.findObjectByID(clienteID);
            }
            
            selectAdressByClienteID(objCliente.getClienteID());
            
            txtIDCliente.setText(String.valueOf(objCliente.getClienteID()));
            txtNomeCliente.setText(objCliente.getNome());
            txtCPF.setText(objCliente.getCpf());
            txtTelefone.setText(objCliente.getTelCelular());
            
            btnGravarCliente.setEnabled(true);
            
            bllHelper.alterColorMenu(lblCadastrarClientes, lblDashboard, lblClientes, lblCadastrarPedidos, lblPedidos);
            bllHelper.visiblePanel(pCadastrarClientes, pDashboard, pClientes, pCadastrarPedidos, pPedidos);
            
            btnGravarEndereco.setEnabled(false);
        }
        else{
            FormPrincipal form = new FormPrincipal();
            
            objModal.setDesc(objConstante.anyoneItemSelected);
            
            Modal modal = new Modal(form, true);
            modal.setVisible(true);
        }
    }//GEN-LAST:event_btnAlterarClienteActionPerformed

    private void btnAlterarEnderecosClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAlterarEnderecosClienteActionPerformed
        
    }//GEN-LAST:event_btnAlterarEnderecosClienteActionPerformed

    private void btnBackToThePreviousListClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackToThePreviousListClientesActionPerformed
        if(txtFiltroClientes.getText().equals("")){
            if(paginationClientes.getCurrentPage() > 1)
                paginationClientes.setCurrentPage(paginationClientes.getCurrentPage() -1);
        
            selectClientes();
        }
    }//GEN-LAST:event_btnBackToThePreviousListClientesActionPerformed

    private void btnGoToTheNextListClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGoToTheNextListClientesActionPerformed
        // TODO add your handling code here:
        if(txtFiltroClientes.getText().equals("")){
            paginationClientes.setCurrentPage(paginationClientes.getCurrentPage() +1);
        
            selectClientes();
        }
    }//GEN-LAST:event_btnGoToTheNextListClientesActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FormPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormPrincipal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAlterar;
    private javax.swing.JButton btnAlterarCliente;
    private javax.swing.JButton btnAlterarEnderecosCliente;
    private javax.swing.JButton btnBackToThePreviousListClientes;
    private javax.swing.JButton btnCadastrarCliente;
    private javax.swing.JButton btnConsultaCliente;
    private javax.swing.JButton btnConsultarEnderecosCliente;
    private javax.swing.JButton btnConsultarPedido;
    private javax.swing.JButton btnExcluirCliente;
    private javax.swing.JButton btnExcluirPedido;
    private javax.swing.JButton btnGoToTheNextListClientes;
    private javax.swing.JButton btnGravarCliente;
    private javax.swing.JButton btnGravarEndereco;
    private javax.swing.JButton btnGravarItem;
    private javax.swing.JButton btnGravarPedido;
    private javax.swing.JComboBox<String> cbClientes;
    private javax.swing.JComboBox<String> cbEndereco;
    private javax.swing.JComboBox<String> cbEntrega;
    private javax.swing.JComboBox<String> cbFiltroClientes;
    private javax.swing.JComboBox<String> cbFiltroEnderecoCliente;
    private javax.swing.JComboBox<String> cbFiltroPedidos;
    private javax.swing.JComboBox<String> cbPagamento;
    private javax.swing.JComboBox<String> cbStatusPedido;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblBairro;
    private javax.swing.JLabel lblCEP;
    private javax.swing.JLabel lblCPF;
    private javax.swing.JLabel lblCadastrarClientes;
    private javax.swing.JLabel lblCadastrarPedidos;
    private javax.swing.JLabel lblCampoCadastroCliente;
    private javax.swing.JLabel lblCampoCadastroEndereco;
    private javax.swing.JLabel lblCampoCadastroItem;
    private javax.swing.JLabel lblCampoCadastroPedido;
    private javax.swing.JLabel lblCidade;
    private javax.swing.JLabel lblCliente;
    private javax.swing.JLabel lblClientes;
    private javax.swing.JLabel lblClientesDados;
    private javax.swing.JLabel lblComplemento;
    private javax.swing.JLabel lblDashboard;
    private javax.swing.JLabel lblEnderecosCliente;
    private javax.swing.JLabel lblEndereço;
    private javax.swing.JLabel lblEntrega;
    private javax.swing.JLabel lblEstado;
    private javax.swing.JLabel lblFrete;
    private javax.swing.JLabel lblIDCliente;
    private javax.swing.JLabel lblIDEndereco;
    private javax.swing.JLabel lblIDItem;
    private javax.swing.JLabel lblIDPedido;
    private javax.swing.JLabel lblImageTesouro;
    private javax.swing.JLabel lblItem;
    private javax.swing.JLabel lblItensPedido;
    private javax.swing.JLabel lblLogoVendas;
    private javax.swing.JLabel lblLogradouro;
    private javax.swing.JLabel lblMoneyDados;
    private javax.swing.JLabel lblNomeCliente;
    private javax.swing.JLabel lblNumArrecadacao;
    private javax.swing.JLabel lblNumClientes;
    private javax.swing.JLabel lblNumVendas;
    private javax.swing.JLabel lblNumero;
    private javax.swing.JLabel lblPagamento;
    private javax.swing.JLabel lblPedidos;
    private javax.swing.JLabel lblQuantidadeItem;
    private javax.swing.JLabel lblStatusPedido;
    private javax.swing.JLabel lblTelefone;
    private javax.swing.JLabel lblTituloAbaArrecadacao;
    private javax.swing.JLabel lblTituloAbaClientes;
    private javax.swing.JLabel lblTituloAbaVendas;
    private javax.swing.JLabel lblTituloCadastroClientes;
    private javax.swing.JLabel lblTituloCadastroPedidos;
    private javax.swing.JLabel lblTituloClientes;
    private javax.swing.JLabel lblTituloPedidos;
    private javax.swing.JLabel lblTituloTesouro;
    private javax.swing.JLabel lblTotalCliente;
    private javax.swing.JLabel lblValorItem;
    private javax.swing.JPanel pArrecadacaoDados;
    private javax.swing.JPanel pCadastrarClientes;
    private javax.swing.JPanel pCadastrarPedidos;
    private javax.swing.JPanel pClientes;
    private javax.swing.JPanel pClientesDados;
    private javax.swing.JPanel pDashboard;
    private javax.swing.JPanel pLogoClientes;
    private javax.swing.JPanel pLogoMoney;
    private javax.swing.JPanel pLogoVendas;
    private javax.swing.JPanel pMenu;
    private javax.swing.JPanel pPedidos;
    private javax.swing.JPanel pPerfil;
    private javax.swing.JPanel pPrincipal;
    private javax.swing.JPanel pTela;
    private javax.swing.JPanel pTituloCadastroClientes;
    private javax.swing.JPanel pTituloCadastroClientes1;
    private javax.swing.JPanel pTituloClientes;
    private javax.swing.JPanel pTituloPedidos;
    private javax.swing.JPanel pVendasDados;
    private javax.swing.JPanel panelCadastroCliente;
    private javax.swing.JPanel panelCadastroCliente1;
    private javax.swing.JPanel panelCadastroPedido;
    private javax.swing.JPanel panelCadastroPedidoItem;
    private javax.swing.JScrollPane spClientes;
    private javax.swing.JTable tbClientes;
    private javax.swing.JTable tbEnderecosCliente;
    private javax.swing.JTable tbItensPedido;
    private javax.swing.JTable tbPedidos;
    private javax.swing.JTextField txtBairro;
    private javax.swing.JFormattedTextField txtCEP;
    private javax.swing.JFormattedTextField txtCPF;
    private javax.swing.JTextField txtCidade;
    private javax.swing.JTextField txtComplemento;
    private javax.swing.JTextField txtEstado;
    private javax.swing.JTextField txtFiltroClientes;
    private javax.swing.JTextField txtFiltroEnderecoCliente;
    private javax.swing.JTextField txtFiltroPedidos;
    private javax.swing.JTextField txtFrete;
    private javax.swing.JTextField txtIDCliente;
    private javax.swing.JTextField txtIDEndereco;
    private javax.swing.JTextField txtIDItem;
    private javax.swing.JTextField txtItem;
    private javax.swing.JTextField txtLogradouro;
    private javax.swing.JTextField txtNomeCliente;
    private javax.swing.JFormattedTextField txtNumero;
    private javax.swing.JTextField txtPedidoID;
    private javax.swing.JTextField txtQuantidade;
    private javax.swing.JFormattedTextField txtTelefone;
    private javax.swing.JTextField txtValorItem;
    // End of variables declaration//GEN-END:variables
}
