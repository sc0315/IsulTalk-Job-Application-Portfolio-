
	
//채팅목록	페이지
	function chatView() {
		$.ajax({
		    url : "chatList",
		    dataType : "html",
		    type : "get",  
		    success : function(result){
		        $("#friendList").html(result);
		    }
		});
	}
	
	
//친구목록 페이지
	function friendView() {
		
			$.ajax({
			    url : "friendList",
			    dataType : "html",
			    type : "get",  
			    success : function(result){
			        $("#friendList").html(result);
			    }
			});
	}


// 친구목록 클릭시 프로필 불러오기
	function goFriendProfile(event) {
	var clickedProfile = event.currentTarget;
	var pfId = clickedProfile.querySelector(".profileId").value;
	$.ajax({	
			    url : "friendProfile",
			    dataType : "html",
			    type : "get", 
			     data: { profileId : pfId },
			    success : function(result){
			        $("#chat").html(result);
			    }
				});		
	}
	
	
// 내프로필 불러오기
function goMyFriendProfile() {
	var pfId = document.getElementById("myProfileId").value;
	$.ajax({	
			    url : "friendProfile",
			    dataType : "html",
			    type : "get", 
			    data: { profileId : pfId },
			    success : function(result){
			        $("#chat").html(result);
			    }
				});		
	}					


//친구찾기 페이지
	function searchMemberView() {
			$.ajax({
		    url : "searchMember",
		    dataType : "html",
		    type : "get",  
		    success : function(result){
		        $("#chat").html(result);
		    }
			});
		
	}	
	
	
//정보변경 페이지
	function modifyMyInfo() {
			$.ajax({
		    url : " modifyMyInfo",
		    dataType : "html",
		    type : "get",  
		    success : function(result){
		        $("#chat").html(result);
		    }
			});
		
	}	
		
	/*----------------채팅--*/
	var ws;
	window.onload = function(){
		getRoom();
		createRoom();
	}
	

	function commonAjax(url, parameter, type, callbak, contentType){
		$.ajax({
			url: url,
			data: parameter,
			type: type,
			contentType : contentType!=null?contentType:'application/x-www-form-urlencoded; charset=UTF-8',
			success: function (res) {
				callbak(res);
			},
			error : function(err){
				console.log('error');
				callbak(err);
			}
		});
	}
	
	
	

	
function wsEvt() {
	ws.onopen = function(data){ //소켓이 열리면 초기화 세팅하기
	console.log(document.getElementById("sessionId").value);
	}
	ws.onmessage = function(data) { //메시지를 받으면 처리하는 내용
		var msg = data.data;
		if(msg != null && msg.trim() != ''){
			var rcv_data = '';
			var rcv_data = JSON.parse(msg);
			console.log('receive : ', rcv_data);
				if (rcv_data.type == "message"){
					if (rcv_data.sessionId == document.getElementById("sessionId").value) {
						var wrap = document.getElementById("wrap");
						var tableHTML = "<table class='myMessageBox'>" +
							"<tr>" +
						    "<td></td>" +
						    "<td class='myNick'>" +
						    "<span> &nbsp; &nbsp; &nbsp;</span>" +
						    "<span>" + rcv_data.chatProfile.nick_name + "</span>" +
						    "</td>" +
						    "<td rowspan='2'>" +
						    "<div class='friendProfileImageWrap'>" +
						    "<img class='friendProfileImage' src='" + rcv_data.chatProfile.profile_img + "'/>" +
						    "</div>" +
						    "</td>" +
						    "</tr>" +
						    "<tr>" +
						    "<td class='timeAlign'>" +
						    "<span class='chatTime'>" + rcv_data.currentDate + "</span>" +
						    "</td>" +
							"<td class='chatting ch2'>" +
						    "<div class='textbox'>" + rcv_data.msg + "</div>" +
						    "</td>" +
						    "</tr>" +
						    "</table>";
						    wrap.innerHTML += tableHTML;
						    var scrollContainer = document.getElementById("wrap");
							scrollContainer.scrollTop = scrollContainer.scrollHeight;
					} else {
						var wrap = document.getElementById("wrap");
					   	var tableHTML = " <table class='yourMessageBox'>"+
						" 	<tr>" +
						"			<td rowspan='2'>" +
						"				<div class='friendProfileImageWrap'>" +
						"					<img class='friendProfileImage' src='" + rcv_data.chatProfile.profile_img + "'/>" +
						"				</div>" +
						"			</td>" +
						"			<td class='yourNick'>" +
						"				<span style='text-align:left'>" + rcv_data.chatProfile.nick_name + "</span>" +
						"				<span> &nbsp; &nbsp; &nbsp;</span> " +
						"			</td>" +
						"			<td>" +
						"			</td>" +
						"		</tr>" +
						"		<tr>" +
						"			<td class='chatting ch1'>" +
						"				<div class='textbox'>" + rcv_data.msg + "</div>" +
						"			</td>" +
						"			<td class='timeAlign'>" +
						"			<span class='chatTime'>" + rcv_data.currentDate + "</span>" +
						"			</td>" +
						"		</tr>" +
						"	</table>";
						wrap.innerHTML += tableHTML;
						var scrollContainer = document.getElementById("wrap");
						scrollContainer.scrollTop = scrollContainer.scrollHeight;
					}
				}
		}
	}
	document.addEventListener("keypress", function(e){
		if(e.keyCode == 13){ //enter press
			send();
		}
	});
}
	
