/*회원관리*/
package spring.mvc.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import spring.mvc.dao.MemberDAOImpl;
import spring.mvc.model.SpringMemberBean;
import spring.mvc.model.ZipcodeBean;
import spring.mvc.model.ZipcodeBean2;

@Controller
public class MemberController {
	
	/* Spring DI 의존관계 설정 */
	@Autowired
	private MemberDAOImpl memService;
	
	/* 로그인 입력 폼 */
	@RequestMapping(value="/member_Login.do")
	public ModelAndView mem_Login(){
		
		return new ModelAndView("member/member_login");
		// view page 지정 : /jsp/member/member_login.jsp
	}	
	/* 회원가입 입력 폼 */
	@RequestMapping(value="/member_Join.do")
	public ModelAndView mem_Join(){
		
		return new ModelAndView("member/member_join");
	}	
	/* 아이디 중복 체크 */
	@RequestMapping(value="/mem_idcheck.do",
			            method=RequestMethod.POST)
	public void mem_idcheck(HttpServletRequest 
			request, HttpServletResponse response) throws SQLException, IOException{
		String id= request.getParameter("memid");
		PrintWriter out = response.getWriter();
		
		SpringMemberBean bean = this.memService.idCheck(id);
		int re = 0;
		if(bean != null){ // 중복 아이디가 있는 경우
			re = 1; // 중복된 경우			
		}
		out.println(re);  // ajax()에 값을 넘긴
		//return null;
	}
	
	/* 우편번호 검색 입력창 */
	@RequestMapping(value="/zip_find.do")
	public ModelAndView zip_find(){
		return new ModelAndView("member/member_post");
		// view page : /jsp/member/member_post.jsp
	}
	
	/*우편번호 검색 결과*/
	@RequestMapping(value="/zip_find_ok.do",method=RequestMethod.POST)
	public ModelAndView zip_find_ok(HttpServletRequest request,
			  HttpServletResponse response) throws Exception{
		    String dong=request.getParameter("dong");

			List<ZipcodeBean2> zipcodeList=new ArrayList<ZipcodeBean2>();

			zipcodeList=this.memService.findZipcode("%"+dong+"%");
			//동을 기준으로 주소를 검색해서 컬렉션에 저장
			//%는 하나 또는 하나이상의 임의의 문자와 매핑

			List<ZipcodeBean> zipcodeList2=new ArrayList<ZipcodeBean>();

			for(int i=0; i<zipcodeList.size();i++){
				ZipcodeBean2 zipcode_addr=zipcodeList.get(i);

				String zipcode=zipcode_addr.getZipcode();//우편번호 저장
				String sido=zipcode_addr.getSido(); //서울시,경기도 저장
				String gugun=zipcode_addr.getGugun();//구,군
				String dong2=zipcode_addr.getDong(); //동
				String addr=sido+" "+gugun+" "+dong2;//서울시 서초구 서초동
				//String addr2=sido+" "+gugun+" "+dong2+" "+bunji; //번지까지 저장
				//zipcodeList.add(zipcode+","+addr+","+addr2);

				ZipcodeBean zip=new ZipcodeBean();
				zip.setZipcode(zipcode);
				zip.setAddr(addr);

				zipcodeList2.add(zip);
				//컬렉션에 주소를 요소값으로 저장
			}

			ModelAndView zipcodeM=new ModelAndView("member/member_post");
			//생성자 전달인자의 member/member_post의 뜻은 member폴더의 뷰페이지
			//member_post.jsp파일이 실행
			zipcodeM.addObject("zipcodelist",zipcodeList2);
			//좌측 zipcodelist키값에 우측의 컬렉션 우편번호와 주소값을 저장
			zipcodeM.addObject("dong",dong);		
	        //dong키에 입력 읍면동을 저장.
			return zipcodeM;	
	  }
	
