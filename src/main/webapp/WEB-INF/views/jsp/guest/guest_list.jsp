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
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>방명록 목록</title>
</head>

<body>
 <div id="gList_wrap">
  <h2 class="gList_title">방명록 목록</h2>
  <div id="gList_count">총 게시물 수: ${total_count} 개</div>
  <table id="gList_t">
   <tr>
        <td height="26" class="no">번호</td>
	    <td class="title">제목</td>
		<td class="writer">작성자</td>
		<td class="date">날짜</td>
		<td class="hit">조회수</td>
   </tr>
   <c:forEach var="gList" items="${guestList}">
     <tr>
      <th>${gList.guest_no}</th>
      <td style="padding-left:10px;">
       <a href="guest_cont.do?no=${gList.guest_no}" 
       onfocus="this.blur();">${gList.guest_title}</a>
       <!-- *.do?no=번호값은 웹주소창에 값을 노출하면서 get방식으로
       no피라미터에 글번호를 담아서 넘김. -->
      </td>
      <th>${gList.guest_name}</th>
      <th>${gList.guest_regdate}</th>
      <th>${gList.guest_hit}</th>
     </tr>
   </c:forEach>
  </table>
  <div id="gList_menu">
  <input type="button" value="글쓰기" class="input_b"
  onclick="location='guest_write.do'" />
  </div> 
  </div>
</body>
</html>
</div>
</div>

<div class="clear"></div>
<%@ include file="/resources/include/footer.jsp" %>

