package com.cpt.study;

import com.cpt.work.TestBoss;
import com.cpt.work.TestComputer;
import com.cpt.work.TestWorker;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Bean
    public TestComputer testComputer() {
        return new TestComputer();
    }

    @Bean
    public TestWorker testWorker() {
        return new TestWorker(testComputer());
    }

    @Bean
    public TestBoss testBoss() {
        return new TestBoss(testComputer());
    }

    /*@Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setDefaultPropertyInclusion(JsonInclude.Include.NON_NULL);
        return objectMapper;
    }*/
}
