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
<title>방명록 삭제폼</title>
<link rel="stylesheet" type="text/css" href="./css/guest.css" />
<script src="./js/jquery.js"></script>
<script>
 function del_check(){
	 if($.trim($("#del_pwd").val())==""){
		 alert("삭제 비번을 입력하세요!");
		 $("#del_pwd").val("").focus();
		 return false;
	 }
 }
</script>
</head>
<body>
 <div id="gDel_wrap">
  <h2 class="gDel_title">방명록 삭제</h2>
  <form method="post" action="guest_del_ok.do" 
  onsubmit="return del_check()">
  <input type="hidden" name="no" value="${no}" />
   <table id="gDel_t">
    <tr>
     <th>비밀번호</th>
     <td>
      <input type="password" name="del_pwd" id="del_pwd"
      size="14" class="box" />
     </td>
    </tr>
   </table>
   <div id="gDel_menu">
   <input type="submit" value="삭제" class="input_b" />
   <input type="reset" value="취소" class="input_b"
   onclick="$('#del_pwd').focus();" />
   </div>
  </form>
 </div>
</body>
</html>

</div>
</div>

<div class="clear"></div>
<%@ include file="/resources/include/footer.jsp" %>