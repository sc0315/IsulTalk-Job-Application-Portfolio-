package com.isul.main;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.isul.dto.ChatListDTO;
import com.isul.dto.FindMemberDTO;
import com.isul.dto.ProfileDTO;

@Mapper
@Repository
public interface MainDAO {

	// 내 프로필 불러오기
	public ProfileDTO getMyProfile(String id);

	// 친구 리스트 프로필 불러오기
	List<ProfileDTO> getFriendList(String id);

	// 나를 추가한 친구 리스트 프로필 불러오기
	List<ProfileDTO> getAddedMeList(String id);

	// 내가 참가한 방번호와 참가자 프로필
	List<ChatListDTO> roomMemberAndProfile(String id);

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
	public void changeNickName(String nickName, String myId);

	// 상태메세지 변경
	public void changeStatusMessage(String statusMessage, String myId);

	// 프로필 이미지 변경
	public void changeProfileImage(String savePath, String myId);

	// 프로필 배경 변경
	public void changeBackImage(String savePath, String myId);

	// 비밀번호 변경
	public void changeInfoPassword(String secPass, String myId);
	
	// 회원 탈퇴
	public void deleteMember(String myId);
}
