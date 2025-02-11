package com.apartmentrental.controllers;

import com.apartmentrental.models.FullRentalDescription;
import com.apartmentrental.models.Rental;
import com.apartmentrental.repositories.RentalRepository;
import com.apartmentrental.views.ConsoleView;

import java.util.List;
import java.util.Scanner;

public class RentalController {
    private final RentalRepository rentalRepository = new RentalRepository();
    private final ConsoleView view = new ConsoleView();
    public void rentApartment(int userId) {
        Scanner scanner = new Scanner(System.in);
        boolean isValidInput = false;

        while (!isValidInput) {
            try {
                System.out.print("Введите ID квартиры для аренды: ");
                int apartmentId = scanner.nextInt();
                scanner.nextLine();
                System.out.print("Введите дату заезда (YYYY-MM-DD): ");
                String startDate = scanner.nextLine();
                System.out.print("Введите срок аренды (количество тип, например, 5 day): ");
                String durationType = scanner.nextLine();

                rentalRepository.rentApartment(userId, apartmentId, startDate, durationType);
                isValidInput = true;
                System.out.println("Квартира успешно арендована!");
            } catch (Exception e) {
                System.out.println("Произошла ошибка при вводе данных. Пожалуйста, попробуйте снова.");
                // Очистка буфера ввода в случае ошибки
                scanner.nextLine();
            }
        }
    }

    public void viewUserRentals(int userId) {
        List<Rental> rentals = rentalRepository.getRentalsByUserId(userId);
        if (rentals.isEmpty()) {
            System.out.println("Нет активных аренд.");
        } else {
            for (Rental rental : rentals) {
                System.out.println("Арендована квартира ID: " + rental.getApartmentId() +
                        " | Дата заезда: " + rental.getStartDate() + " | Дата окончания: " + rental.getEndDate());
            }
        }
    }
    public void getFullRentalDescriptionByUserId(int id) {
        List<FullRentalDescription> fullDescriptions = rentalRepository.getFullRentalDescriptionByUserId(id);

        if (fullDescriptions.isEmpty()) {
            view.displayMessage("У пользователя с ID " + id + " нет активных аренд.");
        } else {
            for (FullRentalDescription description : fullDescriptions) {
                view.displayMessage("ID аренды: " + description.getId());
                view.displayMessage("ID пользователя: " + description.getUserId());
                view.displayMessage("Имя пользователя: " + description.getUserName());
                view.displayMessage("ID квартиры: " + description.getApartmentId());
                view.displayMessage("Название квартиры: " + description.getApartmentName());
                view.displayMessage("Дата начала аренды: " + description.getStartDate());
                view.displayMessage("Дата окончания аренды: " + description.getEndDate());
                view.displayMessage("-----------------------------");
            }
        }
    }
}
