--springBbs.sql
drop table springBbs purge;

create table springBbs(
	bbs_no number primary key,
	bbs_name varchar2(50) not null,
	bbs_subject varchar2(200) not null,
	bbs_pass varchar2(20) not null,
	bbs_content varchar2(4000) not null,
	bbs_file varchar2(100),  /* 이진파일 */
	bbs_hit number default 0, /* 조회수 */
	bbs_ref number, /* 글 그룹번호 */
	bbs_step number, /* 답변글 위치번호 */
	bbs_level number, /* 답변글 레벨순서 */
	bbs_regdate date /* 글 등록 날짜 */
);
-- 시퀀스 객체 생성
drop sequence springBbs_seq;
create sequence springBbs_seq
       increment by 1 start with 1 nocache;
-- 테이블 확인
select * from springBbs;     

insert into springBbs (bbs_no,bbs_name,bbs_subject,bbs_pass,
	 bbs_content,bbs_file,bbs_ref,bbs_step,bbs_level,
	 bbs_regdate) values(springBbs_seq.nextval,'name','subject',
	 'pass','content','file',springBbs_seq.nextval,0,0,
	 sysdate)
       
       
       
       
       
       