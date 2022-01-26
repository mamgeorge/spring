package com.example.embedded.configuration;

import com.example.embedded.model.Employee;
import com.example.embedded.model.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.gemfire.CacheFactoryBean;
import org.springframework.data.gemfire.LocalRegionFactoryBean;
import org.springframework.data.gemfire.repository.config.EnableGemfireRepositories;
import  org.apache.geode.cache.GemFireCache;

import java.util.Properties;

// basePackages = "com.baeldung.spring.data.gemfire.repository"
// basePackages = "com.baeldung.spring.data.gemfire.function"
//@Configuration @EnableGemfireRepositories()
public class GemfireConfiguration {

	//@Autowired EmployeeRepository employeeRepository;

	//@Bean
	Properties gemfireProperties() {
		Properties properties = new Properties();
		properties.setProperty("name","SpringDataGemFireApplication");
		properties.setProperty("mcast-port", "0");
		properties.setProperty("log-level", "config");
		return properties;
	}

	//@Bean
	CacheFactoryBean gemfireCache() {
		CacheFactoryBean cacheFactoryBean = new CacheFactoryBean();
		cacheFactoryBean.setClose(true);
		cacheFactoryBean.setProperties(gemfireProperties());
		return cacheFactoryBean;
	}

	//@Bean(name="employee")
	LocalRegionFactoryBean<String, Employee> getEmployee(final GemFireCache gemFireCache) {
		LocalRegionFactoryBean<String, Employee> employeeRegion = new LocalRegionFactoryBean();
		employeeRegion.setCache(gemFireCache);
		employeeRegion.setName("employee");
		// ...
		return employeeRegion;
	}
}
