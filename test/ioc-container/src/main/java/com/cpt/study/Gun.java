package com.cpt.study;

import org.springframework.stereotype.Component;

@Component
public class Gun implements Weapon {

    @Override
    public void attack() {
        System.out.println("빵야빵야");
    }
}