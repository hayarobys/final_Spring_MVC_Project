<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="spring.mvc.model.*"%>

<%

List<SpringBoardBean2> boardList=(List<SpringBoardBean2>)request.getAttribute("boardList");

int nowpage=(Integer)request.getAttribute("page");
int maxpage=(Integer)request.getAttribute("maxpage");
int startpage=(Integer)request.getAttribute("startpage");
int endpage=(Integer)request.getAttribute("endpage");
%>
<%@ include file="/resources/include/admin_header.jsp" %>
<!--  ../ 한 단계 상위 폴더로 이동, 외부 포함 파일을 읽어오는 jsp 형식  -->
<div class="clear"></div>
<!-- 본문 내용 -->
<div id="article"> 
  <%@ include file="/resources/include/admin_login.jsp" %>
  
 <div id="article_c"> 
<!-- 본문 내용 -->
   <div id="aMain_cont">
     <div id="aGongji_list">
      <h2 class="aGlist_title">관리자 게시판 목록</h2>
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
		for(int i=0;i<boardList.size();i++){
			SpringBoardBean2 board=boardList.get(i);			
	%>
			<tr align="center" valign="middle" >
				<td height="23">
					 <%if(board.getBoard_step() == 0){
			 //답변글이 아닐때는 글번호가 출력%>
			  <%=board.getBoard_no()%> <%}else{
            //답변글일때는 글번호를 뺀다.%> &nbsp; <%}%>
				</td>

				<td class="title_box">
				 <%if(board.getBoard_step()!=0){
				//답변글일때 답변글 순서만큼 레벨값 번호가
				//1씩증가한다. 즉 아래 답변글일수록 
				//들여쓰기한다.
   			for(int a=1;a<=board.getBoard_step();a++){ %> &nbsp; <%} %> <img
					src="resources/images/AnswerLine.gif"> <%} %> 
					 <a
					href="admin_board_cont.do?board_no=<%=board.getBoard_no()%>&page=<%=nowpage%>&state=cont"
					onfocus="this.blur();"> <%=board.getBoard_title()%></a>
				</td>

				<td><%=board.getBoard_name()%></td>
				<td><%=board.getBoard_regdate().substring(0,10)%></td>
				<td><%=board.getBoard_hit() %></td>
				<td>
<input type="button" value="수정" class="admin_b"
onclick="location='admin_board_cont.do?board_no=<%=board.getBoard_no()%>&page=<%=nowpage%>&state=edit'" />				
				</td>
				<td>
<input type="button" value="삭제" class="admin_b"
onclick="if(confirm('정말로 삭제 하시겠습니까?')){
location='admin_board_cont.do?board_no=<%=board.getBoard_no()%>&page=<%=nowpage%>&state=del' 
}else{ return; }" />
<!-- 자바스크립트에서 window객체 하위의 confirm()메서드는 확인/취소 단추
를 가진 경고창을 만들어 준다. 확인단추를 클릭하면 반환값으로 true로 리턴하고,
취소 단추를 클릭하면false를 반환한다. 즉 관리자 모드에서 삭제 유무를 다시
확인하기 위해서 주로 사용한다. 확인을 클릭하면 삭제창으로 이동하고,취소를
클릭하면 삭제를 안하고 그대로 현재창에 있게 된다. --> 				
				</td>
			</tr>
			<%	   
		} %>
		</table>
		<div id="aGlist_paging">
			<%if(nowpage<=1){ %>
			[이전]&nbsp;
			<%}else{ %>
			<a href="admin_board_list.do?page=<%=nowpage-1 %>" onfocus="this.blur();">[이전]</a>&nbsp;
			<%} %>

			<%for(int a=startpage;a<=endpage;a++){
				if(a==nowpage){%>
			[<%=a %>]
			<%}else{ %>
			<a href="admin_board_list.do?page=<%=a %>" onfocus="this.blur();">[<%=a %>]
			</a>&nbsp;
			<%} %>
			<%} %>

			<%if(nowpage>=maxpage){ %>
			[다음]
			<%}else{ %>
			<a href="admin_board_list.do?page=<%=nowpage+1 %>" onfocus="this.blur();">[다음]</a>
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
		          <option value="board_title">게시판제목</option>
		          <option value="board_cont">게시판내용</option>		          	          
		         </select>
		         <input name="find_name" id="find_name" size="10"
		         class="admin_box" />
		         <input type="submit" value="검색" class="admin_b"  />
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
    