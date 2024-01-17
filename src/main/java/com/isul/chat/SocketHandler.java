package com.isul.chat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.isul.dto.ChatMessageDTO;


//여러 클라이언트(채팅창)가 발송한 메시지를 처리해 주는 클래스
@Component
public class SocketHandler extends TextWebSocketHandler {

	// 웹 소켓 세션을 저장할 맵 생성
	// 키 : sessionId, 값 : session 객체
	private HashMap<String, WebSocketSession> sessionMap = new HashMap<>();

	@Autowired
	ChatService chatService;

	// 방정보와 웹소켓 세션 목록을 저장할 리스트
	List<HashMap<String, Object>> roomList = new ArrayList<>();

	// 웹소켓 연결 성공 시 동작
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		super.afterConnectionEstablished(session);

		boolean flag = false; // 리스트에 방정보가 저장되어 있는지 여부

		String url = session.getUri().toString();
		System.out.println(url);
		String roomNumber = url.split("/chatting/")[1]; // url 에서 방번호 추출 [0] 부터 시작 , "/chatting/"기준

		// room list에서 url에서 요청한 방번호가 있는지 검색
		int idx = roomList.size();
		if (roomList.size() > 0) {
			for (int i = 0; i < roomList.size(); i++) {
				String roomNo = (String) roomList.get(i).get("roomNumber");

				if (roomNo.equals(roomNumber)) { // roomList에서 방의 존재 여부 확인
					flag = true;
					idx = i; // i번째 인덱스에 방이 존재
					break;
				}
			}

		}

		if (flag) { // 존재하는 방이므로 세션만 추가한다.
			HashMap<String, Object> map = roomList.get(idx); // 키 : 방번호, 값 : 채팅방의 세션 목록
			map.put(session.getId(), session);
		} else { // 존재하지 않는 방이므로 방번호와 세션을 roomList에 함께 저장
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("roomNumber", roomNumber);
			map.put(session.getId(), session);
			roomList.add(map);
		}

		// 세션을 맵에 저장, 같은 대화방에 속하는 사람은 같은 세션맵에 저장됨
		sessionMap.put(session.getId(), session);

		// 세션 연결 시, 세션 ID를 나의 대화창으로 전송하여 저장
		JSONObject obj = new JSONObject();
		obj.put("type", "getId");
		obj.put("sessionId", session.getId());
		session.sendMessage(new TextMessage(obj.toJSONString()));
		System.out.println("세션 연결됨 : " + session.getId());
	}

	// 메시지를 수신하면 실행
	// 대화방에 모든 사람들에게 메시지 전달
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		String msg = message.getPayload(); // payload 수신한 메시지를 저장하는 변수 // msg 화면에서 json 문자열로 전달됨

		JSONObject obj = jsonToObjectParser(msg);

		String roomNo = (String) obj.get("roomNumber");
		String sessionId = (String) obj.get("sessionId");
		String inputMessage = (String) obj.get("msg");
		String profileImg = (String) obj.get("profileImg");
		System.out.println(roomNo + sessionId + inputMessage + profileImg);

		ChatMessageDTO chatMessage = new ChatMessageDTO();
		chatMessage.setId(sessionId);
		chatMessage.setMessage(inputMessage);
		chatMessage.setRoom_number(Integer.parseInt(roomNo));
		chatService.insertChatMessage(chatMessage);

		HashMap<String, Object> temp = new HashMap<String, Object>();

		if (roomList.size() > 0) {
			for (int i = 0; i < roomList.size(); i++) {

				// rN : roomList에 저장된 채팅방 번호
				String rN = (String) roomList.get(i).get("roomNumber");
				if (rN.equals(roomNo)) {
					temp = roomList.get(i); // 리스트에 존재하는 방번호의 세션 리스트를 가져온다.
					break;
				}
			}
			// 선택된 방의 모든 사용자에게 메시지 송신
			for (String key : temp.keySet()) {

				if (key.equals("roomNumber")) { // 방번호는 session이 아니므로 건너뜀
					continue;
				}

				// 다른 사람에게 대화를 전달하려면 session 객체가 필요
				WebSocketSession was = (WebSocketSession) temp.get(key);

				if (was != null) {
					try {
						// was.sendMessage(new TextMessage(msg)); //대화방의 참여자에게 메시지 전송

						// 다른사람에게 메세지 전달 시, 다시 json 문자열로 변환하여 전송
						was.sendMessage(new TextMessage(obj.toJSONString()));

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	// 웹소켓 연결 종료 시 동작
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		// 소켓 종료

		if (roomList.size() > 0) {
			for (int i = 0; i < roomList.size(); i++) {
				roomList.get(i).remove(session.getId());
			}
		}
		super.afterConnectionClosed(session, status); // 세션 종료

		System.out.println("연결 종료 : " + session.getId());
	}

	// 제이슨형식의 문자열을 받아 java에서 사용하기 위한 JSONObject 객체로 해석해 주는 함수
	public static JSONObject jsonToObjectParser(String jsonStr) {
		JSONParser parser = new JSONParser(); // json 문자열 -> java 객체로 변환

		// 해석된 jsondata를 java 객체에 저장
		JSONObject obj = null;
		try {
			obj = (JSONObject) parser.parse(jsonStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return obj; // 변환된 객체 리턴
	}

}
