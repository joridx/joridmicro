package com.allianz.rws.joridmicro.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;

import com.allianz.rest.support.config.SecurityConfigBase;

@Configuration
@EnableWebSecurity
@EnableAspectJAutoProxy(proxyTargetClass = true)
@Profile({"security"})
public class SecurityConfig extends SecurityConfigBase {
	
	@Override
    public void configure( WebSecurity web ) throws Exception {
        web.ignoring()
        	.antMatchers(
	        	"/",
	            "/secure/info",            
	            "/secure/health",        	
	            "/swagger-ui.html", 
	            "/swagger-resources/**",
	            "/webjars/**", 
	            "/api-docs/**");
    }
	
    @Override
    protected void configure(HttpSecurity http) throws Exception {
		http
			.exceptionHandling()
				.accessDeniedHandler(oauthAccessDeniedHandler)
				.authenticationEntryPoint(oauthAuthenticationEntryPoint)
				.and()
            .authorizeRequests()
                .antMatchers("/api/**").authenticated()
                .and()
            .sessionManagement()
            	.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
            .logout()
                .permitAll();
    }
    
}
