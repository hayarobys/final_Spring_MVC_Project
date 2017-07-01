package spring.mvc.controller;

import java.io.PrintWriter;
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

import spring.mvc.dao.AdminMemberDAOImpl;
import spring.mvc.model.SpringMemberBean;

@Controller
public class AdminMemberController {//관리자 회원관리 컨트롤 클래스

	@Autowired
	private AdminMemberDAOImpl adminMemberService;

	/* 관리자 회원목록 */
	@RequestMapping(value="/admin_member_list.do")
	public ModelAndView admin_member_list(HttpServletRequest request,
			HttpServletResponse response,
			HttpSession session)
	throws Exception{
		response.setContentType("text/html;charset=UTF-8");
		//웹상에 보이는 한글을 안깨지게 언어코딩 타입을 지정
		PrintWriter out=response.getWriter();
		//출력스트림 객체를 생성
		session=request.getSession();//세션 객체를 생성
		String admin_id=(String)session.getAttribute("admin_id");
		//관리자 세션 아이디값을 저장
		
		if(admin_id == null){
			out.println("<script>");
			out.println("alert('관리자 아이디로 로그인 하세요!')");
			out.println("location='admin_index.do'");
			out.println("</script>");
		}else{//관리자 회원목록
			int page=1;
			int limit=7;
			
			AdminMemberDAOImpl gd=new AdminMemberDAOImpl();			
			
			if(request.getParameter("page")!=null){
				//get방식으로 넘어온 쪽번호가 정수형이라고 해도
				//문자열로 바껴져서 넘어온다.
				page=Integer.parseInt(request.getParameter("page"));
				//사칙연산을 하기 위해서 parseInt()메서드에 의해서
				//정수형 숫자로 바꾼다.
			}
			
			int listcount=this.adminMemberService.getListCount(); //총 리스트 수를 받아옴.		
			
			List<SpringMemberBean> memList = gd.getMemberList(page,limit); //리스트를 받아옴.
											
			//총 페이지 수.
	  		int maxpage=(int)((double)listcount/limit+0.95); //0.95를 더해서 올림 처리.
	  		//현재 페이지에 보여줄 시작 페이지 수(1, 11, 21 등...)
	  		int startpage = (((int) ((double)page / 10 + 0.9)) - 1) * 10 + 1;
	  		//현재 페이지에 보여줄 마지막 페이지 수.(10, 20, 30 등...)
	  		int endpage = maxpage;
	  		
	  		if (endpage>startpage+10-1) endpage=startpage+10-1;
	  		
	  		request.setAttribute("page", page);		  //현재 페이지 수.
	  		request.setAttribute("maxpage", maxpage); //최대 페이지 수.
	  		request.setAttribute("startpage", startpage); //현재 페이지에 표시할 첫 페이지 수.
	  		request.setAttribute("endpage", endpage);     //현재 페이지에 표시할 끝 페이지 수.
			request.setAttribute("listcount",listcount); //총게시물수.
			request.setAttribute("memList", memList);
			
			ModelAndView listM= new ModelAndView("admin/admin_member_list");
		   	return listM;
		}
		return null;
	}
	
  /* 관리자 회원관리 검색 */
  @RequestMapping(value="/admin_member_find.do")
  public ModelAndView admin_member_find(HttpServletRequest request,
		  HttpServletResponse response,HttpSession session)
  throws Exception{
	  request.setCharacterEncoding("UTF-8");
	  response.setContentType("text/html;charset=UTF-8");
	  PrintWriter out=response.getWriter();
	  session=request.getSession();
	  
	  String admin_id=(String)session.getAttribute("admin_id");
	  
	  if(admin_id == null){
		  out.println("<script>");
		  out.println("alert('관리자 아이디로 로그인 하세요!')");
		  out.println("location='admin_index.do'");
		  out.println("</script>");		  
	  }else{
		  //회원검색부분
		  
		  	int page=1;
			int limit=7;
			
			if(request.getParameter("page")!=null){
				page=Integer.parseInt(request.getParameter("page"));
			}
			
			String find_name=null;
			
			if(request.getParameter("find_name").trim() != null){
			 find_name=request.getParameter("find_name").trim();
			 //검색어 저장
			 //find_name=new String(find_name.getBytes("8859_1"),"UTF-8");
			 //get방식으로 넘어온 언어 코딩 타입을 UTF-8로 바꿔서 안깨지게 함.
			}
			String find_field=null;
			if(request.getParameter("find_field")!=null){
			find_field=request.getParameter("find_field");
			//검색 필드 저장
			}		
			//System.out.println(find_name);	
			
			int listcount=this.adminMemberService.getListCount3("%"+find_name+"%",find_field); 
			//총 리스트 수를 받아옴.
						
			List<SpringMemberBean> memList = 
				this.adminMemberService.getMemList3(page,limit,find_name,find_field); 
			
			int maxpage=0;
			if(listcount%limit==0){
				maxpage=listcount/limit;
			}else{
				maxpage=listcount/limit+1;
			}
			int startpage=(((int) ((double)page / 10 +0.9))-1)*10+1;
			
			int endpage=maxpage;
			
			if(endpage>startpage+limit-1) endpage=startpage+limit-1;
			
			
	   		request.setAttribute("page", page);		  //현재 페이지 수.
	   		request.setAttribute("maxpage", maxpage); //최대 페이지 수.
	   		request.setAttribute("startpage", startpage); //현재 페이지에 표시할 첫 페이지 수.
	   		request.setAttribute("endpage", endpage);     //현재 페이지에 표시할 끝 페이지 수.
			request.setAttribute("listcount",listcount); //글 수.
			request.setAttribute("memList", memList);
			
			ModelAndView model=new ModelAndView("admin/admin_member_find");
			model.addObject("find_name",find_name);
			model.addObject("find_field",find_field);
			return model;	
	  }
	  return null;
  }
  
