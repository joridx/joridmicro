package com.allianz.rws.joridmicro.service;

import java.util.List;

import com.allianz.rws.joridmicro.model.dto.EmployeeDTO;

public interface EmployeeRestService {

	EmployeeDTO findOne(int empId);

	List<EmployeeDTO> findAll();

	EmployeeDTO create(EmployeeDTO emp);

	EmployeeDTO delete(int empId);

}
