/*게시판*/
package spring.mvc.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import spring.mvc.dao.BoardDAOImpl;
import spring.mvc.model.SpringBoardBean;

@Controller
public class BoardController {
	
	// DAO 객체를 주입
	@Autowired
	private BoardDAOImpl boardService; // 의존관계
 
    /* 게시판 입력 폼 */
    @RequestMapping(value="/board_write.do")
    public ModelAndView board_write(){
    	return new ModelAndView("board/board_write");
    	// jsp/board/baord_write.jps - view page
    }
    
    /* 게시판 저장 */
    @RequestMapping(value="/board_write_ok.do",method=RequestMethod.POST)
    public ModelAndView board_write_ok(HttpServletRequest
    		 request, HttpServletResponse response) throws IOException, SQLException{
    	String board_name
    	     = request.getParameter("board_name");
    	String board_title
	     = request.getParameter("board_title");
    	String board_cont
	     = request.getParameter("board_cont");
    	String board_pwd
	     = request.getParameter("board_pwd");
    	// 테이블에 저장할 데이터를 빈에 저장
    	SpringBoardBean bean = new SpringBoardBean();
    	bean.setBoard_name(board_name);
    	bean.setBoard_title(board_title);
    	bean.setBoard_cont(board_cont);
    	bean.setBoard_pwd(board_pwd);
    	// DAO의 저장 메서드 호출
    	this.boardService.insertBoard(bean);
    	response.sendRedirect("board_list.do");
    	return null;
    }
    
    /* 게시물 목록 */
    @RequestMapping(value="/board_list.do")
    public ModelAndView board_list(HttpServletRequest 
    		request) throws SQLException{
    	int page=1;   // 기본 페이지 수
    	int limit=10; // 한 페이지 당 줄 수 
    	if(request.getParameter("page")!=null){
    		page=Integer.parseInt
    			   (request.getParameter("page"));
    	}
    	// 총 레코드 수
    	int listcount = this.boardService.getListCount();
    	// 한 페이지에 보여줄 목록
    	List boardList = 
    	   this.boardService.getBoardList(page, limit);
    	// 총 페이지 수
    	int maxpage=(int)((double)listcount/limit + 0.95);
    	// 현재 페이지에서 보여줄 시작 페이지 수
    	int startpage =(int)((double)page/10 + 0.9);
    	// 현재 페이지에서 보여줄 끝 페이지 수
    	int endpage = maxpage;
    	if(endpage > startpage+10-1)
    		endpage = startpage+10-1;
    	ModelAndView mv = new ModelAndView();
    	mv.setViewName("board/board_list");
    	mv.addObject("page",page);
    	mv.addObject("maxpage",maxpage);
    	mv.addObject("startpage",startpage);
    	mv.addObject("endpage",endpage);
    	mv.addObject("listcount",listcount);
    	mv.addObject("boardList",boardList);
    	
    	return mv;
    }
    
    /*게시판 내용+수정+답변글 폼+삭제폼 */
    @RequestMapping(value="/board_cont.do")
    public ModelAndView board_cont(HttpServletRequest request,
 		   HttpServletResponse response)
    throws Exception{
 	   
 	   int board_no=Integer.parseInt(request.getParameter("num"));
 	   //글번호를 정수형 숫자로 바꿔서 저장
 	   String state=request.getParameter("state");
 	   //내용보기 cont,수정폼은 edit, 삭제폼 del,답변글 폼 reply
 	   
 	   int page=1;
 	   if(request.getParameter("page") != null){
 		   page=Integer.parseInt(request.getParameter("page"));
 		   //페이지 번호 저장
 	   }
 	   
 	   if(state.equals("cont")){//내용보기일때만 조회수 증가
 		   this.boardService.boardHit(board_no);
 	   }
 	   
 	   SpringBoardBean board=this.boardService.getBoardCont(board_no);
 	   //글번호에 해당하는 게시물 내용을 디비로 부터 가져온다.
 	   String board_cont=board.getBoard_cont();
 	   board_cont=board_cont.replace("\n","<br/>");
 	   //\은 내용을 입력할때 엔터키 친부분을 웹상에 보이게 할때 다음줄로 
 	   //개행(<br/>)을 시켜준다.
 	   
 	   ModelAndView boardM=new ModelAndView();
 	   boardM.addObject("bcont",board);
 	   //bcont키값에 게시물 내용을 저장
 	   boardM.addObject("board_cont",board_cont);
 	   boardM.addObject("page",page);
 	   
 	   if(state.equals("cont")){//내용보기일때
 		   boardM.setViewName("board/board_cont");
 		   //board폴더의 board_cont.jsp가 실행
 	   }else if(state.equals("reply")){//답변 폼일때
 		   boardM.setViewName("board/board_reply");
 	   }else if(state.equals("edit")){//수정폼일때
 		   boardM.setViewName("board/board_edit");
 	   }else if(state.equals("del"))//삭제폼일때
 		   boardM.setViewName("board/board_delete");
 	   return boardM;
    }
    
