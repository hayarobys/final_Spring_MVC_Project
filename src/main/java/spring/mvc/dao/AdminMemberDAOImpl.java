package spring.mvc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import spring.mvc.model.MemberListBean;
import spring.mvc.model.SpringMemberBean;

@Repository
public class AdminMemberDAOImpl {//관리자 회원관리 디비 연동 클래스
	
	@Autowired
	private SqlMapClientTemplate template;

	Connection con=null; //데이터 베이스 연결 레퍼런스 변수 선언
	PreparedStatement pstmt=null; //쿼리문 실행 레퍼런스 변수 선언
	ResultSet rs=null;  //쿼리문 실행 결과 레코드값 을 저장할 레퍼런스 변수
	DataSource ds=null; //커넥션 풀 즉 dbcp 레퍼런스 변수 선언
	String sql=null; //쿼리문을 저장할 레퍼런스 변수 선언
	
	public AdminMemberDAOImpl(){
		//기본생성자 정의
		try{
            Context  ctx=new InitialContext();	
            ds=(DataSource)ctx.lookup("java:comp/env/jdbc/OracleDB");
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	/* 관리자 회원관리 총회원수 */
	public int getListCount() throws SQLException{		
			int count=0;
			count=(Integer)this.template.queryForObject(
					"aMember_count");			
			return count;		
	}

	/* 관리자 회원관리 목록 (JDBC) -> iBatis변경 */
	public List<SpringMemberBean> getMemberList(int page, int limit) {
		sql="select * from (select springMember.*, rownum as rnum "
				+ " from (select * from springMember order by mem_code desc) springMember)"
				+ " where rnum>=? and rnum<=?";
				
				List<SpringMemberBean> list = new ArrayList<SpringMemberBean>();
				
				int startrow=(page-1)*10+1; //읽기 시작할 row 번호.
				int endrow=page*limit;      //읽을 마지막 row 번호.
		
		try{
				con = ds.getConnection();
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, startrow);
				pstmt.setInt(2, endrow);
				rs = pstmt.executeQuery();
					
				while(rs.next()){
					SpringMemberBean m = new SpringMemberBean();
					m.setMem_code(rs.getInt("mem_code"));
					m.setMem_id(rs.getString("mem_id"));
					m.setMem_name(rs.getString("mem_name"));
					m.setMem_phone(rs.getString("mem_phone"));						
					m.setMem_regdate(rs.getString("mem_regdate"));	
					m.setMem_state(rs.getInt("mem_state"));						
					list.add(m);
				}			
				rs.close(); pstmt.close(); con.close();
			}catch(Exception e){
					e.printStackTrace();
			}		
			return list;
	}

	/* 관리자 회원관리 검색 결과 레코드 수 + ibatis*/
	public int getListCount3(String find_name, String find_field)
	throws SQLException{
		int count=0;
		if(find_field.equals("mem_id")){//검색어가 회원아이디 인경우
		count=(Integer)this.template.queryForObject("amemberfind_cnt1",find_name);
		}else if(find_field.equals("mem_name")){//검색어가 회원이름인 경우
		count=(Integer)this.template.queryForObject("amemberfind_cnt2",find_name);	
		}
		return count;
	}

	/*관리자 회원관리 검색 결과 목록 보기 (JDBC) -> iBatis변경 */
	public List<SpringMemberBean> getMemList3(int page, int limit,
			String find_name, String find_field) {
		List<SpringMemberBean> list = new ArrayList<SpringMemberBean>();
		try{
			con = ds.getConnection();
			
			sql="select * from"+"(select rownum r,mem_code,mem_id,"
		     +"mem_name,mem_phone,mem_regdate,mem_state "+
			" from (select * from springMember order by mem_code " +
			" desc) where "+find_field+" like ? and"
			+" rownum <= ?) where r >= ?";
		    
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, "%"+find_name+"%");
			pstmt.setInt(2, page*limit);
			pstmt.setInt(3, ((page-1)*limit)+1);
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				SpringMemberBean m= new SpringMemberBean();
				m.setMem_code(rs.getInt("mem_code"));
				m.setMem_id(rs.getString("mem_id"));
				m.setMem_name(rs.getString("mem_name"));
				m.setMem_phone(rs.getString("mem_phone"));
				m.setMem_state(rs.getInt("mem_state"));
		        m.setMem_regdate(rs.getString("mem_regdate"));
				
		        list.add(m); //컬렉션에 요소값 추가		
			}	
			rs.close(); pstmt.close(); con.close();
		}catch(Exception e){
			e.printStackTrace();
		}				
		return list;
	}

	/*회원정보 가져오기 */
	public SpringMemberBean getmemCont(String mem_id) throws SQLException{
		return (SpringMemberBean)this.template.queryForObject(
				"aMember_info",mem_id);
	}

	/* 회원정보 수정 */
	public void updateMember(SpringMemberBean m33) throws SQLException{
		this.template.update("amem_edit",m33);		
	}
	/* 관리자 회원 삭제 (JDBC) -> iBatis변경*/
	public void deleteMember(String mem_id){
		this.template.delete("amem_del",mem_id);
	}
	/* 관리자 회원 삭제 (JDBC) -> iBatis변경*/
	/*public void deleteMember(String mem_id) {
		try{
			con=ds.getConnection();
			sql="delete from springMember where mem_id=?";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1,mem_id);
			pstmt.executeUpdate();//삭제문 실행
			
			pstmt.close(); con.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}*/
}



