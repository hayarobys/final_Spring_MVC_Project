/* admin.js */
function adminLogin_check(){
	if($.trim($("#admin_id").val())==""){
		alert("관리자 아이디를 입력하세요");
		$("#admin_id").val("").focus();
		return false;
	}
	if($.trim($("#admin_pwd").val())==""){
		alert("관리자 비번을 입력하세요");
		$("#admin_pwd").val("").focus();
		return false;
	}
}