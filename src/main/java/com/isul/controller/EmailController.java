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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.isul.dto.MemberDTO;
import com.isul.member.MemberService;

@Controller
public class EmailController {

	@Autowired
	private MemberService memberService;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	// ----- 회원가입용 이메일 발송 --------------------------------------------------------
	@PostMapping(value = "/emailAuth")
	public String emailAuth(@RequestParam(value = "email") String email,
			@RequestParam(value = "authorizationKey") String authorizationKey) {
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
			message.setText("인증번호6자리" + "[" + authorizationKey + "]");

			// 메시지 전송
			Transport.send(message);

		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return "redirect:member/join";
	}

	// ----- 비밀번호 찾기 이메일 발송 --------------------------------------------------------
	@PostMapping(value = "/findEmailAuth")
	public String findemailAuth(@RequestParam(value = "find_id") String id,
			@RequestParam(value = "find_email") String email,
			@RequestParam(value = "findauthorizationKey") String authorizationKey, MemberDTO memberDTO) {
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
			message.setText("임시 비밀번호" + "[" + authorizationKey + "]" + " 로그인 후에 비밀번호를 변경을 해주세요");

			// 기존 비밀번호를 임시 비밀번호로 변경
			MemberDTO member = memberService.getMemberByIdEmail(id, email);
			member.setPassword(bCryptPasswordEncoder.encode(authorizationKey));
			memberService.changePassword(member);
		
			// 메시지 전송
			Transport.send(message);
	
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return "redirect:/";
	}
}
