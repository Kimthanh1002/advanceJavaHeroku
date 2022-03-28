package com.vti.form.account;

import java.util.List;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.UniqueElements;

import com.vti.validation.account.AccountIdsExists;

import lombok.Data;

@Data
public class DeleteAccountsForm {
	
	@UniqueElements
	@NotEmpty
	@AccountIdsExists
	private List<Integer> ids;
}
