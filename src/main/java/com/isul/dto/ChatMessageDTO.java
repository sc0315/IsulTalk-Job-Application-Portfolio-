package com.isul.dto;

import java.util.Date;

import lombok.Data;

@Data
public class ChatMessageDTO {
	public int room_number;
	public String message;
	public String id;
	public String message_check;
	public Date message_createdate;
	public String profile_img;
	public String nick_name;
}
