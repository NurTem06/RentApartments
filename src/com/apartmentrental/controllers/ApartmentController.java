package com.apartmentrental.controllers;
import com.apartmentrental.models.Apartment;
import com.apartmentrental.repositories.ApartmentRepository;
import com.apartmentrental.views.ConsoleView;

import java.util.List;
import java.util.Scanner;

public class ApartmentController {
    private final ApartmentRepository apartmentRepository = new ApartmentRepository();
    private final ConsoleView view = new ConsoleView();
    public void listApartments() {
        List<Apartment> apartments = apartmentRepository.getAllApartments();

        if (apartments.isEmpty()) {
            view.displayMessage("Нет доступных квартир.");
            return;
        }

        view.displayMessage("Доступные квартиры:");
        view.displayMessage("Категория: Эконом (до 5000 тг/день)");
        for (Apartment apartment : apartments) {
            if (apartment.getPricePerDay() < 5000) {
                displayApartmentDetails(apartment);
            }
        }

        view.displayMessage("\nКатегория: Бизнес (от 5000 тг/день и выше)");
        for (Apartment apartment : apartments) {
            if (apartment.getPricePerDay() >= 5000) {
                displayApartmentDetails(apartment);
            }
        }
    }

    private void displayApartmentDetails(Apartment apartment) {
        view.displayMessage(apartment.getId() + ". " + apartment.getName() + " - " +
                apartment.getCity() + ", " + apartment.getDistrict() + ", " +
                apartment.getStreet() + " | Этаж: " + apartment.getFloor() +
                " | Комнат: " + apartment.getRooms() + " | Цена за день: " +
                apartment.getPricePerDay() + " | Цена за месяц: " +
                apartment.getPricePerMonth() + " | Цена за год: " +
                apartment.getPricePerYear() + " | Доступность: " +
                apartment.getStatus());
    }

    public void searchApartmentByName() {
        String name = view.promptForString("Введите название квартиры: ");

        List<Apartment> apartments = apartmentRepository.findApartmentByName(name);
        if (apartments.isEmpty()) {
            view.displayMessage("Квартира не найдена");
        } else {
            for (Apartment apartment : apartments) {
                System.out.println(apartment.getId() + ". " + apartment.getName() + " - " +
                        apartment.getCity() + ", " + apartment.getDistrict() + ", " + apartment.getStreet());
            }
        }
    }
}