package com.vti.dto;

import java.sql.Date;

import org.springframework.hateoas.RepresentationModel;

import com.vti.entity.Account.Role;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class AccountDTO extends RepresentationModel<DepartmentDTO> {
	private int id;

	private String username;

	private String fullName;
	
	private String firstName;
	
	private String lastName;

	private Role role;

	private String departmentName;

	private Date createdDate;
}
