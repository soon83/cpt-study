package com.cpt.study.lifecycle;

import com.cpt.study.model.TestUser;
import com.cpt.work.TestBoss;
import com.cpt.work.TestComputer;
import com.cpt.work.TestWorker;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class TestInit implements InitializingBean, ApplicationRunner {

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

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("============================== ApplicationRunner");
        TestUser user = (TestUser) applicationContext.getBean("testUser");
        user.attack();

        TestComputer computer = (TestComputer) applicationContext.getBean("testComputer");
        System.out.println("computer = " + computer);
        TestWorker worker = (TestWorker) applicationContext.getBean("testWorker");
        System.out.println("worker = " + worker);
        System.out.println("worker.computer = " + worker.getTestComputer());
        TestBoss boss = (TestBoss) applicationContext.getBean("testBoss");
        System.out.println("boss = " + boss);
        System.out.println("boss.computer = " + boss.getTestComputer());
    }

    @PostConstruct
    public void run() throws Exception {
        System.out.println("============================== PostConstruct");
        TestUser user = (TestUser) applicationContext.getBean("testUser");
        user.attack();
    }
}
