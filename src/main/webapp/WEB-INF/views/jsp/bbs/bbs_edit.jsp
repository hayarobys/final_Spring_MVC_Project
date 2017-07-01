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
<title>자료실 수정</title>
</head>
<body>
 <div id="bWrite_wrap">
  <h2 class="bWrite_title">자료실 수정</h2>
  <form method="post" action="bbs_edit_ok.do"
   onsubmit="return write_check();" enctype="multipart/form-data">
   <!-- 그림,동영상 같은 이진파일을 서버에 업로드 할려면 enctype속성을
   꼭 지정해야 한다. -->
   <input type="hidden" name="bbs_no" value="${bbscont.bbs_no}" />
   <input type="hidden" name="page" value="${page}" />
   
   <table id="bWrite_t">
    <tr>
     <th>글쓴이</th>
     <td>
      <input name="bbs_name" id="bbs_name" size="14" class="box"
      value="${bbscont.bbs_name}" />
     </td>
    </tr>
    
    <tr>
     <th>글제목</th>
     <td>
      <input name="bbs_subject" id="bbs_subject" size="40" class="box"
      value="${bbscont.bbs_subject}" />
     </td>
    </tr>
    
    <tr>
     <th>비밀번호</th>
     <td>
      <input type="password" name="bbs_pass" id="bbs_pass" size="14"
      class="box" />
     </td>
    </tr>
    
    <tr>
     <th>글내용</th>
     <td>
      <textarea name="bbs_content"  id="bbs_content" rows="5"
      cols="40" class="box">${bbscont.bbs_content}</textarea>
     </td>
    </tr>
    
    <c:if test="${bbscont.bbs_step==0}">
    <tr>
     <th>파일첨부</th>
     <td>
      <input type="file" name="bbs_file" />
      <br/>
      <c:if test="${!empty bbscont.bbs_file}">
      ${bbscont.bbs_file}
      </c:if>      
     </td>
    </tr>
    </c:if>
    
   </table>
   <div id="bWrite_menu">
    <input type="submit" value="수정" class="input_b" />
    <input type="reset" value="취소" class="input_b" 
    onclick="$('#bbs_name').focus();" />
   </div>
  </form>
 </div>
</body>
</html>
</div>
</div>

<div class="clear"></div>
<%@ include file="/resources/include/footer.jsp" %>