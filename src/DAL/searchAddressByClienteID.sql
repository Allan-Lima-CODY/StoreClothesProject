SELECT E.EnderecoID,
       CONCAT(E.Logradouro, \", \", E.Numero, \" - \", E.Cidade, \", \", E.Estado, \" - \", E.CEP) Emdereco
    FROM endereco E
    WHERE E.ClienteID = x
    AND x;