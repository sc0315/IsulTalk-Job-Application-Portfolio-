package com.isul.member;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.isul.dto.ChatListDTO;
import com.isul.dto.ChatMessageDTO;
import com.isul.dto.FindMemberDTO;
import com.isul.dto.FriendDTO;
import com.isul.dto.MemberDTO;
import com.isul.dto.ProfileDTO;

@Mapper
@Repository
public interface MemberDAO {
	
	public MemberDTO getMember(String id);
	public MemberDTO getMemberByIdEmail(@Param("id") String id, @Param("email")String email);
	public List<MemberDTO> getMemberList();
	public void insertMember(MemberDTO memberDTO);
	public int idCheck(String id);
	public String confirmID(MemberDTO memberDTO);
	public void changePassword(MemberDTO memberDTO);
	public MemberDTO getMemberByNamePhone(@Param("name") String name, @Param("phone") String phone);
	
	// 내 프로필 불러오기
	public ProfileDTO getMyProfile(String id);
	
	// 친구 리스트 프로필 불러오기
		List<ProfileDTO> getFriendList(String id);
		
		// 나를 추가한 친구 리스트 프로필 불러오기
		List<ProfileDTO> getAddedMeList(String id);
		
		// 내가 참가한 방번호와 참가자 프로필
		List<ChatListDTO> roomMemberAndProfile(String id);
		
		// 선택한 방 채팅 메세지 불러오기
		List<ChatMessageDTO> getChatMessage(int roomNumber);
		
		// 메세지 데이터 전송
		public void insertChatMessage(ChatMessageDTO chatMessageDTO);
		
		// 1:1대화방 조회
		public String findChatRoom(String myId, String yourId);
		
		// 1:1대화방 생성
		public void createChatRoom(String myId, String yourId);
		
		// 추가할 친구목록 검색
		public FindMemberDTO searchAddMember(String myId, String searchCondition, String keyword);
		
		// 검색된 친구 추가
		public void searchMemberAdd(String myId, String yourId);
		
		// 내가 추가한 친구
		public List<FindMemberDTO> getAddedFriendList(String myId);
		
		// 내가 추가한 친구 취소 반대로 넣어서 나를 추가한 친구 거절
		public void addCancle(String yourId, String myId);
		
		// 나를 추가한 친구 수락
		public void friendAccept(String yourId, String myId);
		
		// 대화명 변경
		public void changeNickName (String nickName, String myId);
		
		// 상태메세지 변경
		public void changeStatusMessage (String statusMessage, String myId);
		
		
}

		
		