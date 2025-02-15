package com.apartmentrental.views;

import com.apartmentrental.models.Apartment;
import com.apartmentrental.models.Rental;
import com.apartmentrental.models.User;

import java.util.List;
import java.util.Scanner;

public class ConsoleView {
    private final Scanner scanner = new Scanner(System.in);

    public void displayMessage(String message) {
        System.out.println(message);
    }

    public void displayApartments(List<Apartment> apartments) {
        if (apartments.isEmpty()) {
            displayMessage("Нет доступных квартир.");
        } else {
            for (Apartment apartment : apartments) {
                displayMessage(apartment.getId() + ". " + apartment.getName() + " - " +
                        apartment.getCity() + ", " + apartment.getDistrict() + ", " + apartment.getStreet() +
                        " | Этаж: " + apartment.getFloor() + " | Комнат: " + apartment.getRooms() +
                        " | Цена за день: " + apartment.getPricePerDay() + " | Цена за месяц: " +
                        apartment.getPricePerMonth() + " | Цена за год: " + apartment.getPricePerYear() +
                        " | Доступность: " + apartment.getStatus());
            }
        }
    }

    public String promptForString(String prompt) {
        displayMessage(prompt);
        return scanner.nextLine();
    }

    public int promptForInt(String prompt) {
        displayMessage(prompt);
        return scanner.nextInt();
    }

    public void displayUserWelcome(User user) {
        displayMessage("Добро пожаловать, " + user.getFirstName() + "!");
    }

    public void displayRentals(List<Rental> rentals) {
        if (rentals.isEmpty()) {
            displayMessage("Нет активных аренд.");
        } else {
            for (Rental rental : rentals) {
                displayMessage("Арендована квартира ID: " + rental.getApartmentId() +
                        " | Дата заезда: " + rental.getStartDate() + " | Дата окончания: " + rental.getEndDate());
            }
        }
    }
}