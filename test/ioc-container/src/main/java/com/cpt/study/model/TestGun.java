package com.cpt.study.model;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Primary
@Component
public class TestGun implements TestWeapon {

    @Override
    public void attack() {
        System.out.println("빵야빵야");
    }
}
