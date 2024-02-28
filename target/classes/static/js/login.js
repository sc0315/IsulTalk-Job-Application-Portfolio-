 
 /*
  *	아이디 쿠키 생성,삭제 
  */
document.addEventListener("DOMContentLoaded", function() {
    var key = getCookie("key");  // 쿠키 저장해서 id칸에 넣어줌
    var idInput = document.getElementById("id");
    idInput.value = key;

    if (idInput.value !== "") {  // id칸이 채워져 있다면 체크박스를 체크 상태로 두기
        document.getElementById("check").checked = true;
    }

    // 체크박스에 변화가 있거나 체크박스 체크되어있을때 쿠키 생성
    document.getElementById("check").addEventListener("change", function() {
        if (document.getElementById("check").checked) {
            setCookie("key", idInput.value, 7); // 7일동안 쿠키 보관
        } else {   
            deleteCookie("key");
        }
    });

    // 아이디저장 체크한 상태에서 id 입력하는 경우 쿠키 생성
    idInput.addEventListener("keyup", function() {
        if (document.getElementById("check").checked) {
            setCookie("key", idInput.value, 7);
        }
    });
});
	
	// 쿠키 수정
   function setCookie(cookieName, value, exdays) {
       var exdate = new Date();
       exdate.setDate(exdate.getDate() + exdays);
       var cookieValue = escape(value) + ((exdays == null) ? "" : "; expires=" + exdate.toGMTString());
       document.cookie = cookieName + "=" + cookieValue;
   }
	
	// 쿠키 삭제
   function deleteCookie(cookieName) {
       var expireDate = new Date();
       expireDate.setDate(expireDate.getDate() - 1);
       document.cookie = cookieName + "= " + "; expires=" + expireDate.toGMTString();
   }
   
	// 쿠키 불러오기
   function getCookie(cookieName) {
       cookieName = cookieName + '=';
       var cookieData = document.cookie;
       var start = cookieData.indexOf(cookieName);
       var cookieValue = '';
       if (start != -1) {
           start += cookieName.length;
           var end = cookieData.indexOf(';', start);
           if (end == -1) end = cookieData.length;
           cookieValue = cookieData.substring(start, end);
       }
       return unescape(cookieValue);
   }
   
 /*
 * 전화번호 전송 
 */ 
var storedfindphoneAuthorizationKey; // 인증코드 전역변수
function send_phone() {
	
	var phone = $("#find_phone").val();
	var randomNum = '';
	for( var i = 0; i < 4 ; i ++){
	 randomNum += Math.floor(Math.random() * 10)
	};
	 $("#findphoneAuthorizationKey").val(randomNum);
	
	var phoneAuthorizationKey  = document.getElementById("findphoneAuthorizationKey").value;
	
	console.log(phoneAuthorizationKey);
	console.log(phone);
	
	    $.ajax({
        type: "post",
        url: "findsmsSend",
        data: {
            find_phone: phone,
            findphoneAuthorizationKey: phoneAuthorizationKey
        },
        success: function (data) {
            console.log("success: " + data);
        },
        error: function (error) {
            console.error("Error: " + error);
        }
    });
    alert('인증번호가 전송되었습니다.');
}

/*
 * 이메일 전송
 */
var storedAuthorizationKey; // 인증코드 전역변수
function find_send_email() {
    var email = $("#find_email").val();
    var charSet = ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
                'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'];

    var str = "";

    // 문자 배열 길이의 값을 랜덤으로 10개를 뽑아 구문을 작성함
    var idx = 0;
    for (var i = 0; i < 10; i++) {
        idx = Math.floor(Math.random() * charSet.length);
        str += charSet[idx];
    }

    $("#findauthorizationKey").val(str);

    var authorizationKey = $("#findauthorizationKey").val();

    console.log(authorizationKey);
    console.log(email);

    $.ajax({
        type: "post",
        url: "findEmailAuth",
        data: {
            find_email: email,
            findauthorizationKey: authorizationKey
        },
        success: function (data) {
            console.log("success: " + data);
            if(data=="success"){
				window.location.href="/"
			} else {
				window.location.href="/"
			}
        },
        error: function (error) {
            console.error("Error: " + error);
        }
    });
    alert('임시 비밀번호가 전송되었습니다.');
}
function find_id_phone(){
	var find_phoneCheck = document.getElementById("find_phoneCheck");
	var findphoneAuthorizationKey = $("#findphoneAuthorizationKey").val();
		storedfindphoneAuthorizationKey = findphoneAuthorizationKey; // 인증코드를 전역변수에 저장
		
	if(find_phoneCheck.value == ''){
		alert("인증번호를 입력하세요.");
		find_phoneCheck.focus();
		return false;
		
	} else if(find_phoneCheck.value != storedfindphoneAuthorizationKey){
		alert("문자 인증번호가 일치하지 않습니다.");
		find_phoneCheck.focus();
		return false;
		
	} else {
		true;
	}
}

