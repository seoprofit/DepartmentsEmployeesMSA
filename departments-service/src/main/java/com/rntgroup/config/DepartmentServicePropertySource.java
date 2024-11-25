package com.rntgroup.config;


import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Getter
@Configuration
@PropertySource("classpath:/application.yml")
public class DepartmentServicePropertySource {
    @Value("${app.default-value-salary-fund}")
    private Double defaultValueSalaryFund;
    @Value("${app.topic-name}")
    private String topicName;
}
