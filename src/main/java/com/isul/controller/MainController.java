package com.isul.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.isul.dto.ChatListDTO;
import com.isul.dto.FindMemberDTO;
import com.isul.dto.MemberDTO;
import com.isul.dto.ProfileDTO;
import com.isul.member.MemberService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/main/")
@RequiredArgsConstructor
public class MainController {

	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	public MemberService memberService;

	
	
	@RequestMapping("/")
	public String check(ProfileDTO profileDTO, Model model, HttpSession session) {
		System.out.println("메인");
		String sessionId = (String) session.getAttribute("loginId");

		List<ProfileDTO> friendList = memberService.getFriendList(sessionId);
		model.addAttribute("friendList", friendList);

		List<ProfileDTO> addedFriendList = memberService.getAddedMeList(sessionId);
		model.addAttribute("addedFriendList", addedFriendList);

		ProfileDTO profile = memberService.getMyProfile(sessionId);
		model.addAttribute("profile", profile);
		return "main/main";
	}

	@RequestMapping("/chat")
	public String chatView() {
		System.out.println("채팅창 열기");
		return "main/chat";
	}

	@RequestMapping("/chatList")
	public String chatListView(ChatListDTO chatListDTO, HttpSession session, Model model) {
		System.out.println("채팅 목록 열기");
		String sessionId = (String) session.getAttribute("loginId");

		List<ChatListDTO> chatList = memberService.roomMemberAndProfile(sessionId);
		model.addAttribute("chatList", chatList);
		System.out.println(chatList);

		return "main/chatList";
	}

