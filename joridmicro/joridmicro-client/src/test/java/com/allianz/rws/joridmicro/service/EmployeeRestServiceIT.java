package com.allianz.rws.joridmicro.service;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.allianz.rws.joridmicro.model.dto.EmployeeDTO;
import com.allianz.rws.joridmicro.service.EmployeeRestService;
import com.allianz.rest.support.model.AllianzContextEPACBean;
import com.allianz.rest.support.model.LoginBean;
import com.allianz.rest.support.model.OAuthTokensBean;
import com.allianz.rest.support.service.OAuthLoginService;
import com.allianz.rest.support.util.AllianzCompanyConverter;
import com.allianz.rest.support.util.AllianzContextHolder;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/test-client-config.xml")
public class EmployeeRestServiceIT {
	
	private static Logger logger = LoggerFactory.getLogger(EmployeeRestServiceIT.class);
	
	private static final String MY_WEBSERVICE_HOST = "http://localhost:8080";
	private static final String OAUTH_HOST = "http://wwwd.es.intrallianz.com";
	
	private AllianzContextEPACBean epacBean;
	
	@Autowired 
	private EmployeeRestService employeeRestService;
	
	@Autowired OAuthLoginService loginService;
	
	@Rule
    public ExpectedException thrown = ExpectedException.none();
	
	
	private static final EmployeeDTO EMPLOYEE_1;
	
	static {
		EMPLOYEE_1 = new EmployeeDTO();
		EMPLOYEE_1.setName("Makinavaja");
		EMPLOYEE_1.setId(423423423);
	}
	
	
	@Before
	public void before(){

		LoginBean loginInfo = new LoginBean();
		loginInfo.setClientId("INTRANET");
		loginInfo.setClientSecret("f8739f23-61ea-4df7-b70b-853e9b6eb0f3");
		loginInfo.setGrantType("password");
		loginInfo.setUsername("ALDEVRR0");
		loginInfo.setPassword("despwd");
		
		epacBean = new AllianzContextEPACBean();
		epacBean.setBaseUrl(OAUTH_HOST);
		epacBean.setCompanyId(AllianzCompanyConverter.COD_ALLIANZ);
		
		AllianzContextHolder.setContext(epacBean);		
		
    	OAuthTokensBean tokens = loginService.login(loginInfo);
        
        AllianzContextHolder.getContext().setOAuthTokens(tokens);
        AllianzContextHolder.getContext().setBaseUrl(MY_WEBSERVICE_HOST);
        
		
		employeeRestService.create(EMPLOYEE_1);
	}
	
	@After
	public void after(){
		employeeRestService.delete(EMPLOYEE_1.getId());
	}
	
	@Test
	public void create(){
		
		EmployeeDTO employee = new EmployeeDTO();
		employee.setName("Indurain");
		employee.setId(21212);
		
		logger.info("Creating user: " + employee.getName());
		
		EmployeeDTO result = employeeRestService.create(employee);
		
		Assert.assertEquals(employee.getName(), result.getName() );
		Assert.assertEquals(employee.getId(), result.getId());
		Assert.assertNotNull(result.getCreatedDate());
	}
	
    @Test
	public void findOne() {
    	
    	EmployeeDTO result = employeeRestService.findOne(EMPLOYEE_1.getId());
    	
		Assert.assertEquals(EMPLOYEE_1.getName(), result.getName());
		Assert.assertEquals(EMPLOYEE_1.getId(), result.getId() );
		Assert.assertNotNull(result.getCreatedDate());
    }
    
    @Test
	public void delete() {
    	
		EmployeeDTO employee = new EmployeeDTO();
		employee.setName("Mazinger");
		employee.setId(12313);
		
		employeeRestService.create(employee);
    	
    	EmployeeDTO result = employeeRestService.delete(employee.getId());
    	
		Assert.assertEquals(employee.getName(), result.getName());
		Assert.assertEquals(employee.getId(), result.getId() );
		Assert.assertNotNull(result.getCreatedDate());
    }
    
    @Test
	public void findAll() {
        List<EmployeeDTO> result = employeeRestService.findAll();
		
		Assert.assertTrue(result.size() > 0);
		
	}
    
}
