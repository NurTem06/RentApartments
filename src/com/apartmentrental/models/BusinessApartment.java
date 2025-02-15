package com.apartmentrental.models;

public class BusinessApartment extends Apartment {
    public BusinessApartment(int id, String name, String city, String district, String street, int floor, int rooms, double pricePerDay, double pricePerMonth, double pricePerYear, String status, String availabilityDate, double rating) {
        super(id, name, city, district, street, floor, rooms, pricePerDay, pricePerMonth, pricePerYear, status, availabilityDate, rating);
        if (pricePerDay < 5000) {
            throw new IllegalArgumentException("Цена за день для бизнес класса должна быть от 5000 тг и выше");
        }
    }

    @Override
    public String getCategory() {
        return "Бизнес";
    }
}
