<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/resources/include/admin_header.jsp" %>
<!--  ../ 한 단계 상위 폴더로 이동, 외부 포함 파일을 읽어오는 jsp 형식  -->
<div class="clear"></div>
<!-- 본문 내용 -->
<div id="article"> 
  <%@ include file="/resources/include/admin_login.jsp" %>
  
 <div id="article_c"> 
   <!-- 본문 내용 -->
   <div id="aMain_cont">
    <div id="aGongji_write">
     <h2 class="aGongjiw_title">관리자 공지사항</h2>
     <form method="post" action="admin_Gongji_write_ok.do"
     onsubmit="return aGongji_write();">
       <table id="aGongjiw_t">
        <tr>
         <th>작성자</th>
         <td>
          <input name="gongji_name" id="gongji_name" 
          size="14" class="admin_box" />
         </td>
        </tr>
        
        <tr>
         <th>공지제목</th>
         <td>
          <input name="gongji_title" id="gongji_title" size="40"
          class="admin_box" />
         </td>
        </tr>
        
        <tr>
         <th>공지내용</th>
         <td>
          <textarea name="gongji_cont" id="gongji_cont" rows="5"
          cols="35" class="admin_box"></textarea>
         </td>        
        </tr>
        
        <tr>
         <th>비밀번호</th>
         <td>
         <input type="password" name="gongji_pwd" id="gongji_pwd"
         size="14" class="admin_box" />
         </td>
        </tr>
       </table>
       
       <div id="aGongjiw_menu">
        <input type="submit" value="저장" class="admin_b" />
        <input type="reset" value="취소" class="admin_b"
        onclick="$('#gongji_name').focus();" />
       </div>
     </form>  
     </div>  
   </div>
 </div>
</div>

<div class="clear"></div>
<%@ include file="/resources/include/footer.jsp" %>

