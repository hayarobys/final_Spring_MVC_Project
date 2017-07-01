<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/resources/include/header.jsp" %>
<div class="clear"></div>
<!-- 본문 내용 -->
<div id="article"> 
  <%@ include file="/resources/include/login.jsp" %>
  
 <div id="article_c">        
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>관리자 로그인 페이지</title>
</head>
<body>
  <div id="aIndex_wrap">
   <h2 class="aIndex_title">관리자 로그인</h2>
   <form method="post" action="admin_login_ok.do"
    onsubmit="return adminLogin_check();">
     <table id="aIndex_t">
      <tr>
       <th>관리자 아이디</th>
       <td>
        <input name="admin_id" id="admin_id" class="admin_box"
        size="14" />
       </td>
      </tr>
      
      <tr>
       <th>관리자 비번</th>
       <td>
        <input type="password" name="admin_pwd" id="admin_pwd"
        class="admin_box" size="14" />
       </td>
      </tr>
     </table>   
     
     <div id="aIndex_menu">
      <input type="submit" value="로그인"  class="admin_b" />
      <input type="reset" value="취소" class="admin_b" 
      onclick="$('#admin_id').focus();" />
     </div> 
   </form>    
  </div>
</body>
</html>
</div>
</div>

<div class="clear"></div>
<%@ include file="/resources/include/footer.jsp" %>