<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="spring.mvc.model.*"%>
<%
List<SpringMemberBean> memList=(List<SpringMemberBean>)request.getAttribute("memList");
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
<!--  ../ 한 단계 상위 폴더로 이동, 외부 포함 파일을 읽어오는 jsp 형식  -->
<div class="clear"></div>
<!-- 본문 내용 -->
<div id="article"> 
  <%@ include file="/resources/include/admin_login.jsp" %>
  
 <div id="article_c"> 
   <!-- 본문 내용 -->
   <div id="aMain_cont">
    <div id="aGongji_list">
      <h2 class="aGlist_title">관리자 회원 목록</h2>
		<div id="aGlist_count">
			총회원 수 : ${listcount}<br />
		</div>

		<table id="aGlist_t">
			<tr>
				<td height="26" class="no">번호</td>
				<td class="id">아이디</td>
				<td class="name">회원이름</td>
				<td class="phone">폰번호</td>
				<td class="date">가입날짜</td>
				<td class="state">가입유무</td>
				<td class="no">수정</td>
				<td class="no">삭제</td>
			</tr>
			<%	    	
		for(int i=0;i<memList.size();i++){
			SpringMemberBean m=memList.get(i);			
	%>
			<tr align="center" valign="middle" >
				<td height="23">
					 <%=m.getMem_code()%> 
				</td>

				<td align="center">
				 <a
					href="admin_member_cont.do?mem_id=<%=m.getMem_id()%>&page=<%=nowpage%>&state=cont"
					onfocus="this.blur();"><%=m.getMem_id()%></a>
				</td>

				<td><%=m.getMem_name() %></td>
				<td><%=m.getMem_phone()%></td>
				<td><%=m.getMem_regdate().substring(0,10)%></td>
				<td><%=m.getMem_state()%></td>
				<td>
<input type="button" value="수정" class="admin_b"
onclick="location='admin_member_cont.do?mem_id=<%=m.getMem_id()%>&page=<%=nowpage%>&state=edit'" />				
				</td>
				<td>
<input type="button" value="삭제" class="admin_b"
onclick="if(confirm('정말로 삭제 하시겠습니까?')){
location='admin_member_del.do?mem_id=<%=m.getMem_id()%>&page=<%=nowpage%>' 
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
			<a href="admin_member_list.do?page=<%=nowpage-1 %>" onfocus="this.blur();">[이전]</a>&nbsp;
			<%} %>

			<%for(int a=startpage;a<=endpage;a++){
				if(a==nowpage){%>
			[<%=a %>]
			<%}else{ %>
			<a href="admin_member_list.do?page=<%=a %>" onfocus="this.blur();">[<%=a %>]
			</a>&nbsp;
			<%} %>
			<%} %>

			<%if(nowpage>=maxpage){ %>
			[다음]
			<%}else{ %>
			<a href="admin_member_list.do?page=<%=nowpage+1 %>" onfocus="this.blur();">[다음]</a>
			<%} %>
		</div>
				
		<!-- 관리자 회원 검색 부분 -->
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
		  <form method="get" action="admin_member_find.do"
		  onsubmit="return find_check()">
		      <table id="aGlistFind_t">
		        <tr>
		         <td>
		         <select name="find_field">
		          <option value="mem_id">아이디</option>
		          <option value="mem_name">회원이름</option>		          	          
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
 
 
 
 
   
       