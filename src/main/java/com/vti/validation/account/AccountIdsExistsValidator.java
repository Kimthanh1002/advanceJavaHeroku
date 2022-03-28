package com.vti.validation.account;

import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.vti.service.IAccountService;

public class AccountIdsExistsValidator implements ConstraintValidator<AccountIdsExists, List<Integer>> {

	@Autowired
	private IAccountService service;

	@SuppressWarnings("deprecation")
	@Override
	public boolean isValid(List<Integer> ids, ConstraintValidatorContext constraintValidatorContext) {

		if (StringUtils.isEmpty(ids )) {
			return true;
		}

		return service.isAccountExistsByIds(ids);
	}


}

