package com.allianz.rws.joridmicro.controller;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.allianz.rws.joridmicro.ITTestBase;
import com.allianz.rest.support.model.AllianzContextEPACBean;
import com.allianz.rest.support.model.LoginBean;
import com.allianz.rest.support.model.OAuthTokensBean;
import com.allianz.rest.support.service.OAuthLoginService;
import com.allianz.rest.support.util.AllianzContextHolder;

@Component
public class ITTestHelper {
	
	private static final String MY_WEBSERVICE_HOST = "http://localhost:8080";
	private static final String COMPANY_ID = "ALZ";
	
	private String token;
	
	@Autowired
	private OAuthLoginService oAuthLoginService;
	
	@PostConstruct
	private void setToken(){
		
		
		LoginBean loginInfo = new LoginBean();
		loginInfo.setClientId("INTRANET");
		loginInfo.setClientSecret("f8739f23-61ea-4df7-b70b-853e9b6eb0f3");
		loginInfo.setGrantType("password");
		loginInfo.setUsername("ALDEVRR0");
		loginInfo.setPassword("despwd");
		
		AllianzContextEPACBean epacBean = new AllianzContextEPACBean();
		epacBean.setBaseUrl(ITTestBase.ARCH_WEBSERVICES_HOST);
		epacBean.setCompanyId(COMPANY_ID);
		
		AllianzContextHolder.setContext(epacBean);		
		
    	OAuthTokensBean tokens = oAuthLoginService.login(loginInfo);
        
        AllianzContextHolder.getContext().setOAuthTokens(tokens);
        AllianzContextHolder.getContext().setBaseUrl(MY_WEBSERVICE_HOST);
		
    	token = tokens.getAccessToken();
	}
	
	public String getToken(){
		return token;
	}
}

