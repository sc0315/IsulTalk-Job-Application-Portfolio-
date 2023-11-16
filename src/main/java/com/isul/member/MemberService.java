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
}
