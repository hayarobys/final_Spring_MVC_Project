package spring.mvc.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import spring.mvc.model.SpringAdminBean;

@Repository
public class AdminDAOImpl {  // db 연동 클래스
	@Autowired
	private SqlMapClientTemplate template;
	
	/* 관리자 로그인 인증 */
	public SpringAdminBean getAdmin_id(String admin_id){
		SpringAdminBean bean =
		(SpringAdminBean)this.template.
		   queryForObject("adminid_check",admin_id);
		return bean;
	}

}