    /*답변 저장*/   
    @RequestMapping(value="/board_reply_ok.do",method=RequestMethod.POST)
    public ModelAndView board_reply_ok(HttpServletRequest request,
 		   HttpServletResponse response)
    throws Exception{
 	   
 	   int board_no=Integer.parseInt(request.getParameter("board_no"));
 	   //글번호를 저장
 	   int board_ref=Integer.parseInt(request.getParameter("board_ref"));
 	   //글 그룹번호 저장
   int board_step=Integer.parseInt(request.getParameter("board_step"));
   //답변글 위치번호 저장
   int board_level=Integer.parseInt(request.getParameter("board_level"));
   //답변글 레벨 순서
   int page=1;
        if(request.getParameter("page") != null){
     	   page=Integer.parseInt(request.getParameter("page"));
     	   //페이지 번호를 저장
        }
        
        String board_name=request.getParameter("board_name").trim();
        String board_title=request.getParameter("board_title").trim();
        String board_cont=request.getParameter("board_cont").trim();
        String board_pwd=request.getParameter("board_pwd").trim();
        
        SpringBoardBean b=new SpringBoardBean();
        b.setBoard_no(board_no); b.setBoard_ref(board_ref);
        b.setBoard_step(board_step);
        b.setBoard_level(board_level);
        b.setBoard_name(board_name);
        b.setBoard_title(board_title);
        b.setBoard_cont(board_cont);
        b.setBoard_pwd(board_pwd);
        
        this.boardService.replyBoard(b);//저장메서드 호출
        
        response.sendRedirect("board_list.do?page="+page);      
 	   return null;
    }
    
    /* 게시물 수정완료 */   
    @RequestMapping(value="/board_edit_ok.do",method=RequestMethod.POST)
    public ModelAndView board_edit_ok(HttpServletRequest request,
 		   HttpServletResponse response)
    throws Exception{
 	   response.setContentType("text/html;charset=UTF-8");
 	   PrintWriter out=response.getWriter();
 	   //출력스트림 객체 생성
 	   
 	   int board_no=Integer.parseInt(request.getParameter("board_no"));
 	   //히든으로 넘어온 글번호를 정수형 숫자로 바꿔서 저장
 	   int page=1;
 	   
 	   if(request.getParameter("page") != null){
 		   page=Integer.parseInt(request.getParameter("page"));
 	   }
 	   
 	   String board_name=request.getParameter("board_name").trim();
 	   String board_title=request.getParameter("board_title").trim();
 	   String board_cont=request.getParameter("board_cont").trim();
 	   String board_pwd=request.getParameter("board_pwd").trim();
 	   
 	   SpringBoardBean db_pwd=this.boardService.getBoardCont(board_no);
 	   //번호에 해당하는 게시물 내용을 디비로 부터 가져온다.
 	   
 	   if(!db_pwd.getBoard_pwd().equals(board_pwd)){//비번이 다른경우
 		   out.println("<script>");
 		   out.println("alert('비번이 다릅니다!')");
 		   out.println("history.go(-1)");
 		   out.println("</script>");
 	   }else{//비번이 같은 경우
 		   SpringBoardBean editB=new SpringBoardBean();
 		   editB.setBoard_no(board_no);
 		   editB.setBoard_name(board_name);
 		   editB.setBoard_title(board_title);
 		   editB.setBoard_cont(board_cont);
 		   
 		   this.boardService.editBoard(editB);//수정메서드 호출
 		   /*ibatis 로 글번호를 기준으로 이름,제목,내용을 수정하시오! */
 		   
 		   response.sendRedirect(
 	   "board_cont.do?num="+board_no+"&page="+page+"&state=cont");
 	   }
 	   return null;
    }
       /* 게시물 삭제 완료 */   
    @RequestMapping(value="/board_del_ok.do",method=RequestMethod.POST)
    public ModelAndView board_del_ok(HttpServletRequest request,
 		   HttpServletResponse response)
    throws Exception{
 	   response.setContentType("text/html;charset=UTF-8");
 	   PrintWriter out=response.getWriter();
 	   
 	   int board_no=Integer.parseInt(request.getParameter("board_no"));
 	   int page=Integer.parseInt(request.getParameter("page"));
 	   String board_pwd=request.getParameter("board_pwd").trim();
 	   	      
 	   SpringBoardBean db_pwd=this.boardService.getBoardCont(board_no);
 	   
 	   if(db_pwd.getBoard_pwd().equals(board_pwd)){
 		   this.boardService.deleteBoard(board_no);
 		   
 		   response.sendRedirect("board_list.do?page="+page);
 	   }else{
 		   out.println("<script>");
 		   out.println("alert('비번이 다릅니다!')");
 		   out.println("history.back()");
 		   out.println("</script>");
 	   }
 	   return null;
    }
    

}

