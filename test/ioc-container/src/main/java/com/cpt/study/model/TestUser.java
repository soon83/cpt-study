package com.cpt.study.model;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Component
@Controller
@Service
@Repository
@Configuration
public class TestUser {

    private TestWeapon testWeapon;

    public TestUser(TestWeapon testWeapon) {
        this.testWeapon = testWeapon;
    }

    public void attack() {
        if (testWeapon == null) {
            System.out.println("주먹 휙휙");
        } else {
            testWeapon.attack();
        }
    }
}
