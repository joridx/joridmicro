package com.allianz.rws.joridmicro.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.allianz.rws.joridmicro.ClientConfig;
import com.allianz.rws.joridmicro.model.dto.EmployeeDTO;
import com.allianz.rws.joridmicro.service.EmployeeRestService;
import com.allianz.rest.support.model.RESTResponseBean;
import com.allianz.rest.support.util.AllianzContextHolder;
import com.allianz.rest.support.util.AllianzRestTemplate;

@Service
public class EmployeeRestServiceImpl implements EmployeeRestService {
	
	private static final String EMPLOYEES_RESOURCE = ClientConfig.API_BASE_URL + "/employees";
	
	@Autowired
	private AllianzRestTemplate allianzRestTemplate;
	
	public List<EmployeeDTO> findAll() {
		Map<String, Object> urlVariables = new HashMap<String, Object>();
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("Authorization", "Bearer " + AllianzContextHolder.getContext().getOAuthTokens().getAccessToken());
		
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);
		
		ResponseEntity<RESTResponseBean<List<EmployeeDTO>>> response = allianzRestTemplate
				.exchange(
						EMPLOYEES_RESOURCE,
						HttpMethod.GET,
						entity,
						new ParameterizedTypeReference<RESTResponseBean<List<EmployeeDTO>>>() {
						}, urlVariables);
		
		List<EmployeeDTO> userPage = response.getBody().getData();
		
		return userPage;
	}
	
	@Override
	public EmployeeDTO findOne(int employeeId) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("Authorization", "Bearer " + AllianzContextHolder.getContext().getOAuthTokens().getAccessToken());

		
		HttpEntity<EmployeeDTO> requestEntity = new HttpEntity<EmployeeDTO>(null, headers);
		Map<String, Object> urlVariables = new HashMap<String, Object>();
		
		ResponseEntity<RESTResponseBean<EmployeeDTO>> response = allianzRestTemplate
				.exchange(
						EMPLOYEES_RESOURCE + "/" + employeeId,
						HttpMethod.GET,
						requestEntity,
						new ParameterizedTypeReference<RESTResponseBean<EmployeeDTO>>() {
						}, urlVariables);
		
		return response.getBody().getData();
	}

	@Override
	public EmployeeDTO create(EmployeeDTO emp) {
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("Authorization", "Bearer " + AllianzContextHolder.getContext().getOAuthTokens().getAccessToken());
		
		HttpEntity<EmployeeDTO> requestEntity = new HttpEntity<EmployeeDTO>(emp, headers);
		Map<String, Object> urlVariables = new HashMap<String, Object>();
		
		ResponseEntity<RESTResponseBean<EmployeeDTO>> response = allianzRestTemplate
				.exchange(
						EMPLOYEES_RESOURCE,
						HttpMethod.POST,
						requestEntity,
						new ParameterizedTypeReference<RESTResponseBean<EmployeeDTO>>() {
						}, urlVariables);
		
		return response.getBody().getData();
	}

	@Override
	public EmployeeDTO delete(int employeeId) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("Authorization", "Bearer " + AllianzContextHolder.getContext().getOAuthTokens().getAccessToken());
		
		HttpEntity<EmployeeDTO> requestEntity = new HttpEntity<EmployeeDTO>(null, headers);
		Map<String, Object> urlVariables = new HashMap<String, Object>();


		ResponseEntity<RESTResponseBean<EmployeeDTO>> response = allianzRestTemplate
				.exchange(
						EMPLOYEES_RESOURCE + "/" + employeeId,
						HttpMethod.DELETE,
						requestEntity,
						new ParameterizedTypeReference<RESTResponseBean<EmployeeDTO>>() {
						}, urlVariables);
		
		return response.getBody().getData();
	}
	
	


}
