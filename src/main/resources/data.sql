INSERT INTO Petshop
    (nome, email, senha, telefone, razao_social, cnpj, cep, estado, cidade, bairro, rua, numero)
VALUES
    (
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

INSERT INTO Cliente
    (nome, email, senha)
VALUES
    ('Ana', 'ana@gmail.com', '#1Exemplo'),
    ('Elisa', 'elisa@gmail.com', '#1Exemplo');

INSERT INTO Pet
    (fk_cliente, nome, especie, raca, sexo, data_nasc, castrado)
VALUES
    (1, 'Fluffy', 'CACHORRO', 'LABRADOR', 'M', '2022-01-01', 1),
    (2, 'Kira', 'GATO', 'EGIPCIO', 'F', '2021-02-02', 0);

INSERT INTO Servico
    (fk_petshop, nome, preco, descricao)
VALUES
    (1, 'BANHO', 34.90, 'Banho completo e perfume ao final do processo'),
    (1, 'TOSA', 74.90, 'Corte de pelos a seco'),
    (1, 'BANHO_E_TOSA', 99.90, 'Tosa e lavagem completa do pet com direito a perfume e acessórios');

INSERT INTO Agendamento
    (fk_cliente, fk_petshop, fk_pet, fk_servico, data_hora)
VALUES
    (1, 1, 1, 3, '2033-01-01 13:30:00'),
    (2, 1, 2, 1, '2033-01-01 17:00:00');

INSERT INTO AvaliacaoPetshop
    (fk_cliente, fk_petshop, nota)
VALUES
    (1, 1, 4.7),
    (2, 1, 4.2);

INSERT INTO Favorito
    (fk_cliente, fk_petshop)
VALUES
    (1, 1);
