package com.cpt.study;

import org.springframework.stereotype.Component;

//@Component
public class TestUser {

    private TestWeapon testWeapon;

    public TestUser() {
    }


//    @Autowired
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
