package com.allianz.rws.joridmicro.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.allianz.rws.joridmicro.model.dto.EmployeeDTO;
import com.allianz.rest.support.model.RESTResponseBean;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = {"employee"})
@RequestMapping(path = { "/api/employees", "/api/v1/employees" }, produces = {MediaType.APPLICATION_JSON_VALUE} )
public interface EmployeeRestApi {

	@SuppressWarnings("serial")
	final static class EmployeeDTOResponse extends RESTResponseBean<EmployeeDTO> {
	}; 
		
	@SuppressWarnings("serial")
	final static class EmployeeDTOListResponse extends RESTResponseBean<List<EmployeeDTO>> {
	}; 
	
	@ApiOperation(
			value = "Find employee by ID", 
		    notes = "Returns a employe with the given ID",
		    response = EmployeeDTOResponse.class)
	  	@ApiResponses(
	  		value = { 
	  			@ApiResponse(code = 500, message = "Internal server error"),
	  			@ApiResponse(code = 404, message = "Person not found") 
	  		})
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	ResponseEntity<?> findOne(int empId);

	@ApiOperation(
			value = "List employees", 
		    notes = "Returns the list of employees",
		    response = EmployeeDTOListResponse.class)
	  	@ApiResponses(
	  		value = { 
	  			@ApiResponse(code = 500, message = "Internal server error")
	  		})	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> findAll(
			@RequestParam(value="page", defaultValue="0") int page,
			@RequestParam(value="size", defaultValue="10") int size,
			@RequestParam(value="orderBy", required=false) List<String> orderBy);

	
	@ApiOperation(
			value = "Create employee ", 
		    notes = "Create the employee with the given fields",
		    response = EmployeeDTOResponse.class)
	  	@ApiResponses(
	  		value = { 
	  			@ApiResponse(code = 500, message = "Internal server error"),
	  		})
	@RequestMapping(method = RequestMethod.POST)
	ResponseEntity<?> create(EmployeeDTO emp);
	
	@ApiOperation(
			value = "Update an employee", 
		    notes = "Update the employee with the given fields",
		    response = EmployeeDTOResponse.class)
	  	@ApiResponses(
	  		value = { 
	  			@ApiResponse(code = 500, message = "Internal server error"),
	  		})
	@RequestMapping(value="/id", method = RequestMethod.PUT)
	ResponseEntity<?> update(EmployeeDTO emp);	

	@ApiOperation(
			value = "Delete employee by ID", 
		    notes = "Delete the employee with the given ID from the repository",
		    response = EmployeeDTOResponse.class)
	  	@ApiResponses(
	  		value = { 
	  			@ApiResponse(code = 500, message = "Internal server error"),
	  			@ApiResponse(code = 404, message = "Person not found") 
	  		})
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	ResponseEntity<?> delete(int empId);
	
}
