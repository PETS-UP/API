INSERT INTO cliente
(id, nome, email, senha, data_nasc, telefone, cpf, cep, estado, cidade, bairro, rua, numero, latitude, longitude)
VALUES
(1, 'Ana Beatriz', 'ana@gmail.com', '$2a$10$QvpTkHUC36.jMoXZMO9pWusTiYEelC9ZcDSYGDEniQlQuMV0RuiKW', '1993-01-01', '11902345678', '12345678901', '01414001', 'SP', 'São Paulo', 'Cerqueira César', 'Rua Haddock Lobo', '595', -23.561860, -46.665830);

INSERT INTO petshop
(id, nome, email, senha, telefone, razao_social, cnpj, cep, estado, cidade, bairro, rua, numero)
VALUES
(2, 'PetsUp Pet Shop', 'petsup@gmail.com', '$2a$10$QvpTkHUC36.jMoXZMO9pWusTiYEelC9ZcDSYGDEniQlQuMV0RuiKW', '11912345678', 'PetsUp Serviços de Cuidados a Animais LTDA', '12345678901234', '01412000', 'SP', 'São Paulo', 'Cerqueira César', 'Rua Augusta', '1818'),
(3, 'Petmania', 'petmania@gmail.com', '$2a$10$QvpTkHUC36.jMoXZMO9pWusTiYEelC9ZcDSYGDEniQlQuMV0RuiKW', '11987654321', 'Petmania Atendimento a Animais LTDA', '12345678901235', '01419100', 'SP', 'São Paulo', 'Cerqueira César', 'Alameda Santos', '2053'),
(4, 'Petlove', 'petlove@gmail.com', '$2a$10$QvpTkHUC36.jMoXZMO9pWusTiYEelC9ZcDSYGDEniQlQuMV0RuiKW', '11901234567', 'Petlove Serviços a Animais LTDA', '12345678901236', '05418001', 'SP', 'São Paulo', 'Pinheiros', 'Rua Deputado Lacerda Franco', '268');

INSERT INTO pet
(fk_cliente, nome, especie, sexo)
VALUES
(1, 'Fluffy', 'CACHORRO', 'M'),
(1, 'Mel', 'GATO', 'F');

INSERT INTO servico
(fk_petshop, nome, preco, descricao)
VALUES
(2, 1, 34.90, 'Banho completo e perfume ao final do processo'),
(2, 2, 74.90, 'Corte de pelos a seco'),
(2, 0, 99.90, 'Tosa e lavagem completa do pet com direito a perfume e acessórios'),
(3, 1, 29.90, 'Para o bem-estar do seu melhor amigo!'),
(3, 2, 54.90, 'Pets também merecem um novo corte de cabelo'),
(3, 0, 69.90, 'Serviço completo para o seu pet'),
(4, 1, 32.90, 'Banho com sabão espumante'),
(4, 2, 44.90, 'Tosa com profissionais de primeira');

INSERT INTO agendamento
(fk_cliente, fk_petshop, fk_pet, fk_servico, data_hora)
VALUES
(1, 2, 1, 1, '2023-06-24 13:30:00'),
(1, 2, 2, 1, '2023-06-24 15:00:00'),
(1, 2, 1, 2, '2023-06-30 14:30:00');

INSERT INTO avaliacao
(fk_cliente, fk_petshop, nota)
VALUES
(1, 2, 4.7),
(1, 2, 4.2),
(1, 2, 4.0),
(1, 2, 3.8),
(1, 2, 4.5);

INSERT INTO avaliacao
(fk_cliente, fk_petshop, nota)
VALUES
(1, 3, 4.7),
(1, 3, 4.5),
(1, 3, 4.5),
(1, 3, 3.9),
(1, 3, 4.5);

select * from cliente;
INSERT INTO favorito
(fk_cliente, fk_petshop)
VALUES
(1, 2);

INSERT INTO cliente_petshop_subscriber
(id, fk_cliente_id, fk_petshop_id)
VALUES
(1, 1, 2);

UPDATE cliente
SET bairro = 'Cerqueira César', cep = '01414001', cidade = 'São Paulo', estado = 'SP', numero = '595', rua = 'Rua Haddock Lobo', telefone = '11902345678', cpf = '12345678901', data_nasc = '1993-01-01'
WHERE id = 1;