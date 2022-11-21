package com.cpt.study.lifecycle;

import com.cpt.study.model.TestUser;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class TestInit implements InitializingBean {

    private final ApplicationContext applicationContext;

    public TestInit(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("============================== InitializingBean");
        TestUser user = (TestUser) applicationContext.getBean("testUser");
        user.attack();
    }
}
