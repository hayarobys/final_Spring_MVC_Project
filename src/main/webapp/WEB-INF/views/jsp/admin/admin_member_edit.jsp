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
 <!-- 본문 내용-->
 <div id="aMain_cont">
  <div id="edit_wrap2">
  <h2 class="edit_title">정보수정</h2>
  <form name="m" method="post" action="admin_member_edit_ok.do"
  onsubmit="return edit_check()">
  <input type="hidden" name="mem_id" value="${infoM.mem_id}" />
  <input type="hidden" name="page" value="${page}" />
   <table id="edit_t">
    <tr>
     <th>아이디</th> <td>${infoM.mem_id}</td>
    </tr>
    
    <tr>
     <th>비밀번호</th>
     <td>
     <input type="password" name="mem_pwd" id="mem_pwd" size="14"
     class="box" />
     </td>
    </tr>
    
    <tr>
     <th>비밀번호 확인</th>
     <td>
     <input type="password" name="mem_pwd2" id="mem_pwd2" size="14"
     class="box" />
     </td>
    </tr>
    
    <tr>
     <th>회원이름</th>
     <td>
      <input name="mem_name" id="mem_name" size="14" 
      value="${infoM.mem_name}" class="box" />
     </td>
    </tr>
    
    <tr>
     <th>우편번호</th>
     <td>
      <input name="mem_zip" id="mem_zip" size="3" maxlength="3"
      class="box" readonly value="${infoM.mem_zip}" />-<input 
      name="mem_zip2" id="mem_zip2" size="3" maxlength="3"
      class="box" readonly value="${infoM.mem_zip2}" />
      <input type="button"  value="우편번호검색" class="input_b"
      onclick="post_check()" />
     </td>
    </tr>
    
    <tr>
     <th>주소</th>
     <td>
      <input name="mem_addr" id="mem_addr" size="40" class="box"
      value="${infoM.mem_addr}" readonly />
     </td>
    </tr>
    
    <tr>
     <th>나머지주소</th>
     <td>
      <input name="mem_addr2" id="mem_addr2" size="30" class="box"
      value="${infoM.mem_addr2}" />
     </td>
    </tr>
    
    <tr>
     <th>휴대전화번호</th>
     <td>
     <%@ include file="../../../../resources/include/phone_number.jsp" %>
     <select name="mem_phone01" id="mem_phone01">
       <option value="">=선택=</option>
       <c:forEach var="p01" items="${phone}">
         <option value="${p01}" <c:if test="${phone01==p01}">
         selected </c:if>>${p01}</option>
       </c:forEach>
     </select>-<input name="mem_phone02" id="mem_phone02" size="4"
     maxlength="4" class="box" value="${phone02}" />-<input 
     name="mem_phone03" id="mem_phone03" size="4" maxlength="4"
     class="box" value="${phone03}" />
     </td>
    </tr>
    
    <tr>
      <th>전자우편</th>
      <td>
      <%@ include file="../../../../resources/include/mail_domain.jsp" %>
      <input name="mail_id" id="mail_id" size="10"
      class="box" value="${mail_id}" />@<input name="mail_domain"
      id="mail_domain" size="14" class="box" value="${mail_domain}"
      readonly />
      <select name="mail_list" onchange="domain_list()">
       <option value="">=선택=</option>
       <c:forEach var="m" items="${mail}">
        <option value="${m}" <c:if test="${mail_domain==m}">
        selected </c:if>>${m}</option>
       </c:forEach>
      </select>
      </td>
    </tr>
   </table>
   <div id="edit_menu">
    <input type="submit" value="수정" class="input_b" />
    <input type="reset" value="취소" class="input_b" 
    onclick="$('#mem_pwd').focus();" />
   </div>
  </form>
		</div>
    </div>
   </div>
  </div>
</div>

<div class="clear"></div>
<%@ include file="/resources/include/footer.jsp" %> 