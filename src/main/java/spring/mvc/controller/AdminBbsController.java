package spring.mvc.controller;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import spring.mvc.dao.AdminBbsDAOImpl;
import spring.mvc.dao.BbsDAOImpl;
import spring.mvc.model.SpringBbsBean;
import spring.mvc.model.SpringBbsBean2;

import com.oreilly.servlet.MultipartRequest;

@Controller
public class AdminBbsController {

	@Autowired
	private AdminBbsDAOImpl adminBbsService;

	/*관리자 자료실 목록 */
	@RequestMapping(value="/admin_bbs_list.do")
	public ModelAndView admin_bbs_list(HttpServletRequest request,
			HttpServletResponse response,HttpSession session)
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
			//AdminBbsDAOImpl bd=new AdminBbsDAOImpl();
			int page=1;
			int limit=7;

			if(request.getParameter("page")!=null){				
				page=Integer.parseInt(request.getParameter("page"));				
			}

			int listcount=this.adminBbsService.getListCount(); //총 리스트 수를 받아옴.		
			// 사용자 단 bbs 리스트 
			List<SpringBbsBean> bbsList = 
					this.adminBbsService.getBbsList(page,limit); //리스트를 받아옴.
			// 관리자 단 bbs 리스트		
			List<SpringBbsBean2> bbsList2=new ArrayList<SpringBbsBean2>();

			String bbs_subject=null;

