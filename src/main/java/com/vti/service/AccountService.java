package com.vti.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.transaction.Transactional;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.vti.entity.Account;
import com.vti.entity.Account.Role;
import com.vti.entity.Department;
import com.vti.form.account.AccountFilterForm;
import com.vti.form.account.CreatingAccountForm;
import com.vti.form.account.DeleteAccountsForm;
import com.vti.form.account.UpdateAccountForm;
import com.vti.form.account.UpdateUserForm;
import com.vti.repository.IAccountRepository;
import com.vti.repository.IDepartmentRepository;
import com.vti.specification.account.AccountSpecification;

@Service
public class AccountService implements IAccountService {

	@Autowired
	private IAccountRepository repository;

	@Autowired
	private IDepartmentRepository depRepository;

	@Autowired
	private ModelMapper modelMapper;

	public Page<Account> getAllAccounts(Pageable pageable, String search, AccountFilterForm filterForm) {
		// sort default createDate Desc
//		Pageable page = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
//				Sort.by("createdDate").descending());
		Specification<Account> where = AccountSpecification.buildWhere(search, filterForm);
		return repository.findAll(where, pageable);
	}

	public Account getAccountByID(int id) {
		return repository.findById(id).get();
	}

	public List<Role> getAllRoles() {
		List<Role> roles = new ArrayList<>();
		for (Role role : Role.values()) {
			roles.add(role);
		}
		return roles;
	}

	@Transactional
	public void createAccount(CreatingAccountForm form) {
		TypeMap<CreatingAccountForm, Account> typeMap = modelMapper.getTypeMap(CreatingAccountForm.class,
				Account.class);
		if (typeMap == null) {
			modelMapper.addMappings(new PropertyMap<CreatingAccountForm, Account>() {
				@Override
				protected void configure() {
					skip(destination.getId());
				}
			});
		}

		Account account = modelMapper.map(form, Account.class);
		String passDefault = "123456";
		// convert to Bcrypt
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(passDefault);
		// set password mặc đinh là 123456
		account.setPassword(hashedPassword);

		// set lại totalmember cho department vì thêm 1 acc
		Department dep = depRepository.getDepartmentById(form.getDepartmentId());
		depRepository.getDepartmentById(form.getDepartmentId()).setTotalMember(dep.getTotalMember() + 1);

		repository.save(account);
	}

	public void updateAccount(UpdateAccountForm form) {
		
		if (isAccountExistsById(form.getId()) == true) {

			// trừ đi 1 cho totalMember của department cũ nếu acc có dep khác null
			Account acc = repository.getAccountById(form.getId());

			if (acc.getDepartment() != null) {

				int oldDepId = acc.getDepartment().getId();
				Department dep = depRepository.getDepartmentById(oldDepId);
				depRepository.getDepartmentById(oldDepId).setTotalMember(dep.getTotalMember() - 1);
			}
			// convert form to Entity
			Account accEntity = modelMapper.map(form, Account.class);
			accEntity.setCreatedDate(acc.getCreatedDate());
			accEntity.setPassword(acc.getPassword());

			// thêm 1 cho totalMember của Department mới
			Department dep1 = depRepository.getDepartmentById(form.getDepartmentId());
			depRepository.getDepartmentById(form.getDepartmentId()).setTotalMember(dep1.getTotalMember() + 1);

			repository.save(accEntity);

		} else {
			System.out.println("Account not exist");
		}
	}

	public void updateUser(UpdateUserForm form) {
		
		if (isAccountExistsById(form.getId()) == true) {

			Account acc = repository.getAccountById(form.getId());
			// cần kiểm tra pass thuộc acc có Id được nhập vào đã đúng chưa khi user muốn
			// thay đổi dữ liệu
			//vì pass được lưu vào dataBase đã được  mã hoá theo BCrypt nên phải check theo mã BCrypt 
			if (BCrypt.checkpw(form.getPassword(),acc.getPassword())) {
				
				// convert password to Bcrypt
				PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
				String hashedPassword = passwordEncoder.encode(form.getNewPass());
				form.setPassword(hashedPassword);
				// convert form to Entity
				Account accEntity = modelMapper.map(form, Account.class);

				// fill infor form don't have
				accEntity.setCreatedDate(acc.getCreatedDate());
				accEntity.setPassword(hashedPassword); // set pass bằng pass mới đã được mã hoá trc khi cho vào database
				accEntity.setRole(acc.getRole());
				accEntity.setDepartment(acc.getDepartment());
				accEntity.setUsername(acc.getUsername());

				repository.save(accEntity);
			} else {
				System.out.println("wrong password ");
			}

		} else {
			System.out.println("account not  exists ");
		}
	}

	public void deleteAccount(int id) {
		repository.deleteById(id);
	}

	public void deleteAccounts(DeleteAccountsForm form) {
		repository.deleteAllById(form.getIds());
	}
//public int sendEmailWhenForgetPass( String toEmail) {
//	int code = 0;
//    try {            
//        Email email = new SimpleEmail();
//        String myEmail = "hieunghi123@gmail.com";
//        String pass = "Hieunghi123";
//        
//        code = (int) Math.floor(((Math.random() * 899999) + 100000));
//        // Cấu hình thông tin Email Server
//        email.setHostName("smtp.googlemail.com");
//        email.setSmtpPort(465);
//        email.setAuthenticator(new DefaultAuthenticator(myEmail,pass));
//        
//        // Với gmail cái này là bắt buộc.
//        email.setSSLOnConnect(true);
//        
//        // Người gửi
//        email.setFrom(myEmail);
//        
//        // Tiêu đề
//        email.setSubject("OTP change password  ");
//        
//        // Nội dung email
//        email.setMsg("mã OTP của bạn là " + code);
//        
//        // Người nhận
//        email.addTo(toEmail);            
//        email.send();
//        System.out.println("Sent!!");
//    } catch (Exception e) {
//        e.getStackTrace();
//    }
//    return code;
//}
//	
//public void changePass() {
//	
//}
	
	
	public Account getAccountById(int id) {
		return repository.getAccountById(id);
	}

	public boolean isAccountExistsByUserName(String userName) {
		return repository.existsByUsername(userName);
	}

	public boolean isAccountExistsById(int id) {
		return repository.existsById(id);
	}

	public boolean isAccountExistsByIds(List<Integer> ids) {
		boolean isExists = true;
		for (Integer id : ids) {
			if (!repository.existsById(id)) {
				isExists = false;
				break;
			}
		}
		return isExists;

	}

	public boolean isAccountExistsByUsername(String username) {
		return repository.existsByUsername(username);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Account account = repository.findByUsername(username);

		if (account == null) {
			throw new UsernameNotFoundException(username);
		}

		return new User(account.getUsername(), account.getPassword(),
				AuthorityUtils.createAuthorityList(account.getRole().toString()));
	}

	@Override
	public Account getAccountByUsername(String username) {
		return repository.findByUsername(username);
	}

}
