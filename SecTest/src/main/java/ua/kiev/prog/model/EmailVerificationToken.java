package ua.kiev.prog.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import ua.kiev.prog.model.CustomUser;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class EmailVerificationToken {
    private static final int EXPIRATION = 60 * 24;

    @Id
    @GeneratedValue
    private Long id;

    private String token;

    @OneToOne(targetEntity = CustomUser.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(nullable = false, name = "user_id")
    private CustomUser user;


    public EmailVerificationToken(String token, CustomUser user) {
        this.token = token;
        this.user = user;
    }


}