var chatProfile = '';

function getProfile() {
	if (!chatProfile) {
		const userId = document.getElementById("sessionId").value;
		$.ajax({
		    url : `/main/profile?id=${userId}`,
		    type : "get",  
		    success : function(result){
		    	console.log('result : ',  JSON.parse(result));
		        chatProfile = JSON.parse(result);
		    },
		    async : false
		});
	}
	return chatProfile;
}

function send() {
	let today = new Date();
	let year = today.getFullYear(); // 년도
	let month = today.getMonth() + 1;  // 월
	let date = today.getDate();  // 날짜
	let hours = today.getHours(); // 시
	let minutes = today.getMinutes();  // 분
	var sendDate = year+ '-' + month +'-'+ date + ' ' + hours + ':' + minutes;
    var option = {
        type: "message",
        roomNumber: document.getElementById("roomNumber").value,
        sessionId: document.getElementById("sessionId").value,
        currentDate: sendDate,
        msg: document.getElementById("chatting").value,
        chatProfile: getProfile()
    };
    console.log('option : ', option);
    ws.send(JSON.stringify(option));
    document.getElementById("chatting").value = ""; // clear;
}
	
function goChat(event) {
var clickedChat = event.currentTarget;
var rN = clickedChat.querySelector(".roomNumber").value;
console.log(rN);
$.ajax({	
		    url : "/moveChatting?roomNumber=" + rN,
		    dataType : "html",
		    type : "get",  
		    success : function(result){
		        $("#chat").html(result);
		    }
			});
}

function checkChatRoom() {
	var friendId = document.getElementById("friendId").value;
	$.ajax({	
		    url : "/checkChatRoom?friendId=" + friendId,
		    dataType : "html",
		    type : "get",  
		    success : function(result){
		        $("#chat").html(result);
		   		 }
		});
}

function wsClose() {
			console.log("웹소켓닫기");
				ws.close();
}



/* 새로운 친구 검색 */
function searchAddMember() {
		var condition = document.getElementById("searchCondition").value;
		var keyword = document.getElementById("searchKeyword").value;
	$.ajax({	
		    url : "/main/searchMember",
		    dataType : "html",
		    type : "post",
		    data : {condition:condition, keyword:keyword },
		    success : function(result){
		        $("#chat").html(result);
		   		 }
		});
}


/* 새로 검색된 친구 추가 */
function addFriend() {
		var memberId = document.getElementById("searchMemeberId").value;
	$.ajax({	
		    url : "/main/searchMemberAdd",
		    dataType : "html",
		    type : "post",
		    data : {memberId, memberId},
		    success : function(result){
		        $("#chat").html(result);
		   		 }
		});
		alert("친구 신청을 보냈습니다.");
		 $.ajax({
		    url : "searchMember",
		    dataType : "html",
		    type : "get",  
		    success : function(result){
		        $("#chat").html(result);
		    }
			});
}


