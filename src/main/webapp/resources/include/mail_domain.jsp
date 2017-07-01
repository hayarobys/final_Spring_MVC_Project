<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
   // 메일의 종류를 스트링 배열에 초기홯나다.
   String[] mail={"naver.com", "daum.net", "nate.com",
		          "gmail.com","직접입력"};
   request.setAttribute("mail",mail);
   // "mail"키에 mail 배열의 값을 저장한다.
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>mail_domain.jsp</title>
</head>
<body>

</body>
</html>