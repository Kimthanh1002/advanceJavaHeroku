package com.vti.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.vti.entity.Account;
import com.vti.entity.Department;
import com.vti.entity.Department.Type;
import com.vti.form.department.CreatingDepartmentForm;
import com.vti.form.department.DeleteDepartmentsForm;
import com.vti.form.department.DepartmentFilterForm;
import com.vti.form.department.UpdatingDepartmentForm;
import com.vti.repository.IAccountRepository;
import com.vti.repository.IDepartmentRepository;
import com.vti.specification.department.DepartmentSpecification;

@Service
public class DepartmentService implements IDepartmentService {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private IDepartmentRepository repository;

	@Autowired
	private IAccountRepository accountRepository;

	public Page<Department> getAllDepartments(Pageable pageable, String search, DepartmentFilterForm filterForm) {

		// sort default total member desc
//		Pageable page = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("totalMember").descending());

		// build where
		Specification<Department> where = DepartmentSpecification.buildWhere(search, filterForm);
		return repository.findAll(where, pageable);
	}

	public Department getDepartmentByID(int id) {
		return repository.findById(id).get();
	}

	public List<Type> getAllTypes() {
		List<Type> types = new ArrayList<>();
		for (Type role : Type.values()) {
			types.add(role);
		}
		return types;
	}

	public void createDepartment(CreatingDepartmentForm form) {
		// get list account by list Id
		List<Account> accounts = accountRepository.findAllById(form.getAccountIds());

		// sửa lại totalMember cho department cũ khi các acc được add vào department mới
		for (Account acc : accounts) {
			if (acc.getDepartment() != null) {
				int oldTotalMember = acc.getDepartment().getTotalMember();
				acc.getDepartment().setTotalMember(oldTotalMember - 1);
			}
		}
		// convert form to entity
		Department departmentEntity = modelMapper.map(form, Department.class);

		// totalMember = sizeAccount
		departmentEntity.setTotalMember(accounts.size());
		departmentEntity.setAccounts(accounts);

		// create department
		Department department = repository.save(departmentEntity);

		// set account's department
		for (Account account : accounts) {
			account.setDepartment(department);
		}

		accountRepository.saveAll(accounts);
	}

	public void updateDepartment(UpdatingDepartmentForm form) {

		Department department = repository.getById(form.getId());
		// chỉ thay đổi Type
		department.setType(form.getType());

		repository.save(department);
	}

	public void deleteDepartment(int id) {
// trước khi xoá department thì set lại department của account thuộc nó nếu k acc cũng bị xoá theo 
		Department dep = repository.getById(id);
		dep.getAccounts().forEach(acc -> acc.setDepartment(null));
//		for (Account acc : dep.getAccounts()) {
//			acc.setDepartment(null);
//			accountRepository.save(acc);
//		} 
		repository.deleteById(id);
	}

	public void deleteDepartments(DeleteDepartmentsForm form) {
		// trước khi xoá department thì set lại department của account thuộc nó nếu k
		// acc cũng bị xoá theo
		List<Department> deps = repository.findAllById(form.getIds());
		deps.forEach(dep -> dep.getAccounts().forEach(acc -> acc.setDepartment(null)));

		repository.deleteAllById(form.getIds());
	}

	public boolean isDepartmentExistsByName(String name) {
		return repository.existsByName(name);
	}

	public boolean isDepartmentExistsByID(Integer id) {
		return repository.existsById(id);
	}

	public boolean isDepartmentExistsByIds(List<Integer> ids) {
		boolean isExists = true;
		for (Integer id : ids) {
			if (!repository.existsById(id)) {
				isExists = false;
				break;
			}
		}
		return isExists;

	}

}
