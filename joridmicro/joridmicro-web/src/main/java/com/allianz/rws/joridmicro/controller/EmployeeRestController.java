package com.allianz.rws.joridmicro.controller;

import java.util.List;

import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.allianz.rws.joridmicro.model.Employee;
import com.allianz.rws.joridmicro.model.dto.EmployeeDTO;
import com.allianz.rws.joridmicro.service.EmployeeService;
import com.allianz.rest.support.model.RESTResponseBean;
import com.google.common.base.Function;
import com.google.common.collect.Lists;

/**
 * Handles requests for the Employee service.
 */
@RestController
public class EmployeeRestController implements EmployeeRestApi {
	
	
	private static Logger logger = LoggerFactory.getLogger(EmployeeRestController.class);
	
	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private DozerBeanMapper mapper;
	
	@Override
	public @ResponseBody ResponseEntity<?> findOne(@PathVariable("id") int empId) {
		
		Employee employee = employeeService.findOne(empId);
		EmployeeDTO employeeDTO = mapper.map(employee, EmployeeDTO.class);
		logger.debug("findOne()" + empId);
		

		ResponseEntity<?> result = RESTResponseBean.builder()
				.success(true)
				.status(HttpStatus.OK)
				.data(employeeDTO)
				.buildResponseEntity();
		
		return result;
	}
	

	@Override
	public @ResponseBody ResponseEntity<?> findAll(
			@RequestParam(value="page", defaultValue="0") int page,
			@RequestParam(value="size", defaultValue="10") int size,
			@RequestParam(value="orderBy", required=false) List<String> orderBy) {
		
		List<Employee> employees = employeeService.findAll();
		List<EmployeeDTO> employeesDTO = transformToDto(employees);
		logger.debug("findAll()");
		
		ResponseEntity<?> result = RESTResponseBean.builder()
				.success(true)
				.status(HttpStatus.OK)
				.data(employeesDTO)
				.buildResponseEntity();
		
		return result;
	}
	

	@Override
	public @ResponseBody ResponseEntity<?> create(@RequestBody EmployeeDTO employeDTO) {
		
		Employee employee = mapper.map(employeDTO, Employee.class);
		employee = employeeService.create(employee);
		EmployeeDTO employeeDTO = mapper.map(employee, EmployeeDTO.class);
		
		ResponseEntity<?> result = RESTResponseBean.builder()
				.success(true)
				.status(HttpStatus.OK)
				.data(employeeDTO)
				.buildResponseEntity();
		
		return result;
	}
	
	@Override
	public ResponseEntity<?> update(@RequestBody EmployeeDTO employeDTO) {
		Employee employee = mapper.map(employeDTO, Employee.class);
		employee = employeeService.update(employee);
		EmployeeDTO employeeDTO = mapper.map(employee, EmployeeDTO.class);
		
		ResponseEntity<?> result = RESTResponseBean.builder()
				.success(true)
				.status(HttpStatus.OK)
				.data(employeeDTO)
				.buildResponseEntity();
		
		return result;
	}	
	

	@Override
	public @ResponseBody ResponseEntity<?> delete(@PathVariable("id") int empId)  {
		
		Employee employee = employeeService.delete(empId);
		EmployeeDTO employeeDTO = mapper.map(employee, EmployeeDTO.class);
		
		ResponseEntity<?> result = RESTResponseBean.builder()
				.success(true)
				.status(HttpStatus.OK)
				.data(employeeDTO)
				.buildResponseEntity();
		
		return result;
	}
	
	
	private List<EmployeeDTO> transformToDto(List<Employee> employees){
		List<EmployeeDTO> result = Lists.transform(employees, new Function<Employee, EmployeeDTO>() {
			public EmployeeDTO apply(Employee employee) {
				return mapper.map(employee, EmployeeDTO.class);
			}
		});
		
		return result;
	}



	
	
	
}