/* 새로 추가된 친구 삭제 */
function addCancle() {
		var memberId = document.getElementById("addMemeberId").value;
	$.ajax({	
		    url : "/main/addMemberCancle",
		    dataType : "html",
		    type : "post",
		    data : {memberId, memberId},
		    success : function(result){
		        $("#chat").html(result);
		   		 }
		});
		alert("친구 신청을 취소했습니다.");
		 $.ajax({
		    url : "searchMember",
		    dataType : "html",
		    type : "get",  
		    success : function(result){
		        $("#chat").html(result);
		    }
			});
}

/*친구 신청 수락*/
function friendAccept() {
	var memberId = document.getElementById("friendListId").value;
	$.ajax({	
		    url : "/main/friendAccept",
		    dataType : "html",
		    type : "post",
		    data : {memberId, memberId},
		    success : function(result){
		        $("#chat").html(result);
		   		 }
		});
	alert("친구 신청을 수락했습니다.");
	 location.reload();
}

/*친구 신청 거절*/
function friendReject() {
	var memberId = document.getElementById("friendListId").value;
	$.ajax({	
		    url : "/main/friendReject",
		    dataType : "html",
		    type : "post",
		    data : {memberId, memberId},
		    success : function(result){
		        $("#chat").html(result);
		   		 }
		});
		alert("친구 신청을 거절했습니다.");
		 location.reload();
}


/*정보변경 시작*/
/*비밀번호 변경*/
function changePassword() {
	var password = document.getElementById("password").value;
	var checkPassword = document.getElementById("pwdCheck").value;
	var pwdregex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d).{8,14}$/;
	
	console.log(password);
	console.log(checkPassword);
	
	if (password != checkPassword) {
	alert("비밀번호 불일치");
	} else if (!pwdregex.test(password)) {
        alert("비밀번호는 8~14자 영문 대문자, 소문자, 숫자를 각 1개 이상 포함해야 합니다.");
        document.getElementById("password").focus();
        return false;
    } else {
		$.ajax({	
			    url : "/main/changeInfoPassword",
			    dataType : "html",
			    type : "post",
			    data : {password, password},
			    success : function(result){
			        $("#chat").html(result);
			   		 }
			});
			alert("비밀번호를 변경했습니다.");
			$.ajax({
		    url : "modifyMyInfo",
		    dataType : "html",
		    type : "get",  
		    success : function(result){
		        $("#chat").html(result);
		    }
			});
	}
}





/* 꾸미기 */
//친구목록 화살표 회전
var rotationDegree = 0;
		var rotationDegree2 = 0;
		function rotateImage() {
			// 이미지 요소를 가져와서 적절한 각도로 회전
			var imgElement = document.getElementById('rotatingImage');
			rotationDegree -= 180;
			imgElement.style.transform = 'rotate(' + rotationDegree + 'deg)';
		}
		function rotateImage2() {
			// 이미지 요소를 가져와서 적절한 각도로 회전
			var imgElement = document.getElementById('rotatingImage2');
			rotationDegree2 -= 180;
			imgElement.style.transform = 'rotate(' + rotationDegree2 + 'deg)';
		}
		function toggleAccordion(titleElement) {
			// Find the parent section
			var section = titleElement.parentElement;

			// Toggle the content's display property
			var content = section.querySelector('.accordion-content');
			if (content.style.display === 'none') {
				content.style.display = 'block';
			} else {
				content.style.display = 'none';
			}
		}
		
		
//대화방 참가자 드롭 다운
		function toggleDropdown() {
			  var dropdownContent = document.getElementById("dropdownContent");
			  if (dropdownContent.style.display === "block") {
			    dropdownContent.style.display = "none";
			  } else {
			    dropdownContent.style.display = "block";
			  }
			}

			// Close the dropdown if the user clicks outside of it
			window.onclick = function(event) {
			  if (!event.target.matches('.dropdown button')) {
			    var dropdowns = document.getElementsByClassName("dropdown-content");
			    var i;
			    for (i = 0; i < dropdowns.length; i++) {
			      var openDropdown = dropdowns[i];
			      if (openDropdown.style.display === "block") {
			        openDropdown.style.display = "none";
			      }
			    }
			  }
			}
			

