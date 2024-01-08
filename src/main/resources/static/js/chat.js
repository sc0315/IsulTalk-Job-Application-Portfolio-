
	
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
