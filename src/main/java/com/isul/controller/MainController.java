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
import com.isul.main.MainService;
import com.isul.member.MemberService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/main/")
@RequiredArgsConstructor
public class MainController {

	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	public MemberService memberService;

	@Autowired
	public MainService mainService;
	
	
	//메인 화면 페이지
	@RequestMapping("/")
	public String check(ProfileDTO profileDTO, Model model, HttpSession session) {
		
		System.out.println("메인");
		String sessionId = (String) session.getAttribute("loginId");

		List<ProfileDTO> friendList = mainService.getFriendList(sessionId);
		model.addAttribute("friendList", friendList);

		List<ProfileDTO> addedFriendList = mainService.getAddedMeList(sessionId);
		model.addAttribute("addedFriendList", addedFriendList);

		ProfileDTO profile = mainService.getMyProfile(sessionId);
		model.addAttribute("profile", profile);
		return "main/main";
	}
	
	
	// 채팅 페이지 
	@RequestMapping("/chat")
	public String chatView() {
		return "main/chat";
	}
	
	
	// 채팅 목록 페이지 
	@RequestMapping("/chatList")
	public String chatListView(ChatListDTO chatListDTO, HttpSession session, Model model) {
		String sessionId = (String) session.getAttribute("loginId");
		List<ChatListDTO> chatList = mainService.roomMemberAndProfile(sessionId);
		model.addAttribute("chatList", chatList);
		return "main/chatList";
	}
	
	
	// ajax로 프로필 받기
	@ResponseBody
	@RequestMapping("/profile")
	public String getProfile(ProfileDTO profileDTO, Model model, @RequestParam("id") String id) {
		
		ProfileDTO profile = mainService.getMyProfile(id);
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			String json = objectMapper.writeValueAsString(profile);
			return json;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	// 친구 리스트 페이지
	@RequestMapping("/friendList")
	public String friendListView(ProfileDTO profileDTO, Model model, HttpSession session) {
		
		String sessionId = (String) session.getAttribute("loginId");

		List<ProfileDTO> friendList = mainService.getFriendList(sessionId);
		model.addAttribute("friendList", friendList);

		List<ProfileDTO> addedFriendList = mainService.getAddedMeList(sessionId);
		model.addAttribute("addedFriendList", addedFriendList);

		ProfileDTO profile = mainService.getMyProfile(sessionId);
		model.addAttribute("profile", profile);

		return "main/friend";
	}

	
	// 친구 프로필 상세 보기
	@RequestMapping("/friendProfile")
	public String getFriendProfile(ProfileDTO profileDTO, Model model, @RequestParam("profileId") String profileId) {
		
		ProfileDTO profile = mainService.getMyProfile(profileId);
		model.addAttribute("profile", profile);
		return "main/profile";
	}
	
	
	// 가입정보 변경 페이지
	@RequestMapping("/modifyMyInfo")
	public String modifyMyInfoView(HttpSession session, Model model) {
		
		String loginId = (String) session.getAttribute("loginId");
		MemberDTO loginMember = memberService.getMember(loginId);
		model.addAttribute("loginMember", loginMember);
		return "main/modifyMyInfo";
	}
	
	// 친구 찾기 페이지  
//	@RequestMapping("findMemberList")
//	public String findMemberList() {
//		return "redirect:/main/searchMember";
//	} 
	
	
	// 친구 찾기 페이지
	@GetMapping("/searchMember")
	public String searchAddMember(HttpSession session, Model model) {
		
		String myId = (String) session.getAttribute("loginId");
		List<FindMemberDTO> memberList = mainService.getAddedFriendList(myId);
		model.addAttribute("memberList", memberList);
		return "main/searchMember";
	}
	
	
	// 조건별 친구 검색
	@PostMapping("/searchMember")
	public String searchAddMemberAction(@RequestParam(value = "condition", defaultValue = "") String searchCondition,
			@RequestParam(value = "keyword", defaultValue = "") String keyword, HttpSession session, Model model) {
		
		String myId = (String) session.getAttribute("loginId");
		FindMemberDTO member = mainService.searchAddMember(myId, searchCondition, keyword);
		String ms = null;

		if (member == null) {
			ms = "일치하는 사용자가 없습니다.";
		}

		List<FindMemberDTO> memberList = mainService.getAddedFriendList(myId);
		model.addAttribute("memberList", memberList);
		model.addAttribute("ms", ms);
		model.addAttribute("member", member);

		return "main/searchMember";
	}

	
	// 검색된 친구 추가
	@PostMapping("/searchMemberAdd")
	public String searchMemberAdd(@RequestParam(value = "memberId", defaultValue = "") String yourId,
			HttpSession session, Model model) {
		String myId = (String) session.getAttribute("loginId");
		mainService.searchMemberAdd(myId, yourId);
		return "main/searchMember";
	}

	
	// 검색 추가된 친구 취소
	@PostMapping("/addMemberCancle")
	public String addMemberCancle(@RequestParam(value = "memberId", defaultValue = "") String yourId,
			HttpSession session, Model model) {
		
		String myId = (String) session.getAttribute("loginId");
		mainService.addCancle(yourId, myId);
		List<FindMemberDTO> memberList = mainService.getAddedFriendList(myId);
		model.addAttribute("memberList", memberList);
		return "main/searchMember";
	}

	
	// 친구 추가 거절
	@PostMapping("/friendReject")
	public String friendReject(@RequestParam(value = "memberId", defaultValue = "") String myId, HttpSession session,
			Model model) {
		String yourId = (String) session.getAttribute("loginId");
		List<FindMemberDTO> memberList = mainService.getAddedFriendList(myId);
		model.addAttribute("memberList", memberList);
		mainService.addCancle(yourId, myId);
		return "main/main";
	}

	
	// 친구 추가 수락
	@PostMapping("/friendAccept")
	public String friendAccept(@RequestParam(value = "memberId", defaultValue = "") String yourId, HttpSession session,
			Model model) {
		String myId = (String) session.getAttribute("loginId");
		List<FindMemberDTO> memberList = mainService.getAddedFriendList(myId);
		model.addAttribute("memberList", memberList);
		mainService.friendAccept(yourId, myId);
		return "main/main";
	}

	
	// 프로필 대화명 변경
	@GetMapping("/changeNickName")
	public String changeNickName(@RequestParam(value = "nickName", defaultValue = "") String nickName,
			HttpSession session) {
		
		String myId = (String) session.getAttribute("loginId");
		mainService.changeNickName(nickName, myId);
		return "main/profile";
	}

	
	// 프로필 상태메세지 변경
	@GetMapping("/changeStatusMessage")
	public String changeStatusMessage(@RequestParam(value = "statusMessage", defaultValue = "") String statusMessage,
			HttpSession session) {
	
		String myId = (String) session.getAttribute("loginId");
		mainService.changeStatusMessage(statusMessage, myId);
		return "main/main";
	}

	
	// 프로필 이미지 업로드
	@PostMapping("/profileImageUpload")
	public String imageUpload(@RequestParam("file") MultipartFile file, HttpSession session) {

		String myId = (String) session.getAttribute("loginId");
		try {
			String dbPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\images\\profile";
			byte[] bytes = file.getBytes();
			String realPath = myId + "_" + file.getOriginalFilename();
			String savePath = "/images/profile/" + myId + "_" + file.getOriginalFilename();
			mainService.changeProfileImage(savePath, myId);
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

		String myId = (String) session.getAttribute("loginId");
		try {
			String dbPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\images\\background";
			byte[] bytes = file.getBytes();
			String realPath = myId + "_" + file.getOriginalFilename();
			String savePath = "/images/background/" + myId + "_" + file.getOriginalFilename();
			mainService.changeBackImage(savePath, myId);
			Path path = Paths.get(dbPath, realPath);
			Files.write(path, bytes);
		} catch (IOException e) {

			e.printStackTrace();
		}
		return "redirect:/main/main";
	}
	

	// 비밀 번호 변경
	@PostMapping("/changeInfoPassword")
	public String changeInfoPassword(@RequestParam(value = "password", defaultValue = "") String password,
			HttpSession session) {
		
		String myId = (String) session.getAttribute("loginId");
		String secPass = bCryptPasswordEncoder.encode(password);
		mainService.changeInfoPassword(secPass, myId);
		return "/main/searchMember";
	}

	
	// 회원 탈퇴
	@GetMapping("/deleteMember")
	public String deleteMember(HttpSession session) {
		String myId = (String) session.getAttribute("loginId");
		mainService.deleteMember(myId);
		return "/logout";
	}

}