package spring.mvc.controller;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import spring.mvc.dao.AdminBoardDAOImpl;
import spring.mvc.model.SpringBoardBean;
import spring.mvc.model.SpringBoardBean2;

@Controller
public class AdminBoardController {//관리자 게시판 컨트롤러

	@Autowired
	private AdminBoardDAOImpl adminBoardService;
		
	/*관리자 게시판 목록 */
	@RequestMapping(value="/admin_board_list.do")
	public ModelAndView admin_board_list(HttpServletRequest request,
			HttpServletResponse response,HttpSession session,
			@ModelAttribute SpringBoardBean board)
	throws Exception{
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out=response.getWriter();
		session=request.getSession();
		
		String admin_id=(String)session.getAttribute("admin_id");
		//관리자 아이디 취득
		
		if(admin_id== null){
			out.println("<script>");
			out.println("alert('관리자 아이디로 로그인 하세요!')");
			out.println("location='admin_index.do'");
			out.println("</script>");
		}else{			
			int page=1;
			int limit=7;
			
			if(request.getParameter("page")!=null){				
				page=Integer.parseInt(request.getParameter("page"));				
			}
			
			int listcount=this.adminBoardService.getListCount(); //총 리스트 수를 받아옴.		
			
			List<SpringBoardBean> boardList =
					this.adminBoardService.getBoardList(page,limit); //리스트를 받아옴.
					
			List<SpringBoardBean2> boardList2=new ArrayList<SpringBoardBean2>();
			
			String board_title=null;
			
			for(int i=0;i<boardList.size();i++){
				//SpringBoardBean board=boardList.get(i);//게시물 목록을 받아옴
				board=boardList.get(i);//게시물 목록을 받아옴
			    board_title=board.getBoard_title();//게시물 제목을 저장
			  
			  if(board_title.length() >= 14){//문자열 길이가 14자 이상이면
				  board_title=board_title.substring(0,14)+"...";
				  //0이상 14미만 사이의 문자열을 반환+... 으로 처리		  
			  }
			  SpringBoardBean2 board2=new SpringBoardBean2();
			  board2.setBoard_title(board_title);
			  board2.setBoard_regdate(board.getBoard_regdate());
			  board2.setBoard_no(board.getBoard_no());
			  board2.setBoard_name(board.getBoard_name());
			  board2.setBoard_hit(board.getBoard_hit());
			  board2.setBoard_step(board.getBoard_step());
			  boardList2.add(board2);//컬렉션에 요소값 추가
			}		
			
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
			request.setAttribute("boardList", boardList2);
			
			ModelAndView listM= new ModelAndView("admin/admin_board_list");
		   	return listM;
		}
		return null;
	}
	
   /*관리자 게시판 검색 */
   @RequestMapping(value="/admin_board_find.do")
   public ModelAndView admin_board_find(HttpServletRequest request,
			HttpServletResponse response,HttpSession session,
			@ModelAttribute SpringBoardBean board)
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
		
		    AdminBoardDAOImpl dao=new AdminBoardDAOImpl();  
		 	int page=1;
			int limit=7;
			
			if(request.getParameter("page")!=null){
				page=Integer.parseInt(request.getParameter("page"));
			}
			
			String find_name=null;//검색어 저장
			
			if(request.getParameter("find_name").trim() != null){
				find_name=request.getParameter("find_name").trim();				
				//find_name=new String(find_name.getBytes("8859_1"),"UTF-8");
				//get방식으로 넘어온 언어 코딩 타입을 UTF-8로 바꿔서 안깨지게 함.
			}
			String find_field=null;//검색 필드 저장
			if(request.getParameter("find_field")!=null){
				find_field=request.getParameter("find_field");				
			}		
			//System.out.println("find_name : "+find_name);
			//System.out.println("find_field : "+find_field);
			
			int listcount=this.adminBoardService.getListCount3("%"+find_name+"%",find_field); 
			//총 리스트 수를 받아옴.
			//System.out.println("listcount : "+listcount);
					
		    List<SpringBoardBean> boardList = dao.getBoardSearchList(page,limit,"%"+find_name+"%",find_field); 
			//리스트를 받아옴.	
			
