/*방명록*/
package spring.mvc.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;





import spring.mvc.dao.GuestDAO;
import spring.mvc.model.SpringGuestBean;

@Controller
public class GuestController {
	
	@Autowired
	private GuestDAO guestService;

	/* 방명록 목록보기 */
	@RequestMapping(value="/guest_list.do")
	public ModelAndView guest_list(){
		int count = 
		  this.guestService.listCount(); //전체레코드수
		List<SpringGuestBean> list =
		this.guestService.getGuestList();// 전체 레코드
		Map<String,Object> model = new
				  HashMap<String,Object>();
		model.put("guestList",list);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("guest/guest_list");
		// jsp/guest/guest_list.jsp
		mv.addObject("total_count",count);
		mv.addAllObjects(model);		
		return mv;
	}
	/* 방명록 글쓰기 폼*/
	@RequestMapping(value="/guest_write.do")
	public ModelAndView guest_write(){
		return new ModelAndView("guest/guest_write");
		// jsp/guest/guest_write.jsp - view page 지정
	}
	/* 방명록 저장 */
	@RequestMapping(value="/guest_write_ok.do", 
			      method=RequestMethod.POST)
	public ModelAndView guest_write_ok(HttpServletRequest
			request, HttpServletResponse response) throws IOException{
		
		String guest_name=
			request.getParameter("guest_name").trim();
		String guest_title =
			request.getParameter("guest_title").trim();
		String guest_cont =
				request.getParameter("guest_cont").trim();
		String guest_pwd =
				request.getParameter("guest_pwd").trim();
		SpringGuestBean bean = new SpringGuestBean();
		bean.setGuest_name(guest_name);
		bean.setGuest_title(guest_title);
		bean.setGuest_cont(guest_cont);
		bean.setGuest_pwd(guest_pwd);
		// 방명록 저장 메서드 호출
		this.guestService.insertGuest(bean);
		// 방명록 목록 보기
		response.sendRedirect("guest_list.do");
		return null;
	}
	/* 방명록 내용보기 */
	@RequestMapping(value="/guest_cont.do",method=RequestMethod.GET)
	public ModelAndView guest_cont(HttpServletRequest
			 request, HttpServletResponse response){
		int no =
		  Integer.parseInt(request.getParameter("no"));
		// 조회수 증가
		this.guestService.hitUpdate(no);
		// 방명록 내용 보기
		SpringGuestBean bean = 
		  this.guestService.getGuestCont(no);
		ModelAndView mv = new ModelAndView();
		// view page 지정
		mv.setViewName("guest/guest_cont");
		// 방명록 결과를 g키에 저장
		mv.addObject("bean", bean);		
		
		return mv;
	}
	/* 방명록 수정폼 */
	@RequestMapping(value="/guest_edit.do")
	public ModelAndView guest_edit(HttpServletRequest
			 request, HttpServletResponse response){
		int no = 
		 Integer.parseInt(request.getParameter("no"));
		SpringGuestBean bean = 
		    this.guestService.getGuestCont(no);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("guest/guest_edit");
		mv.addObject("bean",bean);
		return mv;
	}
	/* 방명록 수정 */
	@RequestMapping(value="/guest_edit_ok.do",method=RequestMethod.POST)
	public ModelAndView guest_edit_ok(HttpServletRequest
			 request, HttpServletResponse response) throws IOException{
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		int no = 
	 	 Integer.parseInt(request.getParameter("no"));
		String guest_name =
				request.getParameter("guest_name");
		String guest_title =
				request.getParameter("guest_title");
		String guest_cont =
				request.getParameter("guest_cont");
		String guest_pwd =
				request.getParameter("guest_pwd");
		SpringGuestBean bean = 
			  this.guestService.getGuestCont(no);
		if(bean.getGuest_pwd().equals(guest_pwd)){
			SpringGuestBean beane = 
					new SpringGuestBean();
			beane.setGuest_no(no);
			beane.setGuest_name(guest_name);
			beane.setGuest_title(guest_title);
			beane.setGuest_cont(guest_cont);
			beane.setGuest_pwd(guest_pwd);
			// 수정 메서드 호출
			this.guestService.guestEdit(beane);
			response.sendRedirect("guest_cont.do?no="+no);
		}else{ // 비번이 틀린 경우
			out.println("<script>");
			out.println("alert('비밀번호가 틀립니다.')");
			out.println("history.back()");
			out.println("</script>");			
		}
		return null;
	}
	/* 방명록 삭제폼 */
	@RequestMapping(value="/guest_del.do")
	public ModelAndView guest_del(HttpServletRequest
			 request){
		int no = 
		  Integer.parseInt(request.getParameter("no"));
		ModelAndView mv = new ModelAndView();
		mv.setViewName("guest/guest_del");
		mv.addObject("no",no);
		return mv;
	}
	/* 방명록 삭제 */
	@RequestMapping(value="/guest_del_ok.do",method=RequestMethod.POST)
	public ModelAndView guest_del_ok(HttpServletRequest
			request, HttpServletResponse response) throws IOException{
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		int no = 
		  Integer.parseInt(request.getParameter("no"));
		String del_pwd = 
		  request.getParameter("del_pwd").trim();
		SpringGuestBean bean = 
		   this.guestService.getGuestCont(no);
		if(bean.getGuest_pwd().equals(del_pwd)){
			this.guestService.deleteGuest(no);
			response.sendRedirect("guest_list.do");
		}else{ // 비번이 틀린 경우
			out.println("<script>");
			out.println("alert('비밀번호가 틀립니다.')");
			out.println("history.back()");
			out.println("</script>");
		}
		return null;
	}
}






