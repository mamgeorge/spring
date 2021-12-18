package com.example.sb_webflux.configuration;

import java.util.concurrent.Executors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

@Configuration(proxyBeanMethods = false)
public class ConfigurationAny {

	@Value("${spring.datasource.maximum-pool-size}") private int connectionPoolSize;

	@Bean public Scheduler jdbcScheduler() {
		return Schedulers.fromExecutor(Executors.newFixedThreadPool(connectionPoolSize));
	}

	@Bean public TransactionTemplate transactionTemplate(PlatformTransactionManager PTM) {
		return new TransactionTemplate(PTM);
	}
}