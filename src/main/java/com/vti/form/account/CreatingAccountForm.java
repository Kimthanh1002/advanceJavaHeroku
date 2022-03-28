package com.vti.form.account;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import com.vti.validation.account.AccountUsernameNotExists;
import com.vti.validation.department.DepartmentIDExists;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class CreatingAccountForm {
	@NonNull
	@NotBlank(message = "{Department.createDepartment.form.name.NotBlank}")
	@Length(min=5,max = 50, message = "{Department.createDepartment.form.name.Length} ")
	@AccountUsernameNotExists
//	@Pattern(regexp = "^[A-Za-z0-9]+$",message = "{Department.createDepartment.form.name.Pattern}")
	private String username;
	
	@NonNull
	@NotBlank(message = "{Department.createDepartment.form.name.NotBlank}")
	@Length(min=2,max = 50, message = "{Department.createDepartment.form.name.Length} ")
//	@Pattern(regexp = "[A-Za-z ]+$",message = "{Department.createDepartment.form.name.Pattern}")
	private String firstName;
	
	@NonNull
	@NotBlank(message = "{Department.createDepartment.form.name.NotBlank}")
	@Length(min=2,max = 50, message = "{Department.createDepartment.form.name.Length} ")
//	@Pattern(regexp = "[A-Za-z ]+$",message = "{Department.createDepartment.form.name.Pattern}")
	private String lastName;
	
	@NonNull
	@Pattern(regexp = "ADMIN|MANAGER|EMPLOYEE", message = "The type must be ADMIN, MANAGER, or EMPLOYEE")
	private String role;
	
	@DepartmentIDExists
	private Integer departmentId;

}