	  /* 회원 저장*/
	  @RequestMapping(value="/member_Join_Ok.do",method=RequestMethod.POST)
	  public void mem_join_ok(HttpServletRequest request,
			  HttpServletResponse response) throws Exception{
		 
		  PrintWriter out=response.getWriter();		  
		  String mem_id=request.getParameter("mem_id").trim();
		  SpringMemberBean db_id=this.memService.idSearch(mem_id);
		  
		  //중복 아이디 검색		  
		  if(db_id != null){//중복 아이디가 있는 경우
			  out.println("<script>");
			  out.println("alert('동일 아이디 입니다!')");
			  out.println("history.go(-1)");
			  out.println("</script>");
		  }else{//중복 아이디가 없는 경우 회원가입
		  String mem_pwd=request.getParameter("mem_pwd").trim();
		  String mem_name=request.getParameter("mem_name").trim();
		  String mem_zip=request.getParameter("mem_zip").trim();
		  String mem_zip2=request.getParameter("mem_zip2").trim();
		  String mem_addr=request.getParameter("mem_addr").trim();
		  String mem_addr2=request.getParameter("mem_addr2").trim();
		  String phone01=request.getParameter("mem_phone01").trim();
		  String phone02=request.getParameter("mem_phone02").trim();
		  String phone03=request.getParameter("mem_phone03").trim();
		  String mem_phone=phone01+"-"+phone02+"-"+phone03;
		  String mail_id=request.getParameter("mail_id").trim();
		  String mail_domain=request.getParameter("mail_domain").trim();
		  String mem_email=mail_id+"@"+mail_domain;
		  
		  SpringMemberBean m=new SpringMemberBean();
		  m.setMem_id(mem_id); m.setMem_pwd(mem_pwd);
		  m.setMem_name(mem_name); m.setMem_zip(mem_zip);
		  m.setMem_zip2(mem_zip2); m.setMem_addr(mem_addr);
		  m.setMem_addr2(mem_addr2); m.setMem_phone(mem_phone);
		  m.setMem_email(mem_email);
		  
		  this.memService.insertMember(m);//저장메서드 호출
		  response.sendRedirect("member_Login.do");  
		  }
		  //return null;
	  }
	  
	  /* 로그인 인증 체크 */
	  @RequestMapping(value="/member_Login_ok.do",method=RequestMethod.POST)
	  public ModelAndView login_ok(HttpServletRequest request,
			  HttpServletResponse response, HttpSession session) throws IOException, SQLException{
		 response.setContentType("text/html;charset=UTF-8");
		 PrintWriter out = response.getWriter();
		 String id = request.getParameter("login_id").trim();
		 String pwd = request.getParameter("login_pwd").trim();
		 // idSearch()메서드를 호출하여 로그인 결정
		 SpringMemberBean bean = this.memService.idSearch(id);
		 // 기존 비번 검색
		 if(bean != null){
			 if(bean.getMem_pwd().equals(pwd)){
				 session.setAttribute("id",id);
				 session.setAttribute("name",bean.getMem_name());
				 // main.jsp 페이지 이동
				 ModelAndView mv = new ModelAndView("main");
				 return mv;
				 //response.sendRedirect("main.do");
			 }else{ // 비번이 틀린 경우
				 out.println("<script>");
				 out.println("alert('비번이 틀립니다.')");
				 out.println("history.back()");
				 out.println("</script>");
			 }
		 }else{ // id가 틀린 경우
			 out.println("<script>");
			 out.println("alert('가입 회원이 아닙니다.')");
			 out.println("history.back()");
			 out.println("</script>");
		 }
		 return null;
	  }
	  /* 로그 아웃 */
	  @RequestMapping(value="/member_Logout.do",method=RequestMethod.POST)
	  public ModelAndView logout(HttpServletRequest request,
			  HttpServletResponse response, HttpSession session) throws IOException{
		  response.setContentType("text/html;charset=UTF-8");
		  PrintWriter out = response.getWriter();
		  session.invalidate(); // 세션 만료
		  
		  out.println("<script>");
		  out.println("alert('로그아웃 되었습니다.')");
		  out.println("location='./index.jsp'");
		  out.println("</script>");
		  return null;
	  }
	  /* 비번찾기 입력폼 */
	  @RequestMapping(value="/pwd_Find.do")
	  public ModelAndView pwd_find(){
		  ModelAndView mv = new ModelAndView();
		  mv.setViewName("member/pwd_find");
		  // jsp/member/pwd_find.jsp
		  return mv;
	  }
	  /* 비번 찾기 결과 */
	  @RequestMapping(value="/pwd_Find_ok.do",method=RequestMethod.POST)
	  public ModelAndView pwd_find_ok(HttpServletRequest 
			  request, HttpServletResponse response) throws IOException{
		  response.setContentType("text/html;charset=UTF-8");
		  PrintWriter out = response.getWriter();
		  String pwd_id 
		      = request.getParameter("pwd_id").trim();
		  String pwd_name 
		      = request.getParameter("pwd_name").trim();
		  SpringMemberBean bean=new SpringMemberBean();
		  bean.setMem_id(pwd_id);
		  bean.setMem_name(pwd_name);
		  SpringMemberBean bean1 
		         = this.memService.findPwd(bean);
		  if(bean1 != null){
			  ModelAndView mv = 
				new ModelAndView("member/pwd_find_ok");			   
			  mv.addObject("password",bean1.getMem_pwd());
			  return mv;
		  }else{ // 입력한 id와 이름이 없는 경우
			  out.println("<script>");
			  out.println("alert('검색된 비번이 없습니다')");
			  out.println("history.back()");
			  out.println("</script>");			  
		  }
		  return null;
	  }
	  
