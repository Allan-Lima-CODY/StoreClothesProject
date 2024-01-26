DELETE
	FROM itens_pedido
		WHERE Itens_PedidoID IN
			(SELECT itens FROM(
				SELECT ip.Itens_PedidoID itens
					FROM itens_pedido ip
					LEFT JOIN pedido P ON (ip.PedidoID = P.PedidoID)
					LEFT JOIN Cliente C ON (P.ClienteID = C.ClienteID)
					WHERE C.ClienteID = x) AS ItensPedido);
DELETE FROM pedido WHERE ClienteID = x;
DELETE FROM endereco WHERE ClienteID = x;
DELETE FROM cliente WHERE ClienteID = x;