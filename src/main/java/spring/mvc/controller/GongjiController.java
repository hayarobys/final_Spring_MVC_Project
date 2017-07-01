/*공지사항*/
package spring.mvc.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import spring.mvc.dao.GongjiDAOImpl;
import spring.mvc.model.SpringGongjiBean;
import spring.mvc.model.SpringGongjiBean2;

@Controller
public class GongjiController {
	
	@Autowired
	private GongjiDAOImpl gongjiService; // 포함관계

	/*사용자 단 페이지 공지 목록 보기 */
	@RequestMapping(value="/gongji_list.do")
	public ModelAndView gongji_list(HttpServletRequest request,
			HttpServletResponse response)
	throws Exception{		
		int page=1;
		int limit=10;
		
		if(request.getParameter("page")!=null){
			//get방식으로 넘어온 쪽번호가 정수형이라고 해도
			//문자열로 바껴져서 넘어온다.
			page=Integer.parseInt(request.getParameter("page"));			
		}
		// 총 레코드 수를 가져옴
		int listcount=this.gongjiService.getListCount(); 		
		// 전체 리스트를 가져옴
		List<SpringGongjiBean> gonjiList = 
			this.gongjiService.getBoardList(page,limit); 
					
		List<SpringGongjiBean2> gList2=new ArrayList<SpringGongjiBean2>();
		
		String gongji_title=null;
		
		for(int i=0;i<gonjiList.size();i++){
		  SpringGongjiBean g=gonjiList.get(i);//공지 목록을 받아옴
		  gongji_title=g.getGongji_title();//공지 제목을 저장
		  
		  if(gongji_title.length() >= 24){//문자열 길이가 24자 이상이면
			  gongji_title=gongji_title.substring(0,24)+"...";
			  //0이상 24미만 사이의 문자열을 반환+... 으로 처리		  
		  }
		  SpringGongjiBean2 g2=new SpringGongjiBean2();
		  g2.setGongji_title(gongji_title);
		  g2.setGongji_regdate(g.getGongji_regdate());
		  g2.setGongji_no(g.getGongji_no());
		  g2.setGongji_name(g.getGongji_name());
		  g2.setGongji_hit(g.getGongji_hit());
		  gList2.add(g2);//컬렉션에 요소값 추가
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
		request.setAttribute("gonjiList", gList2);
		
		ModelAndView listM= new ModelAndView("gongji/gongji_list");	// 이동할 페이지를 생성자로 지정해주고 있다.
	   	return listM;
	}
	/* 사용자 단 공지사항 내용보기 */
	@RequestMapping(value="/gongji_cont.do")
	public ModelAndView gongji_cont(HttpServletRequest 
			request, HttpServletResponse response) throws SQLException{
		// 공지번호 저장
		int gongji_no = 
   		Integer.parseInt(request.getParameter("gongji_no"));
		int page=1;
		if(request.getParameter("page") !=null){
			// 페이지 번호 저장
			page=
			Integer.parseInt(request.getParameter("page"));
		}
		// 조회수 증가
		this.gongjiService.updateGongjgHit(gongji_no);
		// 내용가져오기
		SpringGongjiBean2 bean = 
		this.gongjiService.getGongjiCont(gongji_no);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("gongji/gongji_cont");
		mv.addObject("bean",bean);
		mv.addObject("page",page);
		
		return mv;
	}
}




