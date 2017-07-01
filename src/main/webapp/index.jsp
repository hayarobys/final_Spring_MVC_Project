<%@ page contentType="text/html; charset=UTF-8"%>
<!-- html  주석문기호. 위 부분은 jsp(java server pages)에서 지시자라고 한다. 즉   jsp 정의문 -->
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>메인 화면</title>

<!-- css 외부 포함파일을 불러오는 것. css는  디자인 UI를 작성 -->
<link rel="stylesheet" type="text/css" href="resources/css/main.css" />

<!-- jQuery 자바스크립트 라이브러리 외부 파일을 읽어온다 -->
<script src="resources/js/jquery.js"></script>
</head>

<body>
	<div id="main_wrap">
		<!-- 상단 header -->

		<div id="header">
			<div id="logo">
				<!-- 그림삽입 태그 -->
				<a href="index.jsp" onfocus="this.blur()"> 
					<img src="resources/images/logo.png" border="0" id="logo_img" />
				</a>
			</div>

			<div id="top_menu">
				<ul>	<!-- 각 메뉴마다 전담 컨트롤러를 따로 만들어 두었다. -->
					<li><a href="./gongji_list.do" onfocus="this.blur()">공지사항</a>
					<li><a href="./board_list.do" onfocus="this.blur()">게시판</a></li>
					<li><a href="./bbs_list.do" onfocus="this.blur()">자료실</a></li>
					<li><a href="./guest_list.do" onfocus="this.blur()">방명록</a></li>
					<li><a href="./admin_index.do" onfocus="this.blur()">관리자</a></li>
				</ul>
			</div>

			<div id="top_login">
				<ul>
					<li><a href="index.jsp" onfocus="this.blur()">홈</a></li> &nbsp;
					<!-- &nbsp;은 한칸 공백을 띄워준다 .-->
					<li><a href="./member_Login.do" onfocus="this.blur()">로그인</a></li>
					<li><a href="./member_Join.do" onfocus="this.blur()">회원가입</a></li>
				</ul>
			</div>
		</div>

		<div class="clear"></div>

		<!-- 본문 내용 -->
		<div id="article">
			<div id="left_menu">
				<script language="javascript">
					//자바스크립트는 클라이언트 스크립트 언어로서 c언어 문법을따라간다.
					function login_check() {//함수 정의
						if (window.document.f.id.value == "") {
							//자바스크립트 최상위 객체는  window이다.아무것도 입력하지 않았다면
							alert("아이디를 입력하세요!");//경고창 띄운다.
							f.id.focus();//포커스를 이동
							return false;
						}
						if ($.trim($("#pwd").val()) == "") {
							//$는 jQuery 이다.trim()을 사용하여 양쪽 공백을 제거
							alert("비번을 입력하세요!");
							$("#pwd").val("").focus();//초기화 하고 포커스 이동
							return false;
						}
					}
					/* 공지창 띄우기 */
					function pwd_find() { // 함수정의
						window.open("./pwd_Find.do", "비번찾기",
								"width=320px, height=250px, scrollbars=yes");
						// 자바스크립트에서  window 객체의 open() 메서드로 새로운 공지창을 만듬
						// open("공지창 파일경로", "공지창 이름", "공지창 속성")
						// 폭 320, 높이 250, 스크롤바가 생성되는 공지창을 만듬
					}
				</script>
				<form name="f" method="post" action="member_Login_ok.do"
					onsubmit="return login_check()">
					<!-- form태그는 사용자에서 입력한 정보를 서버로 보내는 태그이다.
        				 method=post방식으로 보내면 보안이 좋다.웹주소창에 노출이 안됨.
        				 action="서버파일주소"를 지정한다. -->
					<table id="login_t">
						<tr>
							<th>아이디</th>
							<!-- th태그는 열을 만들고 글자를 중앙 정렬,진하게 해준다. -->
							<td><input type="text" name="login_id" id="id"
								class="input_box" size="14" /></td><!-- text 는 한줄 입력필드를 만든다. -->
						</tr>

						<tr>
							<th>비밀번호</th>
							<td><input type="password" name="login_pwd" id="pwd"
								class="input_box" size="14" /></td>
						</tr>
					</table>
					<div id="login_menu">
						<input type="submit" value="로그인" class="input_b" /> 
						<input type="reset" value="취소" class="input_b" />
						<input type="button" value="비번찾기" class="input_b" onclick="pwd_find()" />
						<!-- 클릭이벤트를 발생해서   pwd_find()함수 호출 -->
					</div>
				</form>
			</div>

			<div id="article_c">
				<!-- 최신 공지사항 목록보기 메뉴 추가 시작 -->
				<table width="650" height="400">
					<tr>
						<td align="center">
							<a href="./index_GongjiList.do"> ★★ 최신	공지사항 보기 ★★ </a> <br><br> 
							<img src="resources/images/spring.jpg" width="470"><br>
									[그림] Spring MVC Flow
						</td>
					</tr>
				</table>
				<!-- 최신 공지사항 목록보기 메뉴 추가 끝 -->
			</div>
		</div>
		
		<div class="clear"></div>

		<!-- 하단 내용 -->
		<div id="footer">
			<h2 class="footer_title">서울시 마포구 대흥동. 
				TEL) 02-000-0000, FAX) 02-000-0000</h2>
		</div>
	</div>
</body>
</html>