<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    

<%@ include file="/resources/include/admin_header.jsp" %>
<!--  ../ 한 단계 상위 폴더로 이동, 외부 포함 파일을 읽어오는 jsp 형식  -->
<div class="clear"></div>
<!-- 본문 내용 -->
<div id="article"> 
  <%@ include file="/resources/include/admin_login.jsp" %>
 
 <div id="article_c"> 
    <!-- 본문 내용 -->
   <div id="aMain_cont">
     <div id="meminfo_wrap">
       <h2 class="meminfo_title">회원정보</h2>
       <table id="meminfo_t">
        <tr>
         <th>아이디</th> <td>${infoM.mem_id}</td>
        </tr>
        
        <tr>
         <th>비밀번호</th> <td>${infoM.mem_pwd}</td>
        </tr>
        
        <tr>
         <th>회원이름</th> <td>${infoM.mem_name}</td>
        </tr>
        
        <tr>
         <th>집주소</th>
         <td>
          ${infoM.mem_zip}-${infoM.mem_zip2}<br/>
          ${infoM.mem_addr}&nbsp;${infoM.mem_addr2}
         </td>
        </tr>
        
        <tr>
          <th>휴대전화</th> <td>${infoM.mem_phone}</td>
        </tr>
        
        <tr>
         <th>전자우편</th> <td>${infoM.mem_email}</td>
        </tr>
        
        <tr>
         <th>가입날짜</th> <td>${mem_regdate}</td>
        </tr>
        
        <tr>
         <th>탈퇴유무</th>
         <td>
          <c:if test="${infoM.mem_state==1}">
                  가입회원
          </c:if>
          <c:if test="${infoM.mem_state ==2}">
                  탈퇴회원
          </c:if>
         </td>
        </tr>
        
        <c:if test="${!empty del_cont}">
        <tr>
          <th>탈퇴사유</th>
          <td>
          ${del_cont}
          </td>
        </tr>
        </c:if>
        
        <c:if test="${!empty mem_deldate}">
        <tr>
         <th>탈퇴날짜</th>
         <td>
          ${mem_deldate}
         </td>
        </tr>
        </c:if>
        
       </table>
       <div id="meminfo_menu">
        <input type="button" value="회원목록" class="admin_b"
        onclick="location='admin_member_list.do?page=${page}'" />        
       </div>
     </div>
   </div>
 </div>
 </div>

<div class="clear"></div>
<%@ include file="/resources/include/footer.jsp" %>   
   