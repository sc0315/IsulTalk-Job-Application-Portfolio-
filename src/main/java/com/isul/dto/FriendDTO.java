package com.isul.dto;

import lombok.Data;

@Data
public class FriendDTO {
	public String id;
	public String friend_id;
	public int friend_approval;
	public int friend_block;
}
