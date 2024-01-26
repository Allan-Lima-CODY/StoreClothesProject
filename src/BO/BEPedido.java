/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BO;

import BO.BECliente;
import java.util.Date;

/**
 *
 * @author allan
 */
public class BEPedido {
    private int pedidoID;
    private BEEndereco endereco;
    private BECliente cliente;
    private Date dataCriacao;
    private BEEntrega entrega;
    private BEPagamento pagamento;
    private Double frete;
    private Double total;
    private BEStatus status;

    public int getPedidoID() {
        return pedidoID;
    }

    public void setPedidoID(int pedidoID) {
        this.pedidoID = pedidoID;
    }

    public BEEndereco getEndereco() {
        return endereco;
    }

    public void setEndereco(BEEndereco endereco) {
        this.endereco = endereco;
    }

    public BECliente getCliente() {
        return cliente;
    }

    public void setCliente(BECliente cliente) {
        this.cliente = cliente;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public BEEntrega getEntrega() {
        return entrega;
    }

    public void setEntrega(BEEntrega entrega) {
        this.entrega = entrega;
    }

    public BEPagamento getPagamento() {
        return pagamento;
    }

    public void setPagamento(BEPagamento pagamento) {
        this.pagamento = pagamento;
    }

    public Double getFrete() {
        return frete;
    }

    public void setFrete(Double frete) {
        this.frete = frete;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public BEStatus getStatus() {
        return status;
    }

    public void setStatus(BEStatus status) {
        this.status = status;
    }
    
    
}
