package com.isul.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.isul.dto.MemberDTO;
import com.isul.member.MemberService;

import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.exception.NurigoMessageNotReceivedException;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.service.DefaultMessageService;

@Controller
public class SMSController {

	@Autowired
	private MemberService memberService;

	@RequestMapping("/member/smsSend")
	public String sendSMS(@RequestParam(value = "phone") String phone,
			@RequestParam(value = "phoneAuthorizationKey") String phoneAuthorizationKey) {
		DefaultMessageService messageService = NurigoApp.INSTANCE.initialize("NCSWMNXARVJESEUB",
				"NEWRGIN4YBX8HZPCILKZ45N3MDB4WJNO", "https://api.coolsms.co.kr");
		// Message 패키지가 중복될 경우 net.nurigo.sdk.message.model.Message로 치환하여 주세요
		Message message = new Message();
		message.setFrom("01077757510");
		message.setTo(phone);
		message.setText("테스트문자 인증번호 4자리 [" + phoneAuthorizationKey + "]");

		try {
			// send 메소드로 ArrayList<Message> 객체를 넣어도 동작합니다!
			messageService.send(message);
		} catch (NurigoMessageNotReceivedException exception) {
			// 발송에 실패한 메시지 목록을 확인할 수 있습니다!
			System.out.println(exception.getFailedMessageList());
			System.out.println(exception.getMessage());
		} catch (Exception exception) {
			System.out.println(exception.getMessage());
		}
		return "redirect:member/join";
	}

	@RequestMapping("/member/findsmsSend")
	public String findsendSMS(@RequestParam(value = "find_phone") String phone,
			@RequestParam(value = "findphoneAuthorizationKey") String phoneAuthorizationKey) {
		DefaultMessageService messageService = NurigoApp.INSTANCE.initialize("NCSWMNXARVJESEUB",
				"NEWRGIN4YBX8HZPCILKZ45N3MDB4WJNO", "https://api.coolsms.co.kr");
		// Message 패키지가 중복될 경우 net.nurigo.sdk.message.model.Message로 치환하여 주세요
		Message message = new Message();
		message.setFrom("01077757510");
		message.setTo(phone);
		message.setText("테스트문자 인증번호 4자리 [" + phoneAuthorizationKey + "]");

		try {
			// send 메소드로 ArrayList<Message> 객체를 넣어도 동작합니다!
			messageService.send(message);
		} catch (NurigoMessageNotReceivedException exception) {
			// 발송에 실패한 메시지 목록을 확인할 수 있습니다!
			System.out.println(exception.getFailedMessageList());
			System.out.println(exception.getMessage());
		} catch (Exception exception) {
			System.out.println(exception.getMessage());
		}
		return "redirect:member/find_id";
	}

	/*
	 * 아이디 폰번호로 찾기
	 */
	@SuppressWarnings("unused")
	@RequestMapping("member/findphone")
	public String find_id(@RequestParam(value = "find_phone") String phone,
			@RequestParam(value = "find_name") String name, HttpServletResponse response) throws IOException {

		MemberDTO member = memberService.getMemberByNamePhone(name, phone);
		response.setCharacterEncoding("utf-8");

		if (member != null) {
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script> alert('아이디는 " + member.getId() + " 입니다'); history.go(-3); </script>");
			out.close();

			return null;
		} else {
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>alert('회원 정보가 틀렸습니다.');history.go(-1);</script>");
			out.close();

			return null;
		}
	}
}
