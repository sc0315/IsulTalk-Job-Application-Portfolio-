 
 /*
  *	아이디 쿠키 생성,삭제 
  */
 $(document).ready(function(){
       var key = getCookie("key");	// 쿠키 저장해서 id칸에 넣어줌
       $("#id").val(key);
       
       if ($("#id").val() !== "") {	// id칸이 채워져 있다면 체크박스를 체크 상태로 두기
           $("#check").attr("checked", true);
       }
		// 체크박스에 변화가 있거나 체크박스 체크되어있을때 쿠키 생성
       $("#check").change(function(){	
           if ($("#check").is(":checked")) { 
               setCookie("key", $("#id").val(), 7); // 7일동안 쿠키 보관
           } else {		// 체크박스 체크 해제 시 쿠키 삭제
               deleteCookie("key"); 
           }
       });
		var loginId = document.getElementById(loginId);
		// 아이디저장 체크한 상태에서 id 입력하는 경우 쿠키 생성
       $("#id").keyup(function(){
           if ($("#check").is(":checked")) {
               setCookie("key", $("#id").val(), 7);
           }
       });
       
   });

   function setCookie(cookieName, value, exdays) {
       var exdate = new Date();
       exdate.setDate(exdate.getDate() + exdays);
       var cookieValue = escape(value) + ((exdays == null) ? "" : "; expires=" + exdate.toGMTString());
       document.cookie = cookieName + "=" + cookieValue;
   }

   function deleteCookie(cookieName) {
       var expireDate = new Date();
       expireDate.setDate(expireDate.getDate() - 1);
       document.cookie = cookieName + "= " + "; expires=" + expireDate.toGMTString();
   }

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
   