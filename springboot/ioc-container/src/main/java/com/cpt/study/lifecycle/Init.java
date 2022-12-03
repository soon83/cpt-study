package com.cpt.study.lifecycle;

import com.cpt.study.model.User;
import com.cpt.work.Boss;
import com.cpt.work.Computer;
import com.cpt.work.Worker;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class Init implements InitializingBean, ApplicationRunner {

    private final ApplicationContext applicationContext;

    public Init(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("============================== InitializingBean");
        User user = (User) applicationContext.getBean("testUser");
        user.attack();
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("============================== ApplicationRunner");
        User user = (User) applicationContext.getBean("testUser");
        user.attack();

        Computer computer = (Computer) applicationContext.getBean("testComputer");
        System.out.println("computer = " + computer);
        Worker worker = (Worker) applicationContext.getBean("testWorker");
        System.out.println("worker = " + worker);
        System.out.println("worker.computer = " + worker.getComputer());
        Boss boss = (Boss) applicationContext.getBean("testBoss");
        System.out.println("boss = " + boss);
        System.out.println("boss.computer = " + boss.getComputer());
    }

    @PostConstruct
    public void run() throws Exception {
        System.out.println("============================== PostConstruct");
        User user = (User) applicationContext.getBean("user");
        user.attack();
    }
}
