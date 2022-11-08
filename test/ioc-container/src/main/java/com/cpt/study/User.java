package com.cpt.study;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class User {

    private Weapon weapon;

    public User() {
    }


//    @Autowired
    public User(Weapon weapon) {
        this.weapon = weapon;
    }

    public void attack() {
        if (weapon == null) {
            System.out.println("주먹 휙휙");
        } else {
            weapon.attack();
        }
    }
}
