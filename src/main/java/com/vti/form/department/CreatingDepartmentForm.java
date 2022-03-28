package com.vti.form.department;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

import com.vti.validation.account.AccountIdsExists;
import com.vti.validation.department.DepartmentNameNotExists;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreatingDepartmentForm {

	@NotBlank(message = "{Department.createDepartment.form.name.NotBlank}")
	@Length(min=1,max = 50, message = "{Department.createDepartment.form.name.Length} ")
//	@Pattern(regexp = "[A-Za-z0-9 ]+$",message = "{Department.createDepartment.form.name.Pattern}")
	@DepartmentNameNotExists
	private String name;

	@Pattern(regexp = "DEV|TEST|PM|ScrumMaster", message = "The type must be DEV, TEST, PM or ScrumMaster")
	private String type;
	
	@NotEmpty(message = "{Department.createDepartment.form.name.NotBlank}")
	@AccountIdsExists
	private List<Integer> accountIds;

}
