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
<title>방명록 수정</title>
<script src="./js/jquery.js"></script>
<script src="./js/guest.js"></script>
<link rel="stylesheet" type="text/css" href="./css/guest.css" />
</head>
<body>
 <div id="gEdit_wrap">
  <h2 class="gEdit_title">방명록 수정폼</h2>
  <form method="post" action="guest_edit_ok.do"
  onsubmit="return write_check();">
   <input type="hidden" name="no" value="${bean.guest_no}" />
   <table id="gEdit_t">
    <tr>
     <th>작성자</th>
     <td>
      <input name="guest_name" id="guest_name" size="14" 
      class="box" value="${bean.guest_name}" />
     </td>
    </tr>
    
    <tr>
     <th>제목</th>
     <td>
      <input name="guest_title" id="guest_title" size="30"
      class="box" value="${bean.guest_title}" />
     </td>
    </tr>
    
    <tr>
     <th>글내용</th>
     <td>
      <textarea name="guest_cont" id="guest_cont" rows="5"
      cols="35" class="box" >${bean.guest_cont}</textarea>
     </td>
    </tr>
    
    <tr>
     <th>비밀번호</th>
     <td>
      <input type="password" name="guest_pwd" id="guest_pwd"
      size="14" class="box" />
     </td>
    </tr>
   </table>
   <div id="gEdit_menu">
    <input type="submit" value="수정" class="input_b" />
    <input type="reset" value="취소" class="input_b"
    onclick="$('#guest_name').focus();" />
   </div>
  </form>
 </div>
</body>
</html>

</div>
</div>

<div class="clear"></div>
<%@ include file="/resources/include/footer.jsp" %>