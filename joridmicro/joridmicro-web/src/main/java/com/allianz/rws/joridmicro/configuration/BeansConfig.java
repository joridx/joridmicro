package com.allianz.rws.joridmicro.configuration;

import java.util.ArrayList;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.allianz.rest.support.util.AllianzRestTemplate;

@Configuration
public class BeansConfig {

	@Bean(name = "beanMapper")
	public DozerBeanMapper getDozerBeanMapper() {
		DozerBeanMapper ref = new DozerBeanMapper();
		List<String> mappingFileUrls = new ArrayList<String>();
		mappingFileUrls.add("dozer-bean-mappings.xml");
		ref.setMappingFiles(mappingFileUrls);
		return ref;
	}
	
    @Bean(name = "allianzRestTemplate")
    public AllianzRestTemplate getAllianzRestTemplate() {
    	return new AllianzRestTemplate();
    }	

}
