CREATE TABLE endereco (
    id bigint not null auto_increment,
    logradouro VARCHAR(255) NOT NULL,
    cep VARCHAR(10) NOT NULL,
    numero INT NOT NULL,
    cidade VARCHAR(100) NOT NULL,
    estado VARCHAR(100) NOT NULL,
    pessoa_id bigint,
    principal tinyint,

    primary key(id),
    FOREIGN KEY (pessoa_id) REFERENCES pessoas(id)
);