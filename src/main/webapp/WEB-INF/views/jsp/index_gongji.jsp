<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    
<%@ include file="/resources/include/header.jsp" %>
<!--  ../ 한 단계 상위 폴더로 이동, 외부 포함 파일을 읽어오는 jsp 형식  -->
<div class="clear"></div>
<!-- 본문 내용 -->
<div id="article"> 
  <%@ include file="/resources/include/login.jsp" %>
  
 <div id="article_c">
 
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>최근 게시판 목록</title>
</head>

<body>
  <!-- 최신 공지사항 목록보기 추가 시작 -->   
   <div id="indexGongji_wrap">
   <h3 class="indexGongji_title">최신 공지사항(5개)목록 보기</h3>
   <table id="indexGongji_t">
    <tr>
     <th>공지번호</th><th>공지제목</th> <th>등록날짜</th>     
    </tr>
    <c:forEach var="g" items="${gongji_List}">
      <tr>
       <td align="center">${g.gongji_no}</td>
       <td  width="100">
       <a href="index_GongjiCont.do?no=${g.gongji_no}"
       onfocus="this.blur();">${g.gongji_title}</a></td>
       <th width="120">${g.gongji_regdate}</th>
      </tr>
    </c:forEach>
   </table>
 </div>
 <!-- 최신 공지사항 목록보기 추가 끝 --> 
    <div class="clear"></div>
</div>
</body>
</html>
</div>

<div class="clear"></div>
<%@ include file="../../../../resources/include/footer.jsp" %>