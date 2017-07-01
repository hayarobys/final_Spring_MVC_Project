<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
      <h2 class="aGcont_title">관리자 게시판 내용</h2>
      <table id="aGcont_t">
       <tr>
        <th>제목</th> <td>${aboard_cont.board_title}</td>
       </tr>
       
       <tr>
        <th>내용</th> <td>${board_cont}</td>
       </tr>
       
       <tr>
        <th>조회수</th> <td>${aboard_cont.board_hit}</td>
       </tr>
      </table>
      
      <div id="aGcont_menu">
      <input type="button" value="목록" class="admin_b"
      onclick="location='admin_board_list.do?page=${page}'" />
      </div>
     </div>
   </div>
 </div>
</div>

<div class="clear"></div>
<%@ include file="/resources/include/footer.jsp" %>   
