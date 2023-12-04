
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
			if (content.style.display === 'block') {
				content.style.display = 'none';
			} else {
				content.style.display = 'block';
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
		    url : "chat",
		    dataType : "html",
		    type : "get",  
		    data : { a:"값1", b:"값2"},   // 호출할 url 에 있는 페이지로 넘길 파라메터
		    success : function(result){
		        $("#chat").html(result);
		    }
		});
		$.ajax({
		    url : "chatList",
		    dataType : "html",
		    type : "get",  
		    data : { a:"값1", b:"값2"},   // 호출할 url 에 있는 페이지로 넘길 파라메터
		    success : function(result){
		        $("#friendList").html(result);
		    }
		});
	}
	
	
//친구목록		
	function friendView() {
			$.ajax({
		    url : "profile",
		    dataType : "html",
		    type : "get",  
		    data : { a:"값1", b:"값2"},   // 호출할 url 에 있는 페이지로 넘길 파라메터
		    success : function(result){
		        $("#chat").html(result);
		    }
			});
			$.ajax({
			    url : "friendList",
			    dataType : "html",
			    type : "get",  
			    data : { a:"값1", b:"값2"},   // 호출할 url 에 있는 페이지로 넘길 파라메터
			    success : function(result){
			        $("#friendList").html(result);
			    }
			});
	}

//친구찾기
	function searchMemberView() {
			$.ajax({
		    url : "searchMember",
		    dataType : "html",
		    type : "get",  
		    data : { a:"값1", b:"값2"},   // 호출할 url 에 있는 페이지로 넘길 파라메터
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
		    data : { a:"값1", b:"값2"},   // 호출할 url 에 있는 페이지로 넘길 파라메터
		    success : function(result){
		        $("#chat").html(result);
		    }
			});
		
	}	
		

//고객센터
	function csboardView() {
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

// 글 상세정보
	function getBoardForm(event){
	var clickedRow = event.currentTarget;
	
	var board_number = clickedRow.querySelector(".board_number").value;
    
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
				console.log(result);
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
		$.ajax({
			url : "updateBoard",
			dataType:"html",
			type : "post",
			data: $('#updateform').serialize(),
			success : function(result){
				alert("글이 수정되었습니다.");
				

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