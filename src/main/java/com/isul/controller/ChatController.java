package com.isul.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.isul.chat.Room;
import com.isul.dto.ChatMessageDTO;
import com.isul.member.MemberService;


// 대화방 클릭시 채팅창을 열어주는 기능 처리
@Controller 
public class ChatController {
		
		@Autowired
		public MemberService memberService; 
		
		@RequestMapping(value="chat", method=RequestMethod.GET)
		public String chatView() {
			return "main/chat";
		}

	// 방의 목록을 List에 저장
		List<Room> roomList = new ArrayList<Room>();
		static int roomNumber = 0;
		
		// 대화방을 표시할 메소드
		@RequestMapping("/room")
		public String room() {
				return "main/room";
		}
		
		// 방목록을 조회
		@RequestMapping("/getRoom")
		@ResponseBody
		public List<Room> getRoom(){
			
			return roomList;
		}
		
		// 방 생성하기
		@RequestMapping("/createRoom")
		@ResponseBody
		public List<Room> createRoom(@RequestParam HashMap<Object, Object> params) {
			System.out.println(params.get("roomName"));
			
			String roomName = (String)params.get("roomName");
			
			if(roomName != null && !roomName.trim().equals("")) {
				Room room = new Room();
				
				room.setRoomNumber(++roomNumber);
				room.setRoomName(roomName);
				
				roomList.add(room);
			}
			return roomList;
		}
		
		
//		//채팅방으로 이동
//		@RequestMapping("moveChatting")
//		public ModelAndView chatting (@RequestParam("roomNumber") String roomNumber, ModelAndView modelView, HttpSession session) {
//			int rN = Integer.parseInt(roomNumber);
//			String chatId = (String)session.getAttribute("loginId");
//			modelView.addObject("memberId", chatId);
//			modelView.addObject("roomNumber", rN);
//			modelView.setViewName("main/chat");
//			return modelView;
//		}
		
		@RequestMapping("moveChatting")
		public String chatting (@RequestParam("roomNumber") String roomNumber, Model model, HttpSession session) {
			int rN = Integer.parseInt(roomNumber);
			String chatId = (String)session.getAttribute("loginId");
			List<ChatMessageDTO> messageList = memberService.getChatMessage(rN);
			System.out.println(messageList);
			model.addAttribute("messageList", messageList);
			model.addAttribute("roomNumber", rN);
			model.addAttribute("memberId", chatId);
			return "main/chat";
		}
	
}