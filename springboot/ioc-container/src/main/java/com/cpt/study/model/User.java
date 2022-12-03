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
public class User {

    private Weapon weapon;

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
