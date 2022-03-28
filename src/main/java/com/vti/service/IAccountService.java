package com.vti.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.vti.entity.Account;
import com.vti.entity.Account.Role;
import com.vti.form.account.AccountFilterForm;
import com.vti.form.account.CreatingAccountForm;
import com.vti.form.account.DeleteAccountsForm;
import com.vti.form.account.UpdateAccountForm;
import com.vti.form.account.UpdateUserForm;

public interface IAccountService extends UserDetailsService{

	public Page<Account> getAllAccounts(Pageable pageable, String search, AccountFilterForm filterForm);

	public Account getAccountByID(int id);
	
	public List<Role> getAllRoles();
	
	public void createAccount(CreatingAccountForm form);
	
	public void updateAccount(UpdateAccountForm form);
	
	public void updateUser(UpdateUserForm form);
	
	public Account getAccountById(int id);
	
	public void deleteAccount(int id);
	
	public void deleteAccounts(DeleteAccountsForm form);
	
	public boolean isAccountExistsByUserName(String userName);
	
	public boolean isAccountExistsById(int id);
	
	public boolean isAccountExistsByIds(List<Integer> ids);
	
	public Account getAccountByUsername(String username);
}
