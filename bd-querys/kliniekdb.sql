-- Criando Tabelas
create table usuario(
	usuarioid serial primary key,
	username varchar(30) unique not null,
	senha varchar(100) not null,
	estado varchar(30) Default 'activo',
	dataCriacao Timestamp DEFAULT (current_timestamp AT TIME ZONE 'UTC'),
	eliminado boolean default false
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
	dataRegisto Timestamp DEFAULT (current_timestamp AT TIME ZONE 'UTC')
);

create table telefone(
	pessoaid int references pessoa(pessoaid) on delete cascade,
	numeroTelefone varchar(30) not null,
	tipo varchar(30) default 'pessoal'
);

create table paciente(
	pacienteid int references pessoa(pessoaid) primary key on delete cascade,
	profissao varchar(30) not null,
	estadoActual varchar(40),
	eliminado boolean default false
);

create table recepcionista(
	recepcionistaid int references pessoa(pessoaid) primary key on delete cascade,
	eliminado boolean default false
);

create table medico(
	medicoid int references pessoa(pessoaid) primary key on delete cascade,
	carteiraProfissional varchar(30) unique not null,
	especialidadeid int references especialidade on delete cascade,
	eliminado boolean default false
);

create table especialidade(
	especialidadeid serial primary key,
	nome varchar(80) not null unique,
	descricao varchar(200) not null
);

--Gestao de Horarios

create table diaSemana(
	diaid serial primary key,
	designacao varchar(30)
);

insert into diaSemana (designacao) values 
('domingo'), ('segunda-feira'), ('terca-feira'), ('quarta-feira'), 
('quinta-feira'), ('sexta-feira'), ('sabado');

create table periodo(
	periodoid serial primary key,
	p_designacao varchar(70),
	horainicio Time not null,
	horafim Time not null
);

insert into periodo (p_designacao, horainicio, horafim) values 
('normal', '07:00', '15:30'), ('noite', '15:00', '23:30'), ('plantao', '23:00', '07:30');

create table disponibilidade (
	medicoid int references medico not null on delete cascade,
	diaid int references diaSemana not null on delete cascade,
	periodoid int references periodo not null on delete cascade,
	Primary key (medicoid, diaid)
);

-- Marcacao de consultas

create table tipoConsulta(
	tipoconsultaid serial primary key,
	designacao varchar(120),
	descricao varchar(200),
	preco numeric(10,2),
	disponivel boolean default true
);


create table tipoExame(
	tipoexameid serial primary key,
	designacao varchar(120),
	descricao varchar(200),
	preco numeric(10,2),
	disponivel boolean default true
);

create table exame(
	exameid serial primary key,
	tipoexameid int references tipoExame,
	pacienteid int references paciente,
	recepcionistaid int references recepcionista,
	data Date,
	hora Time,
	observacao varchar(200),
	positivo boolean default false,
	urgente boolean default false,
	realizado boolean default false,
	Primary Key (data, hora)
);

create table consulta(
	consultaid serial primary key,
	tipoconsultaid int references tipoconsulta,
	medicoid int references medico,
	pacienteid int references paciente,
	recepcionistaid int references recepcionista,
	dia Date,
	hora Time,
	descricao varchar(300),
	prescricao varchar(300),
	observacao varchar(200),
	urgente boolean default false,
	realizada boolean default false,
	Primary Key (medicoid, dia, hora)
);