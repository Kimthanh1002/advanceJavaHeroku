package com.vti.form.account;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;

import org.hibernate.validator.constraints.Length;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class UpdateUserForm {
	
	@NonNull
	@PositiveOrZero( message = "{Department.createDepartment.form.name.PositiveOrZero}" )
	private Integer id;
	
	@NotBlank(message = "{Department.createDepartment.form.name.NotBlank}")
	@Length(min=2,max = 50, message = "{Department.createDepartment.form.name.Length} ")
//	@Pattern(regexp = "[a-zA-Z][a-zA-Z ]+",message = "{Department.createDepartment.form.name.Pattern}")
	private String firstName;
	
	@NotBlank(message = "{Department.createDepartment.form.name.NotBlank}")
	@Length(min=2,max = 50, message = "{Department.createDepartment.form.name.Length} ")
//	@Pattern(regexp = "[a-zA-Z][a-zA-Z ]+",message = "{Department.createDepartment.form.name.Pattern}")
	private String lastName;
	
	@NotBlank(message = "{Department.createDepartment.form.name.NotBlank}")
	@Length(min=6,max = 8, message = "{Department.createDepartment.form.name.LengthPass} ")
	private  String password;

	@NotBlank(message = "{Department.createDepartment.form.name.NotBlank}")
	@Length(min=6,max = 8, message = "{Department.createDepartment.form.name.LengthPass} ")
	private String newPass;

}
