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
<title>게시물 내용보기</title>
<link rel="stylesheet" type="text/css" href="./css/board.css" />
</head>
<body>
  <div id="dCont_wrap">
   <h2 class="dCont_title">게시물 내용</h2>
   <table id="dCont_t">
    <tr>
     <th>글제목</th>
     <td>${bcont.board_title}</td>
    </tr>
    
    <tr>
     <th>글내용</th>
     <td>${board_cont}</td>
    </tr>
    
    <tr>
     <th>조회수</th>
     <td>${bcont.board_hit}</td>
    </tr>
   </table>
   <div id="dCont_menu">
   <input type="button" value="답변" class="input_b"
onclick="location='board_cont.do?num=${bcont.board_no}&page=${page}&state=reply'" />
   <input type="button" value="수정" class="input_b"
onclick="location='board_cont.do?num=${bcont.board_no}&page=${page}&state=edit'" />
   <input type="button" value="삭제" class="input_b"
onclick="location='board_cont.do?num=${bcont.board_no}&page=${page}&state=del'" />
   <input type="button" value="목록" class="input_b"
onclick="location='board_list.do?page=${page}'" />             
   </div>
  </div>
</body>
</html>

</div>
</div>

<div class="clear"></div>
<%@ include file="/resources/include/footer.jsp" %>







