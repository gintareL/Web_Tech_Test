# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table user (
  id                        varchar(255),
  email                     varchar(255),
  name                      varchar(255),
  password                  varchar(255),
  groesse                   double,
  gewicht                   double,
  geschlecht                integer,
  constraint ck_user_geschlecht check (geschlecht in (0,1)))
;




# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists user;

SET REFERENTIAL_INTEGRITY TRUE;

