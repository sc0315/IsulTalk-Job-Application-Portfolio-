package com.isul.controller;


import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.isul.chat.ChatService;
import com.isul.dto.ChatMessageDTO;


// 대화방 클릭시 채팅창을 열어주는 기능 처리
@Controller 
public class ChatController {
		
		@Autowired
		public ChatService chatService; 
		
		@RequestMapping(value="chat", method=RequestMethod.GET)
		public String chatView() {
			return "main/chat";
		}

	
		@RequestMapping("checkChatRoom")
		public String checkChatRoom(@RequestParam("friendId") String friendId, HttpSession session, Model model,
				@Param("myId") String myId,
				@Param("yourId") String yourId) {
			
			myId = (String)session.getAttribute("loginId");
			yourId = friendId;
			String findRoomNumber = chatService.findChatRoom(myId, yourId);
			
			if (findRoomNumber != null) {
				model.addAttribute("roomNumber", findRoomNumber);
				int rN = Integer.parseInt(findRoomNumber);
				List<ChatMessageDTO> messageList = chatService.getChatMessage(rN);
				System.out.println(messageList);
				model.addAttribute("messageList", messageList);
				model.addAttribute("roomNumber", rN);
				model.addAttribute("memberId", myId);
				return "main/chat";
			} else {
				chatService.createChatRoom(myId, friendId);
				findRoomNumber = chatService.findChatRoom(myId, yourId);
				model.addAttribute("roomNumber", findRoomNumber);
				int rN = Integer.parseInt(findRoomNumber);
				String chatId = (String)session.getAttribute("loginId");
				List<ChatMessageDTO> messageList = chatService.getChatMessage(rN);
				System.out.println(messageList);
				model.addAttribute("messageList", messageList);
				model.addAttribute("roomNumber", rN);
				model.addAttribute("memberId", chatId);
				return "main/chat";
			}
		}
		
		
		// 방으로 이동
		@RequestMapping("moveChatting")
		public String chatting (@RequestParam("roomNumber") String roomNumber, Model model, HttpSession session) {
			
			int rN = Integer.parseInt(roomNumber);
			String chatId = (String)session.getAttribute("loginId");
			List<ChatMessageDTO> messageList = chatService.getChatMessage(rN);
			System.out.println(messageList);
			model.addAttribute("messageList", messageList);
			model.addAttribute("roomNumber", rN);
			model.addAttribute("memberId", chatId);
			return "main/chat";
		}
		
		
	
}