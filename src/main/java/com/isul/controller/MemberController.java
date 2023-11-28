package com.isul.controller;


import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import com.isul.member.MemberService;

@Controller
@RequestMapping("/member/")
public class MemberController {
	
	
	//----- 이메일 발송 --------------------------------------------------------
	@PostMapping(value = "/emailAuth")
	public void emailAuth(@RequestParam(value="email") String email,
							@RequestParam(value="authorizationKey") String authorizationKey) {
		 final String username = "rlatkdcjf86@naver.com";
	     final String password = "Da357159";
	        // SMTP 서버 설정
	        Properties props = new Properties();
	        props.put("mail.smtp.auth", "true");
	        props.put("mail.smtp.starttls.enable", "true");
	        props.put("mail.smtp.host", "smtp.naver.com");
	        props.put("mail.smtp.port", "587");

	        // 세션 생성
	        Session session = Session.getInstance(props, new Authenticator() {
	            protected PasswordAuthentication getPasswordAuthentication() {
	                return new PasswordAuthentication(username, password);
	            }
	        });

	        try {
	            // 메시지 생성
	            Message message = new MimeMessage(session);
	            message.setFrom(new InternetAddress(username));
	            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
	            message.setSubject("이메일 테스트");
	            message.setText("인증번호6자리"
	            		+ "["+ authorizationKey +"]");

	            // 메시지 전송
	            Transport.send(message);
	            System.out.println("메일 보내기 성공 " + email + " 로 인증번호 [" + authorizationKey +"]");
	        } catch (MessagingException e) {
	            e.printStackTrace();
	        }
	    
		//return "redirect:signUp_form";
		
	}
	
	
	
	@Autowired
	MemberService memberService;
	
	//테스트용
	@GetMapping("/memberList")
	public String getMemberList(Model model){
		model.addAttribute("memberList", memberService.getMemberList());
		return "member/memberList";
	}
	
	//회원가입폼 
	@GetMapping("/join")
	public String joinFormView() {
		return "member/join";
	}
	
	//아이디 찾기 팝업
	@GetMapping("/find_id")
	public String findIdView() {
		return "member/find_id";
	}
	
	//비밀번호 찾기 폼
	@GetMapping("/find_password")
	public String findPasswordView() {
		return "member/find_password";
	}
	
	//채팅테스트
		@GetMapping("/chatTest")
		public String chatTest() {
			return "main/chatTest";
		}
	
	
	
}
