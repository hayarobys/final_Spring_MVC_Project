package spring.mvc.controller;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import spring.mvc.dao.AdminGongjiDAOImpl;
import spring.mvc.model.SpringGongjiBean;

@Controller
public class AdminGongjiController {//관리지 공지사항 컨트롤러

	@Autowired
	private AdminGongjiDAOImpl adminGongjiService;
		
	
	/*관리자 공지사항 글쓰기 폼*/
	@RequestMapping(value="/admin_Gongji_write.do")
	public ModelAndView admin_gongji_write(HttpServletRequest request,
			HttpServletResponse response,HttpSession session)
	throws Exception{
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out=response.getWriter();
		session=request.getSession();
		
		String admin_id=(String)session.getAttribute("admin_id");
		if(admin_id==null){
			out.println("<script>");
			out.println("alert('관리자로 로그인 하세요!')");
			out.println("location='admin_index.do'");
			out.println("</script>");
		}else{
			ModelAndView writeM=
		new ModelAndView("admin/admin_Gongji_write");
		//admin 폴더의 admin_Gongji_write.jsp 뷰페이지가 실행
			return writeM;
		}
		return null;
	}
	
	/*관리자 공지사항 저장 */
	@RequestMapping(value="/admin_Gongji_write_ok.do"
			,method=RequestMethod.POST)
	public ModelAndView admin_gongji_write_ok(HttpServletRequest 
			request,HttpServletResponse response,
			HttpSession session)
	throws Exception{
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out=response.getWriter();
		session=request.getSession();
		
		String admin_id=(String)session.getAttribute("admin_id");
		if(admin_id==null){
			out.println("<script>");
			out.println("alert('관리자로 로그인 하세요!')");
			out.println("location='admin_index.do'");
			out.println("</script>");
		}else{
		String gongji_name=request.getParameter("gongji_name").trim();
		String gongji_title=request.getParameter("gongji_title").trim();
		String gongji_cont=request.getParameter("gongji_cont").trim();
		String gongji_pwd=request.getParameter("gongji_pwd").trim();
		
		SpringGongjiBean g=new SpringGongjiBean();
		g.setGongji_name(gongji_name); g.setGongji_title(gongji_title);
		g.setGongji_cont(gongji_cont); g.setGongji_pwd(gongji_pwd);
		
		this.adminGongjiService.insertGongji(g);//저장메서드 실행
		
		response.sendRedirect("admin_Gongji_list.do");
		//공지 목록으로 이동
		}
		return null;
	}
	
	/*관리자 공지사항 목록 */
	@RequestMapping(value="/admin_Gongji_list.do")
	public ModelAndView admin_gongji_list(HttpServletRequest request,
			HttpServletResponse response,HttpSession session)
	throws Exception{		
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out=response.getWriter();
		session=request.getSession();
		
		String admin_id=(String)session.getAttribute("admin_id");
		if(admin_id==null){
			out.println("<script>");
			out.println("alert('관리자로 로그인 하세요!')");
			out.println("location='admin_index.do'");
			out.println("</script>");			
		}else{
			
	/* 관리자 공지사항 목록 뷰페이지 파일명이 admin_Gongji_list.jsp
	 * 페이지와 컨트롤 부분 ,jdbc 작성 과제물 */
			AdminGongjiDAOImpl gd=new AdminGongjiDAOImpl();
			int page=1;
			int limit=7;
			
			if(request.getParameter("page")!=null){
				//get방식으로 넘어온 쪽번호가 정수형이라고 해도
				//문자열로 바껴져서 넘어온다.
				page=Integer.parseInt(request.getParameter("page"));
				//사칙연산을 하기 위해서 parseInt()메서드에 의해서
				//정수형 숫자로 바꾼다.
			}
			
			int listcount=this.adminGongjiService.getListCount(); //총 리스트 수를 받아옴.		
			
			List gonjiList = gd.getBoardList(page,limit); //리스트를 받아옴.
					
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
			request.setAttribute("gonjiList", gonjiList);
			
			ModelAndView listM= new ModelAndView("admin/admin_Gongji_list");
		   	return listM;
		}
		return null;
	}
	
	/* 관리자 공지사항 검색 */
	@RequestMapping(value="/admin_Gongji_find.do")
	public ModelAndView admin_Gongji_find(HttpServletRequest request,
			HttpServletResponse response,HttpSession session)
	throws Exception{
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out=response.getWriter();//출력스트림객체 생성
		session=request.getSession();//세션 객체 생성
		
		String admin_id=(String)session.getAttribute("admin_id");
		//관리자 세션 아이디값을 저장
		if(admin_id==null){
			out.println("<script>");
			out.println("alert('관리자 아이디로 다시 로그인 하세요!')");
			out.println("location='admin_index.do'");
			out.println("</script>");
		}else{
			/* 관리자 검색 공지 부분을 작성하고, 검색 결과 뷰페이지는
			 * admin_Gongji_find.jsp (과제물)
			 */
			AdminGongjiDAOImpl gd=new AdminGongjiDAOImpl();		  
			
		  	int page=1;
			int limit=7;
			
			if(request.getParameter("page")!=null){
				page=Integer.parseInt(request.getParameter("page"));
			}
			
			String find_name=null;
			
			if(request.getParameter("find_name").trim() != null){
			 find_name=request.getParameter("find_name").trim();
			 //검색어 저장
             // Tomcat의 server.xml 환경설정에서 인코딩 방식을 지정했으면
             // 아래 코드를 주석처리한다.
			 //find_name=new String(find_name.getBytes("8859_1"),"UTF-8");
			 //get방식으로 넘어온 언어 코딩 타입을 UTF-8로 바꿔서 안깨지게 함.
			}
			String find_field=null;
			if(request.getParameter("find_field")!=null){
			find_field=request.getParameter("find_field");
			//검색 필드 저장
			}		
						
			int listcount=this.adminGongjiService.getListCount3("%"+find_name+"%",find_field); 
			//총 리스트 수를 받아옴.
						
			List<SpringGongjiBean> gongjiList = gd.getBbsList3(page,limit,find_name,find_field); 
			//리스트를 받아옴.			
			
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
			request.setAttribute("gongjiList", gongjiList);
			
			ModelAndView model=new ModelAndView("admin/admin_Gongji_find");
			model.addObject("find_name",find_name);
			model.addObject("find_field",find_field);
			return model;	
		}
		return null;
	}
	
