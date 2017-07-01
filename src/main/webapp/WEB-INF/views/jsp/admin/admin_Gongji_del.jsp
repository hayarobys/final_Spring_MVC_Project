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
   <script>
     function  del_check(){
    	 if($.trim($("#gongji_pwd").val())==""){
    		 alert("삭제 비번을 입력하세요!");
    		 $("#gongji_pwd").val("").focus();
    		 return false;
    	 }
     }
   </script> 
   <div id="aMain_cont">    
     <div id="aGongji_del">
       <h2 class="aGdel_title">관리자 공지 삭제</h2>
       <form method="post" action="admin_Gongji_del_ok.do"
       onsubmit="return del_check();">
        <input type="hidden" name="gongji_no" value="${agcont.gongji_no}"/>
        <input type="hidden" name="page" value="${page}" />
        <table id="aGdel_t">
         <tr>
          <th>비밀번호</th>
          <td>
           <input type="password" name="gongji_pwd" id="gongji_pwd"
           size="14" class="admin_box" />
          </td>
         </tr>
        </table>
        <div id="aGdel_menu">
         <input type="submit" value="삭제" class="admin_b" />
         <input type="reset" value="취소" class="admin_b" 
         onclick="$('#gongji_pwd').focus();" />
        </div>
       </form>
     </div>
   </div>
  </div>
</div>

<div class="clear"></div>
<%@ include file="/resources/include/footer.jsp" %>     