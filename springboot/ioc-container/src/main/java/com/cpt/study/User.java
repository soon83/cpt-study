package com.cpt.study;

import org.springframework.stereotype.Component;

@Component
public class User {

    private Weapon weapon;

    public User(Weapon weapon) {
        System.out.println(weapon);
        this.weapon = weapon;
    }

    public void attack() {
        weapon.attack();
    }
}
