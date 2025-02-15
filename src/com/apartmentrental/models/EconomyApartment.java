package com.apartmentrental.models;

public class EconomyApartment extends Apartment {
    public EconomyApartment(int id, String name, String city, String district, String street, int floor, int rooms, double pricePerDay, double pricePerMonth, double pricePerYear, String status, String availabilityDate, double rating) {
        super(id, name, city, district, street, floor, rooms, pricePerDay, pricePerMonth, pricePerYear, status, availabilityDate, rating);
        if (pricePerDay >= 5000) {
            throw new IllegalArgumentException("Цена за день для эконом класса должна быть меньше 5000 тг");
        }
    }

    @Override
    public String getCategory() {
        return "Эконом";
    }
}