	/*관리자 공지사항 내용&수정폼&삭제폼 */
	@RequestMapping(value="/admin_Gongji_cont.do")
	public ModelAndView admin_Gongji_cont(HttpServletRequest request,
			HttpServletResponse response,HttpSession session)
	throws Exception{
	    response.setContentType("text/html;charset=UTF-8");
	    PrintWriter out=response.getWriter();
	    session=request.getSession();
	    
	    String admin_id=(String)session.getAttribute("admin_id");
	    
	    if(admin_id == null){
	    	out.println("<script>");
	    	out.println("alert('관리자로 로그인 하세요!')");
	        out.println("location='admin_index.do'");
	        out.println("</script>");
	    }else{
   	int gongji_no=Integer.parseInt(request.getParameter("gongji_no"));
    int page=1;
    if(request.getParameter("page") != null){
    	page=Integer.parseInt(request.getParameter("page"));
    	//페이지 번호 저장
    }
    String state=request.getParameter("state");
    //내용보기일때는 cont,수정창 edit,삭제폼은 del
    
    SpringGongjiBean g=this.adminGongjiService.getGongjiCont(gongji_no);
    //번호를 기준으로 디비로 부터 공지 내용을 가져옴.
    
    String gongji_cont=g.getGongji_cont().replace("\n","<br/>");
    //공지 내용 중 엔터키 친부분을 웹상에 보이게 할때 다음줄로 개행
    
    ModelAndView agongjiCM=new ModelAndView();
    agongjiCM.addObject("agcont",g);
    agongjiCM.addObject("agongjicont",gongji_cont);
    agongjiCM.addObject("page",page);
    
    if(state.equals("cont")){//내용보기일때
    	agongjiCM.setViewName("admin/admin_Gongji_cont");
    	//admin폴더의 admin_Gongji_cont.jsp뷰페이지가 실행
    }else if(state.equals("edit")){//수정창
    	agongjiCM.setViewName("admin/admin_Gongji_edit");
    }else if(state.equals("del")){//삭제창
    	agongjiCM.setViewName("admin/admin_Gongji_del");
    }
    return agongjiCM;
	    }
		return null;	
	}	
	
	/*관리자 공지사항 수정 */
	@RequestMapping(value="/admin_Gongji_edit_ok.do"
			,method=RequestMethod.POST)
	public ModelAndView admin_Gongji_edit_ok(HttpServletRequest 
			request,HttpServletResponse response,
			HttpSession session)
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
	int gongji_no=Integer.parseInt(request.getParameter("gongji_no"));
	int page=1;
	if(request.getParameter("page") != null){
		page=Integer.parseInt(request.getParameter("page"));
	}
	
	String gongji_name=request.getParameter("gongji_name").trim();
	String gongji_title=request.getParameter("gongji_title").trim();
	String gongji_cont=request.getParameter("gongji_cont").trim();
	String gongji_pwd=request.getParameter("gongji_pwd").trim();
	
	SpringGongjiBean g=new SpringGongjiBean();
	g.setGongji_no(gongji_no); g.setGongji_name(gongji_name);
	g.setGongji_cont(gongji_cont); g.setGongji_title(gongji_title);
	g.setGongji_pwd(gongji_pwd);
	
	this.adminGongjiService.editGongji(g);//수정메서드 호출
	
	response.sendRedirect(
"admin_Gongji_cont.do?gongji_no="+gongji_no+"&page="+page+"&state=cont");
		}
		return null;
	}
	
	/*관리자 공지사항 삭제 */
	@RequestMapping(value="/admin_Gongji_del_ok.do"
			,method=RequestMethod.POST)
	public ModelAndView admin_Gongji_del_ok(HttpServletRequest 
			request,HttpServletResponse response,
			HttpSession session)
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
	int gongji_no=Integer.parseInt(request.getParameter("gongji_no"));
	//히든으로 넘어온 글번호를 정수형 숫자로 바꿔서 저장
	int page=1;
	if(request.getParameter("page") != null){
		page=Integer.parseInt(request.getParameter("page"));
	}
	String gongji_pwd=request.getParameter("gongji_pwd").trim();
	SpringGongjiBean db_pwd=this.adminGongjiService.getGongjiCont(gongji_no);
	//번호에 해당하는 디비 비번을 가져옴.
	
	if(db_pwd.getGongji_pwd().equals(gongji_pwd)){//비번이 같다면
		AdminGongjiDAOImpl gd=new AdminGongjiDAOImpl();
		gd.deleteGongji(gongji_no);//번호를 기준으로 삭제
		
		response.sendRedirect("admin_Gongji_list.do?page="+page);
		//관리자 공지 목록으로 이동
	}else{
		out.println("<script>");
		out.println("alert('비번이 다릅니다!')");
		out.println("history.go(-1)");
		out.println("</script>");
	   }
     }
		return null;
	}
}

