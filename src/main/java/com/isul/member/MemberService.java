package com.isul.member;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.isul.dto.MemberDTO;

@Service
public class MemberService {
	
	@Autowired
	MemberDAO memberDAO;
	
	public List<MemberDTO> getMemberList() {
		return memberDAO.getMemberList();
	};
	
	public void insertMember(MemberDTO memberDTO) {
		memberDAO.insertMember(memberDTO);
	}
	
	public int idCheck(String id) {
		int result = 0;
        
        result = memberDAO.idCheck(id);
        return result;
    }
}
