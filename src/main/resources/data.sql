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

INSERT INTO cliente
(id, nome, email, senha, data_nasc, telefone, cpf, cep, estado, cidade, bairro, rua, numero, latitude, longitude)
VALUES
(5, 'Marcos Vinicius', 'marcos@gmail.com', '$2a$10$QvpTkHUC36.jMoXZMO9pWusTiYEelC9ZcDSYGDEniQlQuMV0RuiKW', '1993-01-01', '11919455724', '12247670901', '01414001', 'SP', 'São Paulo', 'Cerqueira César', 'Rua Haddock Lobo', '595', -23.561860, -46.665830);

INSERT INTO pet
(fk_cliente, nome, especie, sexo)
VALUES
(1, 'Fluffy', 'CACHORRO', 'M'),
(1, 'Mel', 'GATO', 'F'),
(5, 'Spike', 'CACHORRO', 'M'),
(5, 'Lin', 'ROEDOR', 'F');

INSERT INTO servico
(fk_petshop, nome, preco, descricao)
VALUES
(2, 0, 34.90, 'Banho completo e perfume ao final do processo'),
(2, 1, 74.90, 'Corte de pelos a seco'),
(2, 2, 99.90, 'Tosa e lavagem completa do pet com direito a perfume e acessórios'),
(3, 0, 29.90, 'Para o bem-estar do seu melhor amigo!'),
(3, 1, 54.90, 'Pets também merecem um novo corte de cabelo'),
(3, 2, 69.90, 'Serviço completo para o seu pet'),
(4, 0, 32.90, 'Banho com sabão espumante'),
(4, 1, 44.90, 'Tosa com profissionais de primeira');

INSERT INTO agendamento
(data_hora, fk_cliente, fk_pet, fk_petshop, fk_servico)
VALUES
('2023-06-13 13:30:00', 5, 3, 2, 1),
('2023-06-13 15:00:00', 5, 3, 2, 2),
('2023-06-14 12:30:00', 5, 4, 2, 1),
('2023-06-14 14:00:00', 5, 4, 2, 2),
('2023-06-15 12:00:00', 5, 3, 2, 1),
('2023-06-16 13:00:00', 5, 3, 2, 1),
('2023-06-17 12:00:00', 5, 3, 2, 1),
('2023-06-17 13:00:00', 5, 3, 2, 2),
('2023-06-17 14:00:00', 5, 4, 2, 1),
('2023-06-17 15:00:00', 5, 4, 2, 2),
('2023-06-18 14:00:00', 5, 3, 2, 1),
('2023-06-19 15:00:00', 5, 4, 2, 1),
('2023-06-20 13:00:00', 5, 3, 2, 1),
('2023-01-14 13:00:00', 5, 3, 2, 1),
('2023-01-15 13:00:00', 5, 3, 2, 1),
('2023-01-16 13:00:00', 5, 3, 2, 1),
('2023-02-10 12:00:00', 5, 3, 2, 3),
('2023-02-14 12:00:00', 5, 4, 2, 3),
('2023-02-15 13:00:00', 5, 4, 2, 3),
('2023-02-15 14:00:00', 5, 3, 2, 3),
('2023-03-15 13:00:00', 5, 3, 2, 3),
('2023-03-16 14:00:00', 5, 3, 2, 3),
('2023-03-17 12:00:00', 5, 3, 2, 1),
('2023-03-17 13:00:00', 5, 4, 2, 2),
('2023-03-18 14:00:00', 5, 4, 2, 1),
('2023-04-18 15:00:00', 5, 3, 2, 3),
('2023-04-20 12:00:00', 5, 3, 2, 1),
('2023-05-19 13:00:00', 5, 3, 2, 1),
('2023-05-19 13:00:00', 5, 4, 2, 1),
('2023-05-19 13:00:00', 5, 4, 2, 1);

INSERT INTO avaliacao
(fk_cliente, fk_petshop, nota)
VALUES
(5, 2, 4.7),
(5, 2, 4.2),
(5, 2, 4.0),
(5, 2, 3.8),
(5, 2, 4.5);

INSERT INTO avaliacao
(fk_cliente, fk_petshop, nota)
VALUES
(5, 2, 5.0),
(5, 2, 5.0),
(5, 2, 4.0),
(5, 3, 3.0),
(5, 4, 4.0);

UPDATE cliente
SET bairro = 'Cerqueira César', cep = '01414001', cidade = 'São Paulo', estado = 'SP', numero = '595', rua = 'Rua Haddock Lobo', telefone = '11902345678', cpf = '12345678901', data_nasc = '1993-01-01'
WHERE id = 1;