		    // 관리자용 bean 객체를 저장하는 리스트 객체
            List<SpringBoardBean2> bList2=new ArrayList<SpringBoardBean2>();
			
			String board_title=null;
			
			for(int i=0;i<boardList.size();i++){
				board=boardList.get(i);//리스트에서 레코드(원소) 가져옴
			    board_title=board.getBoard_title();//게시물 제목을 저장
			  
			  if(board_title.length() >= 14){//문자열 길이가 14자 이상이면
				  board_title=board_title.substring(0,14)+"...";
				  //0이상 14미만 사이의 문자열을 반환+... 으로 처리		  
			  }
			  /* 관리자용 bean으로 저장 */
			  SpringBoardBean2 baord2=new SpringBoardBean2();
			  baord2.setBoard_title(board_title);
			  baord2.setBoard_regdate(board.getBoard_regdate());
			  baord2.setBoard_no(board.getBoard_no());
			  baord2.setBoard_name(board.getBoard_name());
			  baord2.setBoard_hit(board.getBoard_hit());
			  baord2.setBoard_step(board.getBoard_step());
			  bList2.add(baord2); //컬렉션에 요소값 추가
			}		
	   		
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
			request.setAttribute("boardList", bList2);
			
			ModelAndView model=new ModelAndView("admin/admin_board_find");
			model.addObject("find_name",find_name);
			model.addObject("find_field",find_field);
			return model;				
	   }
	   return null;
   }
   
   /*관리자 게시판 글쓰기 폼*/
   @RequestMapping(value="/admin_board_write.do")
   public ModelAndView admin_board_write(HttpServletRequest request,
		   HttpServletResponse response,HttpSession session)
   throws Exception{
	   response.setContentType("text/html;charset=UTF-8");
	   PrintWriter out=response.getWriter();
	   //출력 스트림 객체 생성
	   session=request.getSession();
	   String admin_id=(String)session.getAttribute("admin_id");
	   //관리자 아이디를 저장
	   
	   if(admin_id == null){
		out.println("<script>");
		out.println("alert('관리자 아이디로 로그인 하세요!')");
		out.println("location='admin_index.do'");
		out.println("</script>");
	   }else{		   
		   ModelAndView aboardM=
				   new ModelAndView("admin/admin_board_write");
		   return aboardM;
	   }	   			   
	   return null;
   }
   
   /*관리자 게시판 저장 */
   @RequestMapping(value="/admin_board_write_ok.do",method=RequestMethod.POST)
   public ModelAndView admin_board_write_ok(HttpServletRequest request,
		   HttpServletResponse response,HttpSession session,
		   @RequestParam(value="board_name") String board_name)
   throws Exception{
	   response.setContentType("text/html;charset=UTF-8");
	   PrintWriter out=response.getWriter();
	   session=request.getSession();
	   String admin_id=(String)session.getAttribute("admin_id");
	   
	   if(admin_id==null){
		   out.println("<script>");
		   out.println("alert('관리자 아이디로 로그인 하세요!')");
		   out.println("location='admin_index.do'");
		   out.println("</script>");
	   }else{
		   //String board_name=request.getParameter("board_name").trim();
		   String board_title=request.getParameter("board_title").trim();
		   String board_cont=request.getParameter("board_cont").trim();
		   String board_pwd=request.getParameter("board_pwd").trim();
		   
		   SpringBoardBean b=new SpringBoardBean();
		   
		   b.setBoard_name(board_name); b.setBoard_title(board_title);
		   b.setBoard_cont(board_cont); b.setBoard_pwd(board_pwd);
		   
		   this.adminBoardService.insertBoard(b);//저장메서드
		   
		   response.sendRedirect("admin_board_list.do");
	   }
	   return null;
   }
   
   /*관리자 게시판 내용 */
   @RequestMapping(value="/admin_board_cont.do")
   public ModelAndView admin_board_cont(HttpServletRequest request,
		   HttpServletResponse response,HttpSession session)
   throws Exception{
	   response.setContentType("text/html;charset=UTF-8");
	   PrintWriter out=response.getWriter();
	   session=request.getSession();
	   
	   String admin_id=(String)session.getAttribute("admin_id");
	   
	   if(admin_id==null){
		   out.println("<script>");
		   out.println("alert('관리자 아이디로 로그인 하세요!')");
		   out.println("location='admin_index.do'");
		   out.println("</script>");
	   }else{
	   int board_no=Integer.parseInt(request.getParameter("board_no"));
	   
	   int page=1;
	   if(request.getParameter("page") != null){
		   page=Integer.parseInt(request.getParameter("page"));
	   }
	   
	   String state=request.getParameter("state");
	   //내용보기 일때는 state,수정일때 edit
	   
	   AdminBoardDAOImpl bd=new AdminBoardDAOImpl();
	   
	   SpringBoardBean b=bd.getBoardCont(board_no);
	   //번호에 해당하는 게시물 내용을 가져온다.	
	   
	   String board_cont=b.getBoard_cont().replace("\n","<br>");
	   //게시물 내용을 입력할때 엔터키 친부분을 다음줄로 개행
	   
	   ModelAndView acontM=new ModelAndView();
	   acontM.addObject("aboard_cont",b);
	   acontM.addObject("board_cont",board_cont);
	   acontM.addObject("page", page);
	   
	   if(state.equals("cont")){
		   acontM.setViewName("admin/admin_board_cont");
	   }else if(state.equals("edit")){//수정일때
		   acontM.setViewName("admin/admin_board_edit");
	   }else if(state.equals("del")){//삭제일때
		   acontM.setViewName("admin/admin_board_del");
	   }
	   return acontM;
	   }
	   return null;
   }
   
   /*관리자 게시판 수정 */
   @RequestMapping(value="/admin_board_edit_ok.do",method=RequestMethod.POST)
   public ModelAndView admin_board_edit_ok(HttpServletRequest request,
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
	   int board_no=Integer.parseInt(request.getParameter("board_no"));
	   
	   int page=1;
	   if(request.getParameter("page") != null){
		   page=Integer.parseInt(request.getParameter("page"));
	   }
	   
	   String board_name=request.getParameter("board_name").trim();
	   String board_title=request.getParameter("board_title").trim();
	   String board_cont=request.getParameter("board_cont").trim();
	   String board_pwd=request.getParameter("board_pwd").trim();
	   
	   AdminBoardDAOImpl bd=new AdminBoardDAOImpl();
	   
	   SpringBoardBean db_pwd=bd.getBoardCont(board_no);
	   
	   if(db_pwd.getBoard_pwd().equals(board_pwd)){//비번이 같다면
		   SpringBoardBean editb=new SpringBoardBean();
		   editb.setBoard_name(board_name);
		   editb.setBoard_title(board_title);
		   editb.setBoard_cont(board_cont);
		   editb.setBoard_no(board_no);
		   
		   this.adminBoardService.editBoard(editb);//수정메서드 
		   
		   //response.sendRedirect("admin_board_cont.do?page="+page);
           response.sendRedirect("admin_board_list.do?page="+page);		   
	   }else{
		   out.println("<script>");
		   out.println("alert('비번이 다릅니다!')");
		   out.println("history.back()");//뒤로 한칸 이동
		   out.println("</script>");
	     }
	   }
	   return null;
   }
   
   /*관리자 게시판 삭제 */
   @RequestMapping(value="/admin_board_del_ok.do",method=RequestMethod.POST)
   public ModelAndView admin_board_del_ok(HttpServletRequest request,
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
 	   
	   int board_no=Integer.parseInt(request.getParameter("board_no"));
       int page=1;
       
       if(request.getParameter("page") != null){
    	   page=Integer.parseInt(request.getParameter("page"));
       }
       
       String board_pwd=request.getParameter("board_pwd").trim();
       
       SpringBoardBean db_pwd=
    		   this.adminBoardService.getBoardCont(board_no);
       
       if(db_pwd.getBoard_pwd().equals(board_pwd)){//비번이 같다면
    	   this.adminBoardService.deleteBoard(board_no);//삭제
    	   
    	   response.sendRedirect("admin_board_list.do?page="+page);
       }else{
    	   out.println("<script>");
    	   out.println("alert('비밀번호가 다릅니다!')");
    	   out.println("history.go(-1)");
    	   out.println("</script>");
       }
	   }
	   return null;
   }
}


