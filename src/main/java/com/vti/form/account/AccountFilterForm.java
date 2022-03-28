package com.vti.form.account;


import com.vti.entity.Account.Role;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AccountFilterForm {

	private Role  role;
	
	private String  departmentName;

}

