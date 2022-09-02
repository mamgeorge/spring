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
@EnableJpaRepositories( basePackages = "com.basics.securing.services" )
@PropertySource( "classpath:application.yml" )
public class GeneralConfiguration {

	@Autowired Environment environment;

	// @Primary, @ConfigurationProperties( prefix = "spring.datasource" ) // had issues
	@Bean public DataSource dataSource( ) {

		DataSourceBuilder DSB = DataSourceBuilder.create();
		String driverClassName = environment.getProperty("driverClassName");
		String jdbcUrl = "jdbc:sqlite:" + environment.getProperty("jdbcUrl");
		System.out.println("#### driverCN: " + driverClassName);
		System.out.println("#### jdbcUrl: " + jdbcUrl);
		DSB.driverClassName(driverClassName);
		DSB.url(jdbcUrl);

		// note that DataSource can also be built from DMDS DriverManagerDataSource
		// return DataSourceBuilder.create().build();
		DataSource dataSource = DSB.build();
		return dataSource;
	}

	@Bean public LocalContainerEntityManagerFactoryBean entityManagerFactory( ) {

		LocalContainerEntityManagerFactoryBean LCEMFB = new LocalContainerEntityManagerFactoryBean();
		JpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
		String packagesToScan = "com.basics.securing.services";

		LCEMFB.setDataSource(dataSource());
		LCEMFB.setPackagesToScan(packagesToScan);
		LCEMFB.setJpaVendorAdapter(jpaVendorAdapter);
		LCEMFB.setJpaProperties(getProperties());
		return LCEMFB;
	}

	// hibernateDialect must call SqliteDialect
	// hibernateHbm2ddlAuto nust call none (since the DB exists)
	@NotNull final Properties getProperties( ) {

		final Properties properties = new Properties();
		String hibernateHbm2ddlAuto = environment.getProperty("hibernate.hbm2ddl.auto");
		String hibernateDialect = environment.getProperty("hibernate.dialect");
		String hibernateShowSql = environment.getProperty("hibernate.show_sql");
		System.out.println("#### hibernateDialect: " + hibernateDialect);

		if ( hibernateHbm2ddlAuto != null ) {
			properties.setProperty("hibernate.hbm2ddl.auto", hibernateHbm2ddlAuto);
		}
		if ( hibernateDialect != null ) { properties.setProperty("hibernate.dialect", hibernateDialect); }
		if ( hibernateShowSql != null ) { properties.setProperty("hibernate.show_sql", hibernateShowSql); }

		return properties;
	}
}
