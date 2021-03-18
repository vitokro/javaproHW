package ua.kiev.prog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
    // разобраться с кодом последнего проекта, добавить в учетную запись пользователя новое поле “возраст”.
    // Подумать, как реализовать подтверждение регистрации по email и реализовать данный механизм.
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}