	  /*정보 수정 폼 */
	  @RequestMapping(value="/member_Edit.do")
	  public ModelAndView mem_edit(HttpServletRequest request,
			  HttpServletResponse response,HttpSession session)
	  throws Exception{
		  response.setContentType("text/html;charset=UTF-8");
		  PrintWriter out=response.getWriter();
		  session=request.getSession();
		  
		  String id=(String)session.getAttribute("id");
		  //세션 아이디값을 가져옴
		  
		  if(id==null){
			  out.println("<script>");
			  out.println("alert('다시 로그인 해주세요!')");
			  out.println("location='member_Login.do'");
			  out.println("</script>");
		  }else{
			  SpringMemberBean m=this.memService.idSearch(id);
			  //아이디에 해당하는 회원정보를 가져옴
			  
			  StringTokenizer p=
					  new StringTokenizer(m.getMem_phone(), "-");
			  //-를 기준으로 폰번호를 분리
			  String phone01=p.nextToken();//첫번째 폰번호 저장
			  String phone02=p.nextToken();//두번째 폰번호 저장
			  String phone03=p.nextToken(); //세번째 폰번호 저장
			  
			  StringTokenizer em=
					  new StringTokenizer(m.getMem_email(),"@");
			  //@를 기준으로 전자우편 주소를 분리
			  String mail_id=em.nextToken();
			  String mail_domain=em.nextToken();
			  
			  ModelAndView editM=new ModelAndView("member/member_edit");
			  editM.addObject("edit",m);
			  //edit키에 m객체를 저장
			  editM.addObject("phone01",phone01);
			  editM.addObject("phone02",phone02);
			  editM.addObject("phone03",phone03);
			  editM.addObject("mail_id",mail_id);
			  editM.addObject("mail_domain",mail_domain);
			  return editM;
		  }
		  return null;
	  }
	  
