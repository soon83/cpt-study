package com.cpt.study.service;

import org.springframework.stereotype.Component;

@Component
public class NotificationService {

    public void pushEmail() {
        System.out.println("# [이메일] 회원가입 성공 메시지 보냄");
    }

    public void pushSms() {
        System.out.println("# [SMS] 회원가입 성공 메시지 보냄");
    }
}
