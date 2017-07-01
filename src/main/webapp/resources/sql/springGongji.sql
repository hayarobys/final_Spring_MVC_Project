-- springGongji.sql
drop table springGongji purge;

create table springGongji(
gongji_no number primary key
,gongji_name varchar2(50) not null
,gongji_title varchar2(200) not null
,gongji_cont varchar2(4000) not null
,gongji_pwd varchar2(50) not null
,gongji_hit number default 0
,gongji_regdate date
);

drop sequence springGongji_seq;

create sequence springGongji_seq
       increment by 1 start with 1 nocache;
       
insert into SPRINGGONGJI
    values(springGongji_seq.nextval,'관리자','공지사항',
    '공지사항 내용','1234',default,sysdate);

select * from SPRINGGONGJI; 

-- 공지사항 최신 글 5개 보기 쿼리문
select * from( 
  select * from SPRINGGONGJI order by gongji_no desc)
  where rownum <= 5;
-- 서브쿼리를 대상으로 검색된 레코드 중 가장 행 번호
-- 5개만 검색한다.(rownum : 의사컬럼)
  
  


       
       