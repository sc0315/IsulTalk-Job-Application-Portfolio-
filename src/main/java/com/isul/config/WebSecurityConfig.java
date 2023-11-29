package com.isul.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
public class WebSecurityConfig {
	
	@Lazy
	@Autowired
	private MemberDetailService memberService;
	
	// static, resources 폴더 시큐리티 기능 비활성화
	@Bean
	public WebSecurityCustomizer configure() {
		return (web) -> web.ignoring()
				.requestMatchers(PathRequest.toStaticResources().atCommonLocations());
	}
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity security) throws Exception {
		// url이 "/"인 경우 모두 접근 허가
		security.authorizeRequests().antMatchers("/").permitAll();
		
		// authenticated() - id, pwd를 통해 사용자 인증이 된 사람만 접근 가능
		//security.authorizeRequests().antMatchers("/admin/**").authenticated();
	
		security.csrf().disable();	// csrf - sns 사용자ID를 도용한 웹 사이트 공격)
		
		security.formLogin()	// 사용자 인증을 위한 로그인 화면 사용 설정
				.loginPage("/")	// 로그인에 사용할 URL 지정 -> 로그인 페이지
				.defaultSuccessUrl("/", true);
		
		// 로그아웃 페이지
		security.logout().invalidateHttpSession(true).logoutSuccessUrl("/");
		
		// Member 테이블에서 사용자 조회 후, UserDetails 객체 변환
		security.userDetailsService(memberService);
		return security.build();
	}
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
