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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import spring.mvc.model.SpringGongjiBean;

@Repository
public class AdminGongjiDAOImpl {//관리자 공지사항 디비 연동 클래스
	@Autowired
	private SqlMapClientTemplate template;

	Connection con=null;
	PreparedStatement pstmt=null;
	ResultSet rs=null;
	DataSource ds=null;
	String sql=null;
	
	public AdminGongjiDAOImpl(){
	  try{
		  Context ctx=new InitialContext();
		  ds=(DataSource)ctx.lookup("java:comp/env/jdbc/OracleDB");
	  }catch(Exception e){
		  e.printStackTrace();
	  }
	}
	
	/*관리자 공지사항 저장 */
	public void insertGongji(SpringGongjiBean g) throws SQLException{
		this.template.insert("aGongji_insert",g);		
	}

	/*관리자 공지사항 총 게시물 수*/
	public int getListCount() throws SQLException {
		int count=0;
		count=(Integer)this.template.queryForObject(
				"aGongji_count");
		return count;
	}

	/*관리자 공지사항 목록 (JDBC) */
	public List<SpringGongjiBean> getBoardList(int page, int limit) {
		sql="select * from (select springGongji.*, rownum as rnum "
				+ " from (select * from springGongji order by gongji_no desc)" 
				+ " springGongji) where rnum>=? and rnum<=?";
				
				List<SpringGongjiBean> list = new ArrayList<SpringGongjiBean>();
				// 페이지 당 보여주는 게시물을 7개로 지정한다.
				int startrow=(page-1)*7+1; //읽기 시작할 row 번호.
				int endrow=page*limit;	
		
				try{
					con = ds.getConnection();
					pstmt = con.prepareStatement(sql);
					pstmt.setInt(1, startrow);
					pstmt.setInt(2, endrow);
					rs = pstmt.executeQuery();
					
					while(rs.next()){
						SpringGongjiBean g = new SpringGongjiBean();
						g.setGongji_no(rs.getInt("gongji_no"));
						g.setGongji_name(rs.getString("gongji_name"));
						g.setGongji_title(rs.getString("gongji_title"));
						g.setGongji_cont(rs.getString("gongji_cont"));						
						g.setGongji_hit(rs.getInt("gongji_hit"));
						g.setGongji_regdate(rs.getString("gongji_regdate"));						
						list.add(g);
					}			
					rs.close(); pstmt.close(); con.close();
				}catch(Exception e){
					e.printStackTrace();
			}		
				return list;
	}

	/* 관리자 공지사항 검색 결과 레코드 수 + ibatis*/
	public int getListCount3(String find_name, String find_field) 
	throws SQLException{
		int count=0;
		if(find_field.equals("gongji_title")){//검색어가 공지 제목 인경우
		count=(Integer)this.template.queryForObject("agongjifind_cnt1",find_name);
		}else if(find_field.equals("gongji_cont")){//검색어가 공지 내용인 경우
		count=(Integer)this.template.queryForObject("agongjifind_cnt2",find_name);	
		}
		return count;
	}

	/*관리자 공지사항 검색 결과 목록 보기 (JDBC) */
	public List<SpringGongjiBean> getBbsList3(int page, int limit, 
			String find_name,String find_field) {
		List<SpringGongjiBean> list = new ArrayList<SpringGongjiBean>();
		try{
			con = ds.getConnection();
			sql="select * from"+"(select rownum r, gongji_no,gongji_name,"
		     +"gongji_title,gongji_cont,gongji_hit,gongji_regdate "+
			" from (select * from springGongji order by gongji_no " +
			" desc) where "+find_field+" like ? and"
			+" rownum <= ?) where r >= ?";
		    pstmt=con.prepareStatement(sql);
			pstmt.setString(1, "%"+find_name+"%");
			pstmt.setInt(2, page*limit);
			pstmt.setInt(3, ((page-1)*limit)+1);
			rs = pstmt.executeQuery();

			while(rs.next()){
				SpringGongjiBean g= new SpringGongjiBean();
				g.setGongji_no(rs.getInt("gongji_no"));
				g.setGongji_name(rs.getString("gongji_name"));
				g.setGongji_title(rs.getString("gongji_title"));
				g.setGongji_cont(rs.getString("gongji_cont"));
				g.setGongji_hit(rs.getInt("gongji_hit"));
		        g.setGongji_regdate(rs.getString("gongji_regdate"));
				
		        list.add(g); //컬렉션에 요소값 추가		
			}	
			rs.close(); pstmt.close(); con.close();
		}catch(Exception e){
			e.printStackTrace();
		}				
		return list;
	}

	/*관리자 공지 내용보기+수정+삭제 폼*/
	public SpringGongjiBean getGongjiCont(int gongji_no) throws SQLException{
		return (SpringGongjiBean)this.template.queryForObject(
				"agongji_cont", gongji_no);
	}

	/* 관리자 공지 사항 수정 */
	public void editGongji(SpringGongjiBean g) throws SQLException{
		this.template.update("agongji_edit",g);		
	}

	/* 관리자 공지 사항 삭제 (JDBC) */
	public void deleteGongji(int gongji_no) {
		try{
			con=ds.getConnection();
			sql="delete from springGongji where gongji_no=?";
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1,gongji_no);
			pstmt.executeUpdate();//삭제문 실행
			
			pstmt.close(); con.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}

