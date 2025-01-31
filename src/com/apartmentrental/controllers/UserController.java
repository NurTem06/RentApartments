package com.apartmentrental.controllers;

import com.apartmentrental.models.User;
import com.apartmentrental.repositories.UserRepository;

import java.util.Scanner;

public class UserController {
    private final UserRepository userRepository = new UserRepository();
    private User loggedInUser = null;

    public void start() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("1. Войти в систему");
        System.out.println("2. Регистрация");
        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice == 1) {
            System.out.print("Введите имя пользователя: ");
            String username = scanner.nextLine();
            System.out.print("Введите пароль: ");
            String password = scanner.nextLine();
            loggedInUser = userRepository.loginUser(username, password);

            if (loggedInUser != null) {
                System.out.println("Добро пожаловать, " + loggedInUser.getFirstName() + "!");
            } else {
                System.out.println("Ошибка входа. Проверьте данные.");
            }
        } else if (choice == 2) {
            System.out.print("Введите имя: ");
            String firstName = scanner.nextLine();
            System.out.print("Введите фамилию: ");
            String lastName = scanner.nextLine();
            System.out.print("Введите телефон: ");
            String phone = scanner.nextLine();
            System.out.print("Введите имя пользователя: ");
            String username = scanner.nextLine();
            System.out.print("Введите пароль: ");
            String password = scanner.nextLine();

            userRepository.registerUser(firstName, lastName, phone, username, password);
            System.out.println("Регистрация прошла успешно!");
        }
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }
}
