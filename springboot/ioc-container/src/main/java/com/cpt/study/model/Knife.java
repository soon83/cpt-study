package com.cpt.study.model;

import org.springframework.stereotype.Component;

@Component
public class Knife implements Weapon {

    @Override
    public void attack() {
        System.out.println("슈욱");
    }
}
