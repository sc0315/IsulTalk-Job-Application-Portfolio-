function send_SMS() {

	var phone = document.getElementById("phone").value;
	var randomNum = '';
	for( var i = 0; i < 4 ; i ++){
	 randomNum += Math.floor(Math.random() * 10)
	};
	document.form.phoneAuthorizationKey.value = randomNum;
	var phoneAuthorizationKey  = document.getElementById("phoneAuthorizationKey").value;
	console.log(phoneAuthorizationKey);
	console.log(phone);
	var theForm = document.getElementById("form");
	theForm.action = "smsSend";
	theForm.submit();
}


function send_email() {
	var email = document.getElementById("email").value;
	var randomNum = '';
	for( var i = 0; i < 6 ; i ++){
	 randomNum += Math.floor(Math.random() * 10)
	};
	document.form.authorizationKey.value = randomNum;
	var authorizationKey  = document.getElementById("authorizationKey").value;

	console.log(authorizationKey);
	console.log(email);
	var theForm = document.getElementById("form");
	theForm.action = "emailAuth";
	theForm.submit();
}

//------------------ 문자인증/이메일인증/성별테이블 넣어야함
//회원가입 필수요건
function join(){
	var id = document.getElementById("id");
	var pwd = document.getElementById("password");
	var pwdCheck = document.getElementById("pwdCheck");
	var name = document.getElementById("name");
	var address1 = document.getElementById("address1");
	var zipCode = document.getElementById("zipCode");
	var flexCheckDefault = document.getElementById("flexCheckDefault");
   //6~16 대문자, 소문자, 숫자 각 1개 정규표현식
    var pwdregex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d).{8,14}$/;
	
	if(id.value == ""){
		alert("아이디를 입력하세요.");
		id.focus();
		return false;
	} else if (!idCheck) {
	    alert("아이디 중복 체크를 해주세요.");
	    return false;
	} else if (pwd.value == "") {
	    alert("비밀번호를 입력해 주세요.");
	    pwd.focus();
	    return false;
	} else if (pwd.value != pwdCheck.value) {
	    alert("비밀번호가 일치하지 않습니다.")
	    pwd.focus();
	    return false;
    } else if (!pwdregex.test(pwd.value)) {
        alert("비밀번호는 8~14자 영문 대문자, 소문자, 숫자를 각 1개 이상 포함해야 합니다.");
        pwd.focus();
        return false;
	} else if (authorizationKey != emailCheck.value){
		alert("인증번호가 일치하지 않습니다.");
		return false;
	} else if (name.value == "") {
	    alert("이름을 입력해 주세요.");
	    name.focus();
	    return false;
	} else if (zipCode.value == "") {
	    alert("우편번호 찾기를 눌러주세요.");
	    zipCode.focus();
	    return false;
	} else if (address1.value == "") {
	    alert("주소를 입력해 주세요.");
	    address1.focus();
	    return false;
	} else if (!flexCheckDefault.checked){
		alert("약관에 동의해 주세요.");
		return false;
	} else{
		alert("회원가입 되었습니다.")
		var theForm = document.getElementById("join");
		theForm.action="join";
		theForm.submit();
	}
}


