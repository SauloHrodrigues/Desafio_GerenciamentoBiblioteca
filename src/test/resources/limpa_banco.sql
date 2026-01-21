-- Limpa primeiro as tabelas de relacionamento
DELETE FROM livros_autores;
DELETE FROM aluguel_livros;

-- Depois as tabelas principais
DELETE FROM alugueis;
DELETE FROM livros;
DELETE FROM autores;
DELETE FROM locatarios;

-- (opcional) resetar IDs no H2
ALTER TABLE livros ALTER COLUMN id RESTART WITH 1;
ALTER TABLE autores ALTER COLUMN id RESTART WITH 1;
ALTER TABLE locatarios ALTER COLUMN id RESTART WITH 1;
ALTER TABLE alugueis ALTER COLUMN id RESTART WITH 1;
