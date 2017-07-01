<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="spring.mvc.model.*" %>
<%
List<SpringBbsBean> bbsList=(List<SpringBbsBean>)request.getAttribute("bbslist");
 
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
<title>MVC 자료실</title>	
</head>

<body>
<!-- 게시판 리스트 -->
<div id="list_wrap">
  <h2 class="list_title">자료실 목록</h2>
  <div id="list_count">
     총게시물 수 : ${listcount}<br/>
  </div>
		
<table id="list_t">	
	<tr>
		<td  height="26" class="no">번호</td>
		<td class="title">제목</td>
		<td class="writer">작성자</td>
		<td class="date">날짜</td>
		<td class="hit">조회수</td>
	</tr>	
	<%	    	
		for(int i=0;i<bbsList.size();i++){
			SpringBbsBean bl=bbsList.get(i);			
	%>
	<tr align="center" valign="middle" bordercolor="#333333"
		onmouseover="this.style.backgroundColor='skyblue'"
		onmouseout="this.style.backgroundColor=''">
		<td height="23">
			 <%if(bl.getBbs_step() == 0){
			 //답변글이 아닐때는 글그룹번호가 출력%>
                <%=bl.getBbs_no()%>
            <%}else{
            //답변글일때는 글그룹번호를 뺀다.%>
            &nbsp;
           <%}%>
		</td>
		
		<td class="title_box">
			<%if(bl.getBbs_step()!=0){
				//답변글일때 답변글 순서만큼 레벨값 번호가
				//1씩증가한다. 즉 아래 답변글일수록 
				//들여쓰기한다.
   			for(int a=1;a<=bl.getBbs_step();a++){ %>
	       &nbsp;
           <%} %>
           <img src="resources/images/AnswerLine.gif">
           <%} %>
        <a 
href="bbs_cont.do?num=<%=bl.getBbs_no()%>&page=<%=nowpage%>&state=cont"
onfocus="this.blur();">
        <%=bl.getBbs_subject()%></a>			
		</td>
		
		<td><%=bl.getBbs_name() %></td>
		<td>
		<%=bl.getBbs_regdate().substring(0,10)%>
		</td>	
		<td>
		<%=bl.getBbs_hit() %>
		</td>
	</tr>
	<%	   
		} %>
	</table>
	<div id="list_paging">
			<%if(nowpage<=1){ %>
			[이전]&nbsp;
			<%}else{ %>
			<a href="bbs_list.do?page=<%=nowpage-1 %>" onfocus="this.blur();">[이전]</a>&nbsp;
			<%} %>
			
			<%for(int a=startpage;a<=endpage;a++){
				if(a==nowpage){%>
				[<%=a %>]
				<%}else{ %>
				<a href="bbs_list.do?page=<%=a %>" onfocus="this.blur();">[<%=a %>]</a>&nbsp;
				<%} %>
			<%} %>
			
			<%if(nowpage>=maxpage){ %>
			[다음]
			<%}else{ %>
			<a href="bbs_list.do?page=<%=nowpage+1 %>" onfocus="this.blur();">[다음]</a>
			<%} %>
		</div>
		<div id="list_menu">
		    <input type="button" value="글쓰기" class="input_b"
	         onclick="location='bbs_write.do?page=<%=nowpage%>'">	
		</div>
		<script>
		  function find_check(){
			  if($.trim($("#find_name").val()) == ""){
				  alert("검색어를 입력하세요!");
				  $("#find_name").val("").focus();
				  return false;
			  }
		  }
		</script>		
	    <!-- 검색 창 -->
    	<div id="bbs_find">
	    	 <form method="get" action="bbs_find_ok.do"
	    	 onsubmit="return find_check();">
		        <select name="find_field">
		         <option value="bbs_subject">제목</option>
		         <option value="bbs_name">작성자</option>
		        </select>
		        <input name="find_name" id="find_name" size="14"
		        class="box" />
		        <input type="submit" value="검색" class="input_b" />
		    </form>
		 </div>
	</div>
</body>
</html>
</div>
</div>

<div class="clear"></div>
<%@ include file="/resources/include/footer.jsp" %>