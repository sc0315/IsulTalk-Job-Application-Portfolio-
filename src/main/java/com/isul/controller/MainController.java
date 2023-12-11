package com.isul.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
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

		List<ProfileDTO> addedFriendList = memberService.getAddedMeList(sessionId);
		model.addAttribute("addedFriendList", addedFriendList);
		
		ProfileDTO profile = memberService.getMyProfile(sessionId);
		model.addAttribute("profile", profile);

		return "main/main";
	}
	

	@RequestMapping("/friendList")
	public String friendListView(ProfileDTO profileDTO, Model model, HttpSession session) {
		System.out.println("친구목록 열기");
		String sessionId = (String)session.getAttribute("loginId");
		
		List<ProfileDTO> friendList = memberService.getFriendList(sessionId);
		model.addAttribute("friendList", friendList);

		List<ProfileDTO> addedFriendList = memberService.getAddedMeList(sessionId);
		model.addAttribute("addedFriendList", addedFriendList);
		
		ProfileDTO profile = memberService.getMyProfile(sessionId);
		model.addAttribute("profile", profile);
		
		return "main/friend";
	}
	
	@RequestMapping("/friendProfile")
	public String getFriendProfile(ProfileDTO profileDTO, Model model,
									@RequestParam("profileId") String profileId) {
		System.out.println("친구 프로필 조회 도착");
		ProfileDTO profile = memberService.getMyProfile(profileId);
		model.addAttribute("profile", profile);
		System.out.println("profile" + profile);
		return "main/profile";
	}
	
	@ResponseBody
	@RequestMapping("/profile")
	public String getProfile(ProfileDTO profileDTO, Model model, 
									@RequestParam("id") String id) {
		ProfileDTO profile = memberService.getMyProfile(id);
		ObjectMapper objectMapper = new ObjectMapper();
		try {
            String json = objectMapper.writeValueAsString(profile);
            return json;
        } catch (Exception e) {
            e.printStackTrace();
            // 예외 처리 필요
            return null;
        }
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
