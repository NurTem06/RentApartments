package com.apartmentrental.repositories;
import com.apartmentrental.data.PostgresDB;
import com.apartmentrental.models.Apartment;
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
                double rating = rs.getDouble("rating"); // Предполагается, что у вас есть колонка rating в таблице

                apartments.add(new Apartment(id, name, city, district, street, floor, rooms, pricePerDay, pricePerMonth, pricePerYear, status, availabilityDate, rating));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return apartments;
    }
    @Override
    public List<Apartment> findApartmentByName(String name) {
        List<Apartment> apartments = new ArrayList<>();
        String sql = "SELECT * FROM apartments WHERE name ILIKE ?";
        try (Connection conn = postgresDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + name + "%"); // Поиск по части названия
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                apartments.add(new Apartment(rs.getString("name"), rs.getString("city"), rs.getString("district"), rs.getString("street"), rs.getInt("floor"), rs.getInt("rooms"), rs.getDouble("price_day"), rs.getDouble("price_month"), rs.getDouble("price_year"), rs.getString("status"), rs.getString("availability_date"), rs.getInt("id")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return apartments;
    }
}