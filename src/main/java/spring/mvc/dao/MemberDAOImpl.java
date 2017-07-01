package spring.mvc.dao;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import spring.mvc.model.SpringMemberBean;
import spring.mvc.model.ZipcodeBean2;

@Repository
public class MemberDAOImpl {
	
	@Autowired
	private SqlMapClientTemplate template;

	/*
	 * ibatis 주요 메서드
	 * 1. 한 개의 레코드 값 검색 : queryForObject()
	 * 2. 한 개 이상의 레코드 값 검색 : queryForList()
	 * 3. 레코드 저장 : insert()
	 * 4. 레코드 수정 : update()
	 * 5. 레코드 삭제 : delete()
	 */
	/* ibatis 중복 아이디 체크 */
	public SpringMemberBean idCheck(String id) throws SQLException{
		SpringMemberBean bean = null;
		bean = (SpringMemberBean)this.template
				.queryForObject("member_idcheck",id);
		return bean;
	}
	
	/*우편번호 검색*/
	public List<ZipcodeBean2> findZipcode(String dong) 
	throws SQLException{
		List<ZipcodeBean2> list=this.template.queryForList("zipcodeList",dong);
		return list;
	}

	/*중복 아이디 검색*/
	public SpringMemberBean idSearch(String mem_id) throws SQLException{
		SpringMemberBean db_id=null;
		db_id=(SpringMemberBean)this.template.queryForObject(
				"id_search",mem_id);
		return db_id;
	}

	/*회원 저장*/
	public int insertMember(SpringMemberBean m){
		int re=0;
		try {
			this.template.insert("memInsert",m);
			re=1;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return re;
	}
	/* 비번 찾기 */
	public SpringMemberBean findPwd(SpringMemberBean bean){
		SpringMemberBean b = null;
		b=(SpringMemberBean)this.template.queryForObject("pwd_find",bean);
		return b;
	}
	/* 정보 수정 */
	public void editMember(SpringMemberBean bean){
		this.template.update("memEdit",bean);
	}
	/*회원 탈퇴 */
	public void delMem(SpringMemberBean bean){
		this.template.update("memDEL",bean);
	}

}
