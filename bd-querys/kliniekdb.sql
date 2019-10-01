-- Criando Tabelas
create table usuario(
	usuarioid serial primary key,
	username varchar(30) unique not null,
	senha varchar(100) not null,
	estado varchar(30) Default 'activo',
	dataCriacao Timestamp DEFAULT (current_timestamp AT TIME ZONE 'UTC')
);

create table pessoa (
	pessoaid serial primary key,
	usuarioid int references usuario,
	bi varchar(20) unique not null,
	nuit varchar(20) unique,
	primeiroNome varchar(30) not null,
	apelido varchar(30) not null,
	email varchar(70) unique,
	dataNascimento Date,
	sexo varchar(30),
	endereco varchar(200),
	contactoPrimario varchar(30) not null,
	dataRegisto Timestamp DEFAULT (current_timestamp AT TIME ZONE 'UTC')
);

create table telefone(
	pessoaid int references pessoa(pessoaid) on delete cascade,
	numeroTelefone varchar(30) unique not null,
	Primary Key (pessoaid, numeroTelefone)
);

create table paciente(
	pacienteid int references pessoa(pessoaid) primary key on delete cascade,
	profissao varchar(30) not null,
	estadoActual varchar(40)
);

create table recepcionista(
	recepcionistaid int references pessoa(pessoaid) primary key on delete cascade
);

create table medico(
	medicoid int references pessoa(pessoaid) primary key on delete cascade,
	carteiraProfissional varchar(30) unique not null,
	especialidadeid int references especialidade on delete cascade
);

create table especialidade(
	especialidadeid serial primary key,
	nome varchar(80) not null unique,
	descricao varchar(200) not null
);

