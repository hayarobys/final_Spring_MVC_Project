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
<title>자료실 삭제</title>
<script>
  function del_check(){
	  if($.trim($("#bbs_pass").val())==""){
		  alert("비밀번호를 입력하세요!");
		  $("#bbs_pass").val("").focus();
		  return false;
	  }
  }
</script>
<link rel="stylesheet" type="text/css" href="./css/bbs.css" />
</head>
<body>
 <div id="bDelete_wrap">
  <h2 class="bDelete_title">자료실 삭제</h2>
  <form method="post" action="bbs_delete_ok.do" 
     onsubmit="return del_check();">
     <input type="hidden" name="bbs_no" value="${bbscont.bbs_no}" />
     <input type="hidden" name="page" value="${page}" />
     <table id="bDelete_t">
      <tr>
       <th>비밀번호</th>
       <td>
        <input type="password" name="bbs_pass" id="bbs_pass" size="14"
        class="box" />
       </td>
      </tr>
     </table>
     <div id="bDelete_menu">
     <input type="submit" value="삭제" class="input_b" />
     <input type="reset" value="취소" class="input_b"
     onclick="$('#bbs_pass').focus();" />
     </div>
  </form>     
 </div>
</body>
</html>
</div>
</div>

<div class="clear"></div>
<%@ include file="/resources/include/footer.jsp" %>