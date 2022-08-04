package com.cpt.study.model;

import com.cpt.study.domain.User;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class UserDto {

    /**
     * REQUEST
     */

    @Data
    @Builder
    public static class SearchCondition {
        private Long userId;
        private String userName;
        private Integer userAge;
        private String userEmail;
        private User.Gender userGender;
    }

    @Data
    @Builder
    public static class CreateRequest {
        @NotBlank(message = "[필수값] 똑바로 넣으시면 100원 드립니다,,")
        private String userName;

        @NotNull(message = "[필수값] 똑바로 넣으시면 200원 드립니다,,")
        private Integer userAge;

        @NotBlank(message = "[필수값] 똑바로 넣으시면 300원 드립니다,,")
        @Email(message = "[경고장] 올바른 이메일 형식이어야 합니다.")
        private String userEmail;

        @NotNull(message = "[필수값] 똑바로 넣으시면 400원 드립니다,,")
        private User.Gender userGender;

        public User toUser() {
            return User.builder()
                    .name(userName)
                    .age(userAge)
                    .email(userEmail)
                    .gender(userGender)
                    .build();
        }
    }

    @Data
    @Builder
    public static class UpdateRequest {
        private String userName;
        private Integer userAge;
        private String userEmail;
        private User.Gender userGender;

        public User toUser() {
            return User.builder()
                    .name(userName)
                    .age(userAge)
                    .email(userEmail)
                    .gender(userGender)
                    .build();
        }
    }

    /**
     * RESPONSE
     */

    @Getter
    @Builder
    @ToString
    public static class CreateResponse {
        private final Long userId;

        public CreateResponse(Long userId) {
            this.userId = userId;
        }

        public CreateResponse(User user) {
            this.userId = user.getId();
        }
    }

    @Getter
    @Builder
    @ToString
    @AllArgsConstructor
    public static class MainResponse {
        private final Long userId;
        private final String userName;
        private final Integer userAge;
        private final String userEmail;
        private final User.Gender userGender;

        public MainResponse(User user) {
            this.userId = user.getId();
            this.userName = user.getName();
            this.userAge = user.getAge();
            this.userEmail = user.getEmail();
            this.userGender = user.getGender();
        }
    }
}
