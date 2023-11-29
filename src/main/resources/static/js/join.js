/*
 * 전화번호 전송 
 */ 
var storedphoneAuthorizationKey; // 인증코드 전역변수
function send_SMS() {

	var phone = $("#phone").val();
	var randomNum = '';
	for( var i = 0; i < 4 ; i ++){
	 randomNum += Math.floor(Math.random() * 10)
	};
	 $("#phoneAuthorizationKey").val(randomNum);
	
	var phoneAuthorizationKey  = document.getElementById("phoneAuthorizationKey").value;
	
	console.log(phoneAuthorizationKey);
	console.log(phone);
	
	    $.ajax({
        type: "post",
        url: "smsSend",
        data: {
            phone: phone,
            phoneAuthorizationKey: phoneAuthorizationKey
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
function send_email() {
    var email = $("#email").val();
    var randomNum = '';
    for (var i = 0; i < 6; i++) {
        randomNum += Math.floor(Math.random() * 10)
    }

    $("#authorizationKey").val(randomNum);

    var authorizationKey = $("#authorizationKey").val();

    console.log(authorizationKey);
    console.log(email);

    $.ajax({
        type: "post",
        url: "emailAuth",
        data: {
            email: email,
            authorizationKey: authorizationKey
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
 *아이디 중복확인
*/ 
var idCheck = false; // 아이디체크 전역변수
function id_Check() {
    	// 아이디 불러오기
    	var id = document.getElementById('id').value;
   		
       // 6~12 소문자, 숫자 정규표현식
        var idregex = /^[a-z0-9]{6,12}$/;
       
        if(!idregex.test(id)){
			result = "6~12자 영문 소문자, 숫자만 사용해주세요."
			$("#result_id").html(result).css("color", "red");
    		return false;
		   }
        $.ajax({
            type:'post',
            url:"idCheck",
            data: {"id":id}, //JSON 형태로 DATA 전송
            success: function(data){ 
             
             	if(data == "1"){ 
                    result = "사용 가능한 아이디입니다.";
                    $("#result_id").html(result).css("color", "green");
                    idCheck = true;
                    $("#password").trigger("focus");
                 
          	   }else if(data == "-1"){
					 result="이미 사용중인 아이디입니다.";
                     $("#result_id").html(result).css("color","red");
                     $("#id").val('');
                     idCheck = false;
             }
			 }
        
        }); 
}

/*
 * 주소 API 
 */ 
 function execDaumPostcode() {
        new daum.Postcode({
            oncomplete: function(data) {
                // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

                // 각 주소의 노출 규칙에 따라 주소를 조합한다.
                // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
                var addr = ''; // 주소 변수
                var extraAddr = ''; // 참고항목 변수

                //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
                if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                    addr = data.roadAddress;
                } else { // 사용자가 지번 주소를 선택했을 경우(J)
                    addr = data.jibunAddress;
                }

                // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
                if(data.userSelectedType === 'R'){
                    // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                    // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                    if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                        extraAddr += data.bname;
                    }
                    // 건물명이 있고, 공동주택일 경우 추가한다.
                    if(data.buildingName !== '' && data.apartment === 'Y'){
                        extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                    }
                    // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                    if(extraAddr !== ''){
                        extraAddr = ' (' + extraAddr + ')';
                    }
                    // 조합된 참고항목을 해당 필드에 넣는다.
                    document.getElementById("sample6_extraAddress").value = extraAddr;
                
                } else {
                    document.getElementById("sample6_extraAddress").value = '';
                }

                // 우편번호와 주소 정보를 해당 필드에 넣는다.
                document.getElementById('zipCode').value = data.zonecode;
                document.getElementById("address1").value = addr;
                // 커서를 상세주소 필드로 이동한다.
                document.getElementById("address2").focus();
            }
        }).open();
    }

/*
 *  회원가입 필수요건
 */ 
function join() {
    var id = document.getElementById("id");
    var pwd = document.getElementById("password");
    var pwdCheck = document.getElementById("pwdCheck");
    var name = document.getElementById("name");
    var address1 = document.getElementById("address1");
    var zipCode = document.getElementById("zipCode");
    var authorizationKey = $("#authorizationKey").val();
		storedAuthorizationKey = authorizationKey; // 인증코드를 전역변수에 저장
	var phoneAuthorizationKey = $("#phoneAuthorizationKey").val();
		storedphoneAuthorizationKey = phoneAuthorizationKey; // 인증코드를 전역변수에 저장
    var flexCheckDefault = document.getElementById("flexCheckDefault");
    // 6~16 대문자, 소문자, 숫자 각 1개 정규표현식
    var pwdregex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d).{8,14}$/;

    if (id.value == "") {
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
    } /*else if (emailCheck.value != storedAuthorizationKey) {
        alert("이메일 인증번호가 일치하지 않습니다.");
        emailCheck.focus(); 
        return false;
    }*/ else if (name.value == "") {
        alert("이름을 입력해 주세요.");
        name.focus();
        return false;
    } /*else if (phoneCheck.value != storedphoneAuthorizationKey) {
        alert("문자 인증번호가 일치하지 않습니다.");
        phoneCheck.focus(); 
        return false;
    } */else if (zipCode.value == "") {
        alert("우편번호 찾기를 눌러주세요.");
        zipCode.focus();
        return false;
    } else if (address1.value == "") {
        alert("주소를 입력해 주세요.");
        address1.focus();
        return false;
    } else if (!flexCheckDefault.checked) {
        alert("약관에 동의해 주세요.");
        return false;
    } else if ((name.value || address2.value).length > 20) {
        alert("20자 이내로 적어주세요.");
        return false;
    } else {
        alert("회원가입 되었습니다.")
        var theForm = document.getElementById("join");
        theForm.action = "join";
        theForm.submit();
    }
}