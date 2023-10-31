INSERT INTO cliente
(nome, email, senha, data_nasc, telefone, cpf, cep, estado, cidade, bairro, rua, numero, latitude, longitude, imagem_perfil)
VALUES
('Ana Beatriz', 'ana@gmail.com', '$2a$10$QvpTkHUC36.jMoXZMO9pWusTiYEelC9ZcDSYGDEniQlQuMV0RuiKW', '1993-01-01', '11902345678', '12345678901', '01414001', 'SP', 'São Paulo', 'Cerqueira César', 'Rua Haddock Lobo', '595', -23.561860, -46.665830, 'https://petsupstorage.blob.core.windows.net/imagesstorage/ICON-PROFILE.png');

INSERT INTO petshop
(nome, email, senha, telefone, razao_social, cnpj, cep, estado, cidade, bairro, rua, numero, hora_abertura, hora_fechamento, imagem_perfil)
VALUES
('PetsUp Pet Shop', 'petsup@gmail.com', '$2a$10$QvpTkHUC36.jMoXZMO9pWusTiYEelC9ZcDSYGDEniQlQuMV0RuiKW', '11912345678', 'PetsUp Serviços de Cuidados a Animais LTDA', '12345678901234', '01412000', 'SP', 'São Paulo', 'Cerqueira César', 'Rua Augusta', '1818', '01:00:00', '00:00:00', 'https://petsupstorage.blob.core.windows.net/imagesstorage/ICON-PETSHOP.png'),
('Petmania', 'petmania@gmail.com', '$2a$10$QvpTkHUC36.jMoXZMO9pWusTiYEelC9ZcDSYGDEniQlQuMV0RuiKW', '11987654321', 'Petmania Atendimento a Animais LTDA', '12345678901235', '01419100', 'SP', 'São Paulo', 'Cerqueira César', 'Alameda Santos', '2053', '12:00:00', '20:00:00', 'https://petsupstorage.blob.core.windows.net/imagesstorage/ICON-PETSHOP.png'),
('Petlove', 'petlove@gmail.com', '$2a$10$QvpTkHUC36.jMoXZMO9pWusTiYEelC9ZcDSYGDEniQlQuMV0RuiKW', '11901234567', 'Petlove Serviços a Animais LTDA', '12345678901236', '05418001', 'SP', 'São Paulo', 'Pinheiros', 'Rua Deputado Lacerda Franco', '268', '13:00:00', '21:30:00', 'https://petsupstorage.blob.core.windows.net/imagesstorage/ICON-PETSHOP.png');

INSERT INTO cliente
(nome, email, senha, data_nasc, telefone, cpf, cep, estado, cidade, bairro, rua, numero, latitude, longitude)
VALUES
('Marcos Vinicius', 'marcos@gmail.com', '$2a$10$QvpTkHUC36.jMoXZMO9pWusTiYEelC9ZcDSYGDEniQlQuMV0RuiKW', '1993-01-01', '11919455724', '12247670901', '01414001', 'SP', 'São Paulo', 'Cerqueira César', 'Rua Haddock Lobo', '595', -23.561860, -46.665830);

INSERT INTO pet
(fk_cliente, nome, especie, sexo)
VALUES
(1, 'Fluffy', 'CACHORRO', 'M'),
(1, 'Mel', 'GATO', 'F'),
(2, 'Spike', 'CACHORRO', 'M'),
(2, 'Lin', 'ROEDOR', 'F');

INSERT INTO servico
(fk_petshop, nome, preco, descricao)
VALUES
(1, 0, 34.90, 'Banho completo e perfume ao final do processo'),
(1, 1, 74.90, 'Corte de pelos a seco'),
(1, 2, 99.90, 'Tosa e lavagem completa do pet com direito a perfume e acessórios'),
(2, 0, 29.90, 'Para o bem-estar do seu melhor amigo!'),
(2, 1, 54.90, 'Pets também merecem um novo corte de cabelo'),
(2, 2, 69.90, 'Serviço completo para o seu pet'),
(3, 0, 32.90, 'Banho com sabão espumante'),
(3, 1, 44.90, 'Tosa com profissionais de primeira');

INSERT INTO agendamento
(data_hora, fk_cliente, fk_pet, fk_petshop, fk_servico)
VALUES
('2023-09-13 13:30:00', 2, 3, 1, 1),
('2023-09-13 15:00:00', 2, 3, 1, 2),
('2023-09-14 12:30:00', 2, 4, 1, 1),
('2023-09-14 14:00:00', 2, 4, 1, 2),
('2023-09-15 12:00:00', 2, 3, 1, 1),
('2023-09-16 13:00:00', 2, 3, 1, 1),
('2023-08-17 12:00:00', 2, 3, 1, 1),
('2023-08-17 13:00:00', 2, 3, 1, 2),
('2023-08-17 14:00:00', 2, 4, 1, 1),
('2023-08-17 15:00:00', 2, 4, 1, 2),
('2023-08-18 14:00:00', 2, 3, 1, 1),
('2023-08-19 15:00:00', 2, 4, 1, 1),
('2023-08-20 13:00:00', 2, 3, 1, 1),
('2023-08-14 13:00:00', 2, 3, 1, 1),
('2023-08-15 13:00:00', 2, 3, 1, 1),
('2023-08-16 13:00:00', 2, 3, 1, 1),
('2023-08-10 12:00:00', 2, 3, 1, 3),
('2023-08-14 12:00:00', 2, 4, 1, 3),
('2023-08-15 13:00:00', 2, 4, 1, 3),
('2023-08-15 14:00:00', 2, 3, 1, 3),
('2023-08-15 13:00:00', 2, 3, 1, 3),
('2023-08-16 14:00:00', 2, 3, 1, 3),
('2023-08-17 12:00:00', 2, 3, 1, 1),
('2023-08-17 13:00:00', 2, 4, 1, 2),
('2023-08-18 14:00:00', 2, 4, 1, 1),
('2023-08-18 15:00:00', 2, 3, 1, 3),
('2023-08-20 12:00:00', 2, 3, 1, 1),
('2023-08-19 13:00:00', 2, 3, 1, 1),
('2023-08-19 13:00:00', 2, 4, 1, 1),
('2023-08-19 13:00:00', 2, 4, 1, 1);

INSERT INTO avaliacao
(fk_cliente, fk_petshop, nota)
VALUES
(2, 1, 5.0),
(2, 1, 5.0),
(2, 1, 4.0),
(2, 2, 3.0),
(2, 3, 4.0);

UPDATE cliente
SET bairro = 'Cerqueira César', cep = '01414001', cidade = 'São Paulo', estado = 'SP', numero = '595', rua = 'Rua Haddock Lobo', telefone = '11902345678', cpf = '12345678901', data_nasc = '1993-01-01'
WHERE id = 1;