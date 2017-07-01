<%@ page contentType="text/html; charset=UTF-8"%>
    <!-- html  주석문기호. 위 부분은 jsp(java server pages) 에서
    지시자라고 한다. 즉   jsp 정의문 -->
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>메인 화면</title>
<link rel="stylesheet" type="text/css" href="resources/css/main.css" />
<link rel="stylesheet" type="text/css" href="resources/css/guest.css" />
<link rel="stylesheet" type="text/css" href="resources/css/member.css" />
<link rel="stylesheet" type="text/css" href="resources/css/board.css" />
<link rel="stylesheet" type="text/css" href="resources/css/bbs.css" />
<link rel="stylesheet" type="text/css" href="resources/css/admin.css" />
<link rel="stylesheet" type="text/css" href="resources/css/user.css" />
<link rel="stylesheet" type="text/css" href="resources/css/gongji.css" />

<!-- css 외부 포함파일을 불러오는 것. css는  디자인 UI 를 작성 -->
<script src="resources/js/jquery.js"></script>
<script src="resources/js/guest.js"></script>
<script src="resources/js/member.js"></script>
<script src="resources/js/board.js"></script>
<script src="resources/js/bbs.js"></script>
<script src="resources/js/admin.js"></script>
<!-- jQuery 자바스크립트 라이브러리 외부 파일을 읽어온다 -->
</head>
<body>
 <div id="main_wrap">
  <!-- 상단 header -->
  
  <div id="header">
    <div id="logo">
     <a href="index.jsp" onfocus="this.blur()">
     <img src="resources/images/logo.png" border="0" id="logo_img"/></a>
     <!-- 그림삽입 태그 -->
    </div>
    
    <div id="top_menu">
     <ul>
      <li>
      <a href="./gongji_list.do" 
          onfocus="this.blur()">공지사항</a></li> 
      <li>
      <a href="./board_list.do"
         onfocus="this.blur()">게시판</a></li>
      <li>
       <a href="./bbs_list.do" 
         onfocus="this.blur()">자료실</a></li>
      <li>   
        <a href="./guest_list.do" 
         onfocus="this.blur()">방명록</a></li>
      <li>   
        <a href="./admin_index.do" 
         onfocus="this.blur()">관리자</a></li>       
     </ul>
    </div>
    
    <div id="top_login">
     <ul>
      <li><a href="./index.jsp" onfocus="this.blur()">홈</a></li>
       &nbsp;
      <!-- &nbsp;은 한칸 공백을 띄워준다 .-->
      <li><a href="./member_Login.do" 
      onfocus="this.blur()">로그인</a></li>
      <li><a href="./member_Join.do"
      onfocus="this.blur()">회원가입</a></li>
     </ul>    
    </div>
  </div>
  
  