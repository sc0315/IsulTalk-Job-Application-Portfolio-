package com.isul.controller;


import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
	
	@Autowired
	MemberService memberService;
	
	// 로그인
	@PostMapping("/login")
	public String login(MemberDTO memberDTO, HttpSession session, HttpServletResponse response) throws IOException {
		String loginId = null;
		
		int result = memberService.loginID(memberDTO);
		if(result ==1) { // 1: id가 있음 로그인 성공
			
			loginId = memberDTO.getId();
			session.setAttribute("loginId", loginId);
			return "redirect:/main/";
		} else { // 로그인 실패
			// 한글로 출력
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/html; charset=utf-8");
			
			PrintWriter out = response.getWriter();
			out.println("<script> alert('아이디 또는 비밀번호가 틀립니다.');");
			out.println("history.go(-1); </script>");
			out.close();
			
			return "main/main"; 
		}
	}
	
	// 로그아웃
	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		 session.invalidate();
		 return "index";
	}
	// 회원가입폼 
	@GetMapping("/join")
	public String joinFormView() {
		return "member/join";
	}
	
	// 아이디 찾기 팝업
	@GetMapping("/find_id")
	public String findIdView() {
		return "member/find_id";
	}

	// 회원가입
	@PostMapping("/join")
	public String insertMember(MemberDTO dto) {
		System.out.println(dto);
		dto.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));
		memberService.insertMember(dto);
		return "redirect:../";
	}
	

	// 아이디 중복확인
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

	// 이메일 중복확인
	@RequestMapping("/emailCheck")
    @ResponseBody //ajax 값을 보내기 위해 사용
    public String emailCheck(@RequestParam("email") String email) {
		// result=1 성공, -1 실패
        String result="1";
        
        return result;
		}
}
