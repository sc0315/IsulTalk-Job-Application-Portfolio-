package com.isul.chat;


// 채팅방의 정보를 저장하는 클래스
public class Room {
	int roomNumber;
	
	String roomName;
	
	public int getRoomNumber() {
		return roomNumber;
	}
	public void setRoomNumber(int roomnumber) {
		this.roomNumber = roomnumber;
	}
	public String getRoomName() {
		return roomName;
	}
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
	@Override
	public String toString() {
		return "Room [roomNumber=" + roomNumber + ", roomName=" + roomName + "]";
	}
	
}
