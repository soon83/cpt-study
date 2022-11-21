package com.cpt.study.lifecycle;

import com.cpt.study.model.TestUser;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class TestPostConstruct {

    private final ApplicationContext applicationContext;

    public TestPostConstruct(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @PostConstruct
    public void run() throws Exception {
        System.out.println("============================== PostConstruct");
        TestUser user = (TestUser) applicationContext.getBean("testUser");
        user.attack();
    }
}
