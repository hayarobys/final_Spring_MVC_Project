package spring.mvc.model;

//사용자 단 공지사항 빈
public class SpringGongjiBean2 {
	
	private int gongji_no;
	private String gongji_title;
	private String gongji_name;
	private int gongji_hit;
	private String gongji_cont;
	private String gongji_regdate;
	
	public int getGongji_no() {
		return gongji_no;
	}
	public void setGongji_no(int gongji_no) {
		this.gongji_no = gongji_no;
	}
	public String getGongji_title() {
		return gongji_title;
	}
	public void setGongji_title(String gongji_title) {
		this.gongji_title = gongji_title;
	}
	public String getGongji_cont() {
		return gongji_cont;
	}
	public void setGongji_cont(String gongji_cont) {
		this.gongji_cont = gongji_cont;
	}
	public String getGongji_name() {
		return gongji_name;
	}
	public void setGongji_name(String gongji_name) {
		this.gongji_name = gongji_name;
	}
	public int getGongji_hit() {
		return gongji_hit;
	}
	public void setGongji_hit(int gongji_hit) {
		this.gongji_hit = gongji_hit;
	}
	public String getGongji_regdate() {
		return gongji_regdate;
	}
	public void setGongji_regdate(String gongji_regdate) {
		this.gongji_regdate = gongji_regdate.substring(0,10);
	}
	
	

}
