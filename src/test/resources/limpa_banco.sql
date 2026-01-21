-- Limpa primeiro as tabelas de relacionamento
DELETE FROM livros_autores;

-- Depois as tabelas principais
DELETE FROM livros;
DELETE FROM autores;
DELETE FROM locatarios;

-- (opcional) resetar IDs no H2
ALTER TABLE livros ALTER COLUMN id RESTART WITH 1;
ALTER TABLE autores ALTER COLUMN id RESTART WITH 1;
