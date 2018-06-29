package com.allianz.rws.joridmicro.service;

import java.util.List;

import com.allianz.rws.joridmicro.model.Employee;

public interface EmployeeService {

	Employee findOne(int empId);

	List<Employee> findAll();

	Employee create(Employee emp);

	Employee delete(int empId);

	Employee update(Employee emp);

}
