<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
  String id=(String)session.getAttribute("id"); 
%>  
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
<title>메인화면</title>
<link rel="stylesheet" type="text/css" href="./css/member.css" />
</head>
<body>
  <div id="index_wrap">
    <h2 class="index_title">메인화면</h2>
    <form method="post" action="member_Logout.do">
     <table id="index_t">
      <tr>
       <th>
        <input type="button" value="정보수정" class="input_b"
        onclick="location='member_Edit.do'" />
        <input type="button" value="회원탈퇴" class="input_b"
        onclick="location='member_DEL.do'" />
        <input type="submit" value="로그아웃" class="input_b" />
       </th>
       </tr>
       <tr>
       <th>
        <%=id%>아이디 님 로그인을 환영합니다!
       </th>
      </tr>
     </table>
    </form>
  </div>
</body>
</html>

</div>
</div>

<div class="clear"></div>
<%@ include file="../../../resources/include/footer.jsp" %>



