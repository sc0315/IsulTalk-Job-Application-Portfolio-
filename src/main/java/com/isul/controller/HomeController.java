package com.isul.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.isul.dto.MemberDTO;
import com.isul.member.MemberService;

@Controller
@SessionAttributes("loginId")
public class HomeController {
	
	@Autowired
	private MemberService memberService;
	
	@RequestMapping("/")
	public String check() {
		System.out.println("확인");
		return "index";
	}
	@GetMapping("/login_fail")
	public String login_fail(){
		System.out.println("실패");
		return "member/login_fail";
	}
	
	@PostMapping("/login")
	public String login(MemberDTO memberDTO, HttpSession session, HttpServletResponse response) throws IOException {
		MemberDTO loginId = null;
		
		int result = memberService.loginID(memberDTO);
		System.out.println(result);
		if(result ==1) { // 1: id가 있음 로그인 성공
			loginId = memberService.getMember(memberDTO.getId());
			session.setAttribute("loginId", loginId);
			System.out.println(loginId);
			System.out.println("로그인 성공");
			
			return "redirect:/main/";
		} else { // 로그인 실패
			// 한글로 출력
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/html; charset=utf-8");
			
			PrintWriter out = response.getWriter();
			out.println("<script> alert('아이디 또는 비밀번호가 틀립니다.');");
			out.println("history.go(-1); </script>");
			out.close();
			
			return "redirect:/main/"; 
		}
	}
	
	//----- 이메일 발송 --------------------------------------------------------
	@PostMapping(value = "/findEmailAuth")
	public String findemailAuth(@RequestParam(value="find_id") String id,
								@RequestParam(value="find_email") String email,
							@RequestParam(value="findauthorizationKey") String authorizationKey,
							MemberDTO memberDTO) {
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
	            message.setText("임시 비밀번호"
	            		+ "["+ authorizationKey +"]" + " 로그인 후에 비밀번호를 변경을 해주세요");
	           
	            // 기존 비밀번호를 임시 비밀번호로 변경
	            MemberDTO member = memberService.getMemberByIdEmail(id, email);
	            System.out.println(email);
	            member.setPassword(authorizationKey);
	            memberService.changePassword(member);
	            System.out.println(member.getPassword());
	           
	            // 메시지 전송
	            Transport.send(message);
	            System.out.println("메일 보내기 성공 " + email + " 로 인증번호 [" + authorizationKey +"]");
	         
	           
	        } catch (MessagingException e) {
	            e.printStackTrace();
	        }
	        return "redirect:/";
		}
	
}
