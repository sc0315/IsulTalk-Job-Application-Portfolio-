package com.isul.config;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

import com.isul.dto.MemberDTO;

public class SecurityUser extends User {
	
	public SecurityUser(MemberDTO member) {
		super(member.getId(), member.getPassword(), 
				AuthorityUtils.createAuthorityList());
	}

}
