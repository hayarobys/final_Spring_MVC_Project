package spring.mvc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import spring.mvc.model.SpringBoardBean;

// JDBC(DBCP)+iBatis 연동
@Repository
public class BoardDAOImpl {
	
	// iBatis 객체 주입
	@Autowired
	private SqlMapClientTemplate template;

	Connection con=null;
	PreparedStatement pstmt=null;
	ResultSet rs=null;
	DataSource ds=null;
	String sql=null;
	
	public BoardDAOImpl(){ // 생성자
	  try{
		  Context ctx=new InitialContext();
		  ds=
			(DataSource)ctx.lookup("java:comp/env/jdbc/OracleDB");	  
	  }catch(Exception e){
		  e.printStackTrace();
	  }
	}

	/* 게시판 저장 + iBatis */
	public void insertBoard(SpringBoardBean board){
		this.template.insert("board_insert",board);
	}
	/*게시판 저장(JDBC) */
	/*public void insertBoard(SpringBoardBean board) {
		try{
			con=ds.getConnection();
			sql="select max(board_no) from springBoard";
			//max 함수를 사용하여 최대 글번호값을 가져온다.
			pstmt=con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			  			  
			con=ds.getConnection();
			sql="insert into springBoard values(springBoard_seq.nextval," 
			     +"?,?,?,?,?,springBoard_seq.nextval,0,0,sysdate)";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, board.getBoard_name());
			pstmt.setString(2,board.getBoard_title());
			pstmt.setString(3,board.getBoard_cont());
			pstmt.setString(4,board.getBoard_pwd());
			pstmt.setInt(5,0);   // 조회수 
			//pstmt.setInt(6,num);   // 글그룹번호
			pstmt.executeUpdate();
			
			pstmt.close(); con.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}*/
	
	/* 게시물 총 개수 + iBatis */
	public int getListCount() {
		int re = (Integer)this.template.queryForObject("board_count");
		return re;
	}
	/*게시물 총 개수(JDBC) */
	/*public int getListCount() {
	    int count=0;
	    try{
	    	con=ds.getConnection();
	    	sql="select count(board_no) from springBoard";
	    	pstmt=con.prepareStatement(sql);
	    	rs=pstmt.executeQuery();//쿼리문 실행
	    	if(rs.next()){
	    		count=rs.getInt(1);//총 레코드 개수
	    	}
	    	rs.close(); pstmt.close(); con.close();
	    }catch(Exception e){
	    	e.printStackTrace();
	    }
		return count;
	}	*/
	
	/* 게시판 목록(JDBC)*/
	public List<SpringBoardBean> getBoardList(int page, int limit) {
		sql="select * from (select springBoard.*, rownum as rnum "
				+ " from (select * from springBoard order by " 
				+ " board_ref desc, board_level asc) springBoard)" 
				+ " where rnum>=? and rnum<=?";
		       // iBatis 설정파일에서 관계연산 표현
		       // <![CDATA[ <= ]]>
				List<SpringBoardBean> list = new ArrayList<SpringBoardBean>();
				
				int startrow=(page-1)*10+1; //읽기 시작할 row 번호.
				int endrow=page*limit;					
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
						b.setBoard_hit(rs.getInt("board_hit"));
						b.setBoard_ref(rs.getInt("board_ref"));
						b.setBoard_step(rs.getInt("board_step"));
						b.setBoard_level(rs.getInt("board_level"));						
						b.setBoard_regdate(rs.getString("board_regdate"));						
						list.add(b);
					}			
					rs.close(); pstmt.close(); con.close();
				}catch(Exception e){
					e.printStackTrace();
				}		
				return list;
	}

	/* 게시판 조회수 증가(iBatis)*/
	public void boardHit(int board_no){
		this.template.update("board_hit",board_no);
	}
	
	/* 게시판 내용보기(iBatis) */
	public SpringBoardBean getBoardCont(int board_no){
		SpringBoardBean bean;
		bean = (SpringBoardBean)this.template.
			queryForObject("board_cont",board_no);
		return bean;
	}
	
	/*답변저장(JDBC)*/
	public void replyBoard(SpringBoardBean b) {
		try{
			int board_ref=b.getBoard_ref();
			int board_step=b.getBoard_step();
			int board_level=b.getBoard_level();
			
			con=ds.getConnection();
			sql="update springBoard set board_level=board_level+1 "
			+" where board_ref=? and board_level>?";
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1,board_ref);
			pstmt.setInt(2,board_level);
			pstmt.executeUpdate();//수정 쿼리문 실행
			
			board_step=board_step+1;//답변글 위치번호 1증가
			board_level=board_level+1;//답변글 레벨순서 1증가
			
			sql="insert into springBoard (board_no,board_name,board_title,"
		+"board_cont,board_pwd,board_ref,board_step,board_level,"
		+"board_regdate) values(springBoard_seq.nextval,?,?,?,?,?,?,?,"
		+"sysdate)";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1,b.getBoard_name());
			pstmt.setString(2,b.getBoard_title());
			pstmt.setString(3,b.getBoard_cont());
			pstmt.setString(4,b.getBoard_pwd());
			pstmt.setInt(5,board_ref);
			pstmt.setInt(6,board_step);
			pstmt.setInt(7,board_level);
			pstmt.executeUpdate();//저장 쿼리문 실행
			
			pstmt.close(); con.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	/*게시물 수정(iBatis) */
	public void editBoard(SpringBoardBean bean){
		this.template.update("board_edit",bean);
	}
		
	/*게시물 삭제 + iBatis */
	public void deleteBoard(int board_no) {
		this.template.delete("board_del",board_no);
	}
	
	/*게시물 삭제(JDBC) */
	/*public void deleteBoard(int board_no) {
		try{
			con=ds.getConnection();
			sql="delete from springBoard where board_no=?";
		    pstmt=con.prepareStatement(sql);
		    pstmt.setInt(1,board_no);
		    pstmt.executeUpdate();
		    //삭제 쿼리문 실행
		    
		    pstmt.close(); con.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}*/
	
}


