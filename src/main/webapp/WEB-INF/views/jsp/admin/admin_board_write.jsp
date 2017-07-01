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
    <div id="aGongji_write">
  <h2 class="aGongjiw_title">관리자 게시판 입력</h2>
  <form method="post" action="admin_board_write_ok.do"
  onsubmit="return dWrite_check();">
   <table id="dWrite_t">
    <tr>
     <th>글쓴이</th>
     <td>
      <input name="board_name" id="board_name" size="14" 
      class="box" />
     </td>
    </tr>
    
    <tr>
     <th>글제목</th>
     <td>
      <input name="board_title" id="board_title" size="40"
      class="box" />
     </td>
    </tr>
    
    <tr>
      <th>글내용</th>
      <td>
      <textarea name="board_cont" id="board_cont" rows="5"
      cols="40" class="box"></textarea>
      </td>
    </tr>
    
    <tr>
      <th>비밀번호</th>
      <td>
       <input type="password" name="board_pwd" id="board_pwd"
       size="14" class="box" />
      </td>
    </tr>
   </table>
   <div id="dWrite_menu">
     <input type="submit" value="저장" class="input_b" />
     <input type="reset" value="취소" class="input_b"
     onclick="$('#board_name').focus();" />
   </div>
    </form>
    </div>
   </div>
    </div>
</div>

<div class="clear"></div>
<%@ include file="/resources/include/footer.jsp" %> 
   