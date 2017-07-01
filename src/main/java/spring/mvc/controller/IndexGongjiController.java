package spring.mvc.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import spring.mvc.dao.GongjiDAOImpl;
import spring.mvc.model.SpringGongjiBean2;

@Controller
public class IndexGongjiController {
	
	@Autowired
	private GongjiDAOImpl gongjiService;

	/* 사용자 최신 5개 글 공지 목록 보기 */
	@RequestMapping(value="/index_GongjiList.do")
	public ModelAndView gongji_list(HttpServletRequest request,
			HttpServletResponse response)
	throws Exception{

		List<SpringGongjiBean2> gongji_List
		       =this.gongjiService.getGongji_List();
		//최신공지사항 5개만 공지목록에서 가져온다.
		
		List<SpringGongjiBean2> gList2=new ArrayList<SpringGongjiBean2>();
		
		String gongji_title=null;
		
		for(int i=0;i<gongji_List.size();i++){
		  SpringGongjiBean2 g=gongji_List.get(i);//공지 목록을 받아옴
		  gongji_title=g.getGongji_title();//공지 제목을 저장
		  
		  if(gongji_title.length() >= 14){//문자열 길이가 14자 이상이면
			  gongji_title=gongji_title.substring(0,14)+"...";
			  //0이상 14미만 사이의 문자열을 반환+... 으로 처리		  
		  }
		  SpringGongjiBean2 g2=new SpringGongjiBean2();
		  g2.setGongji_title(gongji_title);
		  g2.setGongji_regdate(g.getGongji_regdate());
		  g2.setGongji_no(g.getGongji_no());
		  gList2.add(g2);//컬렉션에 요소값 추가
		}		
				
		ModelAndView gongM=new ModelAndView("index_gongji");
		//jsp폴더의 index_gongji.jsp 메인화면이 실행
		gongM.addObject("gongji_List", gList2);
		//gongji_List키값에 공지 목록을 저장		
		return gongM;		
	}

	/*사용자 공지 내용보기와 조회수 증가 */
	@RequestMapping(value="/index_GongjiCont.do")
	public ModelAndView indexGongji_cont(HttpServletRequest request,
			HttpServletResponse response)
	throws Exception{
		int no = Integer.parseInt(request.getParameter("no"));
		//get방식으로 넘어온 글번호를 정수형 숫자로 바꿔서 저장
		
		this.gongjiService.updateGongjgHit(no);//조회수 증
		
		SpringGongjiBean2 g=this.gongjiService.getGongjiCont(no);
		//번호를 기준으로 공지내용을 가져옴.
		
		String gCont=g.getGongji_cont().replace("\n", "<br/>");
		//공지 내용 입력할때 엔터키 친부분을 웹상에 보일때 다음줄로 개행(<br/>)
		
		ModelAndView gContM=new ModelAndView("index_gongjiCont");
		//jsp폴더의 index_gongjiCont.jsp가 실행
		gContM.addObject("iGont",g);
		//iGont키값에 레코드 저장
		gContM.addObject("igongjiCont",gCont);
		return gContM;
	}
}





