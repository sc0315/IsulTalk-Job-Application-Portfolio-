package com.isul.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.isul.dto.BoardDTO;
import com.isul.dto.ChatListDTO;
import com.isul.dto.ProfileDTO;
import com.isul.member.MemberService;

@Controller
@RequestMapping("/main/")
public class MainController {
	
	@Autowired
	public MemberService memberService;
	
	@RequestMapping("/")
	public String check(ProfileDTO profileDTO, Model model, HttpSession session) {
		System.out.println("메인");
		String sessionId = (String)session.getAttribute("loginId");
		
		List<ProfileDTO> friendList = memberService.getFriendList(sessionId);
		model.addAttribute("friendList", friendList);
		System.out.println(friendList);
		
		List<ProfileDTO> addedFriendList = memberService.getAddedMeList(sessionId);
		model.addAttribute("addedFriendList", addedFriendList);
		System.out.println(addedFriendList);
		
		return "main/main";
	}
	
	
	@RequestMapping("/profile")
	public String profileView(HttpSession session ) {
		System.out.println("프로필 열기");
		return "main/profile";
	}
	
	@RequestMapping("/friendList")
	public String friendListView(ProfileDTO profileDTO, Model model, HttpSession session) {
		System.out.println("친구목록 열기");
		String sessionId = (String)session.getAttribute("loginId");
		
		List<ProfileDTO> friendList = memberService.getFriendList(sessionId);
		model.addAttribute("friendList", friendList);
		System.out.println(friendList);
		
		List<ProfileDTO> addedFriendList = memberService.getAddedMeList(sessionId);
		model.addAttribute("addedFriendList", addedFriendList);
		System.out.println(addedFriendList);
		
		return "main/friend";
	}
	
	
	@RequestMapping("/chatList")
	public String chatListView(ChatListDTO chatListDTO, HttpSession session, Model model) {
		System.out.println("채팅 목록 열기");
		String sessionId = (String)session.getAttribute("loginId");
		
		List<ChatListDTO> chatList = memberService.roomMemberAndProfile(sessionId);
		model.addAttribute("chatList", chatList);
		System.out.println(chatList);
		
		return "main/chatList";
	}
	
	
	@RequestMapping("/chat")
	public String chatView() {
		System.out.println("채팅창 열기");
		return "main/chat";
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
	
	
	
	
}
