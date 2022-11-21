package com.cpt.study;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
//@ComponentScan(basePackages = "com.cpt.study")
//@ComponentScan(basePackageClasses = SpringbootIocContainerApplication.class)
public class SpringbootIocContainerApplication {

    public static void main(String[] args) {
        System.out.println("=========================");
//        Weapon weapon = new Knife();
//        User user = new User(weapon);
//        user.attack();

        /*ApplicationContext applicationContext = new ClassPathXmlApplicationContext("app.txt");
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        Arrays.stream(beanDefinitionNames).forEach(System.out::println);
        User user = (User) applicationContext.getBean("user");
        user.attack();*/

        /*ApplicationContext applicationContext = new AnnotationConfigApplicationContext(SpringbootIocContainerApplication.class);
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        Arrays.stream(beanDefinitionNames).forEach(System.out::println);
        User user = (User) applicationContext.getBean("user");
        user.attack();*/

        SpringApplication.run(SpringbootIocContainerApplication.class, args);
    }
}
