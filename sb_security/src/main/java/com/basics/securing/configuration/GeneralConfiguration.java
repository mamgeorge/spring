package com.basics.securing.configuration;

import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;
import java.util.Properties;

/*
	https://www.baeldung.com/spring-boot-sqlite

	SpringBoot does not automatically configure Sqlite:
	#1 application.yml: store sqlite configs
		#a add sqlite datasource configs driverClassName & jdbcUrl to yml
		#b add hibernate configs dialect & auto
	#2 GeneralConfiguration: write Configuration file to create sqlite beans
		#a create DataSource bean that reads configs from yml
		#b create LocalContainerEntityManagerFactoryBean bean that reads configs from yml
	#3 SqliteDialect: create sqlite class extending Dialect
		#a must define registerColumnTypes for all used table column types
		#b must add Override to handle Identity Column (1 method)
		#c must add Overrides to disable constraints (extra 5 methods)
		#d referenced in app.yml (hibernate.dialect=com.basics...SqliteDialect)
	#4 SqliteIdentityColumnSupportImpl: create sqlite class extending IdentityColumnSupportImpl
		# has 3 overrides to handle @Id columns; also referenced in SqliteDialect
	#6 assumes Model class matches sqlite schema
		#a has all column names corrected: @Column(name = "CustomerId",updatable = false, nullable = false)
		#b has table name with schema prefix if any: @Table( name = "main.customers" )
	#7 assumes table column datatypes match
		#a repository used Integer oblject, not Long
		#b services used int primitives not long
*/
@Configuration
@EnableJpaRepositories( basePackages = "com.basics.securing.services" )
@PropertySource( "classpath:application.yml" )
public class GeneralConfiguration {

	@Autowired Environment environment;

	@Bean
	// @Primary
	// @ConfigurationProperties( prefix = "spring.datasource" ) // had issues
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
		// return DataSourceBuilder.create().build();
		DataSource dataSource = DSB.build();
		return dataSource;
	}

	// @Bean
	public DataSource dataSourceAlternate() {

		System.out.println("#### using: DriverManagerDataSource" );
		final DriverManagerDataSource DMDS = new DriverManagerDataSource();

		String driverClassName = environment.getProperty("driverClassName");
		String jdbcUrl = "jdbc:sqlite:" + environment.getProperty("jdbcUrl");
		System.out.println("#### driverCN: " + driverClassName);
		System.out.println("#### jdbcUrl: " + jdbcUrl);

		DMDS.setDriverClassName(driverClassName);
		DMDS.setUrl(jdbcUrl);

		// DMDS.setUsername(environment.getProperty("user"));
		// DMDS.setPassword(environment.getProperty("password"));

		return DMDS;
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

	@NotNull
	final Properties getProperties( ) {

		final Properties properties = new Properties();
		String hibernateHbm2ddlAuto = environment.getProperty("hibernate.hbm2ddl.auto");
		String hibernateDialect = environment.getProperty("hibernate.dialect");
		String hibernateShowSql = environment.getProperty("hibernate.show_sql");
		System.out.println("#### hibernateDialect: " + hibernateDialect);

		if ( hibernateHbm2ddlAuto != null ) { properties.setProperty("hibernate.hbm2ddl.auto", hibernateHbm2ddlAuto); }
		if ( hibernateDialect != null ) { properties.setProperty("hibernate.dialect", hibernateDialect); }
		if ( hibernateShowSql != null ) { properties.setProperty("hibernate.show_sql", hibernateShowSql); }

		return properties;
	}
}
