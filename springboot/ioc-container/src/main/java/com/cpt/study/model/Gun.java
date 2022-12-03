package com.cpt.study.model;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Primary
@Component
public class Gun implements Weapon {

    @Override
    public void attack() {
        System.out.println("빵야빵야");
    }
}
