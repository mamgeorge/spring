package com.basics.dbsqlite.configuration;

import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

/*
	https://www.baeldung.com/spring-boot-sqlite
	https://www.baeldung.com/the-persistence-layer-with-spring-and-jpa
*/
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories( basePackages = "com.basics.dbsqlite.services" )
@PropertySource( "classpath:application.yml" )
public class GeneralConfiguration {

	@Autowired Environment environment;

	@Bean
	public DataSource dataSource( ) {

		DataSourceBuilder DSB = DataSourceBuilder.create();
		String driverClassName =  environment.getProperty("spring.datasource.driver-class-name");
		String jdbcUrl = environment.getProperty("spring.datasource.url");
		System.out.println("#### driverCN: " + driverClassName);
		System.out.println("#### jdbcUrl: " + jdbcUrl);
		DSB.driverClassName(driverClassName);
		DSB.url(jdbcUrl);

		// note that DataSource can also be built from DMDS DriverManagerDataSource
		// return DataSourceBuilder.create().build();
		DataSource dataSource = DSB.build();
		return dataSource;
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory( ) {

		LocalContainerEntityManagerFactoryBean LCEMFB = new LocalContainerEntityManagerFactoryBean();
		JpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
		String packagesToScan = "com.basics.dbsqlite.services";

		LCEMFB.setDataSource(dataSource());
		LCEMFB.setPackagesToScan(packagesToScan);
		LCEMFB.setJpaVendorAdapter(jpaVendorAdapter);
		LCEMFB.setJpaProperties(getProperties());
		return LCEMFB;
	}

	// hibernateDialect must call hardcoded SqliteDialect
	// hibernateHbm2ddlAuto must call none (since the DB exists)
	@NotNull final Properties getProperties( ) {

		final Properties properties = new Properties();
		String springUrl = environment.getProperty("spring.datasource.url");
		String springUsername = environment.getProperty("spring.datasource.username");
		String springPassword = environment.getProperty("spring.datasource.password");
		String springDriver = environment.getProperty("spring.datasource.driver-class-name");

		String hibernateAuto = environment.getProperty("spring.jpa.hibernate.ddl-auto");
		String hibernateDialect = environment.getProperty("spring.jpa.properties.hibernate.dialect");
		String hibernateShowSql = "true"; //environment.getProperty("hibernate.show_sql");
		System.out.println("#### hibernateDialect: " + hibernateDialect);

		if ( hibernateAuto != null ) { properties.setProperty("hibernate.hbm2ddl.auto", hibernateAuto); }
		if ( hibernateDialect != null ) { properties.setProperty("hibernate.dialect", hibernateDialect); }
		if ( hibernateShowSql != null ) { properties.setProperty("hibernate.show_sql", hibernateShowSql); }

		return properties;
	}
}