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
<title>메인 화면</title>
 
 <body>  

  <!-- 최신 공지사항 내용보기 추가 시작 -->   
   <div id="iGcont_wrap">
   <h2 class="iGcont_title">최신 공지사항 내용보기</h2>
   <table id="iGcont_t">
    <tr>
     <th>공지제목</th>
     <td>${iGont.gongji_title}</td>
    </tr>
    <tr>
     <th>공지내용</th>
     <td>
      ${igongjiCont}
     </td>
    </tr>
    <tr>
      <th>조회수</th>
      <td>${iGont.gongji_hit}</td>
    </tr>
   </table>
   <div id="iGcont_menu">
    <input type="button" value="목록" class="gbutton_b" 
    onclick="location='index_GongjiList.do'"/>
   </div>
    <!-- 최신 공지사항 내용보기 추가 끝 --> 
    <div class="clear"></div>
</div>
</body>
</html>
</div>
</div>
<div class="clear"></div>
<%@ include file="/resources/include/footer.jsp" %>