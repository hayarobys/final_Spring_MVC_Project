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
import spring.mvc.model.SpringGongjiBean2;

@Repository
public class GongjiDAOImpl {//사용자 공지사항 디비 연동 클래스
	@Autowired
	private SqlMapClientTemplate template;
	
	Connection con=null;
	PreparedStatement pstmt=null;
	ResultSet rs=null;
	DataSource ds=null;
	String sql=null;
	
	public GongjiDAOImpl(){
		try{
			Context ctx=new InitialContext();
			ds=(DataSource)ctx.lookup("java:comp/env/jdbc/OracleDB");
		}catch(Exception e){
			e.printStackTrace();
		}
	}	
	
	/* 사용자 최신 공지목록 5개 보기 */
	public List<SpringGongjiBean2> getGongji_List(){
		return 
		  this.template.queryForList("indexGongjiList");
	}

	/* 사용자 공지 조회수 증가(iBatis) */
	public void updateGongjgHit(int no) throws SQLException{
		this.template.update("gongji_hit",no);		
	}

	/* 사용자 공지 내용보기(iBatis) */
	public SpringGongjiBean2 getGongjiCont(int no) throws SQLException{
		return (SpringGongjiBean2)this.template.queryForObject(
				"igongji_cont",no);
	}

	/* 사용자 공지 목록 개수(iBatis) */
	public int getListCount() throws SQLException{
		int count=0;
		count=(Integer)this.template.queryForObject(
				"aGongji_count");
		return count;
	}

	/* 사용자 공지 목록+JDBC */
	public List<SpringGongjiBean> getBoardList(int page, int limit) {
		sql="select * from (select springGongji.*, rownum as rnum "
				+ " from (select * from springGongji order by gongji_no desc)" 
				+ " springGongji) where rnum>=? and rnum<=?";
				
				List<SpringGongjiBean> list = new ArrayList<SpringGongjiBean>();
				
				int startrow=(page-1)*10+1; //읽기 시작할 row 번호.
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
}
