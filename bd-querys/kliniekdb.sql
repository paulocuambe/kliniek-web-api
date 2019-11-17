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
	pacienteid int references pessoa(pessoaid) primary key ,
	profissao varchar(30) not null,
	estadoActual varchar(40),
	eliminado boolean default false
);

create table recepcionista(
	recepcionistaid int references pessoa(pessoaid) primary key,
	eliminado boolean default false
);

create table medico(
	medicoid int references pessoa(pessoaid) primary key,
	carteiraProfissional varchar(30) unique not null,
	especialidadeid int references especialidade on delete cascade,
	eliminado boolean default false
);

create table especialidade(
	especialidadeid serial primary key,
	nome varchar(80) not null unique,
	descricao varchar(200) not null
);
--
insert into especialidade (nome, descricao)
values ('Pediatria', 'Cuida de crancas.'),
('Genecologista', 'Cuida do sistema reprodutor feminino.'),
('Psicologo', 'Saude mental.'), ('Generalista', 'Cuida das doencas do povo');

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
('normal', '07:00', '15:30'),
('noite', '15:00', '23:30'), ('plantao', '23:00', '07:30');

create table disponibilidade (
	medicoid int references medico not null,
	diaid int references diaSemana not null,
	periodoid int references periodo not null,
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
insert into tipoConsulta (designacao, descricao, preco) values
('Normal', 'Marcada no intervalo das 7 as 12', 1200),
('Urgente', 'Marcada fora do horario normal', 1500),
('Especial', 'Marcada no instante da consulta.', 2000);


create table tipoExame(
	tipoexameid serial primary key,
	designacao varchar(120),
	descricao varchar(200),
	preco numeric(10,2),
	disponivel boolean default true
);

insert into tipoExame (designacao, descricao, preco) values
('Normal', 'Marcada no intervalo das 7 as 12', 1200),
('Urgente', 'Marcada fora do horario normal', 1500),
('Especial', 'Marcada no instante da consulta.', 2000);


-- Table: public.exame

-- DROP TABLE public.exame;
drop table exame;

CREATE TABLE public.exame
(
    exameid integer NOT NULL,
    tipoexameid integer,
    pacienteid integer,
    recepcionistaid integer,
	enfermeiroid integer,
    data date,
    hora time without time zone,
    observacao character varying(200) COLLATE pg_catalog."default",
    positivo boolean DEFAULT false,
    urgente boolean DEFAULT false,
    realizado boolean DEFAULT false,
    preco numeric(10,2),
    CONSTRAINT exame_pkey PRIMARY KEY (exameid),
    CONSTRAINT exame_data_hora_key UNIQUE (data, hora)
,
    CONSTRAINT exame_pacienteid_fkey FOREIGN KEY (pacienteid)
        REFERENCES public.paciente (pacienteid) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT exame_recepcionistaid_fkey FOREIGN KEY (recepcionistaid)
        REFERENCES public.recepcionista (recepcionistaid) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
	CONSTRAINT enfermeiro_enfermeiroid_fkey FOREIGN KEY (enfermeiroid)
        REFERENCES public.enfermeiro (enfermeiroid) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT exame_tipoexameid_fkey FOREIGN KEY (tipoexameid)
        REFERENCES public.tipoexame (tipoexameid) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

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
	unique (medicoid, dia, hora)
);

alter table exame add preco numeric(10, 2);
alter table consulta add preco numeric(10, 2);

-- Tabelas de Auditoria
CREATE TABLE public.auditoria_precos_consultas
(
    tipoconsultaid integer,
    valor_antigo numeric(10,2),
    valor_actual numeric(10,2),
    data_alteracao timestamp without time zone DEFAULT timezone('UTC'::text, CURRENT_TIMESTAMP)
)

CREATE TABLE public.auditoria_precos_exames
(
    tipoexameid integer,
    valor_antigo numeric(10,2),
    valor_actual numeric(10,2),
    data_alteracao timestamp without time zone DEFAULT timezone('UTC'::text, CURRENT_TIMESTAMP)
)

--insercoes

INSERT INTO public.pessoa(
	bi, nuit, primeironome, apelido, email, datanascimento, sexo, endereco)
	VALUES ('1120021121D', '1229101221', 'Carlos', 'Massavanhane', 
			'camjr@gmail.com', '1997-02-01', 'Masculino', 'C. Sol'),
		('11200211721D', '819213131', 'Joao', 'Carlos', 
			'j.carlos@gmail.com', '1987-04-21', 'Masculino', 'Marracuene')
	,('1120127121E', '8129101221', 'Maria', 'Mondlane', 
			'maria.mondlane@gmail.com', '1997-02-01', 'Femenino', 'Magoanine');


	INSERT INTO public.paciente(
	pacienteid, profissao, estadoactual)
	VALUES (1, 'Estudante', 'normal'),
	(3, 'Motorista', 'grave');

INSERT INTO public.medico(
	medicoid, carteiraprofissional, especialidadeid)
	VALUES (2, '5563-A', 1),
	(4, '8163-B', 2);

	INSERT INTO public.consulta(
	tipoconsultaid, medicoid, pacienteid, dia, hora, descricao)
	VALUES (2, 2, 1, '2019-12-16', '09:30', 'com muitas dores de cabeca');

	INSERT INTO public.exame(
	tipoexameid, pacienteid, data, hora)
	VALUES (1, 1, '2019-12-24', '15:15');

--procedures
CREATE OR REPLACE PROCEDURE marcarExame(INT, INT, INT, INT, date, time)
LANGUAGE plpgsql    
AS $$
BEGIN
    -- inserindo dados do exame na tabela exame
	insert into exame(tipoexameid, pacienteid, recepcionistaid, enferemeiroid, data, hora) values($1,$2,$3,$4,$5,$6);
END;
$$;


CREATE OR REPLACE PROCEDURE realizarConsulta(INT, varchar, varchar)
LANGUAGE plpgsql    
AS $$
BEGIN
    -- actualizando dados da consulta 
    UPDATE consulta 
    SET prescricao=$2, observacao=$3, realizada=true
    WHERE consultaid = $1;
END;
$$;

CREATE OR REPLACE PROCEDURE public.marcarconsulta1(
	integer,
	integer,
	integer,
	integer,
	date,
	time without time zone,
	character varying)
LANGUAGE 'plpgsql'

AS $BODY$
BEGIN
 insert into consulta(tipoconsultaid,medicoid,pacienteid,recepcionistaid, dia, hora, descricao) values($1, $2,$3,$4,$5, $6, $7);
END;
$BODY$;

CREATE OR REPLACE PROCEDURE marcarExame(INT, INT, INT, INT, date, time, boolean)
LANGUAGE plpgsql    
AS $$
BEGIN
    -- inserindo dados do exame na tabela exame
	insert into exame(tipoexameid, pacienteid, recepcionistaid, enferemeiroid, data, hora, urgente, realizado) values($1,$2,$3,$4,$5,$6,$7,true);
END;
$$;

CREATE OR REPLACE PROCEDURE realizarExame(INT, varchar, boolean)
LANGUAGE plpgsql    
AS $$
BEGIN
    -- actualizando dados do exame na tabela exame
	UPDATE exame 
    SET observacao = $2, positivo=$3
    WHERE exameid = $1;
END;
$$;

--listar consultas por realizar
CREATE OR REPLACE PROCEDURE listarConsultasPorRealizar()
LANGUAGE 'plpgsql'
AS $BODY$
declare
	reg consulta%rowtype;
BEGIN
	for reg in select * from consulta where realizada=false Loop
	raise notice 'Id Consulta: %', reg.consultaid;
	raise notice 'Id Paciente: %', reg.pacienteid;
	raise notice 'Data: %', reg.dia;
	raise notice 'Hora: %', reg.hora;
	raise notice '====================';
	end loop;
END;
$BODY$

-- Listar consultas por realizar de um paciente
CREATE OR REPLACE PROCEDURE public.listarconsultaporrealizarporpaciente(
	integer)
LANGUAGE 'plpgsql'
AS $BODY$
declare
	reg consulta%rowtype;
BEGIN
	for reg in select * from consulta where realizada=false and pacienteid=$1 
	Loop
		raise notice 'Id Consulta: %', reg.consultaid;
		raise notice 'Data: %', reg.dia;
		raise notice 'Hora: %', reg.hora;
		raise notice '====================';
	end loop;
END;
$BODY$;

--Listar as 10 ultimas  consultas realizadas por um determinado pacientes;
CREATE OR REPLACE PROCEDURE listarDezUltimasConsultasRealizadas(int)
LANGUAGE plpgsql    
AS $$
declare
	reg consulta%rowtype;
BEGIN
    for reg in select * from consulta where pacienteid=$1 and realizada=true order by data limit 10 
    Loop
		raise notice 'Id Consulta: %', reg.consultaid;
		raise notice 'Data: %', reg.dia;
		raise notice 'Hora: %', reg.hora;
		raise notice '====================';
	end loop;
END;
$$;

--Listar os 10 ultimos  exames realizados por um determinado paciente;
CREATE OR REPLACE PROCEDURE listarDezUltimosExamesRealizados(int)
LANGUAGE plpgsql    
AS $$
declare
	reg exame%rowtype;
BEGIN
    for reg in select * from exame where pacienteid=$1 and realizado=true order by data  limit 10 
    Loop
		raise notice 'Id Exame: %', reg.exameid;
		raise notice 'Data: %', reg.data;
		raise notice 'Hora: %', reg.hora;
		raise notice '====================';
	end loop;
END;
$$;

--inserirPessoa(bi, nuit, primeironome, apelido, email, sexo, endereco)
CREATE OR REPLACE PROCEDURE inserirPessoa(varchar, varchar, varchar, varchar, varchar, varchar, varchar)
LANGUAGE plpgsql    
AS $$
BEGIN
   insert into pessoa (bi, nuit, primeironome, apelido, email, datanascimento,sexo, endereco) 
   values ($1, $2, $3, $4, $5, now(),$6, $7);
END;
$$

--inserirPaciente(bi, nuit, primeironome, apelido, email, sexo, endereco, profissao)
CREATE OR REPLACE PROCEDURE inserirPaciente(varchar, varchar, varchar, varchar, varchar, varchar, varchar, varchar)
LANGUAGE plpgsql    
AS  $$
declare
	pid integer;
BEGIN
	call inserirPessoa($1, $2, $3, $4, $5,$6, $7);
	select pessoaid into pid from pessoa where bi=$1;
	insert into paciente(pacienteid, profissao) values(pid, $8);
END;
$$

--inserirMedico(bi, nuit, primeironome, apelido, email, sexo, endereco, carteiraprofissional, especialidade)
CREATE OR REPLACE PROCEDURE inserirMedico(varchar, varchar, varchar, varchar, varchar, varchar, varchar, varchar, int)
LANGUAGE plpgsql    
AS  $$
declare
	pid integer;
BEGIN
 	call inserirPessoa($1, $2, $3, $4, $5,$6, $7);
	select pessoaid into pid from pessoa where bi=$1;
	insert into medico(medicoid, carteiraprofissional, especialidadeid) values(pid, $8, $9);
END;
$$

--inserirRecepcionista(bi, nuit, primeironome, apelido, email, sexo, endereco)
CREATE OR REPLACE PROCEDURE inserirRecepcionista(varchar, varchar, varchar, varchar, varchar, varchar, varchar)
LANGUAGE plpgsql    
AS  $$
declare
	pid integer;
BEGIN
 	call inserirPessoa($1, $2, $3, $4, $5,$6, $7);
	select pessoaid into pid from pessoa where bi=$1;
	insert into recepcionista values(pid);
END;
$$





--funcoes


CREATE OR REPLACE FUNCTION public."contaConsultaPorRealizar"(
	)
    RETURNS integer
    LANGUAGE 'plpgsql'

    COST 100
    VOLATILE 
AS $BODY$declare 
	contador integer;
begin
	select count(*) into contador from consulta where realizada=false;
	return contador;
end;$BODY$;


CREATE OR REPLACE FUNCTION public."contaExamePorRealizar"(
	)
    RETURNS bigint
    LANGUAGE 'plpgsql'

    COST 100
    VOLATILE 
AS $BODY$declare 
	contador integer;
begin
	select count(*) into contador from exame where realizado=false;
	return contador;
end;$BODY$;

ALTER FUNCTION public."contaExamePorRealizar"()
    OWNER TO postgres;

-- Trigger para verificar datas invalidades de exames
CREATE FUNCTION public.dataexame()
    RETURNS trigger
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE NOT LEAKPROOF 
AS $BODY$
   Declare
   diferenca int;
   BEGIN
		select Extract (day from (select  new.data - now())) into diferenca;
		if(diferenca < 0) then
			RAISE EXCEPTION 'Data invalida! A data deve ser superior a data actual'; 
		end if;
      RETURN NEW;
   END;
$BODY$;


CREATE TRIGGER trigger_data_exame Before insert ON exame
FOR EACH ROW EXECUTE PROCEDURE dataexame();

-- Trigger para verificar datas invalidades de exames
CREATE FUNCTION public.dataconsulta()
    RETURNS trigger
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE NOT LEAKPROOF 
AS $BODY$
   Declare
   diferenca int;
   BEGIN
		select Extract (day from (select  new.dia - now())) into diferenca;
		if(diferenca < 0) then
			RAISE EXCEPTION 'Data invalida! A data deve ser superior a data actual'; 
		end if;
      RETURN NEW;
   END;
$BODY$;

CREATE TRIGGER trigger_data_consulta Before insert ON consulta
FOR EACH ROW EXECUTE PROCEDURE dataconsulta();

-- TRIGGERS
-- Auditoria de preco de consultas
CREATE TRIGGER trigger_preco_consulta Before INSERT or update ON tipoconsulta
FOR EACH ROW EXECUTE PROCEDURE auditprecoconsulta();

CREATE FUNCTION public.auditprecoconsulta()
    RETURNS trigger
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE NOT LEAKPROOF 
AS $BODY$
   BEGIN
     	INSERT INTO auditoria_precos_consultas(
	tipoconsultaid, valor_antigo, valor_actual, data_alteracao)
	VALUES (old.tipoconsultaid, old.preco, new.preco, NOW());
      RETURN NEW;
   END;
$BODY$;


--Auditoria de Exames
CREATE TRIGGER trigger_preco_exame Before INSERT or update ON tipoexame
FOR EACH ROW EXECUTE PROCEDURE auditprecoexame();


CREATE FUNCTION public.auditprecoexame()
    RETURNS trigger
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE NOT LEAKPROOF 
AS $BODY$
   BEGIN
     	INSERT INTO auditoria_precos_exames(
	tipoexameid, valor_antigo, valor_actual, data_alteracao)
	VALUES (old.tipoexameid, old.preco, new.preco, NOW());
      RETURN NEW;
   END;
$BODY$;

-- Preencher preco da consulta
CREATE FUNCTION public.preencherprecoconsulta()
    RETURNS trigger
    LANGUAGE 'plpgsql' 
AS $BODY$
   Declare
   tipoc int;
   pr numeric(10,2);
   BEGIN
		tipoc := new.tipoconsultaid;
		select preco into pr from tipoconsulta where tipoconsultaid = tipoc;
		update consulta set preco = pr where preco is null;
    	RETURN NEW;
   END;
$BODY$;

CREATE TRIGGER trigger_alterar_preco_consulta After INSERT ON consulta
FOR EACH ROW EXECUTE PROCEDURE preencherprecoconsulta();

-- Preencher preco da exame
CREATE FUNCTION public.preencherprecoexame()
    RETURNS trigger
    LANGUAGE 'plpgsql' 
AS $BODY$
   Declare
   tipoe int;
   pr numeric(10,2);
   BEGIN
		tipoe := new.tipoexameid;
		select preco into pr from tipoexame where tipoexameid = tipoe;
		update exame set preco = pr where preco is null;
    	RETURN NEW;
   END;
$BODY$;

CREATE TRIGGER trigger_alterar_preco_exame After INSERT ON exame
FOR EACH ROW EXECUTE PROCEDURE preencherprecoexame();


-- Impedir Marcacao de genicologia para homens
CREATE FUNCTION public.impedir_genicologia_homens()
    RETURNS trigger
    LANGUAGE 'plpgsql' 
AS $BODY$ 
   Declare
   esp int;
   s varchar(40);
   BEGIN
		select especialidadeid into esp from medico where medicoid = new.medicoid;
		select sexo into s from pessoa inner join paciente on pessoaid=pacienteid where pessoaid=new.pacienteid;
		if s = 'masculino' or s = 'Masculino' then
			if esp = 2 then
				RAISE EXCEPTION 'Nao eh permitido realizar consultas de genicologias para homens';
			end if;
		end if;
		Return new;
   END;
$BODY$;

CREATE TRIGGER trigger_impedir_genicologia_homens Before insert ON consulta
FOR EACH ROW EXECUTE PROCEDURE impedir_genicologia_homens();

-- Trigger para impedir marcao de consulta de pediatria para maiores de 12 anos
CREATE FUNCTION public.impedir_pediatria()
	RETURNS trigger
	LANGUAGE 'plpgsql' 
AS $BODY$ 
   Declare
   datanasc varchar(40);
   idade int;
   esp int;
   BEGIN
		select especialidadeid into esp from medico where medicoid = new.medicoid;
		select datanascimento into datanasc from pessoa inner join paciente on pessoaid=pacienteid where pessoaid=new.pacienteid;
		select trunc(extract (day from (select now() - to_date(datanasc, 'YYYY-MM-DD')))/365) into idade;
		if esp = 1 then
			if  idade > 12 then
				Raise Exception 'Pediatria eh so para criancas menores de 12 anos.';
			end if;
		end if;
		Return new;
   END;
$BODY$;


CREATE TRIGGER trigger_impedir_pediatria Before insert ON consulta
FOR EACH ROW EXECUTE PROCEDURE impedir_pediatria();