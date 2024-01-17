
	
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
		


/**------------------------------------------------------------------------------------------------------------------------------------
 *	 게시판 (고객센터)
 */
 	
// 게시판 검색
	function searchBoardButton() {
		
	    var condition = document.getElementById("searchBoardcondition").value;
	    var keyword = document.getElementById("keyword").value;
   		csboardView(condition, keyword);
// 검색용 게시판 리스트
	function csboardView(condition, keyword) {  
		console.log();
	    $.ajax({
	        url: "csboard",
	        dataType: "html",
	        type: "post",
	        data:{condition:condition, keyword:keyword},
	        success: function (result) {
	            $("#chat").html(result);
	        }
	        
	    });
	}   
	}
	
// 게시판 화면
	function csboardView(condition, keyword, pageNum) {
	    $.ajax({
	        url: "csboard",
	        dataType: "html",
	        type: "post",
	        data:{condition:condition, keyword:keyword, pageNum:pageNum},
	        success: function (result) {
	            $("#chat").html(result);
	        }
	        
	    });
	}

// 글 상세정보
	function getBoardForm(event){
	var clickedRow = event.currentTarget;
	
	var board_number = clickedRow.querySelector(".board_number").value;
	
		$.ajax({
		    url : "getBoardForm",
		    dataType : "text",
		    type : "get",  
		    data : {board_number:board_number},   // 호출할 url 에 있는 페이지로 넘길 파라메터
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
            
            success: function(){
				
               alert("글이 등록되었습니다.");
             	
            $.ajax({
			    url : "csboard",
			    dataType : "html",
			    type : "get", 
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
			success : function(){
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
		var board_number = document.getElementById('board_number').value;
		var board_content = document.getElementById('reply_content').value;
		
		if (board_content.trim() === "") {
        alert("댓글 내용을 입력하세요.");
        reply_content.focus();
        return;
        }

        $.ajax({
            url: "insertReply",
            dataType : "html",
            type: "post",
            data: {	board_number: board_number,
            		reply_content: board_content,
				
			},
			
            success: function(result){
				
				
				alert("댓글이 등록되었습니다.");
				 $("#chat").html(result);         	
	    }
	    
	    });
	}

// 댓글 삭제
 function reply_delete(event) {
	
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
	

// 대댓글 입력창

function re_replyInsertForm(event) {
    var clickedRow = event.currentTarget;
    var replyInfo = clickedRow.closest('.reply_info');
    var reply2 = replyInfo.querySelector('.re_reply');
    
    if (reply2) {
        if (window.getComputedStyle(reply2).display === 'none') {
            reply2.style.display = 'block';
        } else {
            reply2.style.display = 'none';
        }
    }
    
}
// 대댓글 등록
function re_replyInsert(event){
	
	var clickedRow = event.currentTarget;   
    var re_replyBox = clickedRow.closest('.re_reply');
    var re_reply_number = re_replyBox.querySelector('.re_reply_number');
    var reply_number = re_reply_number.value;
	var re_reply_content = re_replyBox.querySelector('.re_reply_content');
	var reply_content = re_reply_content.value;
	var re_reply_ref = re_replyBox.querySelector('.reply_ref');
	var reply_ref = re_reply_ref.value;
	var re_reply_deep = re_replyBox.querySelector('.reply_deep');
	var reply_deep = re_reply_deep.value;
	var re_reply_level = re_replyBox.querySelector('.reply_level');
	var reply_level = re_reply_level.value;
	
	
	var board_number = document.getElementById('board_number').value;
	var reply_writer = document.getElementById('reply_writer').value;

	if (reply_content.trim() === "") {
        alert("댓글 내용을 입력하세요.");
        re_reply_content.focus();
        return;
        }
	
    $.ajax({
        url: "reinsertReply",
        dataType : "html",
        type: "post",
        data: {reply_content: reply_content,
        		board_number: board_number,
        		reply_number : reply_number,
        		reply_ref : reply_ref,
        		reply_deep : reply_deep	,
        		reply_level : reply_level,
        		reply_writer : reply_writer
        	},
        
        success: function(result){
			alert("댓글이 등록되었습니다.");
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
			

