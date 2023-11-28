package com.isul.member;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.isul.dto.MemberDTO;

@Service
public class MemberService {
	
	@Autowired
	MemberDAO memberDAO;
	
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
	// id가 존재하지 않으면 0, 비밀번호 틀린 경우 -1, 정상 입력 1
	public int loginID(MemberDTO memberDTO) {
	    int result = -1; // 리턴 결과 저장 변수
				
		// 패스워드를 통해 일치여부 확인
		String password = memberDAO.confirmID(memberDTO);
		
		if(password != null && password.equals(memberDTO.getPassword())) {
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
}
