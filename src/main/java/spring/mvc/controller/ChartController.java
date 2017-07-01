package spring.mvc.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import spring.mvc.dao.BbsDAOImpl;
import spring.mvc.dao.BoardDAOImpl;
import spring.mvc.dao.GongjiDAOImpl;
import spring.mvc.dao.GuestDAO;

@Controller
public class ChartController {
		
	@Autowired
	private BbsDAOImpl bbsService;
	@Autowired
	private BoardDAOImpl boardService;
	@Autowired
	private GongjiDAOImpl gongjiService;
	@Autowired
	private GuestDAO guestService;
		
	private Map<String,Object> count() throws SQLException{
		// bbs 게시판 레코드 수
		int bbscount=this.bbsService.getListCount(); 
		int boardcount=this.boardService.getListCount();
		int gongjicount=this.gongjiService.getListCount();
		int guestcount = this.guestService.listCount();
				
		// 2. 모델 객체 생성
		Map<String,Object> model = new
						  HashMap<String,Object>();
		model.put("bbscount", bbscount);
		model.put("boardcount", boardcount);
		model.put("gongjicount", gongjicount);
		model.put("guestcount", guestcount);
		
		return model;
	}
	
	@RequestMapping(value="/admin_chart_view.do")
	public ModelAndView main() throws SQLException{		
		
		Map<String,Object> model = count();
		
		return new ModelAndView("google_chart/google_chart",model);
	}	
	
	@RequestMapping(value="/calcExcel.do")
	public ModelAndView excel() throws SQLException{
		
		Map<String,Object> model = count();
		
		return new ModelAndView("google_chart/calcExcel",model);
	}
}







