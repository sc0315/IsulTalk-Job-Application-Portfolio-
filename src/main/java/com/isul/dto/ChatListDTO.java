package com.isul.dto;

import java.util.Date;

import lombok.Data;

@Data
public class ChatListDTO {
	public String room_number;
	public String id;
	public String profile_img;
	public String background_img;
	public String nick_name;
	public String status_message;
	public String message;
	public String message_check;
	public Date message_createdate;
}
