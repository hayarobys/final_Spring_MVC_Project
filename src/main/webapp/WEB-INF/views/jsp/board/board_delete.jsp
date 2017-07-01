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
<title>게시물 삭제</title>
<script src="./js/jquery.js"></script>
<script>
  function del_check(){
	  if($.trim($("#board_pwd").val())==""){
		  alert("비밀번호를 입력하세요!");
		  $("#board_pwd").val("").focus();
		  return false
	  }
  }
</script>
<link rel="stylesheet" type="text/css" href="./css/board.css" />
</head>
<body> 
 <div id="Del_wrap">
   <h2 class="Del_title">게시물 삭제</h2>
   <form method="post" action="board_del_ok.do"
    onsubmit="return del_check()">
     <input type="hidden" name="board_no" value="${bcont.board_no}" />
     <input type="hidden" name="page" value="${page}" />
     <table id="Del_t">
      <tr>
       <th>비밀번호</th>
       <td>
        <input type="password" name="board_pwd" id="board_pwd"
        size="14" class="box" />
       </td>
      </tr>
     </table>    
     <div id="Del_menu">
      <input type="submit" value="삭제" class="input_b" />
      <input type="reset" value="취소" class="input_b"
      onclick="$('#board_pwd').focus();" />
     </div>
   </form>
 </div>
</body>
</html>
<!-- 과제물
  1. board_del_ok.do 컨트롤러 어노테이션 클래스와 xml작성
  2. 비번이 같은 경우는 삭제되게 하고, 다르면 경고문을 띄우게 하기
  3. 글번호를 기준으로 레코드 삭제(jdbc)
 -->

</div>
</div>

<div class="clear"></div>
<%@ include file="/resources/include/footer.jsp" %>










