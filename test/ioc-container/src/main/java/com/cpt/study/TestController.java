package com.cpt.study;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/test")
public class TestController {

    @GetMapping
    public TestResult getTestResult(@ModelAttribute TestResult request) {
        System.out.println("request = " + request);
        return request;
    }

    public static class TestResult {
        private String name;
        private String hobby;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getHobby() {
            return hobby;
        }

        public void setHobby(String hobby) {
            this.hobby = hobby;
        }

        @Override
        public String toString() {
            return "TestResult{" +
                    "name='" + name + '\'' +
                    ", hobby='" + hobby + '\'' +
                    '}';
        }
    }
}
