<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>비번 찾기 결과</title>
<%@ include file="/resources/include/mem_header.jsp" %>
<!-- <link rel="stylesheet" type="text/css" href="./css/member.css" />
 -->
 </head>
<body>
  <div id="pwdok_wrap">
   <h2 class="pwdok_title">비번찾기결과</h2>
   <table id="pwdok_t">
    <tr>
     <th>비번:</th>
     <td>${password}</td>
    </tr>
   </table>
   <div id="pwdok_menu">
   <input type="button" value="돌아가기" class="input_b" onclick="location='pwd_Find.do'" />
   </div>  
  </div>  
</body>
</html>