<%@ page contentType="text/html; charset=UTF-8"%>
    <!-- html  주석문기호. 위 부분은 jsp(java server pages) 에서
    지시자라고 한다. 즉   jsp 정의문 -->
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>메인 화면</title>

<%@ include file="/resources/include/mem_header.jsp" %>

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
  
  <div class="clear"></div>
  
    <!-- 본문 내용 -->
    <div id="article">
     <div id="left_menu">      
            <!-- 본문내용에 로그인 환영 메시지 삽입 -->
       <div id="aMain_Login">             
         <form method="post" action="admin_Logout.do">
           <table id="aLogin_t">
             <tr>
              <td  align="center">
                 <b>${admin_id}관리자 님 로그인을 환영합니다.</b></td>
             </tr>
             <tr> <td> &nbsp;</td> </tr>
             <tr> 
              <td align="center">
                <input type="submit" value="로그아웃" class="admin_b"/>
              </td>
             </tr>
           </table>
         </form>      
       </div>
     </div>
     
     <div id="article_c">
       <p class="article_cont">
       <!-- 최신 공지사항 목록보기 메뉴 추가 시작 -->
       <table width="650">          
        
          <tr>
            <td align="center">             
             <img src="resources/images/spring.jpg" width="500">
             <br> [그림] Spring MVC Flow             
            </td>
          <tr>
       </table>       
       <!-- 최신 공지사항 목록보기 메뉴 추가 끝 -->      
      
     </div>
    </div>
    
    <div class="clear"></div>
    
    <!-- 하단 내용 -->
    <div id="footer">
    <h2 class="footer_title">
     서울특별시 용산구  디지털단지 에이스 7차 빌딩 702호. TEL) 02-000-0000,
  FAX) 02-000-0000   
    </h2>
    </div>
 </div>
</body>
</html>