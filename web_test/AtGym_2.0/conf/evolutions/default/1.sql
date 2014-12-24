# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

 create table test (
  email                     varchar(255),
  nachname                  varchar(255),
  vorname                   varchar(255),
  password                  varchar(255),
  groesse                   double,
  gewicht                   double,
  geschlecht                integer,
  constraint ck_test_geschlecht check (geschlecht in (0,1)))
;

create table user (
  email                     varchar(255),
  nachname                  varchar(255),
  vorname                   varchar(255),
  password                  varchar(255),
  groesse                   integer,
  gewicht                   double,
  geschlecht                integer,
  bild                      varchar(255),
  constraint ck_user_geschlecht check (geschlecht in (0,1)))
;




# --- !Downs

 PRAGMA foreign_keys = OFF;

drop table test;

drop table user;

PRAGMA foreign_keys = ON;

