package com.isul.chat;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.isul.dto.ChatMessageDTO;

@Mapper
@Repository
public interface ChatDAO {
	// 선택한 방 채팅 메세지 불러오기
	List<ChatMessageDTO> getChatMessage(int roomNumber);

	// 메세지 데이터 전송
	public void insertChatMessage(ChatMessageDTO chatMessageDTO);

	// 1:1대화방 조회
	public String findChatRoom(String myId, String yourId);

	// 1:1대화방 생성
	public void createChatRoom(String myId, String yourId);
}
