package com.allianz.rws.joridmicro.configuration;

import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

import com.allianz.rest.support.config.LegacyLoggingApplicationListener;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication(scanBasePackages = "com.allianz.rws.joridmicro,com.allianz.rest.support.service", exclude = { DataSourceAutoConfiguration.class })
@EnableSwagger2
public class SpringBoot extends SpringBootServletInitializer implements
		CommandLineRunner {

	@Override
	public void run(String... arg0) throws Exception {
		if (arg0.length > 0 && arg0[0].equals("exitcode")) {
			throw new ExitException();
		}
	}

	public static void main(String[] args) throws Exception {
		SpringApplication app = new SpringApplication(SpringBoot.class);
		app.setBannerMode(Banner.Mode.LOG);
		app.run(args);
	}

	/**
	 * Used when run as WAR
	 */
	@Override
	protected SpringApplicationBuilder configure(
			SpringApplicationBuilder builder) {
	    return builder.listeners(new LegacyLoggingApplicationListener())
	            .bannerMode(Banner.Mode.LOG).sources(SpringBoot.class);
	}

	private static class ExitException extends RuntimeException implements ExitCodeGenerator {
		private static final long serialVersionUID = 1L;

		@Override
		public int getExitCode() {
			return 10;
		}

	}
}