--springBoard
drop table springBoard purge;

create table springBoard(
board_no number primary key
,board_name varchar2(50) not null
,board_title varchar2(100) not null
,board_cont varchar2(4000) 
,board_pwd varchar2(30) not null
,board_hit number default 0
,board_ref number   /* 답변글 그룹번호 */
,board_step number  /* 답변글 단계번호 */
,board_level number /* 답변글 순서번호 */
,board_regdate date /* 등록날짜 */
);

drop sequence springBoard_seq;

create sequence springBoard_seq
       increment by 1 start with 1 nocache;
       
select * from springBoard;       



