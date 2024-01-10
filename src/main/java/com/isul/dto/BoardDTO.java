package com.isul.dto;

import java.util.Date;

import lombok.Data;

@Data
public class BoardDTO {
	
	private String board_number;
	private String board_title;            
	private String board_content;           
	private String board_writer;  
	private Date board_createdate;       
	private Date board_updatedate;        
	private String board_secret;  
	private int reply_count;
	
}
