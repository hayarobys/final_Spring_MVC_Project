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
		<h2 class="aGongjiw_title">관리자 자료실 입력</h2>
		<form method="post" action="admin_bbs_write_ok.do"
			onsubmit="return write_check()" enctype="multipart/form-data">
			<!-- 
   그림,동영상 같은 이진파일을 서버에 업로드 할려면 반드시 form태그내에
   enctype="multipart/form-data"를 지정해야 한다.
   -->
			<table id="bWrite_t">
				<tr>
					<th>글쓴이</th>
					<td><input name="bbs_name" id="bbs_name" size="14" class="box" />
					</td>
				</tr>

				<tr>
					<th>제목</th>
					<td><input name="bbs_subject" id="bbs_subject" size="40"
						class="box" /></td>
				</tr>

				<tr>
					<th>비밀번호</th>
					<td><input type="password" name="bbs_pass" id="bbs_pass"
						size="14" class="box" /></td>
				</tr>

				<tr>
					<th>글내용</th>
					<td><textarea name="bbs_content" id="bbs_content" rows="5"
							cols="40" class="box"></textarea></td>
				</tr>

				<tr>
					<th>파일첨부</th>
					<td><input type="file" name="bbs_file" /></td>
				</tr>
			</table>
			<div id="bWrite_menu">
				<input type="submit" value="저장" class="input_b" /> <input
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

