package spring.mvc.model;

//우편번호 DTO 클래스
public class ZipcodeBean {
	private String zipcode;  // 우편번호
	private String addr; // 시도 구군 동 저장(검색주소)
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	
	
	

}
