<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<%@ page import="java.util.*"%>
<%@ page import="spring.mvc.model.*"%>

<%
List<SpringBoardBean2> boardList=(List<SpringBoardBean2>)request.getAttribute("boardList");

int nowpage=((Integer)request.getAttribute("page")).intValue();
int maxpage=((Integer)request.getAttribute("maxpage")).intValue();
int startpage=((Integer)request.getAttribute("startpage")).intValue();
int endpage=((Integer)request.getAttribute("endpage")).intValue();
%>
<%@ include file="/resources/include/admin_header.jsp" %>
<div class="clear"></div>
<!-- 본문 내용 -->
<div id="article"> 
  <%@ include file="/resources/include/admin_login.jsp" %>
  
 <div id="article_c"> 
<!-- 본문 내용 -->
   <div id="aMain_cont">
     <div id="aGongji_list">
      <h2 class="aGlist_title">관리자 게시판 검색</h2>
		<div id="aGlist_count">
			총게시물 수 : ${listcount}<br />
		</div>

		<table id="aGlist_t">
			<tr>
				<td height="26" class="no">번호</td>
				<td class="title">제목</td>
				<td class="writer">작성자</td>
				<td class="date">날짜</td>
				<td class="hit">조회수</td>
				<td class="no">수정</td>
				<td class="no">삭제</td>
			</tr>
			<%	   
		if((boardList != null) && (boardList.size()>0)){	
		for(int i=0;i<boardList.size();i++){
			SpringBoardBean2 b=boardList.get(i);			
	%>
			<tr align="center" valign="middle" >
				<td height="23">					 
			  <%=b.getBoard_no()%> 
				</td>

				<td class="title_box">				 
					 <a
					href="admin_board_cont.do?board_no=<%=b.getBoard_no()%>&page=<%=nowpage%>&state=cont"
					onfocus="this.blur();"> <%=b.getBoard_title()%></a>
				</td>

				<td><%=b.getBoard_name()%></td>
				<td><%=b.getBoard_regdate()%></td>
				<td><%=b.getBoard_hit() %></td>
				<td>
<input type="button" value="수정" class="admin_b"
onclick="location='admin_board_cont.do?board_no=<%=b.getBoard_no()%>&page=<%=nowpage%>&state=edit'" />				
				</td>
				<td>
<input type="button" value="삭제" class="admin_b"
onclick="if(confirm('정말로 삭제 하시겠습니까?')){
location='admin_board_cont.do?board_no=<%=b.getBoard_no()%>&page=<%=nowpage%>&state=del' 
}else{ return; }" />
<!-- 자바스크립트에서 window객체 하위의 confirm()메서드는 확인/취소 단추
를 가진 경고창을 만들어 준다. 확인단추를 클릭하면 반환값으로 true로 리턴하고,
취소 단추를 클릭하면false를 반환한다. 즉 관리자 모드에서 삭제 유무를 다시
확인하기 위해서 주로 사용한다. 확인을 클릭하면 삭제창으로 이동하고,취소를
클릭하면 삭제를 안하고 그대로 현재창에 있게 된다. --> 				
				</td>
			</tr>
			<%	   
		} } else{%>
		<tr>
		 <th colspan="7">게시물 검색 결과가 없습니다!</th>
		</tr>
		<% } %>
		</table>
		<div id="aGlist_paging">
			<%if(nowpage<=1){ %>
			[이전]&nbsp;
			<%}else{ %>
			<a href="admin_board_find.do?page=<%=nowpage-1%>&find_name=${find_name}&find_field=${find_field}" onfocus="this.blur();">[이전]</a>&nbsp;
			<%} %>

			<%for(int a=startpage;a<=endpage;a++){
				if(a==nowpage){%>
			[<%=a %>]
			<%}else{ %>
			<a href="admin_board_find.do?page=<%=a %>&find_name=${find_name}&find_field=${find_field}" onfocus="this.blur();">[<%=a %>]
			</a>&nbsp;
			<%} %>
			<%} %>

			<%if(nowpage>=maxpage){ %>
			[다음]
			<%}else{ %>
			<a href="admin_board_find.do?page=<%=nowpage+1%>&find_name=${find_name}&find_field=${find_field}" onfocus="this.blur();">[다음]</a>
			<%} %>
		</div>
		<div id="aGlist_menu">
			<a href="admin_board_write.do?page=<%=nowpage%>" onfocus="this.blur();">[게시판작성]</a>
		</div>
		
		<!-- 관리자 공지 검색 부분 -->
		<div id="aGlist_find">
		  <script>
		    function find_check(){
		    	if($.trim($("#find_name").val())==""){
		    		alert("검색어를 입력하세요!");
		    		$("#find_name").val("").focus();
		    		return false;
		    	}
		    }
		  </script>
		  <form method="get" action="admin_board_find.do"
		  onsubmit="return find_check()">
		      <table id="aGlistFind_t">
		        <tr>
		         <td>
		         <select name="find_field">
		          <option value="board_title"
		          <c:if test="${find_field == 'board_title'}">
		          ${'selected'}</c:if>>게시판제목</option>
		          <option value="board_cont"
		          <c:if test="${find_field == 'board_cont'}">
		          ${'selected'}</c:if>>게시판내용</option>		          	          
		         </select>
		         <input name="find_name" id="find_name" size="10"
		         class="admin_box" value="${find_name}" />
		         <input type="submit" value="검색" class="admin_b"  />
		         
		         <input type="button" value="목록" class="admin_b"
	               onclick="location='admin_board_list.do'">
		         </td>
		        </tr>
		      </table>
		  </form>
		</div>
    </div>
   </div>
     </div>
</div>

<div class="clear"></div>
<%@ include file="/resources/include/footer.jsp" %> 