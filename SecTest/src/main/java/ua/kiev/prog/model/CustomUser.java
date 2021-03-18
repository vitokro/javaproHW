package ua.kiev.prog.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data @NoArgsConstructor
public class CustomUser {
    @Id
    @GeneratedValue
    private Long id;

    private String login;
    private String password;
    private Integer age;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    private String email;
    private String phone;

    // for email verification
    private boolean enabled;

    public CustomUser(String login, String password, UserRole role, String email, String phone, Integer age) {
        this.login = login;
        this.password = password;
        this.age = age;
        this.role = role;
        this.email = email;
        this.phone = phone;
    }
}
