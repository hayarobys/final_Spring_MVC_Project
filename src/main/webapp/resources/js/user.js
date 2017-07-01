/* guest.js */
function user_check(){
	if($.trim($("#user_id").val())==""){
		alert("아이디를 입력하세요!");
		$("#user_id").val("").focus();
		return false;
	}
	if($.trim($("#user_pwd").val())==""){
		alert("비밀번호를 입력하세요!");
		$("#user_pwd").val("").focus();
		return false;
	}
	if($.trim($("#user_name").val())==""){
		alert("이름을 입력하세요!");
		$("#user_name").val("").focus();
		return false;
	}
}




