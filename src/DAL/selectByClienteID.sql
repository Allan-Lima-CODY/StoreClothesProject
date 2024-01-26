SELECT E.EnderecoID,
       E.CEP,
       E.Estado,
       E.Cidade,
       E.Logradouro,
       E.Numero
    FROM endereco E
    WHERE E.ClienteID = x;