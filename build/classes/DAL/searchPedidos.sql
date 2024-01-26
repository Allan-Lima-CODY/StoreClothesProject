SELECT P.PedidoID,
	   CONCAT(P.EnderecoID, " - ", E.Logradouro, ", ", E.Numero) Endereco,
       CONCAT(P.ClienteID, " - ", C.Nome) Cliente,
       DATE_FORMAT(P.DataCriacao, '%d/%m/%Y') DataCriacao,
       ENTRG.Descricao,
       PGMNT.Descricao,
       S.Descricao,
       P.Frete,
       P.Total
	FROM pedido P
    LEFT JOIN endereco E ON (P.EnderecoID = E.EnderecoID)
    LEFT JOIN cliente C ON (P.ClienteID = C.ClienteID)
    LEFT JOIN entrega ENTRG ON (P.EntregaID = ENTRG.EntregaID)
    LEFT JOIN pagamento PGMNT ON (P.PagamentoID = PGMNT.PagamentoID)
    LEFT JOIN status_pedido S ON (P.StatusID = S.StatusID)
    WHERE x;