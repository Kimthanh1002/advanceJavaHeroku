package com.vti.form.department;

import java.util.Date;

import javax.validation.constraints.Pattern;

import org.springframework.format.annotation.DateTimeFormat;

import com.vti.entity.Department;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DepartmentFilterForm {
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date minCreatedDate;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date maxCreatedDate;
	
	@Pattern(regexp = "DEV|TEST|PM|ScrumMaster", message = "The type must be DEV, TEST, PM or ScrumMaster")
	private Department.Type type;

}

