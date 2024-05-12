create table pessoas(
  id bigint not null auto_increment,
  nome varchar(100) not null,
  data_nascimento varchar(10) not null,

  primary key(id)
)