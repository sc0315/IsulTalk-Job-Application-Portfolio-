package com.isul.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.isul.dto.MemberDTO;
import com.isul.member.MemberService;


@Service
public class MemberDetailService implements UserDetailsService {

	@Autowired
	private MemberService memberService;
	
	@Override
	public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
		// 아이디로 회원 조회
		MemberDTO member = memberService.getMember(id);
		
		if(member != null) {
			// UserDetails 타입으로 객체 변환
			return new SecurityUser(member);
			
		} else {
			throw new UsernameNotFoundException(id + ": 사용자가 존재하지 않습니다.");
		}
				
	}

}
