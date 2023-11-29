package com.isul.controller;


import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


import com.isul.dto.MemberDTO;
import com.isul.member.MemberService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/member/")
@RequiredArgsConstructor
public class MemberController {
	
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	//----- 이메일 발송 --------------------------------------------------------
	@PostMapping(value = "/emailAuth")
	public String emailAuth(@RequestParam(value="email") String email,
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
	        return "redirect:member/join";
	}
	
	
	
	@Autowired
	MemberService memberService;
	
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

	//회원가입
	@PostMapping("/join")
	public String insertMember(MemberDTO dto) {
		System.out.println(dto);
		dto.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));
		memberService.insertMember(dto);
		return "redirect:../";
	}
	

	//아이디 중복확인
	@RequestMapping("/idCheck")
    @ResponseBody //ajax 값을 보내기 위해 사용
    public String idCheck(@RequestParam("id") String id) {
		// result=1 성공, -1 실패
        String result="1";
        
        int flag = memberService.idCheck(id);
        
        if(flag == 1) result ="-1"; 
        //아이디가 있을시 -1 없을시 1 으로 view 로 보냄
        return result;
	}

	
	//이메일 중복확인
		@RequestMapping("/emailCheck")
	    @ResponseBody //ajax 값을 보내기 위해 사용
	    public String emailCheck(@RequestParam("email") String email) {
			// result=1 성공, -1 실패
	        String result="1";
	        
	        return result;
		}
}
