-- Limpa primeiro as tabelas de relacionamento

DELETE FROM aluguel_livros;

-- Depois as tabelas principais
DELETE FROM alugueis;


-- (opcional) resetar IDs no H2
ALTER TABLE alugueis ALTER COLUMN id RESTART WITH 1;
