USE apptiavo;

SELECT COUNT(*) FROM cliente;
SELECT * FROM endereco;
SELECT * FROM pedido;

SELECT * FROM itens_pedido;

INSERT INTO cliente VALUES(1, 'Teste', '454.964.108-86', '11941668120');
INSERT INTO cliente VALUES(9, 'Teste4', '454.964.108-89', '11941668123');
INSERT INTO cliente VALUES(10, 'Teste4', '454.964.108-89', '11941668123');

SELECT @ultimoClienteID := MAX(ClienteID) FROM cliente;
INSERT INTO cliente VALUES(@ultimoClienteID + 1, 'Teste4', '454.964.108-89', '11941668123');

DELIMITER //

CREATE PROCEDURE ExemploLoop()
BEGIN
  DECLARE contador INT DEFAULT 0;

  WHILE contador < 50 DO
    -- Obter o valor máximo de ClienteID na tabela a cada iteração
    SELECT MAX(ClienteID) INTO @maxClienteID FROM cliente;
    
    -- Se não houver registros na tabela, defina @maxClienteID como 0
    IF @maxClienteID IS NULL THEN
      SET @maxClienteID = 0;
    END IF;
    
    -- Usar o valor máximo obtido no loop
    INSERT INTO cliente VALUES(@maxClienteID + 1, 'Teste4', '454.964.108-89', '11941668123');

    -- Incrementar o contador
    SET contador = contador + 1;
  END WHILE;
END;
//
DELIMITER ;

CALL ExemploLoop();

INSERT INTO endereco VALUES(1, 1, '13226560', 'SP', 'Jundiaí', 'Santa Gestrudes', 'Rua Pão', 29, 'dsfsdgdfg');
INSERT INTO endereco VALUES(2, 8, '13226560', 'SP', 'Jundiaí', 'Santa Gestrudes', 'Rua Pão', 29, 'dsfsdgdfg');
INSERT INTO endereco VALUES(3, 9, '13226560', 'SP', 'Jundiaí', 'Santa Gestrudes', 'Rua Pão', 29, 'dsfsdgdfg');

INSERT INTO pedido VALUES(2, 1, 1, DATE_SUB(CURDATE(), INTERVAL 3 DAY), 1, 1, 14.15, 14.15, 1);
INSERT INTO pedido VALUES(3, 1, 1, DATE_SUB(CURDATE(), INTERVAL 3 DAY), 1, 1, 14.15, 14.15, 1);
INSERT INTO pedido VALUES(4, 1, 1, DATE_SUB(CURDATE(), INTERVAL 1 YEAR), 1, 1, 14.15, 14.15, 1);
INSERT INTO pedido VALUES(5, 1, 1, DATE_SUB(CURDATE(), INTERVAL 3 DAY), 1, 1, 14.15, 14.15, 1);
INSERT INTO pedido VALUES(6, 1, 1, DATE_SUB(CURDATE(), INTERVAL 3 DAY), 1, 1, 14.15, 14.15, 1);
INSERT INTO pedido VALUES(8, 2, 8, DATE_SUB(CURDATE(), INTERVAL 3 DAY), 1, 1, 14.15, 14.15, 1);
DELETE FROM pedido WHERE PedidoID = 1;

INSERT INTO itens_pedido VALUES(19, 2, 'Pão', 2, 14.15);
INSERT INTO itens_pedido VALUES(20, 2, 'Pão', 2, 14.15);

UPDATE itens_pedido
	SET PedidoID = 8
    WHERE Itens_PedidoID IN (9, 10);
    
UPDATE pedido
	SET ClienteID = 9;
    
UPDATE endereco
	SET ClienteID = 9
    WHERE EnderecoID = 1;
    
SELECT PedidoID FROM pedido WHERE ClienteID = 8;
                                   
SELECT item.Itens_PedidoID
	FROM itens_pedido item
    LEFT JOIN pedido P ON (item.PedidoID = P.PedidoID)
    LEFT JOIN Cliente C ON (P.ClienteID = C.ClienteID)
    WHERE C.ClienteID = 8;
    
SELECT P.PedidoID,
	   CONCAT(P.EnderecoID, " - ", E.Logradouro, ", ", E.Numero) Address, 
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
    WHERE CONCAT(P.ClienteID, " - ", C.Nome) LIKE "%9%"