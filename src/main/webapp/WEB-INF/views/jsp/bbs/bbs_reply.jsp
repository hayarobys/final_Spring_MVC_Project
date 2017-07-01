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
<title>답변글 폼</title>
</head>
<body>
  <div id="bReply_wrap">
    <h2 class="bReply_title">답변글 폼</h2>
    <form method="post" action="bbs_reply_ok.do" 
    onsubmit="return reply_check();">
     <input type="hidden" name="num" value="${bbscont.bbs_no}" />
     <input type="hidden" name="ref" value="${bbscont.bbs_ref}" />
     <input type="hidden" name="step" value="${bbscont.bbs_step}" />
     <input type="hidden" name="level" value="${bbscont.bbs_level}" />
     <input type="hidden" name="page" value="${page}" />
     
     <table id="bReply_t">
      <tr>
       <th>글쓴이</th>
       <td>
        <input name="bbs_name" id="bbs_name" size="14" class="box" />
       </td>
      </tr>
      
      <tr>
       <th>제목</th>
       <td>
        <input name="bbs_subject" id="bbs_subject" size="40" class="box"
        value="Re:${bbscont.bbs_subject}" />
       </td>
      </tr>
      
      <tr>
        <th>내용</th>
        <td>
        <textarea name="bbs_content" id="bbs_content" rows="5"
        cols="40" class="box"></textarea>
        </td>
      </tr>
      
      <tr>
       <th>비밀번호</th>
       <td>
       <input type="password" name="bbs_pass" id="bbs_pass"
       size="14" class="box" />
       </td>
      </tr>
     </table>
     <div id="bReply_menu">
     <input type="submit" value="답글" class="input_b" />
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