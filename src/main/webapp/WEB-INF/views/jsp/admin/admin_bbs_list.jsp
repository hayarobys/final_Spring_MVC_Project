<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="spring.mvc.model.*"%>

<%
List<SpringBbsBean2> bbsList=(List<SpringBbsBean2>)request.getAttribute("bbsList");
//setAttribute()에 의해서 Object형으로 업캐스팅 되면서
//키값에 저장된다. 반환되는 값도 다운캐스팅을 해서 저장한다.
//다운캐스팅은 업캐스팅이 된것에 한해서 다운캐스팅이 된다.
//레퍼런스간의 캐스팅 즉 다운(업)캐스팅은 상속이 된것에 한에서만
//허용된다.
//int listcount1=((Integer)request.getAttribute("listcount1")).intValue();
//int listcount2=((Integer)request.getAttribute("listcount2")).intValue();

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
      <h2 class="aGlist_title">관리자 자료실 목록</h2>
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
		for(int i=0;i<bbsList.size();i++){
			SpringBbsBean2 b=bbsList.get(i);			
	%>
			<tr align="center" valign="middle" >
				<td height="23">
					 <%if(b.getBbs_step() == 0){
			 //답변글이 아닐때는 글번호가 출력%>
			  <%=b.getBbs_no()%> <%}else{
            //답변글일때는 글번호를 뺀다.%> &nbsp; <%}%>
				</td>

				<td class="title_box">
				 <%if(b.getBbs_step()!=0){
				//답변글일때 답변글 순서만큼 레벨값 번호가
				//1씩증가한다. 즉 아래 답변글일수록 
				//들여쓰기한다.
   			for(int a=1;a<=b.getBbs_step();a++){ %> &nbsp; <%} %> <img
					src="resources/images/AnswerLine.gif"> <%} %> 
					 <a
					href="admin_bbs_cont.do?bbs_no=<%=b.getBbs_no()%>&page=<%=nowpage%>&state=cont"
					onfocus="this.blur();"> <%=b.getBbs_subject()%></a>
				</td>

				<td><%=b.getBbs_name()%></td>
				<td><%=b.getBbs_regdate().substring(0,10)%></td>
				<td><%=b.getBbs_hit() %></td>
				<td>
<input type="button" value="수정" class="admin_b"
onclick="location='admin_bbs_cont.do?bbs_no=<%=b.getBbs_no()%>&page=<%=nowpage%>&state=edit'" />				
				</td>
				<td>
<input type="button" value="삭제" class="admin_b"
onclick="if(confirm('정말로 삭제 하시겠습니까?')){
location='admin_bbs_cont.do?bbs_no=<%=b.getBbs_no()%>&page=<%=nowpage%>&state=del' 
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
			<a href="admin_bbs_list.do?page=<%=nowpage-1 %>" onfocus="this.blur();">[이전]</a>&nbsp;
			<%} %>

			<%for(int a=startpage;a<=endpage;a++){
				if(a==nowpage){%>
			[<%=a %>]
			<%}else{ %>
			<a href="admin_bbs_list.do?page=<%=a %>" onfocus="this.blur();">[<%=a %>]
			</a>&nbsp;
			<%} %>
			<%} %>

			<%if(nowpage>=maxpage){ %>
			[다음]
			<%}else{ %>
			<a href="admin_bbs_list.do?page=<%=nowpage+1 %>" onfocus="this.blur();">[다음]</a>
			<%} %>
		</div>
		<div id="aGlist_menu">
			<a href="admin_bbs_write.do?page=<%=nowpage%>" onfocus="this.blur();">[자료실작성]</a>
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
		  <form method="get" action="admin_bbs_find.do"
		  onsubmit="return find_check()">
		      <table id="aGlistFind_t">
		        <tr>
		         <td>
		         <select name="find_field">
		          <option value="bbs_subject">자료실제목</option>
		          <option value="bbs_content">자료실내용</option>		          	          
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

    