	  /*정보수정*/
	  @RequestMapping(value="/member_Edit_ok.do",method=RequestMethod.POST)
	  public ModelAndView mem_edit_ok(HttpServletRequest request,
			  HttpServletResponse response,HttpSession session)
	  throws Exception{
		  response.setContentType("text/html;charset=UTF-8");
		  PrintWriter out=response.getWriter();
		  session=request.getSession();
		  
		  String id=(String)session.getAttribute("id");
		  if(id == null){
			  out.println("<script>");
			  out.println("alert('다시 로그인 하세요!')");
			  out.println("location='member_Login.do'");
			  out.println("</script>");
		  }else{
			  String mem_pwd=request.getParameter("mem_pwd").trim();
			  String mem_name=request.getParameter("mem_name").trim();
			  String mem_zip=request.getParameter("mem_zip").trim();
			  String mem_zip2=request.getParameter("mem_zip2").trim();
			  String mem_addr=request.getParameter("mem_addr").trim();
			  String mem_addr2=request.getParameter("mem_addr2").trim();
			  String mem_phone01=
					  request.getParameter("mem_phone01");
			  String mem_phone02=
					  request.getParameter("mem_phone02").trim();
			  String mem_phone03=
					  request.getParameter("mem_phone03").trim();				  
			  String mem_phone=mem_phone01+"-"+mem_phone02+"-"+mem_phone03;
			  String mail_id=request.getParameter("mail_id").trim();
			  String mail_domain=request.getParameter("mail_domain").trim();
			  String mem_email=mail_id+"@"+mail_domain;
			  
			  SpringMemberBean m33=new SpringMemberBean();
			  m33.setMem_id(id); m33.setMem_pwd(mem_pwd);
			  m33.setMem_name(mem_name); m33.setMem_zip(mem_zip);
			  m33.setMem_zip2(mem_zip2); m33.setMem_addr(mem_addr);
			  m33.setMem_addr2(mem_addr2); m33.setMem_phone(mem_phone);
			  m33.setMem_email(mem_email);
			  
			  this.memService.editMember(m33); //수정 메서드 호출
			  
			  response.sendRedirect("member_Login.do");
		  }
		  return null;
	  }
	  
	  /*회원 삭제폼*/
	  @RequestMapping(value="/member_DEL.do")
	  public ModelAndView mem_DEL(HttpServletRequest request,
			  HttpServletResponse response,HttpSession session)
	  throws Exception{
		  response.setContentType("text/html;charset=UTF-8");
		  PrintWriter out=response.getWriter();
		  session=request.getSession();
		  
		  String id=(String)session.getAttribute("id");
		  if(id==null){
			  out.println("<script>");
			  out.println("alert('다시 로그인 하세요!')");
			  out.println("location='member_Login.do'");
			  out.println("</script>");
		  }else{
		    ModelAndView delM=new ModelAndView("member/member_delete");
		    return delM;
		  }
		  return null;
	  }
	  
	  /*회원 탈퇴*/
	  @RequestMapping(value="/member_DEL_ok.do",method=RequestMethod.POST)
	  public ModelAndView mem_del_ok(HttpServletRequest request,
			  HttpServletResponse response, HttpSession session)
	  throws Exception{
		  response.setContentType("text/html;charset=UTF-8");
		  //웹상에 보이는 언어코딩 타입 지정
		  PrintWriter out=response.getWriter();//출력스트림 객체 생성
		  session=request.getSession(); //세션 객체 생성
		  
		  String id=(String)session.getAttribute("id");
		  //세션 아이디에 저장된 아이디값을 가져옴.
		  if(id==null){
			  out.println("<script>");
			  out.println("alert('다시 로그인 하세요!')");
			  out.println("location='member_Login.do'");
			  out.println("</script>");
		  }else{
			  SpringMemberBean db_pwd=this.memService.idSearch(id);
			  //아이디에 해당하는 회원정보 즉 비번을 가져옴.
			  String del_pwd=request.getParameter("del_pwd").trim();
			  String del_cont=request.getParameter("del_cont").trim();
			  if(db_pwd.getMem_pwd().equals(del_pwd)){//비번이 같다면
				  SpringMemberBean m=new SpringMemberBean();
				  m.setMem_id(id); m.setMem_delcont(del_cont);
				  
				  this.memService.delMem(m);//삭제 메서드 호출
				  session.invalidate();//세션을 만료
				  
				  response.sendRedirect("member_Login.do");
			  }else{//비번이 다르다면
				  out.println("<script>");
				  out.println("alert('비번이 다릅니다!')");
				  out.println("history.go(-1)");
				  out.println("</script>");
			  }
		  }
		  return null;
	  }
}



