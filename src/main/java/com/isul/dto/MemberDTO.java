package com.isul.dto;

import lombok.Data;

@Data
public class MemberDTO {
	private String id;
	private String password;
	private String name;
	private String phone;
	private String email;
	private String zipCode;
	private String address1;
	private String address2;

}
