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
     <div id="aGongji_edit">
		<h2 class="aGedit_title">관리자 자료실 수정</h2>
		<form method="post" action="admin_bbs_edit_ok.do"
			onsubmit="return write_check();" enctype="multipart/form-data">
			<!-- 그림,동영상 같은 이진파일을 서버에 업로드 할려면 enctype속성을
   꼭 지정해야 한다. -->
			<input type="hidden" name="bbs_no" value="${abbs_cont.bbs_no}" /> <input
				type="hidden" name="page" value="${page}" />

			<table id="bEdit_t">
				<tr>
					<th>글쓴이</th>
					<td><input name="bbs_name" id="bbs_name" size="14" class="box"
						value="${abbs_cont.bbs_name}" /></td>
				</tr>

				<tr>
					<th>글제목</th>
					<td><input name="bbs_subject" id="bbs_subject" size="40"
						class="box" value="${abbs_cont.bbs_subject}" /></td>
				</tr>

				<tr>
					<th>비밀번호</th>
					<td><input type="password" name="bbs_pass" id="bbs_pass"
						size="14" class="box" /></td>
				</tr>

				<tr>
					<th>글내용</th>
					<td><textarea name="bbs_content" id="bbs_content" rows="5"
							cols="40" class="box">${abbs_cont.bbs_content}</textarea></td>
				</tr>

				<c:if test="${abbs_cont.bbs_step==0}">
					<tr>
						<th>파일첨부</th>
						<td><input type="file" name="bbs_file" /> <br /> 
					<c:if
								test="${!empty abbs_cont.bbs_file}">
								      ${abbs_cont.bbs_file}
                    </c:if></td>
					</tr>
				</c:if>

			</table>
			<div id="dEdit_menu">
				<input type="submit" value="수정" class="input_b" /> <input
					type="reset" value="취소" class="input_b"
					onclick="$('#bbs_name').focus();" />
			</div>
		</form>
	</div>
</div>
 </div>
</div>

<div class="clear"></div>
<%@ include file="/resources/include/footer.jsp" %>

