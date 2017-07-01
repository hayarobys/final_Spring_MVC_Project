/*자료실*/
package spring.mvc.controller;

import java.io.File;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import spring.mvc.dao.BbsDAOImpl;
import spring.mvc.model.SpringBbsBean;

import com.oreilly.servlet.MultipartRequest;

@Controller
public class BbsController {
	
	@Autowired
	private BbsDAOImpl bbsService;

	/*자료실 입력폼*/
	@RequestMapping(value="/bbs_write.do")
	public String bbs_write(){
		return "bbs/bbs_write";
	}
	
	/*자료실 저장*/
	@RequestMapping(value="/bbs_write_ok.do",method=RequestMethod.POST)
	public ModelAndView bbs_write_ok(HttpServletRequest request,
			HttpServletResponse response)
	throws Exception{
		SpringBbsBean bbs=new SpringBbsBean();
		
        String saveFolder="C:/KJS/workspace(sts)/chap10_Spring_MVC_Project/WebContent/upload";
        //이진파일 업로드 서버 경로
        int fileSize=5*1024*1024;
        //이진파일 업로드 최대 크기
        
        MultipartRequest multi=null;
        //이진파일 업로드 객체
        multi=new MultipartRequest(request,saveFolder,fileSize,"UTF-8");
        //생성자 첫번째 전달인자 request는 사용자에 입력한 정보를 서버로 가져옴
        //두번째 전달인자는 이진파일 업로드 서버경로
        //세번째 전달인자는 이진파일 최대 크기
        //네번째 전달인자는 언어코딩 타입 지정
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
        	//Calendar은 추상클래스 이기 때문에 new에 의해서 객체 생성을 못함
        	//하지만 getInstance() 정적메서드에 의해서 칼렌더 객체를 생성할수
        	//있다. 년월일 시분초 를 반환하는 추상클래스 이다.
        	int year=c.get(Calendar.YEAR);//년도값
        	int month=c.get(Calendar.MONTH)+1;//월값
        	//+1을 한 이유는 1월이 0으로 반환되기 때문이다.
        	int date=c.get(Calendar.DATE);//일값
        	String homedir=saveFolder+"/"+year+"-"+month+"-"+date;
        	//오늘날짜 새로운 폴더 경로를 저장
            File path1=new File(homedir);
            if(!(path1.exists())){//폴더 경로가 없다면
            	path1.mkdir();//폴더를 생성
            }
            
            Random r=new Random();
            int random=r.nextInt(10000000);
            //천만사이의 임의의 정수형 난수를 발생시킨다.
            
            /****확장자 구하기 시작 ****/
            int index = fileName.lastIndexOf(".");
            //File 클래스의 getName() 메서드는 이진파일명을 받아온다.
            //lastIndexOf("문자")는  String클래스의 메서드로 해당문자
            //를 문자열 끝 즉 우측에서 헤아려 문자의 위치번호를 반환한다.
            String fileExtension = fileName.substring(index + 1);
           //파일의 확장자를 구한다.
           /****확장자 구하기 끝 ***/
            String refileName="BBS33"+year+month+date+random+"."+
            		   fileExtension;//새로운 파일명을 저장
            String fileDBName="/"+year+"-"+month+"-"+date+"/"+refileName;
            //데이터 베이스에 저장될 레코드 값

          UpFile.renameTo(new File(homedir+"/"+refileName));
          //변경된 이진파일로 실제 업로드
          bbs.setBbs_file(fileDBName);
        }
        bbs.setBbs_name(bbs_name); bbs.setBbs_subject(bbs_subject);
        bbs.setBbs_pass(bbs_pass); bbs.setBbs_content(bbs_content);
        
        this.bbsService.insertBbs(bbs);//저장메서드 호출
        
