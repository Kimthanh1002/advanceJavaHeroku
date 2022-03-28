package com.vti.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.vti.entity.Department;
import com.vti.entity.Department.Type;
import com.vti.form.department.CreatingDepartmentForm;
import com.vti.form.department.DeleteDepartmentsForm;
import com.vti.form.department.DepartmentFilterForm;
import com.vti.form.department.UpdatingDepartmentForm;

public interface IDepartmentService {

	public Page<Department> getAllDepartments(Pageable pageable, String search, DepartmentFilterForm filterForm);

	public Department getDepartmentByID(int id);
	
	public List<Type> getAllTypes();

	public void createDepartment(CreatingDepartmentForm form);

	public void updateDepartment(UpdatingDepartmentForm form);
	
	public void deleteDepartment(int id);
	
	public void deleteDepartments(DeleteDepartmentsForm form);

	public boolean isDepartmentExistsByName(String name);

	public boolean isDepartmentExistsByID(Integer id);
	
	public boolean isDepartmentExistsByIds(List<Integer> ids);
}
