package spring.mvc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import spring.mvc.model.SpringBbsBean;

@Repository
public class AdminBbsDAOImpl {
	/* myBatis 객체 주입 */	
	@Autowired
	private SqlSessionTemplate sqlSession;

	/* iBatis 객체 주입 */
	@Autowired
	private SqlMapClientTemplate template;

	Connection con=null;
	PreparedStatement pstmt=null;
	ResultSet rs=null;
	DataSource ds=null;
	String sql=null;
	
	public AdminBbsDAOImpl(){
		try{
	      Context ctx=new InitialContext();
	      ds=(DataSource)ctx.lookup("java:comp/env/jdbc/OracleDB");
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	/* 관리자 자료실 총 게시물 수(myBatis)*/
	public int getListCount() {
		int result=0;
		result = this.sqlSession.
				selectOne("aBbs_count");
		return result;
	}	
	/* 관리자 자료실 총 게시물 수(iBatis)*/
	/*public int getListCount() {
		int result=0;
		result = (int)this.template.
				queryForObject("aBbs_count");
		return result;
	}*/

	/* 관리자 자료실 목록 */
	public List<SpringBbsBean> getBbsList(int page, int limit) {
		sql="select * from (select springBbs.*, rownum as rnum "
				+ " from (select * from springBbs order by bbs_ref desc," 
				+ " bbs_level asc) springBbs)"
				+ " where rnum>=? and rnum<=?";
				
		        List<SpringBbsBean> list = new ArrayList<SpringBbsBean>();
				
				int startrow=(page-1)*7+1; //읽기 시작할 row 번호.(page-1)*limit+1
				int endrow=page*limit;
		
				try{
					con = ds.getConnection();
					pstmt = con.prepareStatement(sql);
					pstmt.setInt(1, startrow);
					pstmt.setInt(2, endrow);
					rs = pstmt.executeQuery();
					
					while(rs.next()){
						SpringBbsBean b = new SpringBbsBean();
						b.setBbs_no(rs.getInt("bbs_no"));
						b.setBbs_name(rs.getString("bbs_name"));
						b.setBbs_subject(rs.getString("bbs_subject"));
						b.setBbs_content(rs.getString("bbs_content"));						
						b.setBbs_ref(rs.getInt("bbs_ref"));
						b.setBbs_step(rs.getInt("bbs_step"));
						b.setBbs_level(rs.getInt("bbs_level"));
						b.setBbs_hit(rs.getInt("bbs_hit"));
						b.setBbs_regdate(rs.getString("bbs_regdate"));			
						list.add(b);
					}			
					rs.close(); pstmt.close(); con.close();
				}catch(Exception e){
					e.printStackTrace();
			}		
				return list;
	}

	/* 관리자 자료실 검색 레코드 수(myBatis) */
	public int getListCount3(String find_name, String find_field) throws SQLException{
		int count=0;
		if(find_field.equals("bbs_subject")){//검색어가 자료실 제목 인경우
			count=this.sqlSession.selectOne("abbsfind_cnt1",find_name);
		}else if(find_field.equals("bbs_content")){//검색어가 자료실 내용인 경우
			count=this.sqlSession.selectOne("abbsfind_cnt2",find_name);	
		}
		return count;
	}	
	/* 관리자 자료실 검색 레코드 수(iBatis) */
	/*public int getListCount3(String find_name, String find_field) throws SQLException{
		int count=0;
		if(find_field.equals("bbs_subject")){//검색어가 자료실 제목 인경우
		count=(int)this.template.queryForObject("abbsfind_cnt1",find_name);
		}else if(find_field.equals("bbs_content")){//검색어가 자료실 내용인 경우
		count=(int)this.template.queryForObject("abbsfind_cnt2",find_name);	
		}
		return count;
	}*/

	public List<SpringBbsBean> getBbsList3(int page, int limit, 
			String find_name,String find_field) {
		List<SpringBbsBean> list = new ArrayList<SpringBbsBean>();
		try{
			con = ds.getConnection();
			sql="select * from(select rownum r, bbs_no,bbs_name,"
		     + " bbs_subject,bbs_content,bbs_ref,bbs_step,bbs_level,bbs_hit,bbs_regdate "
			 + " from (select * from springBbs order by bbs_ref" 
		     + " desc,bbs_step asc) where "+find_field+" like ? and"
			 + " rownum <= ?) where r >= ?";
		    pstmt=con.prepareStatement(sql);
			pstmt.setString(1, "%"+find_name+"%");
			pstmt.setInt(2, page*limit);
			pstmt.setInt(3, ((page-1)*limit)+1);
			rs = pstmt.executeQuery();

			while(rs.next()){
				SpringBbsBean b = new SpringBbsBean();
				b.setBbs_no(rs.getInt("bbs_no"));
				b.setBbs_name(rs.getString("bbs_name"));
				b.setBbs_subject(rs.getString("bbs_subject"));
				b.setBbs_content(rs.getString("bbs_content"));
				b.setBbs_ref(rs.getInt("bbs_ref"));
				b.setBbs_step(rs.getInt("bbs_step"));
				b.setBbs_level(rs.getInt("bbs_level"));
				b.setBbs_hit(rs.getInt("bbs_hit"));
		b.setBbs_regdate(rs.getString("bbs_regdate").substring(0,10));
				
		        list.add(b); //컬렉션에 요소값 추가		
			}	
			rs.close(); pstmt.close(); con.close();
		}catch(Exception e){
			e.printStackTrace();
		}				
		return list;
	}

	/*관리자 자료실 저장(myBatis) */
	public void insertBbs(SpringBbsBean bbs) throws SQLException{
		this.sqlSession.insert("admin_bbs_ins",bbs);		
	}	
	/*관리자 자료실 저장(iBatis) */
	/*public void insertBbs(SpringBbsBean bbs) throws SQLException{
		this.template.insert("admin_bbs_ins",bbs);		
	}*/

	/*관리자 자료실 내용보기(myBatis) */
	public SpringBbsBean getBbsCont(int bbs_no) throws SQLException{
		return (SpringBbsBean)this.sqlSession.
				selectOne("admin_bbs_cont",bbs_no);
	}	
	/*관리자 자료실 내용보기(iBatis) */
	/*public SpringBbsBean getBbsCont(int bbs_no) throws SQLException{
		return (SpringBbsBean)this.template.queryForObject(
				"admin_bbs_cont",bbs_no);
	}*/

	/*관리자 자료실 수정(myBatis) - 업로드 파일 없는 경우 null 발생 */
	/*public void updateBbs(SpringBbsBean bbsbean) throws SQLException{
		this.sqlSession.update("admin_bbs_edit",bbsbean);		
	}*/
	/*관리자 자료실 수정(iBatis) - 업로드 파일 없는 경우 null 발생 */
	/*public void updateBbs(SpringBbsBean bbsbean) throws SQLException{
		this.template.update("admin_bbs_edit",bbsbean);		
	}*/
	/*관리자 자료실 수정 (JDBC) */
	public void updateBbs(SpringBbsBean b) {
	    try{
	    	con=ds.getConnection();
	    	sql="update springBbs set bbs_name=?,bbs_subject=?,"
            +"bbs_content=?,bbs_file=? where bbs_no=?";
	    	pstmt=con.prepareStatement(sql);
	    	pstmt.setString(1,b.getBbs_name());
	    	pstmt.setString(2,b.getBbs_subject());	    	
	    	pstmt.setString(3,b.getBbs_content());
	    	pstmt.setString(4,b.getBbs_file());
	    	pstmt.setInt(5, b.getBbs_no());
	    	
	    	pstmt.executeUpdate();//수정문 실행
	    	
	    	pstmt.close(); con.close();
	    }catch(Exception e){
	    	e.printStackTrace();
	    }
	}

	/*관리자 자료실 삭제(iBatis) */
	public void deleteBbs(int bbs_no) throws SQLException{
		this.sqlSession.delete("admin_bbs_del",bbs_no);		
	}	
	/*관리자 자료실 삭제(iBatis) */
	/*public void deleteBbs(int bbs_no) throws SQLException{
		this.template.delete("admin_bbs_del",bbs_no);		
	}*/	
}
