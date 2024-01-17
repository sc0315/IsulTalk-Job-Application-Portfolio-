package com.isul.main;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.isul.dto.ChatListDTO;
import com.isul.dto.FindMemberDTO;
import com.isul.dto.MemberDTO;
import com.isul.dto.ProfileDTO;
import com.isul.member.MemberDAO;

@Service
public class MainService {

	@Autowired
	public MainDAO mainDAO;

	@Autowired
	public MemberDAO memberDAO;

	// 내 프로필 블러오기
	public ProfileDTO getMyProfile(String id) {
		return mainDAO.getMyProfile(id);
	}

	public List<MemberDTO> getMemberList() {
		return memberDAO.getMemberList();
	}

	// 친구리스트 프로필 불러오기
	public List<ProfileDTO> getFriendList(String id) {
		return mainDAO.getFriendList(id);
	}

	// 나를 추가한 친구 리스트 프로필 불러오기
	public List<ProfileDTO> getAddedMeList(String id) {
		return mainDAO.getAddedMeList(id);
	}

	// 내가 참가한 방번호와 참가자 프로필
	public List<ChatListDTO> roomMemberAndProfile(String id) {
		return mainDAO.roomMemberAndProfile(id);
	}

	// 추가할 친구 검색
	public FindMemberDTO searchAddMember(String myId, String searchCondition, String keyword) {
		return mainDAO.searchAddMember(myId, searchCondition, keyword);
	}

	// 검색된 친구 추가
	public void searchMemberAdd(String myId, String yourId) {
		mainDAO.searchMemberAdd(myId, yourId);
	}

	// 내가 추가한 친구
	public List<FindMemberDTO> getAddedFriendList(String myId) {
		return mainDAO.getAddedFriendList(myId);
	}

	// 내가 추가한 친구 취소 반대로 넣어서 나를 추가한 친구 거절
	public void addCancle(String yourId, String myId) {
		mainDAO.addCancle(yourId, myId);
	}

	// 나를 추가한 친구 수락
	public void friendAccept(String yourId, String myId) {
		mainDAO.friendAccept(yourId, myId);
	}

	// 대화명 변경
	public void changeNickName(String nickName, String myId) {
		mainDAO.changeNickName(nickName, myId);
	}

	// 상태메세지 변경
	public void changeStatusMessage(String statusMessage, String myId) {
		mainDAO.changeStatusMessage(statusMessage, myId);
	}

	// 프로필 이미지 변경
	public void changeProfileImage(String savePath, String myId) {
		mainDAO.changeProfileImage(savePath, myId);
	}

	// 프로필 배경 변경
	public void changeBackImage(String savePath, String myId) {
		mainDAO.changeBackImage(savePath, myId);
	}

	// 비밀번호 변경
	public void changeInfoPassword(String secPass, String myId) {
		mainDAO.changeInfoPassword(secPass, myId);
	}
	
	// 회원 탈퇴
	public void deleteMember(String myId) {
		mainDAO.deleteMember(myId);
	}

}
