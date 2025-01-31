package com.apartmentrental.repositories;
import com.apartmentrental.data.PostgresDB;
import com.apartmentrental.models.Rental;
import com.apartmentrental.repositories.interfaces.IRentalRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RentalRepository implements IRentalRepository {
    PostgresDB postgresDB = new PostgresDB("localhost:5432", "postgres","Nurislam2006","RentApartments");
    private final Connection conn;

    public RentalRepository() {
        try {
            this.conn = postgresDB.getConnection();
        } catch (Exception e) {
            throw new RuntimeException("Ошибка подключения к БД", e);
        }
    }

    @Override
    public void rentApartment(int userId, int apartmentId, String startDate, String durationType) {
        String sql = "INSERT INTO rentals (user_id, apartment_id, start_date, end_date, status) VALUES (?, ?, CAST(? AS DATE), CAST(? AS DATE), 'Active')";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, apartmentId);
            stmt.setString(3, startDate);
            String endDate = calculateEndDate(startDate, durationType);
            stmt.setString(4, endDate);

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Rental> getRentalsByUserId(int userId) {
        List<Rental> rentals = new ArrayList<>();
        String sql = "SELECT * FROM rentals WHERE user_id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                rentals.add(new Rental(rs.getInt("id"), rs.getInt("user_id"), rs.getInt("apartment_id"), rs.getString("start_date"), rs.getString("end_date"), rs.getString("status")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rentals;
    }
    private String calculateEndDate(String startDate, String durationType) {
        String sql = "SELECT (CAST(? AS DATE) + INTERVAL '1 " + durationType + "')::DATE";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, startDate);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return startDate;
    }

}