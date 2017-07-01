<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/resources/include/header.jsp" %>
<!--  ../ 한 단계 상위 폴더로 이동, 외부 포함 파일을 읽어오는 jsp 형식  -->
<div class="clear"></div>
<!-- 본문 내용 -->
<div id="article"> 
  <%@ include file="/resources/include/login.jsp" %>
  
 <div id="article_c"> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>답변 달기 폼</title>
<script src="./js/jquery.js"></script>
<script src="./js/board.js"></script>
<link rel="stylesheet" type="text/css" href="./css/board.css" />
</head>
<body>
 <div id="dReply_wrap">
  <h2 class="dReply_title">답변달기</h2>
  <form method="post" action="board_reply_ok.do" 
  onsubmit="return dWrite_check();">
 <input type="hidden" name="board_no" value="${bcont.board_no}" />
 <input type="hidden" name="board_ref" value="${bcont.board_ref}" />
 <input type="hidden" name="board_step" value="${bcont.board_step}"/>
 <input type="hidden" name="board_level" 
 value="${bcont.board_level}" />
 <input type="hidden" name="page" value="${page}" />
 
   <table id="dReply_t">
    <tr>
     <th>글쓴이</th>
     <td>
      <input name="board_name" id="board_name" class="box" size="14" />
     </td>
    </tr>
    
    <tr>
     <th>글제목</th>
     <td>
      <input name="board_title" id="board_title" class="box"
      size="40" value="Re:${bcont.board_title}" />
     </td>
    </tr>
    
    <tr>
     <th>글내용</th>
     <td>
      <textarea name="board_cont" id="board_cont" rows="6"
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
   <div id="dReply_menu">
    <input type="submit" value="답변" class="input_b" />
    <input type="reset" value="취소" class="input_b" 
    onclick="$('#board_name').focus();" />
   </div>
  </form>
 </div>
</body>
</html>

</div>
</div>

<div class="clear"></div>
<%@ include file="/resources/include/footer.jsp" %>