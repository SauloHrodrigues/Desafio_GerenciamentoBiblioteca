-- Livro
INSERT INTO livros (titulo, isbn, data_de_publicacao, categoria_do_livro,status_livro,ativo)
VALUES
      ('Dom Casmurro', '9788535910663','1899-01-01','ROMANCE',0,true),
      ('O Senhor dos Anéis: A Sociedade do Anel', '9788533613375', '1954-07-29', 'ROMANCE', 0,true);


-- Relacionamento Livro ↔ Autor
INSERT INTO livros_autores (livro_id, autor_id)
VALUES (1, 1), (2, 3);
