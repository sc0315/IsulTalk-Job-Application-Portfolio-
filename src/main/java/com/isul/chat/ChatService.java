package com.isul.chat;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.isul.dto.ChatMessageDTO;

@Service
public class ChatService {
	
	@Autowired
	public ChatDAO chatDAO;
	
	// 선택한 채팅방 메세지 불러오기
	public List<ChatMessageDTO> getChatMessage(int roomNumber) {
		return chatDAO.getChatMessage(roomNumber);
	}
	
	// 메세지 데이터 전송
	public void insertChatMessage(ChatMessageDTO chatMessageDTO) {
		chatDAO.insertChatMessage(chatMessageDTO);
	}
	
	// 1:1대화방 조회
	public String findChatRoom(String myId, String yourId){
		return chatDAO.findChatRoom(myId, yourId);
	};
			
	// 1:1대화방 생성
	public void createChatRoom(String myId, String yourId) {
		chatDAO.createChatRoom(myId, yourId);
	};
}
