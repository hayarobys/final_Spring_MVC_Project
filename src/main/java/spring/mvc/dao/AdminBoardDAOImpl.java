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

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import spring.mvc.model.SpringBoardBean;

@Repository
public class AdminBoardDAOImpl {	
	/* myBatis 객체 주입(설정파일-admin_board2.xml) */	
	@Autowired
	private SqlSessionTemplate sqlSession;

	/* iBatis 객체 주입(설정파일-admin_board.xml) */
	@Autowired
	private SqlMapClientTemplate template;

	Connection con=null;
	PreparedStatement pstmt=null;
	ResultSet rs=null;
	DataSource ds=null;
	String sql=null;
	
	public AdminBoardDAOImpl(){
		try{
	      Context ctx=new InitialContext();
	      ds=(DataSource)ctx.lookup("java:comp/env/jdbc/OracleDB");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/* 관리자 게시판 총 게시물 수(myBatis)*/
	public int getListCount() {
		int count=0;
		count=this.sqlSession.selectOne(
				"aBoard_count");
		return count;
	}
	
	/* 관리자 게시판 총 게시물 수(iBatis)*/
	/*public int getListCount() {
		int count=0;
		count=(int)this.template.queryForObject(
				"aBoard_count");
		return count;
	}*/

	/*관리자 게시판 목록 보기(myBatis) */
	public List<SpringBoardBean> getBoardList(int page, int limit) {
		int startrow=(page-1)*7+1; 
		int endrow=startrow+limit-1;				
		
		Map<String,Integer> map 
		          = new HashMap<String,Integer>();
        map.put("startrow",startrow);
        map.put("endrow",endrow);
		List<SpringBoardBean> list =
		   this.sqlSession.selectList("aBoard_list",map);
		return list;
	}	
	
	/*관리자 게시판 목록 보기(JDBC) */
	/*public List<SpringBoardBean> getBoardList(int page, int limit) {
		sql="select * from (select springBoard.*, rownum as rnum "
				+ " from (select * from springBoard order by " 
				+ " board_ref desc, board_step asc)" 
				+ " springBoard) where rnum>=? and rnum<=?";
			
				List<SpringBoardBean> list = new ArrayList<SpringBoardBean>();
				
				int startrow=(page-1)*7+1; //읽기 시작할 row 번호.
				int endrow=startrow+limit-1; //읽을 마지막 row 번호.				
				
				try{
					con = ds.getConnection();
					pstmt = con.prepareStatement(sql);
					pstmt.setInt(1, startrow);
					pstmt.setInt(2, endrow);
					rs = pstmt.executeQuery();
					
					while(rs.next()){
						SpringBoardBean b = new SpringBoardBean();
						b.setBoard_no(rs.getInt("board_no"));
						b.setBoard_name(rs.getString("board_name"));
						b.setBoard_title(rs.getString("board_title"));
						b.setBoard_cont(rs.getString("board_cont"));						
						b.setBoard_ref(rs.getInt("board_ref"));
						b.setBoard_step(rs.getInt("board_step"));
						b.setBoard_level(rs.getInt("board_level"));
						b.setBoard_hit(rs.getInt("board_hit"));
						b.setBoard_regdate(rs.getString("board_regdate"));			
						list.add(b);
					}			
					rs.close(); pstmt.close(); con.close();
				}catch(Exception e){
					e.printStackTrace();
			}		
				return list;
	}*/

	/*관리자 게시판 검색 게시물 수(myBatis) */
	public int getListCount3(String find_name, String find_field) 
	throws SQLException{
		int count=0;
		if(find_field.equals("board_title")){//검색어가 게시판 제목인 경우
		count=this.sqlSession.selectOne("aboardfind_cnt1",find_name);
		}else if(find_field.equals("board_cont")){//검색어가 게시판 내용인 경우
		count=this.sqlSession.selectOne("aboardfind_cnt2",find_name);	
		}
		return count;
	}
	
	/*관리자 게시판 검색 게시물 수(iBatis) */
	/*public int getListCount3(String find_name, String find_field) 
	throws SQLException{
		int count=0;
		if(find_field.equals("board_title")){//검색어가 게시판 제목 인경우
		count=(int)this.template.queryForObject("aboardfind_cnt1",find_name);
		}else if(find_field.equals("board_cont")){//검색어가 게시판 내용인 경우
		count=(int)this.template.queryForObject("aboardfind_cnt2",find_name);	
		}
		return count;
	}
   *//* 관리자 검색 목록 보기(작업중 myBatis 변경) */
	/*public List<SpringBoardBean> getBoardSearchList(int page, int limit,
			String find_name, String find_field) {
			
		Map<String,Object> map = new HashMap<String,Object>();
		
		String param1 = find_name;
		int param2 = page*limit;		
		int param3 = ((page-1)*limit)+1;
		
		map.put("param1",param1);
		map.put("param2",param2);
		map.put("param3",param3);
		   
		List<SpringBoardBean> list =null;			
		if(find_field.equals("board_title"))
			list = this.sqlSession.selectOne("aBoard_search_list1",map);
		else if(find_field.equals("board_cont"))
			list = this.sqlSession.selectList("aBoard_search_list2",map);		
		return list;
	}	*/
		
	/* 관리자 검색 목록 보기(JDBC -> myBatis 변경) */
	public List<SpringBoardBean> getBoardSearchList(int page, int limit,
			String find_name, String find_field) {
		List<SpringBoardBean> list = new ArrayList<SpringBoardBean>();
		try{
			con = ds.getConnection();
			sql="select * from (select rownum as rnum, board_no,board_name,"
		     + " board_title,board_cont,board_ref,board_step,board_level,"
		     + " board_hit,board_regdate "
			 + " from (select * from springBoard order by board_ref" 
		     + " desc,board_step asc) where "+find_field+" like ? and rownum <= ?)"
		     + " where rnum >= ?";
		    pstmt=con.prepareStatement(sql);
			pstmt.setString(1, find_name); // 실인수값 -> "%"+find_name+"%"
			pstmt.setInt(2, page*limit);
			pstmt.setInt(3, ((page-1)*limit)+1);
			rs = pstmt.executeQuery();

			while(rs.next()){
				SpringBoardBean b = new SpringBoardBean();
				b.setBoard_no(rs.getInt("board_no"));
				b.setBoard_name(rs.getString("board_name"));
				b.setBoard_title(rs.getString("board_title"));
				b.setBoard_cont(rs.getString("board_cont"));
				b.setBoard_ref(rs.getInt("board_ref"));
				b.setBoard_step(rs.getInt("board_step"));
				b.setBoard_level(rs.getInt("board_level"));
				b.setBoard_hit(rs.getInt("board_hit"));
		b.setBoard_regdate(rs.getString("board_regdate").substring(0,10));
				
		        list.add(b); //컬렉션에 요소값 추가		
			}	
			rs.close(); pstmt.close(); con.close();
		}catch(Exception e){
			e.printStackTrace();
		}				
		return list;
	}

	/* 관리자 게시판 저장(myBatis) */
	public void insertBoard(SpringBoardBean b) throws SQLException{
		this.sqlSession.insert("aboard_insert",b);		
	}
	
	/* 관리자 게시판 저장(iBatis) */
	/*public void insertBoard(SpringBoardBean b) throws SQLException{
		this.template.insert("aboard_insert",b);		
	}*/
	
	/*관리자 게시물 내용보기(JDBC -> myBatis 변경) */
	public SpringBoardBean getBoardCont(int board_no) {
		SpringBoardBean b=null;
		
		try{
		   con=ds.getConnection();
		   sql="select * from springBoard where board_no=?";
		   pstmt=con.prepareStatement(sql);
		   pstmt.setInt(1,board_no);
		   rs=pstmt.executeQuery();
		   
		   if(rs.next()){
			   b=new SpringBoardBean();
			   
			   b.setBoard_no(rs.getInt("board_no"));
			   b.setBoard_name(rs.getString("board_name"));
			   b.setBoard_title(rs.getString("board_title"));
			   b.setBoard_cont(rs.getString("board_cont"));
			   b.setBoard_hit(rs.getInt("board_hit"));
			   b.setBoard_pwd(rs.getString("board_pwd"));
		   }
		   rs.close(); pstmt.close(); con.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return b;
	}

	/* 관리자 게시판 수정(myBatis) */
	public void editBoard(SpringBoardBean editb) throws SQLException{
		this.sqlSession.update("aboardEdit",editb);	
	}
	
	/* 관리자 게시판 수정(iBatis) */
	/*public void editBoard(SpringBoardBean editb) throws SQLException{
		this.template.update("aboardEdit",editb);	
	}*/

	/* 관리자 게시판 삭제(myBatis) */
	public void deleteBoard(int board_no) throws SQLException{
		this.sqlSession.delete("aboard_Del",board_no);		
	}
	/* 관리자 게시판 삭제(iBatis) */
	/*public void deleteBoard(int board_no) throws SQLException{
		this.template.delete("aboard_Del",board_no);		
	}*/
}