  /*관리자 회원정보 보기*/
  @RequestMapping(value="/admin_member_cont.do")
  public ModelAndView admin_mem_cont(HttpServletResponse response,
		  HttpServletRequest request,HttpSession session)
  throws Exception{
	  response.setContentType("text/html;charset=UTF-8");
	  PrintWriter out=response.getWriter();
	  session=request.getSession();
	  String admin_id=(String)session.getAttribute("admin_id");
	  
	  if(admin_id == null){
		  out.println("<script>");
		  out.println("alert('관리자 아이디로 로그인 하세요!')");
		  out.println("location='admin_index.do'");
		  out.println("</script>");
	  }else{
		  String mem_id=request.getParameter("mem_id");
		  int page=1;
		  
		  if(request.getParameter("page") != null){
			  page=Integer.parseInt(request.getParameter("page"));
		  }
		  
		  String state=request.getParameter("state");
		  //내용보기 일때 cont,수정 edit, 삭제 del
		  
		  SpringMemberBean m=this.adminMemberService.getmemCont(mem_id);
		  //회원아이디를 기준으로 회원정보를 검색
		  
		  String del_cont=null;		  
		  
		  if(m.getMem_delcont() != null){
			  del_cont=m.getMem_delcont().replace("\n","<br/>");
		      //textarea 태그내에서 엔터키 친부분을 다음줄로 개행
		  }
		  
		  String mem_regdate=m.getMem_regdate().substring(0,10);
		  //0이상 10미만 사이의 문자를 반환
		  
		  String mem_deldate=null;
		  if(m.getMem_deldate() != null){
			  mem_deldate=m.getMem_deldate().substring(0,10);
		  }
		  
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
		  
		  ModelAndView infoM=new ModelAndView();
		  infoM.addObject("infoM",m);
		  infoM.addObject("del_cont",del_cont);
		  infoM.addObject("mem_regdate",mem_regdate);
		  infoM.addObject("mem_deldate",mem_deldate);
		  infoM.addObject("page",page);
		  infoM.addObject("phone01",phone01);
		  infoM.addObject("phone02",phone02);
		  infoM.addObject("phone03",phone03);
		  infoM.addObject("mail_id",mail_id);
		  infoM.addObject("mail_domain",mail_domain);
		  
		  if(state.equals("cont")){//회원정보
			  infoM.setViewName("admin/admin_member_info");
		  }else if(state.equals("edit")){//정보수정
			  infoM.setViewName("admin/admin_member_edit");
		  }
		  return infoM;
	  }
	  return null;
  }
  
  /* 관리자 정보 수정 */
  @RequestMapping(value="/admin_member_edit_ok.do",method=RequestMethod.POST)
  public ModelAndView admin_mem_edit_ok(HttpServletRequest request,
		  HttpServletResponse response,HttpSession session)
  throws Exception{
	  response.setContentType("text/html;charset=UTF-8");
	  PrintWriter out=response.getWriter();
	  session=request.getSession();
	  String admin_id=(String)session.getAttribute("admin_id");
	  
	  if(admin_id == null){
		  out.println("<script>");
		  out.println("alert('관리자 아이디로 로그인 하세요!')");
		  out.println("location='admin_index.do'");
		  out.println("</script>");
	  }else{
		  String mem_id=request.getParameter("mem_id");
		  int page=1;
		  
		  if(request.getParameter("page") != null){
			  page=Integer.parseInt(request.getParameter("page"));
		  }
		  
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
		  m33.setMem_id(mem_id); m33.setMem_pwd(mem_pwd);
		  m33.setMem_name(mem_name); m33.setMem_zip(mem_zip);
		  m33.setMem_zip2(mem_zip2); m33.setMem_addr(mem_addr);
		  m33.setMem_addr2(mem_addr2); m33.setMem_phone(mem_phone);
		  m33.setMem_email(mem_email);
		  
		  this.adminMemberService.updateMember(m33);//수정 메서드 실행
		  
response.sendRedirect("admin_member_cont.do?mem_id="+mem_id+"&page="+page+"&state=cont");
	  }
	  return null;
  }
  
  /*관리자 회원 삭제 */
  @RequestMapping(value="/admin_member_del.do")
  public ModelAndView admin_mem_del(HttpServletRequest request,
		  HttpServletResponse response,HttpSession session)
  throws Exception{
	  response.setContentType("text/html;charset=UTF-8");
	  PrintWriter out=response.getWriter();
	  session=request.getSession();
	  
	  String admin_id=(String)session.getAttribute("admin_id");
	  
	  if(admin_id == null){
		  out.println("<script>");
		  out.println("alert('관리자 아이디로 로그인 하세요!')");
		  out.println("location='admin_index.do'");
		  out.println("</script>");
	  }else{
	  String mem_id=request.getParameter("mem_id");
	  int page=1;
	  if(request.getParameter("page") != null){
	     page=Integer.parseInt(request.getParameter("page"));	  
	  }
	    AdminMemberDAOImpl md=new AdminMemberDAOImpl();
	    md.deleteMember(mem_id);//아이디를 기준으로 삭제
	    
	    response.sendRedirect("admin_member_list.do?page="+page);
	  }
	  return null;
   }
}

