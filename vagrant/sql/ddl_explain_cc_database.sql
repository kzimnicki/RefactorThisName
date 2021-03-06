alter table rootword drop foreign key FKF61E06ECA498D063;
alter table user drop foreign key FK36EBCB79090A83;
alter table user_excludedwords drop foreign key FK266609ABAB89EFE5;
alter table user_excludedwords drop foreign key FK266609AB51086E5;
alter table user_includedwords drop foreign key FK9381ADF9AB89EFE5;
alter table user_includedwords drop foreign key FK9381ADF951086E5;
alter table wordrelation drop foreign key FKB521170651086E5;
alter table wordrelation drop foreign key FKB5211706F34B385;
drop table if exists configuration;
drop table if exists rootword;
drop table if exists translation;
drop table if exists user;
drop table if exists user_excludedwords;
drop table if exists user_includedwords;
drop table if exists word;
drop table if exists wordrelation;
create table configuration (id bigint not null auto_increment, created datetime, updated datetime, isPhrasalVerbAdded bit not null, max integer not null, min integer not null, phrasalVerbTemplate varchar(255) not null, subtitleProcessor varchar(255), subtitleTemplate varchar(255) not null, textTemplate varchar(255) not null, primary key (id)) ENGINE=InnoDB;
create table rootword (id bigint not null auto_increment, rootWord_id bigint not null, primary key (id), unique (rootWord_id)) ENGINE=InnoDB;
create table translation (id bigint not null auto_increment, sourceLang varchar(255), sourceWord varchar(255) not null, transLang varchar(255), transWord varchar(255), primary key (id), unique (sourceLang, transLang, sourceWord)) ENGINE=InnoDB;
create table user (id bigint not null auto_increment, created datetime, updated datetime, enabled bit not null, password varchar(20) not null, role varchar(255) not null, username varchar(40) not null unique, config_id bigint, primary key (id)) ENGINE=InnoDB;
create table user_excludedwords (id bigint not null auto_increment, created datetime, rootWord_id bigint, user_id bigint, primary key (id)) ENGINE=InnoDB;
create table user_includedwords (id bigint not null auto_increment, created datetime, rootWord_id bigint, user_id bigint, primary key (id)) ENGINE=InnoDB;
create table word (id bigint not null auto_increment, frequency integer not null, value varchar(255) not null, wordType integer not null, primary key (id), unique (value, wordType)) ENGINE=InnoDB;
create table wordrelation (id bigint not null auto_increment, rootWord_id bigint not null, word_id bigint not null, primary key (id), unique (word_id, rootWord_id)) ENGINE=InnoDB;
alter table rootword add index FKF61E06ECA498D063 (rootWord_id), add constraint FKF61E06ECA498D063 foreign key (rootWord_id) references word (id);
alter table user add index FK36EBCB79090A83 (config_id), add constraint FK36EBCB79090A83 foreign key (config_id) references configuration (id);
alter table user_excludedwords add index FK266609ABAB89EFE5 (user_id), add constraint FK266609ABAB89EFE5 foreign key (user_id) references user (id);
alter table user_excludedwords add index FK266609AB51086E5 (rootWord_id), add constraint FK266609AB51086E5 foreign key (rootWord_id) references rootword (id);
alter table user_includedwords add index FK9381ADF9AB89EFE5 (user_id), add constraint FK9381ADF9AB89EFE5 foreign key (user_id) references user (id);
alter table user_includedwords add index FK9381ADF951086E5 (rootWord_id), add constraint FK9381ADF951086E5 foreign key (rootWord_id) references rootword (id);
alter table wordrelation add index FKB521170651086E5 (rootWord_id), add constraint FKB521170651086E5 foreign key (rootWord_id) references rootword (id);
alter table wordrelation add index FKB5211706F34B385 (word_id), add constraint FKB5211706F34B385 foreign key (word_id) references word (id);
