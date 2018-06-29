package com.allianz.rws.joridmicro.controller;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.allianz.rws.joridmicro.ITTestBase;
import com.allianz.rws.joridmicro.configuration.SpringBoot;
import com.allianz.rws.joridmicro.model.dto.EmployeeDTO;

import com.allianz.rest.support.model.RESTResponseBean;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = { ITTestHelper.class, SpringBoot.class })
public class EmployeeControllerTestIT extends ITTestBase {

	public static final String EMPLOYEES_RESOURCE = "/api/employees";
	
	@Autowired
	private ITTestHelper iTTestHelper;
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	private static final HttpHeaders HEADERS = new HttpHeaders();
	
	@Before
	public void before(){
		HEADERS.add("Authorization", "Bearer " + iTTestHelper.getToken());
	}

	
	@Test
	public void testFindAll() {
		
		HttpEntity<String> entity = new HttpEntity<String>(HEADERS);
		//we can't get List<Employee> because JSON convertor doesn't know the type of
		//object in the list and hence convert it to default JSON object type LinkedHashMap
		ResponseEntity<String> result = restTemplate.exchange(
				EMPLOYEES_RESOURCE, HttpMethod.GET, entity, String.class);

		assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
	}
	
	
	private ResponseEntity<RESTResponseBean<EmployeeDTO>> create(EmployeeDTO employee){
		
		HttpEntity<EmployeeDTO> entity = new HttpEntity<EmployeeDTO>(employee, HEADERS);
		
		ResponseEntity<RESTResponseBean<EmployeeDTO>> result = restTemplate
				.exchange(
						EMPLOYEES_RESOURCE,
						HttpMethod.POST,
						entity,
						new ParameterizedTypeReference<RESTResponseBean<EmployeeDTO>>() {
						});
		
		return result;
	}
	
	
	private ResponseEntity<RESTResponseBean<EmployeeDTO>> find(final Integer id) {

		HttpEntity<EmployeeDTO> entity = new HttpEntity<EmployeeDTO>(null,
				HEADERS);

		ResponseEntity<RESTResponseBean<EmployeeDTO>> result = restTemplate.exchange(
				EMPLOYEES_RESOURCE + "/" + id, HttpMethod.GET, entity,
				new ParameterizedTypeReference<RESTResponseBean<EmployeeDTO>>() {
				});

		return result;
	}
	
	private ResponseEntity<RESTResponseBean<EmployeeDTO>> delete(
			final Integer id) {
		
		HttpEntity<String> request = new HttpEntity<String>(HEADERS);

		// Once is create it then delete it
		ResponseEntity<RESTResponseBean<EmployeeDTO>> result = restTemplate
				.exchange(
						EMPLOYEES_RESOURCE + "/" + id,
						HttpMethod.DELETE,
						request,
						new ParameterizedTypeReference<RESTResponseBean<EmployeeDTO>>() {
						});
		
		return result;
	}
	
	
	
	@Test
	public void testCreate() {
		
		EmployeeDTO employee = new EmployeeDTO();
		employee.setId(1);
		employee.setName("Pepe Rubianes");
		
		
		ResponseEntity<RESTResponseBean<EmployeeDTO>> result = create(employee);
		
		assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(result.getBody().getData().getName()).isEqualTo(employee.getName());
		
	}
	
	@Test
	public void testFindOne() {
		
		final Integer ID = 2;
		
		EmployeeDTO employee = new EmployeeDTO();
		employee.setId(ID);
		employee.setName("Peter Pan");
		
		ResponseEntity<RESTResponseBean<EmployeeDTO>> result;
		
		create(employee);
		
		result = find(ID);
		
		assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(result.getBody().getData().getId()).isEqualTo(ID);

	}

	@Test
	public void testDelete() {
		
		final Integer ID = 3;
		
		EmployeeDTO employee = new EmployeeDTO();
		employee.setId(ID);
		employee.setName("Hari Seldon");
		
		ResponseEntity<RESTResponseBean<EmployeeDTO>> result;
		
		create(employee);
		
		result = delete(ID);
		
		assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(result.getBody().getData().getId()).isEqualTo(ID);
		
	}
	
}
