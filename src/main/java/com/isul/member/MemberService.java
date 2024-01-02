package com.isul.member;


import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.isul.dto.ChatListDTO;
import com.isul.dto.ChatMessageDTO;
import com.isul.dto.FindMemberDTO;
import com.isul.dto.FriendDTO;
import com.isul.dto.MemberDTO;
import com.isul.dto.ProfileDTO;

import lombok.RequiredArgsConstructor;

@Service
public class MemberService {
	
	@Autowired
	public MemberDAO memberDAO;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	// 회원가입
	public void insertMember(MemberDTO memberDTO) {
		memberDAO.insertMember(memberDTO);
	}
	
	// 중복확인용 id 값 체크
	public int idCheck(String id) {
		int result = 0;
        
        result = memberDAO.idCheck(id);
        return result;
    }
	
	// 회원 정보 조회
	public MemberDTO getMember(String id) {
		return memberDAO.getMember(id);
	}
	public MemberDTO getMemberByIdEmail(@Param("id") String id, @Param("email")String email) {
		return memberDAO.getMemberByIdEmail(id, email);
	}
	
	// 로그인
	// id가 비밀번호 틀린 경우 -1, 정상 입력 1
	public int loginID(MemberDTO memberDTO) {
	    int result = -1; // 리턴 결과 저장 변수
				
		// 패스워드를 통해 일치여부 확인
		String password = memberDAO.confirmID(memberDTO);
		if(memberDTO.getId()==null) {
			result = -1;
		}
		if(password != null && bCryptPasswordEncoder.matches(memberDTO.getPassword(), password)) {
			result = 1;
		} else {
			result = -1;
		}
		return result;
	}
	public void changePassword(MemberDTO memberDTO) {
		memberDAO.changePassword(memberDTO);
	}
	
	public MemberDTO getMemberByNamePhone(@Param("name") String name, @Param("phone") String phone) {
		return memberDAO.getMemberByNamePhone(name, phone);
	}
	
	
	
	// 내 프로필 블러오기
	public ProfileDTO getMyProfile(String id) {
		return memberDAO.getMyProfile(id);
	}
	
	public List<MemberDTO> getMemberList(){
		return memberDAO.getMemberList();
	}
	
	// 친구리스트 프로필 불러오기
		public List<ProfileDTO> getFriendList(String id) {
			return memberDAO.getFriendList(id);
		}
		
		// 나를 추가한 친구 리스트 프로필 불러오기
		public List<ProfileDTO> getAddedMeList(String id) {
			return memberDAO.getAddedMeList(id);
		}
		
		// 내가 참가한 방번호와 참가자 프로필
		public List<ChatListDTO> roomMemberAndProfile(String id) {
			return memberDAO.roomMemberAndProfile(id);
		}
		
		// 선택한 채팅방 메세지 불러오기
		public List<ChatMessageDTO> getChatMessage(int roomNumber) {
			return memberDAO.getChatMessage(roomNumber);
		}
		
		// 메세지 데이터 전송
		public void insertChatMessage(ChatMessageDTO chatMessageDTO) {
			memberDAO.insertChatMessage(chatMessageDTO);
		}
		
		// 1:1대화방 조회
		public String findChatRoom(String myId, String yourId){
			return memberDAO.findChatRoom(myId, yourId);
		};
				
		// 1:1대화방 생성
		public void createChatRoom(String myId, String yourId) {
			memberDAO.createChatRoom(myId, yourId);
		};
	
		// 추가할 친구 검색
		public FindMemberDTO searchAddMember(String myId, String condition, String keyword){
			return memberDAO.searchAddMember(myId, condition, keyword);
		}
		
		// 검색된 친구 추가
		public void searchMemberAdd(String myId, String yourId) {
			memberDAO.searchMemberAdd(myId, yourId);
		}
		
		// 내가 추가한 친구
		public List <FindMemberDTO> getAddedFriendList(String myId) {
			return memberDAO.getAddedFriendList(myId);
		}
		
		// 내가 추가한 친구 취소 반대로 넣어서 나를 추가한 친구 거절
		public void addCancle(String yourId, String myId) {
			memberDAO.addCancle(yourId, myId);
		}
		
		// 나를 추가한 친구 수락
		public void friendAccept(String yourId, String myId) {
			memberDAO.friendAccept(yourId, myId);
		}
		
		
}	
