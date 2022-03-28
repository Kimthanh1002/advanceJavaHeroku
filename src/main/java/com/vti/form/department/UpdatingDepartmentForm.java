package com.vti.form.department;

import javax.validation.constraints.Pattern;

import com.vti.entity.Department.Type;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdatingDepartmentForm {
	
	private  int id;
	
	@Pattern(regexp = "DEV|TEST|PM|ScrumMaster", message = "The type must be DEV, TEST, PM or ScrumMaster")
	private Type type;

}
