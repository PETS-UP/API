INSERT INTO petshop
    (id, nome, email, senha, telefone, razao_social, cnpj, cep, estado, cidade, bairro, rua, numero)
VALUES
    (
        1,
        'Petmania',
        'petmania@gmail.com',
        '#1Exemplo',
        '11987654321',
        'Petmania Serviços de Cuidados Animais',
        '12345678901234',
        '01001000',
        'SP',
        'São Paulo',
        'Sé',
        'Praça da Sé',
        '11'
    );

INSERT INTO cliente
    (id, nome, email, senha, data_nasc, telefone, cpf, cep, estado, cidade, bairro, rua, numero)
VALUES
    (1, 'Ana', 'ana@gmail.com', '#1Exemplo', '1993-01-01', '11912345678', '12345678901', '01001000', 'SP', 'São Paulo', 'Sé', 'Praça da Sé', '11'),
    (2, 'Elisa', 'elisa@gmail.com', '#1Exemplo', '2003-10-02', '11998765431', '09876543210', '01001000', 'SP', 'São Paulo', 'Sé', 'Praça da Sé', '11');

INSERT INTO pet
    (fk_cliente, nome, especie, raca, sexo, data_nasc, castrado)
VALUES
    (1, 'Fluffy', 1, 2, 'M', '2022-01-01', 1),
    (2, 'Kira', 2, 3, 'F', '2021-02-02', 0);

INSERT INTO servico
    (fk_petshop, nome, preco, descricao)
VALUES
    (1, 1, 34.90, 'Banho completo e perfume ao final do processo'),
    (1, 2, 74.90, 'Corte de pelos a seco'),
    (1, 3, 99.90, 'Tosa e lavagem completa do pet com direito a perfume e acessórios');

INSERT INTO agendamento
    (fk_cliente, fk_petshop, fk_pet, fk_servico, data_hora)
VALUES
    (1, 1, 1, 3, '2033-01-01 13:30:00'),
    (2, 1, 2, 1, '2033-01-01 17:00:00');

INSERT INTO avaliacao
    (fk_cliente, fk_petshop, nota)
VALUES
    (1, 1, 4.7),
    (2, 1, 4.2);

INSERT INTO favorito
    (fk_cliente, fk_petshop)
VALUES
    (1, 1);
