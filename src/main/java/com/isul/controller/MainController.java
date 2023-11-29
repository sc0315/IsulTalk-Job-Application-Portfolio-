package com.isul.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/main/")
public class MainController {
	
	@RequestMapping("/")
	public String check() {
		System.out.println("메인");
		return "main/main";
	}
	
	@RequestMapping("/chat")
	public String chatView() {
		System.out.println("채팅창 열기");
		return "main/chat";
	}
	
	@RequestMapping("/chatList")
	public String chatListView() {
		System.out.println("채팅 목록 열기");
		return "main/chatList";
	}
	
	@RequestMapping("/profile")
	public String profileView(HttpSession session ) {
		System.out.println("프로필 열기");
		return "main/profile";
	}
	
	@RequestMapping("/friendList")
	public String friendListView() {
		System.out.println("친구목록 열기");
		return "main/friend";
	}
	
	@RequestMapping("/searchMember")
	public String searchMemberView() {
		System.out.println("친구검색 열기");
		return "main/searchMember";
	}
	
	@RequestMapping("/modifyMyInfo")
	public String modifyMyInfoView() {
		System.out.println("정보변경 열기");
		return "main/modifyMyInfo";
	}
	
	@RequestMapping("/csboard")
	public String csboardView() {
		System.out.println("고객센터 열기");
		return "board/csboard";
	}
	
	
}
