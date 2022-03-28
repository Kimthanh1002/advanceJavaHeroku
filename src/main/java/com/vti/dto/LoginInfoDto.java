package com.vti.dto;

import com.vti.entity.Account.Role;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class LoginInfoDto {
	
	@NonNull
	private Integer id;

	@NonNull
	private String fullName;
	
	@NonNull
	private Role  role;

}
