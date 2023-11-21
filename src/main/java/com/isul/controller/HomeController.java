package com.isul.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.isul.dto.MemberDTO;
import com.isul.member.MemberService;

@Controller
@SessionAttributes("loginUser")
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
	public String login(MemberDTO memberDTO, HttpSession session) {
		MemberDTO loginId = null;
		
		int result = memberService.loginID(memberDTO);
		if(result ==1) { // 1: id가 있음
			loginId = memberService.getMember(memberDTO.getId());
			session.setAttribute("loginId", loginId);
			System.out.println(loginId);
			System.out.println("로그인 성공");
			return "redirect:main/";
		} else { // 사용 인증 실패
			System.out.println("로그인 실패");
			return "member/login_fail";
		}
	}
	
}
