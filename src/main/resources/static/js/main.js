
//친구목록 화살표 회전
var rotationDegree = 0;
		var rotationDegree2 = 0;
		function rotateImage() {
			var imgElement = document.getElementById('rotatingImage');
			rotationDegree -= 180;
			imgElement.style.transform = 'rotate(' + rotationDegree + 'deg)';
		}
		function rotateImage2() {
			var imgElement = document.getElementById('rotatingImage2');
			rotationDegree2 -= 180;
			imgElement.style.transform = 'rotate(' + rotationDegree2 + 'deg)';
		}
		function toggleAccordion(titleElement) {

			var section = titleElement.parentElement;
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
			
			
			
//채팅목록			
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
	
	
//친구목록		
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
	
	
	
	
	
	
	

//친구찾기
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
	
//정보변경
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
		

/**------------------------------------------------------------------------------------------------------------------------------------
 *	 게시판 
 */
 
	function csboardView() {
	
			$.ajax({
		    url : "csboard",
		    dataType : "html",
		    type : "get",  

		    success : function(result){
				
		        $("#chat").html(result);
		    }
			});
		
	}	

// 글 상세정보
	function getBoardForm(event){
	var clickedRow = event.currentTarget;
	
	var board_number = clickedRow.querySelector(".board_number").value;
    	console.log(board_number);
		$.ajax({
		    url : "getBoardForm",
		    dataType : "text",
		    type : "get",  
		    data : {board_number:board_number },   // 호출할 url 에 있는 페이지로 넘길 파라메터
		    success : function(result){
				
		        $("#chat").html(result);
		    }
			});
	}
// 글 등록창
	function insertBoardForm(){
		$.ajax({
		    url : "insertBoardForm",
		    dataType : "html",
		    type : "get",  
		    data : { },   // 호출할 url 에 있는 페이지로 넘길 파라메터
		    success : function(result){
		        $("#chat").html(result);
		    }
			});

	}

// 글 등록
	function boardInsert(){
	
        $.ajax({
            url: "insertBoard",
             dataType : "html",
            type: "post",
            data: $('#insertform').serialize(),
            
            success: function(result){
				
               alert("글이 등록되었습니다.");
             	
            $.ajax({
			    url : "csboard",
			    dataType : "html",
			    type : "get",  
			    data : { },   // 호출할 url 에 있는 페이지로 넘길 파라메터
			    success : function(result){
			        $("#chat").html(result);
			    }
			});
	    }
	    });
	}

// 글 수정창
	function updateBoardForm(){
		$.ajax({
		    url : "updateBoardForm",
		    dataType : "html",
		    type : "get",  
		    data : { },   // 호출할 url 에 있는 페이지로 넘길 파라메터
		    success : function(result){
		        $("#chat").html(result);
		    }
			});
	}
	
// 글 수정
	function boardUpdate(){
		console.log("업데이트 진입")
		$.ajax({
			url : "updateBoard",
			dataType:"html",
			type : "get",
			data: $('#updateform').serialize(),
			success : function(result){
				alert("글이 수정되었습니다.");
				$("#chat").html(result); 
			
			}
			
		});
	}

// 글 삭제
	function boardDelete(){
		$.ajax({
			url : "deleteBoard",
			dataType:"html",
			type:"get",
			data:$("updateform").serialize(),
			success : function(result){
				alert("글이 삭제되었습니다.");
				
		$.ajax({
		    url : "csboard",
		    dataType : "html",
		    type : "get",  
		    data : { },   // 호출할 url 에 있는 페이지로 넘길 파라메터
		    success : function(result){
		        $("#chat").html(result);
		    }
			});
			}
		});
	}
	
// 페이지 넘버 검색해서 띄우기
function loadPage(pageNum) {
	
        $.ajax({
            url: "csboard",
            type: 'GET',
            data: {pageNum:pageNum
            },
            success: function (result) {
                $('#chat').html(result);
              
            }
        });
    }

/**------------------------------------------------------------------------------------------------------------------------------------
 *	 댓글 
 */

// 댓글 등록
	function replyInsert(){
	
        $.ajax({
            url: "insertReply",
            dataType : "html",
            type: "post",
            data: $('#insertReplyForm').serialize(),
            
            success: function(result){
				alert("댓글이 등록되었습니다.");
				 $("#chat").html(result);         	
	    }
	    
	    });
	}

// 댓글 삭제
 function reply_delete(event) {
	


	
	
	
	
	
	
	
	
		var ws;
	window.onload = function(){
		getRoom();
		createRoom();
	}

	function getRoom(){  // 방 목록 가져오기
		commonAjax('/getRoom', "", 'post', function(result){
			createChatingRoom(result);
		});
	}
	
	function createRoom(){
		$("#createRoom").click(function(){
			var msg = { roomName : $('#roomName').val()	};
			commonAjax('/createRoom', msg, 'post', function(result){
				createChatingRoom(result);
			});

			$("#roomName").val("");
		});
	}

	function goRoom(number, name){
		console.log(name);
		console.log(number);
		location.href="/moveChatting?roomName="+name+"&"+"roomNumber="+number;
	}

	function createChatingRoom(res){
		if(res != null){
			var tag = "<tr><th class='num'>순서</th><th class='room'>방 이름</th><th class='go'></th></tr>";
			res.forEach(function(d, idx){
				var rn = d.roomName.trim();
				var roomNumber = d.roomNumber;
				tag += "<tr>"+
							"<td class='num'>"+(idx+1)+"</td>"+
							"<td class='room'>"+ rn +"</td>"+
							"<td class='go'><button type='button' onclick='goRoom(\""+roomNumber+"\", \""+rn+"\")'>참여</button></td>" +
						"</tr>";	
			});
			$("#roomList").empty().append(tag);
		}
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
	
	
	
	

function wsOpen(){
	ws = new WebSocket("ws://" + location.host + "/chatting/" + document.getElementById("roomNumber").value);
	wsEvt();
}
	
	
	
function wsEvt() {
	ws.onopen = function(data){ //소켓이 열리면 초기화 세팅하기
	console.log(document.getElementById("sessionId").value);
	}
	ws.onmessage = function(data) { //메시지를 받으면 처리하는 내용
		var msg = data.data;
		if(msg != null && msg.trim() != ''){
			var rcv_data = JSON.parse(msg);
			console.log('receive : ', rcv_data);
				if (rcv_data.type == "message"){
					if (rcv_data.sessionId == document.getElementById("sessionId").value) {
						var wrap = document.getElementById("wrap");
						var tableHTML = "<table class='myMessageBox'>" +
							"<tr>" +
						    "<td></td>" +
						    "<td class='myNick'>" +
						    "<span>①</span>" +
						    "<span>" + rcv_data.profile.nick_name + "</span>" +
						    "</td>" +
						    "<td rowspan='2'>" +
						    "<div class='friendProfileImageWrap'>" +
						    "<img class='friendProfileImage' src='" + rcv_data.profile.profile_img + "'/>" +
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
						"					<img class='friendProfileImage' src='" + rcv_data.profile.profile_img + "'/>" +
						"				</div>" +
						"			</td>" +
						"			<td class='yourNick'>" +
						"				<span style='text-align:left'>" + rcv_data.profile.nick_name + "</span>" +
						"				<span>①</span> " +
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
	
var profile = '';

function getProfile() {
	if (!profile) {
		const userId = document.getElementById("sessionId").value;
		$.ajax({
		    url : `/main/profile?id=${userId}`,
		    type : "get",  
		    success : function(result){
		    	console.log('result : ',  JSON.parse(result));
		        profile = JSON.parse(result);
		      
		    },
		    async : false
		});
	}
	return profile;
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
        profile: getProfile()
    };
    
    console.log('option : ', option);

    ws.send(JSON.stringify(option));
    document.getElementById("chatting").value = ""; // clear;
}
			
	/*	
		function wsClose() {
			var uN = $("#userName").val();
			var msg = uN + "님이 퇴장하셨습니다!";

			ws.send(msg);
			ws.close();
			
			self.opener = self;
			window.close()
		}
	*/
	
	
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
	
=======
	var clickedRow = event.currentTarget;
	var loginId = document.getElementById("loginId").value;
	var reply_writer =clickedRow.querySelector(".reply_writer").value;
	var reply_number = clickedRow.querySelector(".reply_number").value;
    	
		
		if(loginId == reply_writer){
		$.ajax({
		    url : "deleteReply",
		    dataType : "text",
		    type : "get",  
		    data : {reply_number:reply_number },   // 호출할 url 에 있는 페이지로 넘길 파라메터
		    success : function(result){
				alert("댓글이 삭제되었습니다.");
		        $("#chat").html(result);
		    }
			});
		}else{
			alert("댓글 작성자만 삭제할 수 있습니다.");
			console.log(loginId, reply_writer);
			return false;	
		}
	}

