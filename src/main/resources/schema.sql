drop table if exists CARD;
drop table if exists ACCOUNT;
drop sequence if exists sql_sequence_card_id;
drop sequence if exists sql_sequence_account_id;

create table if not exists ACCOUNT
(
    ID       int      not null,
    "NUMBER" bigint   not null,
    BANK     varchar  not null,
    VERSION  smallint default 0,
    primary key (ID)
);

comment on table account is 'Аккаунт владельца';
comment on column account.id is 'Уникальный айди аккаунта';
comment on column account.number is 'Номер аккаунта';
comment on column account.bank is 'Тип банка, которому принадлежит аккаунт';
comment on column account.version is 'Вспомогательное колонка для оптимистичного лока';

create sequence if not exists sql_sequence_account_id start with 1 increment by 1;
create index idx_account_number on account (number);

create table if not exists CARD
(
    ID          int      not null,
    ACCOUNT_ID  int      not null,
    "NUMBER"    bigint   not null,
    CVC         int      not null,
    "TYPE"      varchar  not null,
    CURRENCY    varchar  not null,
    BALANCE     int      default 0,
    VERSION     smallint default 0,
    primary key (ID),

    constraint fk_card_id_to_account foreign key (ACCOUNT_ID) references ACCOUNT (ID)
);

comment on table card is 'Банковская карточка';
comment on column card.id is 'Уникальный айди карточки';
comment on column card.id is 'Номер карточки';
comment on column card.account_id is 'Айди связи сущности аккаунта';
comment on column card.type is 'Тип карточки';
comment on column card.currency is 'Валюта карточки';
comment on column card.balance is 'Баланс карточки';
comment on column card.version is 'Вспомогательное колонка для оптимистичного лока';

create sequence if not exists sql_sequence_card_id start with 1 increment by 1;
create index idx_card_number on card (number);

insert into account values (next value for sql_sequence_account_id, 2314, 'Sberbank', default);
insert into account values (next value for sql_sequence_account_id, 4872, 'Tinkoff', default);
insert into account values (next value for sql_sequence_account_id, 9813, 'VTB', default);

insert into card values (next value for sql_sequence_card_id, 1, 3475475743563566, 727, 'Visa', 'Dollar', default, default);
insert into card values (next value for sql_sequence_card_id, 2, 2341567387889129, 324, 'MasterCard', 'Euro', default, default);
insert into card values (next value for sql_sequence_card_id, 3, 5893842117421237, 786, 'Mir', 'Ruble', default, default);
insert into card values (next value for sql_sequence_card_id, 3, 9231278432412424, 234, 'MasterCard', 'Ruble', default, default);
insert into card values (next value for sql_sequence_card_id, 2, 1237372643891782, 574, 'MasterCard', 'Dollar', default, default);
insert into card values (next value for sql_sequence_card_id, 2, 9328746273126332, 976, 'Visa', 'Ruble', default, default);
insert into card values (next value for sql_sequence_card_id, 1, 4273623873627412, 264, 'Visa', 'Euro', default, default);
insert into card values (next value for sql_sequence_card_id, 3, 1873273647236487, 754, 'Mir', 'Euro', default, default);
insert into card values (next value for sql_sequence_card_id, 1, 2346512767824671, 327, 'Visa', 'Dollar', default, default);