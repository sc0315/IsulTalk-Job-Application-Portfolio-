package com.isul.member;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.isul.dto.MemberDTO;

@Mapper
@Repository
public interface MemberDAO {

	public MemberDTO getMember(String id);

	public MemberDTO getMemberByIdEmail(@Param("id") String id, @Param("email") String email);

	public List<MemberDTO> getMemberList();

	public void insertMember(MemberDTO memberDTO);

	public int idCheck(String id);

	public String confirmID(MemberDTO memberDTO);

	public void changePassword(MemberDTO memberDTO);

	public MemberDTO getMemberByNamePhone(@Param("name") String name, @Param("phone") String phone);

}
