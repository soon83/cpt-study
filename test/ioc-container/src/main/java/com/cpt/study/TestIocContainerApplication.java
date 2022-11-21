package com.cpt.study;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@ComponentScan(basePackages = "com.cpt.study")
//@ComponentScan(basePackageClasses = TestIocContainerApplication.class)
@SpringBootApplication
public class TestIocContainerApplication {

    public static void main(String[] args) {
        /**
         * 1. 객체 안에서 타 객체를 생성하여 사용하는 방법을 보여줌 (소요시간 10분)
         * 2. 제 3 자가 해주는 의존 주입을 통하여 객체를 사용하는 방법을 보여줌 (소요시간 10분)
         * 3. 인터페이스 의존 주입을 통하여 객체를 사용하는 방법을 보여줌 (소요시간 10분)
         * 4. 스프링 bean 등록을 통하여 객체를 사용하는 방법을 보여줌 (소요시간 10분)
         * 5. 그냥 @SpringBootApplication 이용
         */


        // 2
//        TestGun gun = new TestGun();
//        TestUser user = new TestUser();
//        TestUser user = new TestUser(gun);
//        user.attack();


        // 3
//        TestWeapon weapon = new TestGun();
//        TestWeapon weapon = new TestKnife();
//        TestUser user = new TestUser(weapon);
//        user.attack();


        // 4
//        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("test-application.xml");
//        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
//        Arrays.stream(beanDefinitionNames).forEach(System.out::println);
//        TestUser user = (TestUser) applicationContext.getBean("testUser");
//        user.attack();


        // 5
//        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(TestIocContainerApplication.class);
//        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
//        Arrays.stream(beanDefinitionNames).forEach(System.out::println);
//        TestUser user = (TestUser) applicationContext.getBean("testUser");
//        user.attack();

        // 6
        SpringApplication.run(TestIocContainerApplication.class, args);
    }
}
