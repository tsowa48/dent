create role dent with login password 'dent';
create database dent with owner dent;

create table company
(
	id bigserial not null,
	name text not null,
	address text not null,
	director text not null
);

comment on table company is 'Компания';

create unique index company_id_uindex
	on company (id);

alter table company
	add constraint company_pk
		primary key (id);

create table employee
(
	id bigserial not null,
	fio text not null,
	type int not null,
	type_name text not null,
	cid bigint not null
		constraint employee_company_id_fk
			references company
);

comment on table employee is 'Сотрудник';

comment on column employee.type is 'Тип сотрудника (врач, руководитель, etc)';

comment on column employee.type_name is 'Название типа сотрудника';

create unique index employee_id_uindex
	on employee (id);

alter table employee
	add constraint employee_pk
		primary key (id);

create table client
(
	id bigserial not null,
	fio text not null,
	phone text not null,
	address text not null,
	birth timestamp not null,
	sex int not null,
	fid int
);

comment on table client is 'Клиент';

comment on column client.fio is 'ФИО';

comment on column client.phone is 'Контактный телефон';

comment on column client.address is 'Адрес проживания';

comment on column client.birth is 'Дата рождения';

comment on column client.sex is 'Пол';

comment on column client.fid is 'Откуда узнал о компании';

create unique index client_id_uindex
	on client (id);

alter table client
	add constraint client_pk
		primary key (id);

create table find_out
(
	id bigserial not null,
	name text not null
);

comment on table find_out is 'Откуда узнал о компании';

create unique index find_out_id_uindex
	on find_out (id);

alter table find_out
	add constraint find_out_pk
		primary key (id);

alter table client
	add constraint client_find_out_id_fk
		foreign key (fid) references find_out;

create table service
(
	id bigserial not null,
	name text not null,
	price numeric not null
);

comment on table service is 'Услуга';

comment on column service.name is 'Название';

comment on column service.price is 'Цена';

create unique index service_id_uindex
	on service (id);

alter table service
	add constraint service_pk
		primary key (id);

create table card
(
	id bigserial not null,
	number bigint not null,
	date timestamp not null,
	diagnosis text,
	complaints text,
	anamnesis text,
	doc bigint not null
		constraint card_employee_id_fk
			references employee,
	cid bigint not null
		constraint card_client_id_fk
			references client
);

comment on table card is 'Медицинская карта';

comment on column card.number is 'Номер';

comment on column card.date is 'Дата';

comment on column card.diagnosis is 'Диагноз';

comment on column card.complaints is 'Жалобы';

comment on column card.anamnesis is 'Эпиданамнез';

comment on column card.doc is 'Врач';

comment on column card.cid is 'Клиент';

create unique index card_id_uindex
	on card (id);

alter table card
	add constraint card_pk
		primary key (id);

create table contract
(
	id bigserial not null,
	number bigint not null,
	date date not null,
	cid bigint not null
		constraint contract_company_id_fk
			references company,
	doc bigint not null
		constraint contract_employee_id_fk
			references employee,
	tech bigint
		constraint contract_employee_id_fk_2
			references employee,
	warranty text,
	pid bigint not null
		constraint contract_client_id_fk
			references client
);

comment on table contract is 'Договор';

comment on column contract.number is 'Номер';

comment on column contract.date is 'Дата';

comment on column contract.cid is 'Компания';

comment on column contract.doc is 'Врач';

comment on column contract.tech is 'Зубной техник';

comment on column contract.warranty is 'Гарантия';

comment on column contract.pid is 'Пациент (клиент)';

create unique index contract_id_uindex
	on contract (id);

alter table contract
	add constraint contract_pk
		primary key (id);

create table act
(
	id bigserial not null,
	did bigint not null
		constraint act_contract_id_fk
			references contract,
	number bigint not null,
	date date not null,
	type int not null
);

comment on table act is 'Акт';

comment on column act.did is 'Договор';

comment on column act.number is 'Номер';

comment on column act.date is 'Дата';

comment on column act.type is 'Тип акта';

create unique index act_id_uindex
	on act (id);

alter table act
	add constraint act_pk
		primary key (id);

create table act_service
(
	sid bigint not null
		constraint act_service_service_id_fk
			references service,
	aid bigint not null
		constraint act_service_act_id_fk
			references act,
	amount int not null,
	constraint act_service_pk
		primary key (sid, aid)
);

comment on table act_service is 'Связь Акт-Услуга';

comment on column act_service.sid is 'Услуга';

comment on column act_service.aid is 'Акт';

comment on column act_service.amount is 'Количество';

