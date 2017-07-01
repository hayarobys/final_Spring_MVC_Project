package spring.mvc.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import spring.mvc.dao.AdminDAOImpl;
import spring.mvc.model.SpringAdminBean;

@Controller
public class AdminController {
	
	// DAO 접근 객체 주입
	@Autowired
	private AdminDAOImpl adminService;
	
	
	/* 관리자 로그인 */
	@RequestMapping(value="/admin_index.do")
	public ModelAndView admin_index(){
		return new ModelAndView("admin/admin_index");
	}
	
	/* 관리자 로그인 완료*/
	@RequestMapping(value="/admin_login_ok.do",
			method=RequestMethod.POST)
	public ModelAndView admin_Login_ok(HttpServletRequest 
			request, HttpServletResponse response, 
			HttpSession session) throws IOException{
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		session =request.getSession();
		String admin_id=request.getParameter("admin_id");
		String admin_pwd=request.getParameter("admin_pwd");
		/* 관리자 로그인 인증 */
		SpringAdminBean bean 
		      = this.adminService.getAdmin_id(admin_id);
		if(bean != null){
			if(bean.getAdmin_pwd().equals(admin_pwd)){
				session.setAttribute("admin_id",admin_id);
				response.sendRedirect("admin_main.do");				
			}else{ // 비번이 틀린 경우
				out.println("<script>");
				out.println("alert('비번이 틀립니다.')");
				out.println("history.back()");
				out.println("</script>");
			}
		}else{ // id가 없는 경우
			out.println("<script>");
			out.println("alert('등록된 관리자 아이디가 없습니다.')");
			out.println("history.back()");
			out.println("</script>");
		}
	
		return null;
	}	
	@RequestMapping(value="/admin_main.do")
	public ModelAndView admin_main(HttpServletRequest 
			request, HttpServletResponse response,
			HttpSession session) throws IOException{
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		session =request.getSession();
		
		String id=(String)session.getAttribute("admin_id");
		if(id==null){ // 세션이 만료된 경우
			out.println("<script>");
			out.println("alert('관리자로 다시 로그인 해주세요!')");
			out.println("location='admin_index.do'");
			out.println("</script>");
		}else{
			ModelAndView mv = 
				new ModelAndView("admin/admin_main");
			// view page - jsp/admin/admin_main.jsp
			return mv;
		}
		return null;
	}
	
	/* 관리자 로그아웃 */
	@RequestMapping(value="/admin_Logout.do")
	public ModelAndView admin_logout(HttpServletRequest
			 request, HttpServletResponse response,
			 HttpSession session) throws IOException{
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		session = request.getSession();
		
		session.invalidate(); // 세션 만료
		out.println("<script>");
		out.println("alert('로그아웃 되었습니다!')");
		out.println("location='./index.jsp'");
		out.println("</script>");		
		return null;
	}

}

