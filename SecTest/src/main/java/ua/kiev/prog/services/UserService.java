package ua.kiev.prog.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.kiev.prog.config.AppConfig;
import ua.kiev.prog.model.UserRole;
import ua.kiev.prog.model.CustomUser;
import ua.kiev.prog.model.EmailVerificationToken;
import ua.kiev.prog.repos.UserRepository;
import ua.kiev.prog.repos.VerificationTokenRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VerificationTokenRepository tokenRepository;

    @Transactional(readOnly = true)
    public List<CustomUser> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public CustomUser findByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    @Transactional
    public void deleteUsers(List<Long> ids) {
        ids.forEach(id -> {
            Optional<CustomUser> user = userRepository.findById(id);
            user.ifPresent(u -> {
                if (!AppConfig.ADMIN.equals(u.getLogin())) {
                    userRepository.deleteById(u.getId());
                }
            });
        });
    }

    @Transactional
    public boolean addUser(String login, String passHash,
                           UserRole role, String email,
                           String phone, Integer age) {
        if (userRepository.existsByLogin(login))
            return false;

        CustomUser user = new CustomUser(login, passHash, role, email, phone, age);
        userRepository.save(user);

        return true;
    }

    @Transactional
    public boolean addUser(CustomUser user) {
        if (userRepository.existsByLogin(user.getLogin()))
            return false;
        user.setEnabled(true);
        userRepository.save(user);
        return true;
    }

    @Transactional
    public void updateUser(String login, String email, String phone, Integer age) {
        CustomUser user = userRepository.findByLogin(login);
        if (user == null)
            return;

        user.setEmail(email);
        user.setPhone(phone);
        user.setAge(age);

        userRepository.save(user);
    }

    public EmailVerificationToken getVerificationToken(String token) {
        return tokenRepository.findByToken(token);
    }

    public void createVerificationToken(CustomUser user, String token) {
        EmailVerificationToken myToken = new EmailVerificationToken(token, user);
        tokenRepository.save(myToken);
    }
}
