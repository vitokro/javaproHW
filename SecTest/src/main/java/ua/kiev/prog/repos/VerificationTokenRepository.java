package ua.kiev.prog.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.kiev.prog.model.EmailVerificationToken;

public interface VerificationTokenRepository
        extends JpaRepository<EmailVerificationToken, Long> {

    EmailVerificationToken findByToken(String token);

}
