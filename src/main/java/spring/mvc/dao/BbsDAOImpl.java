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

import spring.mvc.model.SpringBbsBean;

@Repository
public class BbsDAOImpl {
    /* iBatis 객체 주입 */
	@Autowired
	private SqlMapClientTemplate template;

	
	/* jdbc(데이터베이스 커넥션 풀 코드)*/
	Connection con=null;
	PreparedStatement pstmt=null;
	ResultSet rs=null;
	DataSource ds=null;
	String sql=null;
	
	/*커넥션 풀 객체 생성하는 생성자*/
	public BbsDAOImpl(){//기본 생성자 정의
		try{
			Context ctx=new InitialContext();
			ds=(DataSource)ctx.lookup("java:comp/env/jdbc/OracleDB");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/*자료실 저장 + ibatis*/
	public void insertBbs(SpringBbsBean bbs) throws SQLException{
		this.template.insert("bbs_insert",bbs);		
	}

	/*총 게시물 수 + ibatis*/
	public int getListCount() throws SQLException{
		int count=0;
		count=
   ((Integer)this.template.queryForObject("bbs_count"));
		return count;
	}

	/*게시물 목록 : (JDBC)*/
	public List<SpringBbsBean> getBoardList(int page, int limit) {
		sql="select * from (select springBbs.*, rownum as rnum "
				+ " from (select * from springBbs order by bbs_ref desc," 
				+ " bbs_level asc) springBbs)"
				+ " where rnum>=? and rnum<=?";
				
				List<SpringBbsBean> list = new ArrayList<SpringBbsBean>();
				
				int startrow=(page-1)*10+1; //읽기 시작할 row 번호.
				int endrow=page*limit;      //읽을 마지막 row 번호.	
				try{
					con = ds.getConnection();
					pstmt = con.prepareStatement(sql);
					pstmt.setInt(1, startrow);
					pstmt.setInt(2, endrow);
					rs = pstmt.executeQuery();
					
					while(rs.next()){
						SpringBbsBean bbs = new SpringBbsBean();
						bbs.setBbs_no(rs.getInt("bbs_no"));
						bbs.setBbs_name(rs.getString("bbs_name"));
						bbs.setBbs_subject(rs.getString("bbs_subject"));
						bbs.setBbs_content(rs.getString("bbs_content"));
						bbs.setBbs_file(rs.getString("bbs_file"));
						bbs.setBbs_ref(rs.getInt("bbs_ref"));
						bbs.setBbs_step(rs.getInt("bbs_step"));
						bbs.setBbs_level(rs.getInt("bbs_level"));
						bbs.setBbs_hit(rs.getInt("bbs_hit"));
						bbs.setBbs_regdate(rs.getString("bbs_regdate"));						
						list.add(bbs);
					}			
					rs.close(); pstmt.close(); con.close();
				}catch(Exception e){
					e.printStackTrace();
				}		
				return list;
	}

	/*조회수 증가 + ibatis*/
	public void bbsHit(int num) throws SQLException{
		this.template.update("bbs_hit",num);		
	}

	/*자료실 게시물 내용 보기: (JDBC) */
	public SpringBbsBean getBbsCont(int num) {
		SpringBbsBean bbs=null;
		
		try{
			con=ds.getConnection();//오라클 커넥션 풀 연결 객체 생성
			sql="select * from springBbs where bbs_no=?";
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1,num);
			//1번물음표에 정수형 숫자로 글번호 저장
			rs=pstmt.executeQuery();//쿼리문 실행
			
			if(rs.next()){
				bbs=new SpringBbsBean();
				bbs.setBbs_no(rs.getInt("bbs_no"));
				bbs.setBbs_name(rs.getString("bbs_name"));
				bbs.setBbs_subject(rs.getString("bbs_subject"));
				bbs.setBbs_pass(rs.getString("bbs_pass"));
				bbs.setBbs_content(rs.getString("bbs_content"));
				bbs.setBbs_file(rs.getString("bbs_file"));
				bbs.setBbs_hit(rs.getInt("bbs_hit"));
				bbs.setBbs_ref(rs.getInt("bbs_ref"));//글 그룹번호
				bbs.setBbs_step(rs.getInt("bbs_step"));//답변글 위치번호
				bbs.setBbs_level(rs.getInt("bbs_level"));//답변글 레벨
				//순서
				bbs.setBbs_regdate(rs.getString("bbs_regdate"));
			}
			rs.close(); pstmt.close(); con.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return bbs;
	}

	/*답변글 레벨 1씩증가 + ibatis */
	public void updateReply(SpringBbsBean bbs) throws SQLException{
		this.template.update("reply_update",bbs);		
	}

	/* 답변글 저장 + ibatis */
	public void replyok(SpringBbsBean bbs) throws SQLException{
		this.template.insert("reply_insert",bbs);		
	}

	/* 자료실 수정 : (JDBC) */	
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

	/* 자료실 삭제 + ibatis */
	public void deleteBbs(int bbs_no) throws SQLException{
		this.template.delete("bbs_del", bbs_no);		
	}

	/*[참고] 자료실 검색 + ibatis */
	public int getListCount3(String find_name, String find_field) 
			throws SQLException{
		int count=0;
		if(find_field.equals("bbs_name")){//검색어가 글쓴이
		count=((Integer)this.template.queryForObject("bbsfind_cnt1",find_name));
		}else if(find_field.equals("bbs_subject")){//검색어가 제목
		count=((Integer)this.template.queryForObject("bbsfind_cnt2",find_name));	
		}
		return count;
	}

	/*[참고]자료실 검색 결과 목록 (JDBC)*/
	public List<SpringBbsBean> getBbsList3(int page, int limit, String find_name,
			String find_field) {
		List<SpringBbsBean> list = new ArrayList<SpringBbsBean>();
		try{
			con = ds.getConnection();
			sql="select * from"+"(select rownum r, bbs_no,bbs_name,"
		     +"bbs_subject,bbs_content,bbs_file," +
			"bbs_ref,bbs_step,bbs_level,bbs_hit,bbs_regdate "+
			" from (select * from springBbs order by bbs_ref" +
			" desc,bbs_step asc) where "+find_field+" like ? and"
			+" rownum <= ?) where r >= ?";
		    pstmt=con.prepareStatement(sql);
			pstmt.setString(1, "%"+find_name+"%");
			pstmt.setInt(2, page*limit);
			pstmt.setInt(3, ((page-1)*limit)+1);
			rs = pstmt.executeQuery();

			while(rs.next()){
				SpringBbsBean bbsbean = new SpringBbsBean();
				bbsbean.setBbs_no(rs.getInt("bbs_no"));
				bbsbean.setBbs_name(rs.getString("bbs_name"));
				bbsbean.setBbs_subject(rs.getString("bbs_subject"));
				bbsbean.setBbs_content(rs.getString("bbs_content"));
				bbsbean.setBbs_file(rs.getString("bbs_file"));
				bbsbean.setBbs_ref(rs.getInt("bbs_ref"));
				bbsbean.setBbs_step(rs.getInt("bbs_step"));
				bbsbean.setBbs_level(rs.getInt("bbs_level"));
				bbsbean.setBbs_hit(rs.getInt("bbs_hit"));
		bbsbean.setBbs_regdate(rs.getString("bbs_regdate").substring(0,10));
				
		        list.add(bbsbean); //컬렉션에 요소값 추가		
			}	
			rs.close(); pstmt.close(); con.close();
		}catch(Exception e){
			e.printStackTrace();
		}				
		return list;
	}		
}