        response.sendRedirect("bbs_list.do");
        //게시물 목록으로 이동.
		return null;
	}
	
	/*게시물 목록*/
	@RequestMapping(value="/bbs_list.do")
	public ModelAndView bbs_list(HttpServletRequest request,
			HttpServletResponse response)
	throws Exception{
		BbsDAOImpl BbsDAOImpl=new BbsDAOImpl();
		int page=1;
		int limit=10;
		
		if(request.getParameter("page")!=null){
			//get방식으로 넘어온 쪽번호가 정수형이라고 해도
			//문자열로 바껴져서 넘어온다.
			page=Integer.parseInt(request.getParameter("page"));
			//사칙연산을 하기 위해서 parseInt()메서드에 의해서
			//정수형 숫자로 바꾼다.
		}
		
		int listcount=this.bbsService.getListCount(); //총 리스트 수를 받아옴.		
		
		List bbsList = BbsDAOImpl.getBoardList(page,limit); //리스트를 받아옴.
				
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
		request.setAttribute("bbslist", bbsList);
		
		ModelAndView listM= new ModelAndView("bbs/bbs_list");
	   	return listM;
	}
	
	/*게시물 내용 보기*/	
	@RequestMapping(value="/bbs_cont.do")
	public ModelAndView bbs_cont(HttpServletRequest request,
			HttpServletResponse response)
	throws Exception{
		
		int num=Integer.parseInt(request.getParameter("num"));
		//get방식으로 넘어온 글번호를 정수형 숫자로 바꿔서 저장
		String state=request.getParameter("state");
		//내용보기일때는 cont,답변글일때는 reply,수정폼일때는 edit
		//삭제폼일때는 delete
		
		int page=1;
		if(request.getParameter("page") != null){
			page=Integer.parseInt(request.getParameter("page"));
			//페이지 번호를 저장
		}
		
		if(state.equals("cont")){//내용보기 일때만 조회수 증가
		this.bbsService.bbsHit(num);
		}
		
		BbsDAOImpl bbsdao=new BbsDAOImpl();
		
		SpringBbsBean bbs=bbsdao.getBbsCont(num);
		//번호에 해당하는 게시물 내용을 가져옴
		
		String bbs_content=bbs.getBbs_content().replace("\n","<br/>");
		//글내용을 입력할때 엔터키 친부분을 웹상에서 보일때 다음줄로 개행
		
		ModelAndView contM=new ModelAndView();
		contM.addObject("bbscont",bbs);
		//bbscont키값에 bbs를 저장
		contM.addObject("bbs_content",bbs_content);
		contM.addObject("page", page);
		
		if(state.equals("cont")){//내용보기일때
			contM.setViewName("bbs/bbs_cont");
			//bbs폴더의 bbs_cont.jsp 가 실행
		}else if(state.equals("reply")){//답변달기 폼
			contM.setViewName("bbs/bbs_reply");
		}else if(state.equals("edit")){//수정폼
			contM.setViewName("bbs/bbs_edit");
		}else if(state.equals("delete")){//삭제폼일때
			contM.setViewName("bbs/bbs_delete");
		}
		return contM;
	}	
	
	/*답변글 저장*/	
	@RequestMapping(value="/bbs_reply_ok.do",method=RequestMethod.POST)
    public ModelAndView bbs_reply_ok(HttpServletRequest request,
    		HttpServletResponse response) 
	throws Exception{
		SpringBbsBean bbs=new SpringBbsBean();
		
		int bbs_no=Integer.parseInt(request.getParameter("num"));
		//히든으로 넘어온 글번호를 정수형 숫자로 바꿔서 저장
		int bbs_ref=Integer.parseInt(request.getParameter("ref"));
		//글그룹번호 저장
		int bbs_step=Integer.parseInt(request.getParameter("step"));
		//답변글 위치번호
		int bbs_level=Integer.parseInt(request.getParameter("level"));
		//답변글 레벨순서
		int page=Integer.parseInt(request.getParameter("page"));
		//페이지 번호 저장
		String bbs_name=request.getParameter("bbs_name").trim();
		String bbs_subject=request.getParameter("bbs_subject").trim();
		String bbs_content=request.getParameter("bbs_content").trim();
		String bbs_pass=request.getParameter("bbs_pass").trim();
		
		bbs.setBbs_ref(bbs_ref); bbs.setBbs_level(bbs_level);
		
		this.bbsService.updateReply(bbs);//답변글 레벨을 1씩 증가하는
		//업데이트 쿼리문 실행
		
		bbs_step=bbs_step+1;//답변글 위치번호 1 증가
		bbs_level=bbs_level+1;//답변글 레벨 순서 1 증가
		
		bbs.setBbs_name(bbs_name); bbs.setBbs_subject(bbs_subject);
		bbs.setBbs_content(bbs_content);
		bbs.setBbs_pass(bbs_pass); 
		bbs.setBbs_step(bbs_step);
		bbs.setBbs_level(bbs_level);
		
		this.bbsService.replyok(bbs);//답변글 저장메서드 실행
		
		response.sendRedirect("bbs_list.do?page="+page);
		return null;
	}
	
	/* 자료실 수정 */
	@RequestMapping(value="/bbs_edit_ok.do",method=RequestMethod.POST)
	public ModelAndView bbs_edit_ok(HttpServletRequest request,
			HttpServletResponse response)
	throws Exception{
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out=response.getWriter();
		//출력스트림 객체 생성
		
		BbsDAOImpl bbsdao=new BbsDAOImpl();
		SpringBbsBean bbsbean=new SpringBbsBean();
		
		String saveFolder="C:/KJS/workspace(sts)/chap10_Spring_MVC_Project/WebContent/upload";
		//이진파일 업로드 서버 경로
		int fileSize=5*1024*1024; //이진파일 업로드 최대 크기
		
		MultipartRequest multi=null;
		//이진파일 업로드 레퍼 선언
		
		multi=new MultipartRequest(request,saveFolder,fileSize,"UTF-8");
		
		int bbs_no=Integer.parseInt(multi.getParameter("bbs_no"));
		int page=Integer.parseInt(multi.getParameter("page"));
		//페이지 번호 저장
		String bbs_name=multi.getParameter("bbs_name").trim();
		String bbs_subject=multi.getParameter("bbs_subject").trim();
		String bbs_pass=multi.getParameter("bbs_pass").trim();
		String bbs_content=multi.getParameter("bbs_content").trim();
		
		SpringBbsBean db_pwd=bbsdao.getBbsCont(bbs_no);
		//글번호를 기준으로 디비 내용을 가져온다.
		
		if(db_pwd.getBbs_pass().equals(bbs_pass)){//비번이 같다면
			File UpFile=multi.getFile("bbs_file");
			//첨부한 이진파일을 가져옴.
			if(UpFile != null){//첨부한 이진파일이 있다면
				String fileName=UpFile.getName();//이진파일명 저장
				File DelFile=
    new File(saveFolder+db_pwd.getBbs_file());
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
                
                /****확장자 구하기 시작 ****/
                int index = fileName.lastIndexOf(".");
                //File 클래스의 getName() 메서드는 이진파일명을 받아온다.
                //lastIndexOf("문자")는  String클래스의 메서드로 해당문자
                //를 문자열 끝 즉 우측에서 헤아려 문자의 위치번호를 반환한다.
                String fileExtension = fileName.substring(index + 1);
               //파일의 확장자를 구한다.
               /****확장자 구하기 끝 ***/
                String refileName="BBS33"+year+month+date+random+"."+
                		   fileExtension;//새로운 파일명을 저장
                String fileDBName="/"+year+"-"+month+"-"+date+"/"+refileName;
                //오라클 디비에 저장될 레코드 값

                UpFile.renameTo(new File(homedir+"/"+refileName));
                //새로운 이진파일명으로 실제 폴더에 업로드
                
                bbsbean.setBbs_file(fileDBName);
			}
			bbsbean.setBbs_no(bbs_no);
			bbsbean.setBbs_name(bbs_name);
			bbsbean.setBbs_subject(bbs_subject);
			bbsbean.setBbs_content(bbs_content);			
			
			bbsdao.updateBbs(bbsbean);//수정메서드 
			
            response.sendRedirect(
            "bbs_cont.do?num="+bbs_no+"&page="+page+"&state=cont");
            //3개의 값을 get방식으로 넘긴다.
		}else{
			out.println("<script>");
			out.println("alert('비번이 다릅니다!')");
			out.println("history.go(-1)");
			out.println("</script>");
		}
		return null;
	}
	
	/* 자료실 삭제 */
	@RequestMapping(value="/bbs_delete_ok.do",method=RequestMethod.POST)
	public ModelAndView bbs_delete_ok(HttpServletRequest request,
			HttpServletResponse response)
	throws Exception{
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out=response.getWriter();
		//출력스트림 객체 생성
		
		int bbs_no=Integer.parseInt(request.getParameter("bbs_no"));
		int page=Integer.parseInt(request.getParameter("page"));
		//글번호와 쪽번호를 정수형 숫자로 바꿔서 저장
		String bbs_pass=request.getParameter("bbs_pass").trim();
		
		BbsDAOImpl bbsdao=new BbsDAOImpl();
		
		SpringBbsBean db_pwd=bbsdao.getBbsCont(bbs_no);
		//글번호에 해당하는 디비 내용을 가져옴.
		
		String up="C:/KJS/workspace(sts)/chap10_Spring_MVC_Project/WebContent/upload";
		//이진파일 업로드 된 서버 경로
		if(db_pwd.getBbs_pass().equals(bbs_pass)){//비번이 같다면
			this.bbsService.deleteBbs(bbs_no);
			//글번호를 기준으로 레코드 삭제
			if(db_pwd.getBbs_file() != null){//첨부한 이진파일이 존재
				//할 경우만 삭제
				File file=new File(up+db_pwd.getBbs_file());
				//삭제할 파일 객체 생성
				file.delete();//이진파일 삭제
			}
			response.sendRedirect("bbs_list.do?page="+page);
		}else{
			out.println("<script>");
			out.println("alert('비번이 다릅니다!')");
			out.println("history.back();");
			out.println("</script>");
		}
		return null;
	}
	
	/*자료실 검색*/
	@RequestMapping(value="/bbs_find_ok.do")
    public ModelAndView bbs_find_ok(HttpServletRequest request,
    		HttpServletResponse response)
	throws Exception{
		  
		   BbsDAOImpl bd=new BbsDAOImpl();		  
			
		  	int page=1;
			int limit=10;
			
			if(request.getParameter("page")!=null){
				page=Integer.parseInt(request.getParameter("page"));
			}
			
			String find_name=null;
			
			if(request.getParameter("find_name").trim() != null){
			 find_name=request.getParameter("find_name").trim();
			 //검색어 저장
			 //find_name=new String(find_name.getBytes("8859_1"),"UTF-8");
			 //get방식으로 넘어온 언어 코딩 타입을 UTF-8로 바꿔서 안깨지게 함.
			 //만약 server.xml의 64번 라인에 URIEncoding="UTF-8" 설정된 경우 주석처리함
			}
			String find_field=null;
			if(request.getParameter("find_field")!=null){
			find_field=request.getParameter("find_field");
			//검색 필드 저장
			}
			int listcount=this.bbsService.getListCount3("%"+find_name+"%",find_field); 
			//총 리스트 수를 받아옴
			
			List<SpringBbsBean> bbslist = bd.getBbsList3(page,limit,find_name,find_field); 
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
			request.setAttribute("bbslist", bbslist);
			
			ModelAndView model=new ModelAndView("bbs/bbs_find");
			model.addObject("find_name",find_name);
			model.addObject("find_field",find_field);
			return model;	
	}
}

