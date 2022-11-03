package com.cpt.study;

public class UserService {

    UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void print() {
        userRepository.print();
    }
}
