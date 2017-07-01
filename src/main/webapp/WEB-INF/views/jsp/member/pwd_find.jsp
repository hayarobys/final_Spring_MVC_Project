<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>비밀번호 찾기</title>
<%@ include file="/resources/include/mem_header.jsp" %>
<!-- <script src="./js/jquery.js"></script>
<script src="./js/member.js"></script>
<link rel="stylesheet" type="text/css" href="./css/member.css" /> -->
</head>
<body>
  <div id="pwd_wrap">
   <h2 class="pwd_title">비번찾기</h2>
   <form method="post" action="pwd_Find_ok.do"
    onsubmit="return pwd_check();">
    <table id="pwd_t">
     <tr>
      <th>회원아이디</th>
      <td>
       <input name="pwd_id" id="pwd_id" size="14" class="box" />
      </td>
     </tr>
     
     <tr>
      <th>회원이름</th>
      <td>
       <input name="pwd_name" id="pwd_name" size="14" class="box" />
      </td>
     </tr>
    </table>     
    <div id="pwd_menu">
     <input type="submit" value="찾기"  class="input_b" />
     <input type="reset" value="취소" class="input_b" onclick="$('#pwd_id').focus();" />
    </div>
   </form>
  </div>
</body>
</html>