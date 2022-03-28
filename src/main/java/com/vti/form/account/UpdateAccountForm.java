package com.vti.form.account;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;

import org.hibernate.validator.constraints.Length;

import com.vti.validation.account.AccountIDExists;
import com.vti.validation.department.DepartmentIDExists;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class UpdateAccountForm {
	
	@NonNull
	@PositiveOrZero( message = "{Department.createDepartment.form.name.PositiveOrZero}" )
	@AccountIDExists
	private Integer id;
	
	@NotBlank(message = "{Department.createDepartment.form.name.NotBlank}")
	@Length(min=5,max = 30, message = "{Department.createDepartment.form.name.Length} ")
	@Pattern(regexp = "[a-zA-Z0-9][a-zA-Z0-9]+",message = "{Department.createDepartment.form.name.Pattern}")
	private String username;
	
	@NotBlank(message = "{Department.createDepartment.form.name.NotBlank}")
	@Length(min=2,max = 50, message = "{Department.createDepartment.form.name.Length} ")
	@Pattern(regexp = "[a-zA-Z][a-zA-Z ]+",message = "{Department.createDepartment.form.name.Pattern}")
	private String firstName;
	
	@NotBlank(message = "{Department.createDepartment.form.name.NotBlank}")
	@Length(min=2,max = 50, message = "{Department.createDepartment.form.name.Length} ")
	@Pattern(regexp = "[a-zA-Z][a-zA-Z ]+",message = "{Department.createDepartment.form.name.Pattern}")
	private String lastName;
	
	@NonNull
	@Pattern(regexp = "ADMIN|MANAGER|EMPLOYEE", message = "The type must be ADMIN, MANAGER, or EMPLOYEE")
	private String role;
	
	@NonNull
	@DepartmentIDExists
	private Integer departmentId;
}

