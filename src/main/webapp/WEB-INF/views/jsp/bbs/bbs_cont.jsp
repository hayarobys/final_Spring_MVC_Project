<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
<title>자료실 내용보기</title>
<link rel="stylesheet" type="text/css" href="./css/bbs.css" />
</head>
<body>
  <div id="bCont_wrap">
   <h2 class="bCont_title">자료실 내용</h2>
   <table id="bCont_t">
    <tr>
     <th>제목</th>
     <td>${bbscont.bbs_subject}</td>
    </tr>
    
    <tr>
     <th>글내용</th>
     <td>${bbs_content}</td>
    </tr>
    
    <c:if test="${!empty bbscont.bbs_file}">
    <tr>
     <th>첨부파일</th>
     <td>
     <!-- 첨부파일을 클릭하면 첨부파일의 내용이 보이게 링크 지정 -->
     <!-- <a href="./upload${bbscont.bbs_file}" onfocus="this.blur();"
     target="_blank">${bbscont.bbs_file}</a> -->
     
     <img src="./upload${bbscont.bbs_file}" width="150" height="130"
     border="0" />
     </td>
    </tr>
    </c:if>
   </table>
   <div id="bCont_menu">
    <input type="button" value="답변" class="input_b"
    onclick=
"location='bbs_cont.do?num=${bbscont.bbs_no}&state=reply&page=${page}'"/>
    <input type="button" value="수정" class="input_b"
    onclick=
"location='bbs_cont.do?num=${bbscont.bbs_no}&state=edit&page=${page}'" />
    <input type="button" value="삭제" class="input_b"
    onclick=
"location='bbs_cont.do?num=${bbscont.bbs_no}&state=delete&page=${page}'"/>
    <input type="button" value="목록" class="input_b"
    onclick="location='bbs_list.do?page=${page}'" />
   </div>
  </div>
</body>
</html>
</div>
</div>

<div class="clear"></div>
<%@ include file="/resources/include/footer.jsp" %>