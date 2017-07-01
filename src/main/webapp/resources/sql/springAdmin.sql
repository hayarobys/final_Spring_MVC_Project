-- springAdmin.sql

drop table springAdmin purge;

create table springAdmin(
admin_no number(4) primary key,
admin_id varchar2(50) not null,
admin_pwd varchar2(50) not null
);

drop sequence springAdmin_seq;

create sequence springAdmin_seq
   increment by 1 start with 1 nocache;

insert into springAdmin
       values(springAdmin_seq.nextval,'admin','admin');

select * from springAdmin;

delete from springAdmin;
   