			for(int i=0;i<bbsList.size();i++){
				SpringBbsBean bbs=bbsList.get(i);//게시물 목록을 받아옴
				bbs_subject=bbs.getBbs_subject();//게시물 제목을 저장

				if(bbs_subject.length() >= 14){//문자열 길이가 14자 이상이면
					bbs_subject=bbs_subject.substring(0,14)+"...";
					//0이상 14미만 사이의 문자열을 반환+... 으로 처리		  
				}
				SpringBbsBean2 bbs2=new SpringBbsBean2();
				bbs2.setBbs_subject(bbs_subject);
				bbs2.setBbs_regdate(bbs.getBbs_regdate());
				bbs2.setBbs_no(bbs.getBbs_no());
				bbs2.setBbs_name(bbs.getBbs_name());
				bbs2.setBbs_hit(bbs.getBbs_hit());
				bbs2.setBbs_step(bbs.getBbs_step());
				bbsList2.add(bbs2);// 관리자 빈 list로 레코드 저장
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
			request.setAttribute("bbsList", bbsList2);

			ModelAndView listM= new ModelAndView("admin/admin_bbs_list");
			return listM;
		}
		return null;
	}

	/*관리자 자료실 검색 */
	@RequestMapping(value="/admin_bbs_find.do")
	public ModelAndView admin_bbs_find(HttpServletRequest request,
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
			int page=1;
			int limit=7;

			if(request.getParameter("page")!=null){
				page=Integer.parseInt(request.getParameter("page"));
			}

			String find_name=null;

			if(request.getParameter("find_name").trim() != null){
				find_name=request.getParameter("find_name").trim();
			}
			String find_field=null;
			if(request.getParameter("find_field")!=null){
				find_field=request.getParameter("find_field");
				//검색 필드 저장
			}		

			int listcount=this.adminBbsService.getListCount3("%"+find_name+"%",find_field); 
			//총 리스트 수를 받아옴.				

			List<SpringBbsBean> bbsList = 
					this.adminBbsService.getBbsList3(page,limit,find_name,find_field); 
			//리스트를 받아옴.			   		

			List<SpringBbsBean2> bList2=new ArrayList<SpringBbsBean2>();

			String bbs_subject=null;

			for(int i=0;i<bbsList.size();i++){
				SpringBbsBean b=bbsList.get(i);//게시물 목록을 받아옴
				bbs_subject=b.getBbs_subject();//게시물 제목을 저장

				if(bbs_subject.length() >= 14){//문자열 길이가 14자 이상이면
					bbs_subject=bbs_subject.substring(0,14)+"...";
					//0이상 14미만 사이의 문자열을 반환+... 으로 처리		  
				}
				SpringBbsBean2 b2=new SpringBbsBean2();
				b2.setBbs_subject(bbs_subject);
				b2.setBbs_regdate(b.getBbs_regdate());
				b2.setBbs_no(b.getBbs_no());
				b2.setBbs_name(b.getBbs_name());
				b2.setBbs_hit(b.getBbs_hit());
				b2.setBbs_step(b.getBbs_step());
				bList2.add(b2);//컬렉션에 요소값 추가
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
			request.setAttribute("bbsList", bList2);

			ModelAndView model=new ModelAndView("admin/admin_bbs_find");
			model.addObject("find_name",find_name);
			model.addObject("find_field",find_field);
			return model;				
		}
		return null;
	}

	/*관리자 자료실 글쓰기 폼*/
	@RequestMapping(value="/admin_bbs_write.do")
	public ModelAndView admin_bbs_write(HttpServletRequest request,
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
					new ModelAndView("admin/admin_bbs_write");
			return aboardM;
		}	   			   
		return null;
	}

	/* 관리자 자료실 저장 */ 
	@RequestMapping(value="/admin_bbs_write_ok.do",method=RequestMethod.POST)
	public ModelAndView admin_bbs_write_ok(HttpServletRequest request,
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
			SpringBbsBean bbs=new SpringBbsBean();

			String saveFolder="C:/KJS/workspace(sts)/chap10_Spring_MVC_Project/WebContent/upload";
			//이진파일 업로드 서버 경로
			int fileSize=5*1024*1024;
			//이진파일 업로드 최대 크기

			MultipartRequest multi=null;
			//이진파일 업로드 객체
			multi=new MultipartRequest(request,saveFolder,fileSize,"UTF-8");

			String bbs_name=multi.getParameter("bbs_name").trim();
			String bbs_subject=
					multi.getParameter("bbs_subject").trim();
			String bbs_pass=multi.getParameter("bbs_pass").trim();
			String bbs_content=
					multi.getParameter("bbs_content").trim();
			File UpFile=multi.getFile("bbs_file");
			//첨부한 이진파일을 받아옴

			if(UpFile != null){//첨부한 이진파일 있다면
				String fileName=UpFile.getName();//첨부한 이진파일명 저장
				Calendar c=Calendar.getInstance();

				int year=c.get(Calendar.YEAR);//년도값
				int month=c.get(Calendar.MONTH)+1;//월값
				//+1을 한 이유는 1월이 0으로 반환되기 때문이다.
				int date=c.get(Calendar.DATE);//일값

				String homedir=saveFolder+"/"+year+"-"+month+"-"+date;

				File path1=new File(homedir);
				if(!(path1.exists())){//폴더 경로가 없다면
					path1.mkdir();//폴더를 생성
				}

				Random r=new Random();
				int random=r.nextInt(10000000);
				//천만사이의 임의의 정수형 난수를 발생시킨다.

				/****확장자 구하기 시작 ****/
				int index = fileName.lastIndexOf(".");             
				String fileExtension = fileName.substring(index + 1);
				/****확장자 구하기 끝 ***/
				String refileName="adminbbs"+year+month+date+random+"."+
						fileExtension;//새로운 파일명을 저장
				String fileDBName="/"+year+"-"+month+"-"+date+"/"+refileName;
				//데이터 베이스에 저장될 레코드 값

				UpFile.renameTo(new File(homedir+"/"+refileName));
				//변경된 이진파일로 실제 업로드
				bbs.setBbs_file(fileDBName);
			}
			bbs.setBbs_name(bbs_name); bbs.setBbs_subject(bbs_subject);
			bbs.setBbs_pass(bbs_pass); bbs.setBbs_content(bbs_content);

			this.adminBbsService.insertBbs(bbs);//저장메서드 호출

			response.sendRedirect("admin_bbs_list.do");
			//게시물 목록으로 이동.
		}
		return null;
	}

	/*관리자 자료실 내용보기,수정,삭제 폼 */
	@RequestMapping(value="/admin_bbs_cont.do")
	public ModelAndView admin_bbs_cont(HttpServletRequest request,
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
			int bbs_no=Integer.parseInt(request.getParameter("bbs_no"));

			int page=1;
			if(request.getParameter("page") != null){
				page=Integer.parseInt(request.getParameter("page"));
			}

			String state=request.getParameter("state");
			//내용보기 일때는 state,수정일때 edit
			SpringBbsBean b=this.adminBbsService.getBbsCont(bbs_no);
			//번호에 해당하는 게시물 내용을 가져온다.	 	   
			String bbs_cont=b.getBbs_content().replace("\n","<br/>");
			//게시물 내용을 입력할때 엔터키 친부분을 다음줄로 개행

			ModelAndView acontM=new ModelAndView();
			acontM.addObject("abbs_cont",b);
			acontM.addObject("bbs_cont",bbs_cont);
			acontM.addObject("page", page);

			if(state.equals("cont")){
				acontM.setViewName("admin/admin_bbs_cont");
			}else if(state.equals("edit")){//수정일때
				acontM.setViewName("admin/admin_bbs_edit");
			}else if(state.equals("del")){//삭제일때
				acontM.setViewName("admin/admin_bbs_del");
			}
			return acontM;
		}
		return null;
	}

	/* 관리자 자료실 수정 */ 
	@RequestMapping(value="/admin_bbs_edit_ok.do",method=RequestMethod.POST)
	public ModelAndView admin_bbs_edit_ok(HttpServletRequest request,
			HttpServletResponse response,HttpSession session) throws Exception{
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
			SpringBbsBean bbsbean=new SpringBbsBean();  			
			String saveFolder="C:/KJS/workspace(sts)/chap10_Spring_MVC_Project/WebContent/upload";
			//이진파일 업로드 서버 경로
			int fileSize=5*1024*1024; //이진파일 업로드 최대 크기
			MultipartRequest multi=null;  			

			multi=new MultipartRequest(request,saveFolder,fileSize,"UTF-8");  			
			int bbs_no=Integer.parseInt(multi.getParameter("bbs_no"));
			int page=Integer.parseInt(multi.getParameter("page"));
			//페이지 번호 저장
			String bbs_name=multi.getParameter("bbs_name").trim();
			String bbs_subject=multi.getParameter("bbs_subject").trim();
			String bbs_pass=multi.getParameter("bbs_pass").trim();
			String bbs_content=multi.getParameter("bbs_content").trim();

			SpringBbsBean bbsCont=this.adminBbsService.getBbsCont(bbs_no);
			//글번호를 기준으로 디비 내용을 가져온다.

			if(bbsCont.getBbs_pass().equals(bbs_pass)){//비번이 같다면
				File UpFile=multi.getFile("bbs_file");
				//첨부한 이진파일을 가져옴.
				if(UpFile != null){//첨부한 이진파일이 있다면
					String fileName=UpFile.getName();//이진파일명 저장
					File DelFile= new File(saveFolder+bbsCont.getBbs_file());
					//삭제할 파일 객체 생성
					if(DelFile.exists()){//삭제할 파일이 존재한다면
						DelFile.delete();//파일 삭제
					}
					Calendar c=Calendar.getInstance();
					int year=c.get(Calendar.YEAR);//년도
					int month=c.get(Calendar.MONTH)+1;//월값
					//+1을 한이유는 1월이 0으로 반환되기 때문에
					int date=c.get(Calendar.DATE);//일값
					String homedir=saveFolder+"/"+year+"-"+month+"-"+date;
					//오늘날짜 새로운 폴더 경로 저장
					File path1=new File(homedir);
					//새로운 폴더 경로 파일 객체 생성
					if(!(path1.exists())){
						path1.mkdir();//새로운 폴더 생성
					}
					Random r=new Random();
					int random=r.nextInt(10000000);

					//****확장자 구하기 시작 ****//*
					int index = fileName.lastIndexOf(".");
					String fileExtension = fileName.substring(index + 1);
					//****확장자 구하기 끝 ***//*
					String refileName="adminbbs"+year+month+date+random+"."+
							fileExtension;//새로운 파일명을 저장
					String fileDBName="/"+year+"-"+month+"-"+date+"/"+refileName;
					//오라클 디비에 저장될 파일명
					UpFile.renameTo(new File(homedir+"/"+refileName));
					//새로운 이진파일명으로 실제 폴더에 업로드  	                
					bbsbean.setBbs_file(fileDBName);
				}else{
					bbsbean.setBbs_file(bbsCont.getBbs_file());
				}
				bbsbean.setBbs_no(bbs_no);
				bbsbean.setBbs_name(bbs_name);
				bbsbean.setBbs_subject(bbs_subject);
				bbsbean.setBbs_content(bbs_content);			

				this.adminBbsService.updateBbs(bbsbean);//수정메서드 

				response.sendRedirect(
						"admin_bbs_cont.do?bbs_no="+bbs_no+"&page="+page+"&state=cont");
				//3개의 값을 get방식으로 넘긴다.
			}else{  				
				out.println("<script>");
				out.println("alert('비번이 다릅니다!')");
				out.println("history.go(-1)");
				out.println("</script>");
			}
		}
		return null;
	}

	/*관리자 자료실 삭제 */
	@RequestMapping(value="/admin_bbs_del_ok.do",method=RequestMethod.POST)
	public ModelAndView admin_bbs_delete_ok(HttpServletRequest request,
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
			int bbs_no=Integer.parseInt(request.getParameter("bbs_no"));
			int page=Integer.parseInt(request.getParameter("page"));
			//글번호와 쪽번호를 정수형 숫자로 바꿔서 저장
			String bbs_pass=request.getParameter("bbs_pass").trim();

			BbsDAOImpl bbsdao=new BbsDAOImpl();

			SpringBbsBean db_pwd=this.adminBbsService.getBbsCont(bbs_no);
			//글번호에 해당하는 디비 내용을 가져옴.

			String up="C:/KJS/workspace(sts)/chap10_Spring_MVC_Project/WebContent/upload";
			//이진파일 업로드 된 서버 경로
			if(db_pwd.getBbs_pass().equals(bbs_pass)){//비번이 같다면
				this.adminBbsService.deleteBbs(bbs_no);
				//글번호를 기준으로 레코드 삭제
				if(db_pwd.getBbs_file() != null){//첨부한 이진파일이 존재
					//할 경우만 삭제
					File file=new File(up+db_pwd.getBbs_file());
					//삭제할 파일 객체 생성
					file.delete();//이진파일 삭제
				}
				response.sendRedirect("admin_bbs_list.do?page="+page);
			}else{
				out.println("<script>");
				out.println("alert('비번이 다릅니다!')");
				out.println("history.back();");
				out.println("</script>");
			}
		}
		return null;
	}
}

