package com.isul.dto;

import java.util.Date;

import lombok.Data;

@Data
public class MemberDTO {
	private String id;
	private String password;
	private String name;
	private String email;
	private String phone;
	private String zipCode;
	private String address1;
	private String address2;
	private String usage;
	private String contract;
	private Date createDate;
	
}
