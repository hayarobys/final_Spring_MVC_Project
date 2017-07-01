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
<title>사용자 공지사항 내용보기</title>
</head>
<body>
 <div id="uGongji_wrap">
  <h2 class="uGongji_title">사용자 공지 보기</h2>
  <table id="uGongji_t">
   <tr>
    <th>공지제목</th>
    <td>${bean.gongji_title}</td>
   </tr>
   
   <tr>
    <th>공지내용</th>
    <td>${bean.gongji_cont}</td>
   </tr>
   
   <tr>
    <th>조회수</th>
    <td>${bean.gongji_hit}</td>
   </tr>
  </table>
  
  <div id="uGongji_menu">
    <input type="button" value="목록" class="gbutton_b"
    onclick="location='gongji_list.do?page=${page}'" />
  </div>
 </div>
</body>
</html>
</div>
</div>

<div class="clear"></div>
<%@ include file="/resources/include/footer.jsp" %>