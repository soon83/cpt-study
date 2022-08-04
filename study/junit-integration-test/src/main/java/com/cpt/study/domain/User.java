package com.cpt.study.domain;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private Long id;
    private String name;
    private Integer age;
    private String email;
    private Gender gender;

    @Getter
    @RequiredArgsConstructor
    public enum Gender {
        MALE, FEMALE;
    }

    public void update(User user) {
        this.name = user.getName();
        this.age = user.getAge();
        this.email = user.getEmail();
        this.gender = user.getGender();
    }
}
