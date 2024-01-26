SELECT Pdd.PedidoID,
       CONCAT(Endrc.EnderecoID, \"- \", Endrc.Logradouro, ", ", Endrc.Numero) Endereco,
       C.Nome,
       DATE_FORMAT(Pdd.DataCriacao, '%d/%m/%Y') DataCriacao,
       Entrg.Descricao,
       Pgmnt.Descricao,
       SP.Descricao,
       Pdd.Frete,
       Pdd.Total
	FROM pedido Pdd
    LEFT JOIN endereco Endrc ON (Endrc.EnderecoID = Pdd.EnderecoID)
    LEFT JOIN cliente C ON (C.ClienteID = Pdd.ClienteID)
    LEFT JOIN entrega Entrg ON (Entrg.EntregaID = Pdd.EntregaID)
    LEFT JOIN pagamento Pgmnt ON (Pgmnt.PagamentoID = Pdd.PagamentoID)
    LEFT JOIN status_pedido SP ON (SP.StatusID = Pdd.StatusID);