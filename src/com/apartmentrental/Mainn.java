package com.apartmentrental;

import com.apartmentrental.controllers.UserController;
import com.apartmentrental.controllers.ApartmentController;
import com.apartmentrental.controllers.RentalController;
import com.apartmentrental.models.Role;
import com.apartmentrental.models.User;
import com.apartmentrental.views.ConsoleView;

import java.util.Scanner;

public class Mainn {
    public static void main(String[] args) {
        ConsoleView view = new ConsoleView();
        UserController userController = new UserController();
        ApartmentController apartmentController = new ApartmentController();
        RentalController rentalController = new RentalController();

        userController. start();
        User loggedInUser = userController.getLoggedInUser();
        while (loggedInUser == null) {
            Scanner scanner = new Scanner(System.in);
            view.displayMessage("Вы не вошли в систему. Хотите войти? (да/нет)");
            String choice = scanner.nextLine();
            if (choice.equalsIgnoreCase("да")) {
                userController.start();
                loggedInUser = userController.getLoggedInUser();
            } else {
                view.displayMessage("Программа завершена.");
                return;
            }
        }
        while (true) {
            view.displayMessage("\nВыберите действие:");
            if (userController.getLoggedInUser().getRole() == Role.ADMIN){
                view.displayMessage("1. Посмотреть доступные квартиры");
                view.displayMessage("2. Найти квартиру по названию");
                view.displayMessage("3. Арендовать квартиру");
                view.displayMessage("4. Просмотр аренды");
                view.displayMessage("5. Изменение уровня доступа");
                view.displayMessage("6. Выход");
            }
            if (userController.getLoggedInUser().getRole() == Role.MANAGER){
                view.displayMessage("1. Посмотреть доступные квартиры");
                view.displayMessage("2. Найти квартиру по названию");
                view.displayMessage("3. Арендовать квартиру");
                view.displayMessage("4. Просмотр аренды");
                view.displayMessage("5. Просмотр аренд у пользователя");
                view.displayMessage("6. Выход");
            }
            if (userController.getLoggedInUser().getRole() == Role.USER){
                view.displayMessage("1. Посмотреть доступные квартиры");
                view.displayMessage("2. Найти квартиру по названию");
                view.displayMessage("3. Просмотр аренды");
                view.displayMessage("4. Выход");
            }

            int choice = view.promptForInt("Выберите действие: ");
            if (userController.getLoggedInUser().getRole() == Role.MANAGER){
                switch (choice) {
                    case 1 -> apartmentController.listApartments();
                    case 2 -> apartmentController.searchApartmentByName();
                    case 3 -> rentalController.rentApartment(loggedInUser.getId());
                    case 4 -> rentalController.viewUserRentals(loggedInUser.getId());
                    case 5 -> {
                        Scanner scanner = new Scanner(System.in);
                        System.out.print("Введите ID пользователя для просмотра аренд: ");
                        int id = scanner.nextInt();
                        rentalController.getFullRentalDescriptionByUserId(id);}
                    case 6 -> {
                        view.displayMessage("Выход из программы...");
                        return;
                    }
                    default -> view.displayMessage("Неверный выбор, попробуйте снова.");
                }
            }
            if (userController.getLoggedInUser().getRole() == Role.ADMIN){
                switch (choice) {
                    case 1 -> apartmentController.listApartments();
                    case 2 -> apartmentController.searchApartmentByName();
                    case 3 -> rentalController.rentApartment(loggedInUser.getId());
                    case 4 -> rentalController.viewUserRentals(loggedInUser.getId());
                    case 5 -> {
                        Scanner scanner = new Scanner(System.in);
                        System.out.print("Введите ID пользователя для изменения роли: ");
                        int userIdToChange = scanner.nextInt();
                        scanner.nextLine(); // consume newline
                        userController.changeRole(userIdToChange);
                    }
                    case 6 -> {
                        view.displayMessage("Выход из программы...");
                        return;
                    }

                    default -> view.displayMessage("Неверный выбор, попробуйте снова.");
                }
            }
            if (userController.getLoggedInUser().getRole() == Role.USER){
                switch (choice) {
                    case 1 -> apartmentController.listApartments();
                    case 2 -> apartmentController.searchApartmentByName();
                    case 3 -> rentalController.viewUserRentals(loggedInUser.getId());
                    case 4 -> {
                        view.displayMessage("Выход из программы...");
                        return;
                    }
                    default -> view.displayMessage("Неверный выбор, попробуйте снова.");
                }
            }

        }
    }
}