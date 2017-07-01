/* springMember */
drop table springMember purge;

create table springMember(
 mem_code number unique not null /*아이디 중복 구분 필드*/
 /* unique : 중복 데이터 금지,null 허용 */
 , mem_id varchar2(30) primary key
 , mem_pwd varchar2(30) not null
 , mem_name varchar2(20) not null
 , mem_zip varchar2(3) not null
 , mem_zip2 varchar2(3) not null
 , mem_addr varchar2(100) not null
 , mem_addr2 varchar2(100) not null
 , mem_phone varchar2(30) not null
 , mem_email varchar2(100) not null
 , mem_regdate date /*회원등록 날짜*/
 , mem_state number /*가입회원 1,탈퇴회원 2 */
 , mem_delcont varchar2(4000) /*탈퇴사유*/
 , mem_deldate date /*탈퇴날짜*/
);

drop sequence springMember_seq;

/***** springMember_seq 시퀀스 생성 *****/
create sequence springMember_seq
       increment by 1 start with 1 nocache;



insert into springMember (mem_code,mem_id,mem_pwd,mem_name,mem_zip,mem_zip2,
mem_addr,mem_addr2,mem_phone,mem_email,mem_regdate,mem_state)
values(springMember_seq.nextval,'user','1234','홍길동','745','850',
'서울시 강남구 논현동','대한민국아카데미','010-777-7777',
'hone@naver.com',sysdate,1);

select * from springMember;

delete from SPRINGMEMBER where mem_code=3;






