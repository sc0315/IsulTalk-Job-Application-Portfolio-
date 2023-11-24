package com.isul.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.exception.NurigoMessageNotReceivedException;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.service.DefaultMessageService;

@Controller
public class SMSController {

	@RequestMapping("/member/smsSend")
	public String sendSMS(@RequestParam(value="phone") String phone,
						  @RequestParam(value="phoneAuthorizationKey") String phoneAuthorizationKey) {
		System.out.println("문자 발송 도착");
		DefaultMessageService messageService =  NurigoApp.INSTANCE.initialize("NCSWMNXARVJESEUB", "NEWRGIN4YBX8HZPCILKZ45N3MDB4WJNO", "https://api.coolsms.co.kr");
		// Message 패키지가 중복될 경우 net.nurigo.sdk.message.model.Message로 치환하여 주세요
		Message message = new Message();
		message.setFrom("01077757510");
		message.setTo(phone);
		message.setText("테스트문자 인증번호 4자리 [" + phoneAuthorizationKey +"]");

		try {
		  // send 메소드로 ArrayList<Message> 객체를 넣어도 동작합니다!
		  messageService.send(message);
		  System.out.println("문자발송확인");
		} catch (NurigoMessageNotReceivedException exception) {
		  // 발송에 실패한 메시지 목록을 확인할 수 있습니다!
		  System.out.println(exception.getFailedMessageList());
		  System.out.println(exception.getMessage());
		} catch (Exception exception) {
		  System.out.println(exception.getMessage());
		}
		return "redirect:member/join";
	}
	
}
