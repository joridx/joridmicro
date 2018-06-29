package com.allianz.rws.joridmicro.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.allianz.rws.joridmicro.model.Employee;
import com.allianz.rws.joridmicro.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	private static Logger log = LoggerFactory.getLogger(EmployeeServiceImpl.class);
	private Map<Integer, Employee> empData = new HashMap<Integer, Employee>();
	
    @PostConstruct
    public void initService() {
    	
        Employee employee1 = new Employee();
        employee1.setId(111000);
        employee1.setName("Pepe Rubianes");
        create(employee1);
        
        Employee employee2 = new Employee();
        employee2.setId(222000);
        employee2.setName("Miguel Gila");
        create(employee2);
        
        Employee employee3 = new Employee();
        employee3.setId(333000);
        employee3.setName("Chiquito de la Calzada");
        create(employee3);   
        
        Employee employee4 = new Employee();
        employee4.setId(444000);
        employee4.setName("Joaquin Reyes");
        create(employee4);        
        
    }	
	
	@Override
	public Employee findOne(@PathVariable("id") int empId) {
		log.info("findOne()");		
		return empData.get(empId);
	}
	

	@Override
	public List<Employee> findAll() {
		log.info("findAll()");		
		return new ArrayList<Employee>(empData.values());
	}
	

	@Override
	public Employee create(Employee emp) {
		emp.setCreatedDate(new Date());
		empData.put(emp.getId(), emp);
		return emp;
	}
	
	@Override
	public Employee update(Employee emp) {
		empData.get(emp.getId()).setName(emp.getName());
		return empData.get(emp.getId());
	}
	

	@Override
	public Employee delete(int empId) {
		return empData.remove(empId);
	}
	
	
}
