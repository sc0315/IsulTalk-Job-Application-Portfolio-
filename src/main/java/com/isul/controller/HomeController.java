package com.isul.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;
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
	
}
