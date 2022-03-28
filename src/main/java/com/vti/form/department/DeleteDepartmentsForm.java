package com.vti.form.department;

import java.util.List;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.UniqueElements;

import com.vti.validation.department.DepartmentIdsExists;

import lombok.Data;

@Data
public class DeleteDepartmentsForm {
	
	@UniqueElements
	@NotEmpty
	@DepartmentIdsExists
	private List<Integer> ids;
}
