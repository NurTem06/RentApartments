package com.apartmentrental.controllers;

import com.apartmentrental.models.Role;
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
                System.out.println("Добро пожаловать, " + loggedInUser.getFirstName() +"! уровень доступа: "+ loggedInUser.getRole());
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
    public void changeRole(int userId) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Выберите новую роль для пользователя:");
        System.out.println("1. USER");
        System.out.println("2. ADMIN");
        System.out.println("3. MANAGER");
        int choice = scanner.nextInt();
        scanner.nextLine(); // consume newline

        String newRole;
        switch (choice) {
            case 1 -> newRole = "USER";
            case 2 -> newRole = "ADMIN";
            case 3 -> newRole = "MANAGER";
            default -> {
                System.out.println("Неверный выбор роли.");
                return;
            }
        }

        boolean success = userRepository.changeUserRole(userId, newRole);
        if (success) {
            System.out.println("Роль пользователя успешно изменена на " + newRole);
            // Обновляем роль пользователя в объекте loggedInUser, если это текущий пользователь
            if (loggedInUser != null && loggedInUser.getId() == userId) {
                loggedInUser.setRole(Role.valueOf(newRole));
            }
        } else {
            System.out.println("Не удалось изменить роль пользователя.");
        }
    }
    public User getLoggedInUser() {
        return loggedInUser;
    }
}
