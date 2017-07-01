<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>회원탈퇴</title>
<%@ include file="/resources/include/mem_header.jsp" %>
<!-- <script src="./js/jquery.js"></script>
<script src="./js/member.js"></script>
<link rel="stylesheet" type="text/css" href="./css/member.css" /> -->
</head>
<body>
  <div id="del_wrap">
   <h2 class="del_title">회원탈퇴</h2>
   <form method="post" action="member_DEL_ok.do"
   onsubmit="return del_check();">
    <table id="del_t">
     <tr>
      <th>회원아이디</th>
      <td>${id}</td>
     </tr>
     
     <tr>
      <th>회원이름</th>
      <td>
       ${name}
      </td>
     </tr>
     
     <tr>
      <th>회원비번</th>
      <td>
       <input type="password" name="del_pwd" id="del_pwd" size="14"
       class="box" />
      </td>
     </tr>
     
     <tr>
      <th>탈퇴사유</th>
      <td>
       <textarea name="del_cont" rows="8" cols="36" id="del_cont"
       class="box"></textarea>
      </td>
     </tr>
    </table>
    <div id="del_menu">
     <input type="submit" value="탈퇴" class="input_b" />
     <input type="reset" value="취소" class="input_b"
     onclick="$('#del_pwd').focus();" />
    </div>
   </form>
  </div>
</body>
</html>