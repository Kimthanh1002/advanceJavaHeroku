package com.vti.specification.department;

import java.util.Date;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import com.vti.entity.Department;
import com.vti.form.department.DepartmentFilterForm;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

public class DepartmentSpecification {

	@SuppressWarnings("deprecation")
	public static Specification<Department> buildWhere(String search, DepartmentFilterForm filterForm) {
		
		Specification<Department> where = null;
		
		if (!StringUtils.isEmpty(search)) {
			search = search.trim();
			CustomSpecification name = new CustomSpecification("name", search);
			where = Specification.where(name);
		}
				
		// if there is filter by min created date
		if (filterForm != null && filterForm.getMinCreatedDate() != null) {
			CustomSpecification minCreatedDate = new CustomSpecification("minCreatedDate", filterForm.getMinCreatedDate());
			if (where == null) {
				where = minCreatedDate;
			} else {
				where = where.and(minCreatedDate);
			}
		}		
				
		// if there is filter by max created date
		if (filterForm != null && filterForm.getMaxCreatedDate() != null) {
			CustomSpecification maxCreatedDate = new CustomSpecification("maxCreatedDate", filterForm.getMaxCreatedDate());
			if (where == null) {
				where = maxCreatedDate;
			} else {
				where = where.and(maxCreatedDate);
			}
		}
		
		// if there is filter by type
		if (filterForm != null && filterForm.getType() != null) {
			CustomSpecification type = new CustomSpecification("type", filterForm.getType());
			if (where == null) {
				where = type;
			} else {
				where = where.and(type);
			}
		}
		
		return where;
	}
}

@SuppressWarnings("serial")
@RequiredArgsConstructor
class CustomSpecification implements Specification<Department> {

	@NonNull
	private String field;
	@NonNull
	private Object value;

	@Override
	public Predicate toPredicate(
			Root<Department> root, 
			CriteriaQuery<?> query, 
			CriteriaBuilder criteriaBuilder) {

		if (field.equalsIgnoreCase("name")) {
			return criteriaBuilder.like(root.get("name"), "%" + value.toString() + "%");
		}
		
		if (field.equalsIgnoreCase("minCreatedDate")) {
			return criteriaBuilder.greaterThanOrEqualTo(
					root.get("createdDate").as(java.sql.Date.class),
					(Date) value);
		}
	
		if (field.equalsIgnoreCase("maxCreatedDate")) {
			return criteriaBuilder.lessThanOrEqualTo(
					root.get("createdDate").as(java.sql.Date.class),
					(Date) value);
		}
		
		if (field.equalsIgnoreCase("type")) {
			return criteriaBuilder.equal(root.get("type"), value);
		}
		
		return null;
	}
}

