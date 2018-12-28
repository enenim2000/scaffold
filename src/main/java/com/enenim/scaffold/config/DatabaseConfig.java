package com.enenim.scaffold.config;

import com.enenim.scaffold.service.BaseRepositoryService;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        repositoryBaseClass = BaseRepositoryService.class,
        basePackages = {"com.enenim.scaffold.model.dao", "com.enenim.scaffold.repository.dao", "com.enenim.scaffold.service.dao"}
)
public class DatabaseConfig{

}