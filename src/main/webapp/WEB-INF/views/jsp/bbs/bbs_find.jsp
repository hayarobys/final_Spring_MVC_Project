<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<%@ page import="java.util.*"%>
<%@ page import="spring.mvc.model.*" %>
 
<%
List<SpringBbsBean> boardList=(List<SpringBbsBean>)request.getAttribute("bbslist");
//setAttribute()에 의해서 Object형으로 업캐스팅 되면서
//키값에 저장된다. 반환되는 값도 다운캐스팅을 해서 저장한다.
//다운캐스팅은 업캐스팅이 된것에 한해서 다운캐스팅이 된다.
//레퍼런스간의 캐스팅 즉 다운(업)캐스팅은 상속이 된것에 한에서만
//허용된다.
int listcount=((Integer)request.getAttribute("listcount")).intValue();
	int nowpage=((Integer)request.getAttribute("page")).intValue();
	int maxpage=((Integer)request.getAttribute("maxpage")).intValue();
	int startpage=((Integer)request.getAttribute("startpage")).intValue();
	int endpage=((Integer)request.getAttribute("endpage")).intValue();	
%>
<%@ include file="/resources/include/header.jsp" %>
<!--  ../ 한 단계 상위 폴더로 이동, 외부 포함 파일을 읽어오는 jsp 형식  -->
<div class="clear"></div>
<!-- 본문 내용 -->
<div id="article"> 
  <%@ include file="/resources/include/login.jsp" %>
  
 <div id="article_c">   
<html>
<head>
<title>자료실 목록</title>	
</head>
<body>
<div id="bbslist_wrap">
  <h2 class="bbslist_title">검색 목록</h2>
  <div id="bbslist_c">글 개수 : ${listcount}</div>
	
	<table id="bbslist_t">
	<tr align="center" valign="middle" bordercolor="#333333">
		<td style="font-family:Tahoma;font-size:11pt;" width="10%" height="26">
			<div align="center">번호</div>
		</td>
		<td style="font-family:Tahoma;font-size:11pt;" width="40%">
			<div align="center">제목</div>
		</td>
		<td style="font-family:Tahoma;font-size:11pt;" width="15%">
			<div align="center">작성자</div>
		</td>
		<td style="font-family:Tahoma;font-size:11pt;" width="15%">
			<div align="center">날짜</div>
		</td>
		<td style="font-family:Tahoma;font-size:11pt;" width="10%">
			<div align="center">조회수</div>
		</td>
	</tr>
	
	<%
	if((boardList.size()>0) && (boardList != null)){
		for(int i=0;i<boardList.size();i++){
			SpringBbsBean bl=boardList.get(i);
	%>
	<tr align="center" valign="middle" bordercolor="#333333"
		onmouseover="this.style.backgroundColor='F8F8F8'"
		onmouseout="this.style.backgroundColor=''">
		<td height="23" style="font-family:Tahoma;font-size:10pt;">
			 <%if(bl.getBbs_step() == 0){
			 //답변글이 아닐때는 글그룹번호가 출력%>
            <%=bl.getBbs_ref()%>
            <%}else{
            //답변글일때는 글번호를 출력.%>
            <%=bl.getBbs_no()%>
           <%}%>
		</td>
		
		<td style="font-family:Tahoma;font-size:10pt;">
			<div align="left">
			<%if(bl.getBbs_step()!=0){
				//답변글일때 답변글 순서만큼 레벨값 번호가
				//1씩증가한다. 즉 아래 답변글일수록 
				//들여쓰기한다.
   			for(int a=1;a<=bl.getBbs_step();a++){ %>
	       &nbsp;
           <%} %>
           <img src="./images/AnswerLine.gif">
           <%} %>
        <a 
href="bbs_cont.do?num=<%=bl.getBbs_no()%>&page=<%=nowpage%>&state=cont">
        <%=bl.getBbs_subject()%></a>
			</div>
		</td>
		
		<td style="font-family:Tahoma;font-size:10pt;">
			<div align="center"><%=bl.getBbs_name() %></div>
		</td>
		<td style="font-family:Tahoma;font-size:10pt;">
			<div align="center"><%=bl.getBbs_regdate() %></div>
		</td>	
		<td style="font-family:Tahoma;font-size:10pt;">
			<div align="center"><%=bl.getBbs_hit() %></div>
		</td>
	</tr>
	<%}}else{ %>
	<tr>
	<th height="23" colspan="5" style="font-family:Tahoma;font-size:10pt;">
	 검색 목록이 없습니다!
	</th>
	</tr>
	<% } %>
	</table>
	<div id="bbslist_paging">	
			<%if(nowpage<=1){ %>
			[이전]&nbsp;
			<%}else{ %>
			<a href="bbs_find_ok.do?page=<%=nowpage-1 %>&find_name=${find_name}&find_field=${find_field}">[이전]</a>&nbsp;
			<%} %>
			
			<%for(int a=startpage;a<=endpage;a++){
				if(a==nowpage){%>
				[<%=a %>]
				<%}else{ %>
				<a href="bbs_find_ok.do?page=<%=a%>&find_name=${find_name}&find_field=${find_field}">[<%=a %>]</a>&nbsp;
				<%} %>
			<%} %>
			
			<%if(nowpage>=maxpage){ %>
			[다음]
			<%}else{ %>
			<a href="bbs_find_ok.do?page=<%=nowpage+1 %>&find_name=${find_name}&find_field=${find_field}">[다음]</a>
			<%} %>
		</div>
		<div id="bbslist_w">
	     <input type="button" value="글쓰기" class="input_b"
	     onclick="location='bbs_write.do?page=<%=nowpage%>'">
		</div>
		
		<!-- 검색 부분 -->
		<div id="bbsfind">
		  <script src="./js/jquery.js"></script>
		  <script>
		   function find_check(){
			   if($.trim($("#find_name").val())==""){
				   alert("검색어를 입력하세요!");
				   $("#find_name").val("").focus();
				   return false;
			   }
		   }
		  </script>
		  <form method="get" action="bbs_find_ok.do"
		  onsubmit="return find_check()">
		   <table>
		    <tr>
		     <th>
		      <select name="find_field">
		       <option value="bbs_subject"
		       <c:if test="${find_field == 'bbs_subject'}">
		       ${'selected'}</c:if>>글제목</option>		
		       <option value="bbs_name"
		       <c:if test="${find_field == 'bbs_name'}">
		       ${'selected'}</c:if>>작성자</option>		              
		      </select>
		     </th>
		     <td>
		      <input name="find_name" id="find_name" size="18"
		      class="box" value="${find_name}"/>
		      <input type="submit" value="검색" class="input_b" />
		     
		      <input type="button" value="목록" class="input_b"
	           onclick="location='bbs_list.do'">
	     
		      
		     </td>
		    </tr>
		   </table>		 
		  </form>
		</div>
</div>
</body>
</html>
</div>
</div>

<div class="clear"></div>
<%@ include file="/resources/include/footer.jsp" %>