package spring.mvc.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import spring.mvc.model.SpringGuestBean;

@Repository
public class GuestDAOImpl implements GuestDAO {
	/*
	 * JdbcTemplate 주요 메서드
	 * 1. queryForObject(sql,rowMapper,?값) : 조건검색
	 * 2. update(sql,?값1, ?값2) : 수정,삭제,삽입
	 * 3. query(sql,rowMapper) : 레코드 전체검색
	 * 4. queryForList(sql,rowMapper,?값) : list 반환
	 */
	// JdbcTemplate 객체 주입 받기
	@Autowired
	JdbcTemplate jdbcTemplate;

	/* 방명록 저장*/
	@Override
	public void insertGuest(SpringGuestBean gbean) {
		String sql="insert into springGuest(guest_no,"
		+ " guest_name,guest_title,guest_cont,"
		+ " guest_pwd, guest_regdate) "
		+ " values(springGuest_seq.nextval,?,?,?,?, "
		+ " sysdate)";
		this.jdbcTemplate.update(sql,
		   gbean.getGuest_name(),gbean.getGuest_title(),
		   gbean.getGuest_cont(),gbean.getGuest_pwd());
	}

	/* 방명록 목록보기 */ 
	@Override
	public List<SpringGuestBean> getGuestList() {
		String 
		sql="select * from springGuest order by guest_no desc";
		return this.jdbcTemplate.query(sql, 
				new RowMapper<SpringGuestBean>() {

					@Override
					public SpringGuestBean mapRow(ResultSet rs, int rownum)
							throws SQLException {
						SpringGuestBean bean=new SpringGuestBean();
						bean.setGuest_no(rs.getInt("guest_no"));
						bean.setGuest_name(rs.getString("guest_name"));
						bean.setGuest_title(rs.getString("guest_title"));
						bean.setGuest_cont(rs.getString("guest_cont"));
						bean.setGuest_pwd(rs.getString("guest_pwd"));
						bean.setGuest_hit(rs.getInt("guest_hit"));
						bean.setGuest_regdate(rs.getString("guest_regdate"));
						return bean;
					}			
		});
	}

	/* 총 레코드 수*/
	@Override
	public int listCount() {
		String sql="select count(*) from springGuest";
		return this.jdbcTemplate.queryForInt(sql);
		// queryForInt() : 1개의 정수값을 리턴
	}

	/* 방명록 내용 보기*/
	@Override
	public SpringGuestBean getGuestCont(int no) {
		String 
	sql="select * from springGuest where guest_no=?";		
		return this.jdbcTemplate.queryForObject(sql,
				new RowMapper<SpringGuestBean>() {
					@Override
					public SpringGuestBean mapRow(ResultSet rs, int arg1)
							throws SQLException {
				  	    SpringGuestBean bean = new SpringGuestBean();
				  	    bean.setGuest_no(rs.getInt("guest_no"));
				  	    bean.setGuest_name(rs.getString("guest_name"));
				  	    bean.setGuest_title(rs.getString("guest_title"));
				  	    bean.setGuest_cont(rs.getString("guest_cont"));
				  	    bean.setGuest_pwd(rs.getString("guest_pwd"));
				  	    bean.setGuest_hit(rs.getInt("guest_hit"));
				  	    bean.setGuest_regdate(rs.getString("guest_regdate"));
						return bean;  // DTO 객체 리턴
					}			
		},no);
	}

	/* 조회수 증가 */
	@Override
	public void hitUpdate(int no) {
		String sql="update springGuest set "
				+ " guest_hit=guest_hit+1 "
				+ " where guest_no=?";
		this.jdbcTemplate.update(sql, no);
	}

	/* 방명록 수정*/
	@Override
	public void guestEdit(SpringGuestBean ebean) {
		String sql="update springGuest set "
		+ " guest_name=?, guest_title=?, guest_cont=?,"
		+ " guest_pwd=? where guest_no=?";		
		this.jdbcTemplate.update(sql,
			ebean.getGuest_name(),
			ebean.getGuest_title(),
			ebean.getGuest_cont(),
			ebean.getGuest_pwd(),
			ebean.getGuest_no());
	}

	/* 방명록 삭제*/
	@Override
	public void deleteGuest(int no) {
		String sql="delete from springGuest "
			+ " where guest_no=?";	
		this.jdbcTemplate.update(sql, no);
	}

}
