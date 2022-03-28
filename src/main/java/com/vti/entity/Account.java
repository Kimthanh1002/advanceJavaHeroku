package com.vti.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Formula;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@Table(name = "account")
@Data
@NoArgsConstructor
public class Account implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "id")
	@NonNull
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NonNull
	@Column(name = "username", length = 50, nullable = false, unique = true, updatable = false)
	private String username;

	@NonNull
	@Column(name = "password", length = 800, nullable = false)
	private String password;

	@NonNull
	@Column(name = "first_name", length = 50, nullable = false)
	private String firstName;

	@NonNull
	@Column(name = "last_name", length = 50, nullable = false)
	private String lastName;

	@Formula(" concat(first_name, ' ', last_name) ")
	private String fullName;

	@Column(name = "role")
	@Enumerated(EnumType.STRING)
	private Role role;
	
	@Column(name = "create_date")
	@Temporal(TemporalType.TIMESTAMP)
	@CreationTimestamp
	private Date createdDate;
	
	
	@ManyToOne
	@JoinColumn(name = "department_id")
	private Department department;

	public enum Role {
		EMPLOYEE,ADMIN, MANAGER
	}
}
