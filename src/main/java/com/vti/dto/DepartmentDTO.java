package com.vti.dto;

import java.util.Date;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DepartmentDTO extends RepresentationModel<DepartmentDTO> {
	
	private int id;

	private String name;

	private Integer totalMember;
	
	private String type;

	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date createdDate;

}

