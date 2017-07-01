package spring.mvc.dao;

import java.util.List;

import spring.mvc.model.SpringGuestBean;

public interface GuestDAO {
	
	void insertGuest(SpringGuestBean gbean);//방명록저장
	List<SpringGuestBean> getGuestList();// 방명록 목록
	int listCount(); // 총레코드 개수
	void hitUpdate(int no);  // 조회수 증가
	SpringGuestBean getGuestCont(int no);//방명록 내용보기
	void guestEdit(SpringGuestBean ebean);//방명록 수정
	void deleteGuest(int no);// 방명록 삭제
}



