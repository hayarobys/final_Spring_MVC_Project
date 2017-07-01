package spring.mvc.model;

//관리자 용 게시판 빈 클래스
public class SpringBoardBean2 {
	
	private int board_no;
	private String board_name;
	private String board_title;
	private int board_hit;
	private String board_regdate;
	private int board_step;
	public int getBoard_no() {
		return board_no;
	}
	public void setBoard_no(int board_no) {
		this.board_no = board_no;
	}
	public String getBoard_name() {
		return board_name;
	}
	public void setBoard_name(String board_name) {
		this.board_name = board_name;
	}
	public String getBoard_title() {
		return board_title;
	}
	public void setBoard_title(String board_title) {
		this.board_title = board_title;
	}
	public int getBoard_hit() {
		return board_hit;
	}
	public void setBoard_hit(int board_hit) {
		this.board_hit = board_hit;
	}
	public String getBoard_regdate() {
		return board_regdate;
	}
	public void setBoard_regdate(String board_regdate) {
		this.board_regdate = board_regdate;
	}
	public int getBoard_step() {
		return board_step;
	}
	public void setBoard_step(int board_step) {
		this.board_step = board_step;
	}
	 
	
}
