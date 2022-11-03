package com.cpt.study;


import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Arrays;

public class TestIocContainerApplication {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("application-context.xml");
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        System.out.println(Arrays.deepToString(beanDefinitionNames));

        UserService userService = (UserService) applicationContext.getBean("userService");
        System.out.println(userService.userRepository);
        userService.print();
    }
}
