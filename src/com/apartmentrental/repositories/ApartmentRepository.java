package com.apartmentrental.repositories;

import com.apartmentrental.data.PostgresDB;
import com.apartmentrental.models.Apartment;
import com.apartmentrental.models.BusinessApartment;
import com.apartmentrental.models.EconomyApartment;
import com.apartmentrental.repositories.interfaces.IApartmentRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ApartmentRepository implements IApartmentRepository {
    PostgresDB postgresDB = PostgresDB.getInstance();

    @Override
    public List getAllApartments() {
        List apartments = new ArrayList<>();
        String sql = "SELECT * FROM apartments";
        try (Connection conn = postgresDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String city = rs.getString("city");
                String district = rs.getString("district");
                String street = rs.getString("street");
                int floor = rs.getInt("floor");
                int rooms = rs.getInt("rooms");
                double pricePerDay = rs.getDouble("price_day");
                double pricePerMonth = rs.getDouble("price_month");
                double pricePerYear = rs.getDouble("price_year");
                String status = rs.getString("status");
                String availabilityDate = rs.getString("availability_date");
                double rating = rs.getDouble("rating");

                // Определяем категорию квартиры по цене за день
                if (pricePerDay < 5000) {
                    apartments.add(new EconomyApartment(id, name, city, district, street, floor, rooms, pricePerDay, pricePerMonth, pricePerYear, status, availabilityDate, rating));
                } else {
                    apartments.add(new BusinessApartment(id, name, city, district, street, floor, rooms, pricePerDay, pricePerMonth, pricePerYear, status, availabilityDate, rating));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return apartments;
    }

    @Override
    public List findApartmentByName(String name) {
        List apartments = new ArrayList<>();
        String sql = "SELECT * FROM apartments WHERE name ILIKE ?";
        try (Connection conn = postgresDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + name + "%"); // Поиск по части названия
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String city = rs.getString("city");
                String district = rs.getString("district");
                String street = rs.getString("street");
                int floor = rs.getInt("floor");
                int rooms = rs.getInt("rooms");
                double pricePerDay = rs.getDouble("price_day");
                double pricePerMonth = rs.getDouble("price_month");
                double pricePerYear = rs.getDouble("price_year");
                String status = rs.getString("status");
                String availabilityDate = rs.getString("availability_date");
                double rating = rs.getDouble("rating");

                // Определяем категорию квартиры по цене за день
                if (pricePerDay < 5000) {
                    apartments.add(new EconomyApartment(id, name, city, district, street, floor, rooms, pricePerDay, pricePerMonth, pricePerYear, status, availabilityDate, rating));
                } else {
                    apartments.add(new BusinessApartment(id, name, city, district, street, floor, rooms, pricePerDay, pricePerMonth, pricePerYear, status, availabilityDate, rating));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return apartments;
    }
}