package com.isul.member;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.isul.dto.MemberDTO;

@Mapper
@Repository
public interface MemberDAO {
	
	public List<MemberDTO> getMemberList();
	public void insertMember(MemberDTO memberDTO);
	public int idCheck(String id);
}
