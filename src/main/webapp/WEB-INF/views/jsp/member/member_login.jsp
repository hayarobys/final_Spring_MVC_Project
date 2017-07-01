<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>회원관리 로그인</title>
<%@ include file="/resources/include/mem_header.jsp" %>
<!-- <script src="./js/jquery.js"></script>
<script src="./js/member.js"></script>
<link rel="stylesheet" type="text/css" href="./css/member.css" /> -->
</head>
<body>
  <div id="login_wrap">
   <h2 class="login_title">로그인 폼</h2>
   <form method="post" action="member_Login_ok.do"
   onsubmit="return Login_check();">
    <table id="login_t">
     <tr>
      <th>아이디</th>
      <td>
       <input name="login_id" id="login_id" size="14" class="box" />
      </td>
      <th rowspan="2">
       <input type="submit" value="로그인" class="input_Login" />       
      </th>
     </tr>
     
     <tr>
      <th>비밀번호</th>
      <td>
       <input type="password" name="login_pwd" id="login_pwd" 
       size="14" class="box" />
      </td>
     </tr>
     
     <tr>
      <th colspan="3">
       <input type="button" value="회원가입" class="input_b"
       onclick="location='member_Join.do'" />
       <input type="button" value="비번찾기" class="input_b" 
       onclick="location='pwd_Find.do'" />
      </th>
     </tr>
    </table>
   </form>
  </div>
</body>
</html>