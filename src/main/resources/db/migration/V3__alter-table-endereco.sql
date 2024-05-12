ALTER TABLE endereco
ADD CONSTRAINT fk_pessoa_id FOREIGN KEY (pessoa_id) REFERENCES pessoas(id);
