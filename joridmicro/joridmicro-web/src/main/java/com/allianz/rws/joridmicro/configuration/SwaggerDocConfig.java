package com.allianz.rws.joridmicro.configuration;

import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;

import com.allianz.rest.support.config.SwaggerDocConfigBase;

@Configuration
public class SwaggerDocConfig extends SwaggerDocConfigBase {
	
	private static final String SWAGGER_BASEPACKAGE ="com.allianz.rws.joridmicro.controller";

	@Override
	public ApiInfo apiInfo() {
		return new ApiInfoBuilder()
			.title("BASE Service api")
			.description("Interface to the backend services for BASE")
			.license("")
			.licenseUrl("")
			.termsOfServiceUrl("")
			.version("0.0.1-SNAPSHOT")
			.contact(new Contact("", "", ""))
			.build();
	}

	@Override
	protected String getBasePackage() {
		return SWAGGER_BASEPACKAGE;
	}

}
