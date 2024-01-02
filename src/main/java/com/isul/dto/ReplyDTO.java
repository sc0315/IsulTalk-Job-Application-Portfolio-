package com.isul.dto;

import java.util.Date;

import lombok.Data;

@Data
public class ReplyDTO {
	private String board_number;
	private String reply_number;
	private String reply_content;
	private String reply_writer;
	private Date reply_createDate;
	private Date reply_updateDate;
	private String reply_secret;
	private String reply_ref;
	private int reply_deep;
	private int reply_level;
}
