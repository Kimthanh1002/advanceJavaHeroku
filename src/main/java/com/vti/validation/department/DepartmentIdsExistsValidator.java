package com.vti.validation.department;

import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.vti.service.IDepartmentService;

public class DepartmentIdsExistsValidator implements ConstraintValidator<DepartmentIdsExists, List<Integer>> {

	@Autowired
	private IDepartmentService service;

	@SuppressWarnings("deprecation")
	@Override
	public boolean isValid(List<Integer> ids, ConstraintValidatorContext constraintValidatorContext) {

		if (StringUtils.isEmpty(ids )) {
			return true;
		}

		return service.isDepartmentExistsByIds(ids);
	}


}

