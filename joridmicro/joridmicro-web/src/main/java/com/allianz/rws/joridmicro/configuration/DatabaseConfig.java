/*
package com.allianz.rws.joridmicro.configuration;

import java.util.HashMap;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

import org.hibernate.MultiTenancyStrategy;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.hibernate.engine.jdbc.connections.spi.AbstractDataSourceBasedMultiTenantConnectionProviderImpl;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.allianz.rws.joridmicro.configuration.AppConfig.DbConnection;
import com.allianz.rest.support.model.AllianzContextEPACBean;
import com.allianz.rest.support.util.AllianzCompanyConverter;
import com.allianz.rest.support.util.AllianzContextHolder;

@Configuration
@EnableTransactionManagement
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableJpaRepositories(
	basePackages = "com.allianz.rws.joridmicro.repository", 
	entityManagerFactoryRef = "entityManager", 
	transactionManagerRef = "transactionManager")
@Profile({ "database" })
public class DatabaseConfig {

	private static final String MODEL_PACKAGE = "com.allianz.rws.joridmicro.model";

	@Autowired
	private MultiTenantConnectionProvider multiTenantConnectionProvider;
	
	@Autowired
	private CurrentTenantIdentifierResolver currentTenantIdentifierResolver;
	
	@SuppressWarnings("serial")
	protected static class DatasourcesMap extends HashMap<String, DataSource>{
		
	}

	private String getCurrentCompany() {
		String result;
		AllianzContextEPACBean context = AllianzContextHolder.getContext();
		if (context == null || context.getCompanyId() == null) {
			result = AllianzCompanyConverter.COD_ALLIANZ;
		} else {
			result = context.getCompanyId().toUpperCase();
		}
		return result;
	}
	
	
	@Bean(name = "dataSource")
	public DataSource getDefaultDataSource(DatasourcesMap datasources) {
		return datasources.get(getCurrentCompany());
	}
	
	@Bean(name = "datasources")
	public DatasourcesMap getDatasources(
			AppConfig appconfig
			) {
		
		DatasourcesMap result = new DatasourcesMap();
		
		for( DbConnection connection: appconfig.getConnections()) {
			result.put(connection.getId(), createDataSource(connection));
		}

		return result;
	}
	

	private SimpleDriverDataSource createDataSource(DbConnection config) {
		SimpleDriverDataSource simpleDriverDataSource = new SimpleDriverDataSource();
		simpleDriverDataSource.setDriverClass(com.ibm.db2.jcc.DB2Driver.class);
		simpleDriverDataSource.setUrl(config.getUrl());
		simpleDriverDataSource.setUsername(config.getUsername());
		simpleDriverDataSource.setPassword(config.getPassword());
		return simpleDriverDataSource;
	}

	@Bean("entityManager")
	@PersistenceContext(unitName = "remote")
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(DatasourcesMap datasources, EntityManagerFactoryBuilder builder) {
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();

		em.setDataSource(datasources.get(getCurrentCompany()));
		em.setPackagesToScan(new String[] { MODEL_PACKAGE });

		JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();

		em.setJpaVendorAdapter(vendorAdapter);
		em.setJpaProperties(additionalProperties());
		em.setPersistenceUnitName("remote");

		return em;
	}

	@Bean(name = "transactionManager")
	public PlatformTransactionManager transactionManager(DatasourcesMap datasources, EntityManagerFactory emf) {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(emf);
		transactionManager.setDataSource(datasources.get(getCurrentCompany()));
		return transactionManager;
	}

	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
		return new PersistenceExceptionTranslationPostProcessor();
	}

	private Properties additionalProperties() {
		Properties properties = new Properties();
		
		properties.setProperty("hibernate.archive.autodetection", "class");
		properties.setProperty("hibernate.bytecode.use_reflection_optimizer", "false");
		properties.setProperty("hibernate.dialect", "org.hibernate.dialect.DB2Dialect");
		properties.setProperty("hibernate.show_sql", "false");
		properties.setProperty("hibernate.hbm2ddl.auto", "");
		properties.setProperty("hibernate.cache.provider_class", "org.hibernate.cache.HashtableCacheProvider");
		
		properties.put(org.hibernate.cfg.Environment.MULTI_TENANT, MultiTenancyStrategy.DATABASE);
		properties.put(org.hibernate.cfg.Environment.MULTI_TENANT_CONNECTION_PROVIDER, multiTenantConnectionProvider);
		properties.put(org.hibernate.cfg.Environment.MULTI_TENANT_IDENTIFIER_RESOLVER, currentTenantIdentifierResolver);
		properties.put(org.hibernate.cfg.Environment.DIALECT, "org.hibernate.dialect.HSQLDialect");

		return properties;
	}

	@Component
	public class DataSourceBasedMultiTenantConnectionProviderImpl extends
			AbstractDataSourceBasedMultiTenantConnectionProviderImpl {

		private static final long serialVersionUID = 1L;
	
		@Autowired
		@Qualifier("datasources")
		private DatasourcesMap datasources;


		@Override
		protected DataSource selectAnyDataSource() {
			return datasources.get(getCurrentCompany());
		}

		@Override
		protected DataSource selectDataSource(String tenantIdentifier) {
			return datasources.get(tenantIdentifier);
		}

	}

	@Component
	public class CurrentTenantIdentifierResolverImpl implements CurrentTenantIdentifierResolver {

		@Override
		public String resolveCurrentTenantIdentifier() {
			return getCurrentCompany();
		}

		@Override
		public boolean validateExistingCurrentSessions() {
			return true;
		}

	}

}
*/