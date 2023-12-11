package com.isul.chat;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;



@Configuration 			 // 설정파일을 따로 등록하기 위한 어노테이션
@EnableWebSocket		 // 웹소켓 프로그램 활성화
public class WebSocketConfig implements WebSocketConfigurer{

	
	@Autowired
	SocketHandler soketHandler;
	
	// 지정한  URL에 대해 실행 프로그램 등록
	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		// addHandler() 웹소켓 처리 프로그램
		registry.addHandler(soketHandler, "/chatting/{roomNumber}");
	}
	
	
}
