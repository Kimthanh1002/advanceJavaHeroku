package com.vti.controller;


import java.util.List;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vti.dto.AccountDTO;
import com.vti.entity.Account;
import com.vti.form.account.AccountFilterForm;
import com.vti.form.account.CreatingAccountForm;
import com.vti.form.account.DeleteAccountsForm;
import com.vti.form.account.UpdateAccountForm;
import com.vti.form.account.UpdateUserForm;
import com.vti.service.IAccountService;

@RestController
@RequestMapping(value = "api/v1/accounts")
public class AccountController {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private IAccountService service;
	
	@GetMapping()
	public ResponseEntity<?>  getAllAccounts(
			Pageable pageable, 
			@RequestParam(value = "search", required = false) String search,
			AccountFilterForm filterForm) {

		Page<Account> entityPages = service.getAllAccounts(pageable, search, filterForm);

		// convert entities --> dtos
		List<AccountDTO> dtos = modelMapper.map(
				entityPages.getContent(), 
				new TypeToken<List<AccountDTO>>() {}.getType());

		Page<AccountDTO> dtoPages = new PageImpl<>(dtos, pageable, entityPages.getTotalElements());

		return new ResponseEntity<>(dtoPages, HttpStatus.OK);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<?> getAccountByID(@PathVariable(name = "id") int id) {
		Account entity = service.getAccountByID(id);

		// convert entity to dto
		AccountDTO dto = modelMapper.map(entity, AccountDTO.class);

		return new ResponseEntity<>(dto, HttpStatus.OK);
	}
	@GetMapping(value = "/userName/{userName}/exists")
	public ResponseEntity<?> existsByUserName(@PathVariable(name = "userName") String userName) {
		return new ResponseEntity<>(service.isAccountExistsByUserName(userName), HttpStatus.OK);
	}
	@GetMapping(value = "/roles")
	public ResponseEntity<?> getAllRoles(){
		return new ResponseEntity<>(service.getAllRoles(), HttpStatus.OK);
	}

	@PostMapping(value = "/admin/post")
	public ResponseEntity<?> createAccount(@RequestBody @Valid CreatingAccountForm form) {
		service.createAccount(form);
		return new ResponseEntity<String>("Create successfully!", HttpStatus.CREATED);
	}
	

	@PutMapping(value = "/admin/put/{id}")
	public ResponseEntity<?> updateAccount(@PathVariable(name = "id") int id,
			@RequestBody @Valid UpdateAccountForm form) {
		form.setId(id);
		service.updateAccount(form);
		return new ResponseEntity<String>("Update successfully!", HttpStatus.OK);
	}
	
	
	@PutMapping(value = "/user/{id}")
	public ResponseEntity<?> updateUser(@PathVariable(name = "id") int id,
			@RequestBody @Valid UpdateUserForm form) {
		form.setId(id);
		service.updateUser(form);
		return new ResponseEntity<String>("Update User successfully!", HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/admin/delete/{id}")
	public void deleteAccount(@PathVariable(name = "id") int id) {
		service.deleteAccount(id);
	}
	
	@DeleteMapping(value = "/admin/delete")
	public void deleteAccounts(@RequestBody @Valid DeleteAccountsForm form)  {
		service.deleteAccounts(form);
	}
	


}

