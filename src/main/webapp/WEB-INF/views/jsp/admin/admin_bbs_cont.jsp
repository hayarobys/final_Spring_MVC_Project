<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>	
<%@ include file="/resources/include/admin_header.jsp" %>
<!--  ../ 한 단계 상위 폴더로 이동, 외부 포함 파일을 읽어오는 jsp 형식  -->
<div class="clear"></div>
<!-- 본문 내용 -->
<div id="article"> 
  <%@ include file="/resources/include/admin_login.jsp" %>
  
 <div id="article_c"> 
<!-- 본문 내용 -->
   <div id="aMain_cont">
     <div id="aGongji_cont">
		<h2 class="aGcont_title">관리자 자료실 내용</h2>
		<table id="aGongji_t">
			<tr>
				<th>제목</th>
				<td>${abbs_cont.bbs_subject}</td>
			</tr>

			<tr>
				<th>글내용</th>
				<td>${bbs_cont}</td>
			</tr>

			<c:if test="${!empty abbs_cont.bbs_file}">
				<tr>
					<th>첨부파일</th>
					<td>
						<a href="./upload${abbs_cont.bbs_file}" 
						onfocus="this.blur();" target="_blank">
						다운로드</a> <br>
                       <img src="./upload${abbs_cont.bbs_file}"
						width="150" height="130" border="0" />
					</td>
				</tr>
			</c:if>
			
			<tr>
				<th>조회수</th>
				<td>${abbs_cont.bbs_hit}</td>
			</tr>
		</table>
		
		<div id="aGcont_menu">
        <input type="button" value="목록" class="admin_b"
        onclick="location='admin_bbs_list.do?page=${page}'" />
      </div>
	</div>
</div>
 </div>
</div>

<div class="clear"></div>
<%@ include file="/resources/include/footer.jsp" %>   