	@ResponseBody
	@RequestMapping("/profile")
	public String getProfile(ProfileDTO profileDTO, Model model, @RequestParam("id") String id) {
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

	@RequestMapping("/friendList")
	public String friendListView(ProfileDTO profileDTO, Model model, HttpSession session) {
		String sessionId = (String) session.getAttribute("loginId");

		List<ProfileDTO> friendList = memberService.getFriendList(sessionId);
		model.addAttribute("friendList", friendList);

		List<ProfileDTO> addedFriendList = memberService.getAddedMeList(sessionId);
		model.addAttribute("addedFriendList", addedFriendList);

		ProfileDTO profile = memberService.getMyProfile(sessionId);
		model.addAttribute("profile", profile);

		return "main/friend";
	}

	@RequestMapping("/friendProfile")
	public String getFriendProfile(ProfileDTO profileDTO, Model model, @RequestParam("profileId") String profileId) {
		ProfileDTO profile = memberService.getMyProfile(profileId);
		model.addAttribute("profile", profile);
		return "main/profile";
	}

	

	@RequestMapping("/modifyMyInfo")
	public String modifyMyInfoView(HttpSession session, Model model) {
		String loginId = (String)session.getAttribute("loginId");
		MemberDTO loginMember = memberService.getMember(loginId);
		model.addAttribute("loginMember", loginMember);
		return "main/modifyMyInfo";
	}
	
	@RequestMapping("findMemberList") 
	public String findMemberList() {
		return "redirect:/main/searchMember";
	}
	
	@GetMapping("/searchMember")
	public String searchAddMember(HttpSession session, Model model) {
		String myId = (String)session.getAttribute("loginId");
		List<FindMemberDTO> memberList =  memberService.getAddedFriendList(myId);
		model.addAttribute("memberList", memberList);
		return "main/searchMember";
	}
	
	@PostMapping("/searchMember")
	public String searchAddMemberAction(@RequestParam(value="condition", defaultValue="") String searchCondition,
			@RequestParam(value="keyword", defaultValue="") String keyword, HttpSession session, Model model) {
			String myId = (String)session.getAttribute("loginId");
			FindMemberDTO member = memberService.searchAddMember(myId, searchCondition, keyword);
			String ms = null;
			
			
			System.out.println("myid : " + myId+"---condition : "+ searchCondition+"---keyword: "+keyword);
			
			if (member == null) {
				ms = "일치하는 사용자가 없습니다.";
			}
			System.out.println(ms);
			List<FindMemberDTO> memberList =  memberService.getAddedFriendList(myId);
			model.addAttribute("memberList", memberList);
			model.addAttribute("ms", ms);
			model.addAttribute("member",member);
			System.out.println(member);
			return "main/searchMember";
		
	}
	
	@PostMapping("/searchMemberAdd")
	public String searchMemberAdd(@RequestParam(value="memberId", defaultValue="") String yourId,
							HttpSession session, Model model) {
			String myId = (String)session.getAttribute("loginId");
			memberService.searchMemberAdd(myId, yourId);
			return "main/searchMember";
		
	}
	
	@PostMapping("/addMemberCancle")
	public String addMemberCancle(@RequestParam(value="memberId", defaultValue="") String yourId,
							HttpSession session, Model model) {
			String myId = (String)session.getAttribute("loginId");
			memberService.addCancle(yourId, myId);
			List<FindMemberDTO> memberList =  memberService.getAddedFriendList(myId);
			model.addAttribute("memberList", memberList);
			return "main/searchMember";
	}
	
	@PostMapping("/friendReject")
	public String friendReject(@RequestParam(value="memberId", defaultValue="") String myId,
							HttpSession session, Model model) {
			String yourId = (String)session.getAttribute("loginId");
			List<FindMemberDTO> memberList =  memberService.getAddedFriendList(myId);
			model.addAttribute("memberList", memberList);
			memberService.addCancle(yourId, myId);
			return "main/main";
	}
	
	@PostMapping("/friendAccept")
	public String friendAccept(@RequestParam(value="memberId", defaultValue="") String yourId,
							HttpSession session, Model model) {
			String myId = (String)session.getAttribute("loginId");
			List<FindMemberDTO> memberList =  memberService.getAddedFriendList(myId);
			model.addAttribute("memberList", memberList);
			memberService.friendAccept(yourId, myId);
			return "main/main";
	}
	
	@GetMapping("/changeNickName")
	public String changeNickName(@RequestParam(value="nickName", defaultValue="") String nickName,
			HttpSession session) {
		String myId = (String)session.getAttribute("loginId");
		memberService.changeNickName(nickName, myId);
		return "main/profile";
	}
	
	@GetMapping("/changeStatusMessage")
	public String changeStatusMessage(@RequestParam(value="statusMessage", defaultValue="") String statusMessage,
			HttpSession session) {
		String myId = (String)session.getAttribute("loginId");
		memberService.changeStatusMessage(statusMessage, myId);
		return "main/main";
	}
	

	// 프로필 이미지 업로드
	@PostMapping("/profileImageUpload")
	public String imageUpload(@RequestParam("file") MultipartFile file, HttpSession session) {

		String myId = (String)session.getAttribute("loginId");
		try {
			String dbPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\images\\profile";
			byte[] bytes = file.getBytes();
			String realPath = myId + "_" + file.getOriginalFilename();
			String savePath = "/images/profile/" + myId + "_" + file.getOriginalFilename();
			memberService.changeProfileImage(savePath, myId);
			 Path path = Paths.get(dbPath, realPath);
	         Files.write(path, bytes);
		} catch (IOException e) {
		
			e.printStackTrace();
		}
		 return "redirect:/main/main";
	}
	
	// 프로필 배경 이미지 업로드
		@PostMapping("/backImageUpload")
		public String backImageUpload(@RequestParam("file") MultipartFile file, HttpSession session) {

			String myId = (String)session.getAttribute("loginId");
			try {
				String dbPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\images\\background";
				byte[] bytes = file.getBytes();
				String realPath = myId + "_" + file.getOriginalFilename();
				String savePath = "/images/background/" + myId + "_" + file.getOriginalFilename();
				memberService.changeBackImage(savePath, myId);
				 Path path = Paths.get(dbPath, realPath);
		         Files.write(path, bytes);
			} catch (IOException e) {
				
				e.printStackTrace();
			}
			 return "redirect:/main/main";
		}
	
		@PostMapping("/changeInfoPassword")
		public String changeInfoPassword(@RequestParam(value="password", defaultValue="") String password, HttpSession session) {
			String myId = (String)session.getAttribute("loginId");
			String secPass = bCryptPasswordEncoder.encode(password);
			memberService.changeInfoPassword(secPass, myId);
			return "/main/searchMember";
		}
}