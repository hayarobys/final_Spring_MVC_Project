-- springGuest.sql
drop table springGuest purge;

create table springGuest(
guest_no number primary key
,guest_name varchar2(30) not null
,guest_title varchar2(200) not null
,guest_cont varchar2(4000) not null
,guest_pwd varchar2(30) not null
,guest_hit number default 0
,guest_regdate date
);

drop sequence springGuest_seq;

create sequence springGuest_seq
       increment by 1 start with 1 nocache;
       
select * from springGuest;

       



