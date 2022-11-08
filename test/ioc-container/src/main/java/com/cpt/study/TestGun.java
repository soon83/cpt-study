package com.cpt.study;

import org.springframework.stereotype.Component;

//@Component
public class TestGun implements TestWeapon {

    @Override
    public void attack() {
        System.out.println("빵야빵야");
    }
}
