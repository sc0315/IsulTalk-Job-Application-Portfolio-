

function goPopup(){
	var url = "jusoPopup";
	window.open(url,"pop","width=570,height=420, scrollbars=yes, resizable=yes"); 
}

function jusoCallBack(roadFullAddr,roadAddrPart1,addrDetail,roadAddrPart2,engAddr, jibunAddr, zipNo, admCd, rnMgtSn, bdMgtSn,detBdNmList,bdNm,bdKdcd,siNm,sggNm,emdNm,liNm,rn,udrtYn,buldMnnm,buldSlno,mtYn,lnbrMnnm,lnbrSlno,emdNo){
		document.form.roadFullAddr.value = roadFullAddr;
		document.form.addrDetail.value = addrDetail;
		document.form.zipNo.value = zipNo;	
}


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