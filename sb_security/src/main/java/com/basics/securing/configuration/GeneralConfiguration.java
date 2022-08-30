package com.basics.securing.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;
import java.util.Properties;

/*
	SpringBoot does not automatically configure Sqlite:
	1) add sqlite datasource configs to app.yml (driverClassName, jdbcUrl)
	2) create DataSource bean (reading from app.yml)
	3) create sqlite class extending Dialect to registerColumnType; referenced in app.yml
		hibernate.dialect=com.basics.securing.configuration.SqliteDialect
	4) modify Dialect class to disable constraints (extra 5 methods)
	5) create sqlite class extending IdentityColumnSupportImpl to handle @Id columns; also referenced in Dialect
	6) assumes Model class has @Column names corrected, and table name with schema prefix if any
	7) assumes table column datatypes match repository & services

*/
@Configuration
@EnableJpaRepositories( basePackages = "com.basics.securing.services" )
@PropertySource( "classpath:application.yml" )
public class GeneralConfiguration {

	@Autowired Environment environment;

	@Bean
	public DataSource dataSource( ) {

		DataSourceBuilder DSB = DataSourceBuilder.create();
		String driverClassName = environment.getProperty("driverClassName");
		String jdbcUrl = "jdbc:sqlite:" + environment.getProperty("jdbcUrl");
		System.out.println("#### driverCN: " + driverClassName);
		System.out.println("#### jdbcUrl: " + jdbcUrl);
		DSB.driverClassName(driverClassName);
		DSB.url(jdbcUrl);
		// DSB.username(environment.getProperty("user"));
		// DSB.password(environment.getProperty("password"));

		// note that DataSource can also be built from DMDS DriverManagerDataSource
		DataSource dataSource = DSB.build();
		return dataSource;
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory( ) {

		final LocalContainerEntityManagerFactoryBean LCEMFB = new LocalContainerEntityManagerFactoryBean();
		LCEMFB.setDataSource(dataSource());
		LCEMFB.setPackagesToScan("com.basics.securing.services");
		LCEMFB.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		LCEMFB.setJpaProperties(getProperties());
		return LCEMFB;
	}

	final Properties getProperties( ) {

		final Properties properties = new Properties();
		if ( environment.getProperty("hibernate.hbm2ddl.auto") != null ) {
			properties.setProperty("hibernate.hbm2ddl.auto",
				environment.getProperty("hibernate.hbm2ddl.auto"));
		}
		if ( environment.getProperty("hibernate.dialect") != null ) {
			properties.setProperty("hibernate.dialect",
				environment.getProperty("hibernate.dialect"));
		}
		if ( environment.getProperty("hibernate.show_sql") != null ) {
			properties.setProperty("hibernate.show_sql",
				environment.getProperty("hibernate.show_sql"));
		}
		return properties;
	}
//	@Bean @ConfigurationProperties( prefix = "spring.datasource" ) // @Primary
//	public DataSource getDataSource( ) { return DataSourceBuilder.create().build(); }
}
