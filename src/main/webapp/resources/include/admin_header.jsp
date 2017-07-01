<%@ page contentType="text/html; charset=UTF-8"%>
    <!-- html  주석문기호. 위 부분은 jsp(java server pages) 에서
    지시자라고 한다. 즉   jsp 정의문 -->
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>메인 화면</title>
<link rel="stylesheet" type="text/css" href="resources/css/main.css" />
<link rel="stylesheet" type="text/css" href="resources/css/member.css" />
<link rel="stylesheet" type="text/css" href="resources/css/guest.css" />
<link rel="stylesheet" type="text/css" href="resources/css/board.css" />
<link rel="stylesheet" type="text/css" href="resources/css/bbs.css" />
<link rel="stylesheet" type="text/css" href="resources/css/admin.css" />
<link rel="stylesheet" type="text/css" href="resources/css/admin_gongji.css" />
<link rel="stylesheet" type="text/css" href="resources/css/admin_member.css" />
<!-- css 외부 포함파일을 불러오는 것. css는  디자인 UI 를 작성 -->
<script src="resources/js/jquery.js"></script>
<script src="resources/js/member.js"></script>
<script src="resources/js/quest.js"></script>
<script src="resources/js/board.js"></script>
<script src="resources/js/bbs.js"></script>
<script src="resources/js/admin.js"></script>
<script src="resources/js/admin_gongji.js"></script>
</head>
<body>
 <div id="main_wrap">
  <!-- 상단 header -->
  
  <div id="header">
    <div id="logo">
     <a href="index.jsp" onfocus="this.blur()">
     <img src="resources/images/logo.jpg" border="0" id="logo_img"/></a>
     <!-- 그림삽입 태그 -->
    </div>
    
    <div id="top_menu">
     <ul>
      <li>
       <a href="./admin_Gongji_list.do"
         onfocus="this.blur()">
         <font color="green">공지사항</font></a>             
      <li>
      <a href="./admin_board_list.do"
         onfocus="this.blur()">
           <font color="green">게시판</font></a> 
      <li>
       <a href="./admin_bbs_list.do" 
         onfocus="this.blur()">
           <font color="green">자료실</font></a>
      <li>
        <a href="./admin_member_list.do" 
           onfocus="this.blur()">
           <font color="green">회원관리</font></a> 
       <li>
        <a href="./admin_chart_view.do" target="_blank"
           onfocus="this.blur()">
              <font color="green">게시물현황</font></a>           
      <!-- onfocus는 포커스를 가졌을때 발생하는 자바스크립트 
      사건처리기 즉 이벤트 핸들러 이다. 클릭시 사각점선을 사라지게 함 --> 
     </ul>
    </div>
    
    <div id="top_login">
     <ul>            
      <li><a href="./admin_main.do" 
      onfocus="this.blur()">관리자 홈</a></li>
      <li><a href="./admin_Logout.do"
      onfocus="this.blur()">로그아웃</a></li>
     </ul>    
    </div>
  </div>  