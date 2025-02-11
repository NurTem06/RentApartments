package com.apartmentrental.repositories;
import com.apartmentrental.data.PostgresDB;
import com.apartmentrental.models.FullRentalDescription;
import com.apartmentrental.models.Rental;
import com.apartmentrental.repositories.interfaces.IRentalRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RentalRepository implements IRentalRepository {
    PostgresDB postgresDB = PostgresDB.getInstance();


    public RentalRepository() {
        try {
            postgresDB.getInstance();
        } catch (Exception e) {
            throw new RuntimeException("Ошибка подключения к БД", e);
        }
    }
    @Override
    public void rentApartment(int userId, int apartmentId, String startDate, String durationType) {
        String checkStatusSql = "SELECT status FROM apartments WHERE id = ?";
        String insertSql = "INSERT INTO rentals (user_id, apartment_id, start_date, end_date, status) VALUES (?, ?, CAST(? AS DATE), CAST(? AS DATE), 'Active')";
        String updateSql = "UPDATE apartments SET status = 'booked', availability_date = CAST(? AS DATE) WHERE id = ?";
        String[] validDurations = {"day", "month", "year"};

        // Разбиваем строку durationType на количество и тип
        String[] parts = durationType.split(" ");
        if (parts.length != 2) {
            System.out.println("Неверный формат длительности аренды. Используйте формат: количество тип (например, 5 day)");
            return;
        }

        int durationAmount;
        try {
            durationAmount = Integer.parseInt(parts[0]);
        } catch (NumberFormatException e) {
            System.out.println("Количество должно быть числом.");
            return;
        }

        String durationUnit = parts[1].toLowerCase();
        if (!Arrays.asList(validDurations).contains(durationUnit)) {
            System.out.println("Неверный тип длительности аренды. Используйте day, month или year.");
            return;
        }

        try (Connection conn = postgresDB.getConnection();
             PreparedStatement checkStatusStmt = conn.prepareStatement(checkStatusSql);
             PreparedStatement insertStmt = conn.prepareStatement(insertSql);
             PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {

            checkStatusStmt.setInt(1, apartmentId);
            ResultSet rs = checkStatusStmt.executeQuery();
            if (rs.next()) {
                String currentStatus = rs.getString("status");
                if ("booked".equalsIgnoreCase(currentStatus)) {
                    System.out.println("Квартира уже забронирована и не может быть арендована.");
                    return;
                }
            } else {
                System.out.println("Квартира с указанным ID не найдена.");
                return;
            }

            insertStmt.setInt(1, userId);
            insertStmt.setInt(2, apartmentId);
            insertStmt.setString(3, startDate);

            String calculateEndDateSql = "SELECT (CAST(? AS DATE) + INTERVAL '" + durationAmount + " " + durationUnit + "')::DATE";
            try (PreparedStatement calculateStmt = conn.prepareStatement(calculateEndDateSql)) {
                calculateStmt.setString(1, startDate);
                ResultSet endDateRs = calculateStmt.executeQuery();
                if (endDateRs.next()) {
                    String endDate = endDateRs.getString(1);
                    insertStmt.setString(4, endDate);
                } else {
                    System.out.println("Не удалось рассчитать дату окончания аренды.");
                    return;
                }
            }

            insertStmt.executeUpdate();

            updateStmt.setString(1, startDate);
            updateStmt.setInt(2, apartmentId);
            updateStmt.executeUpdate();

            System.out.println("Операция с квартирой была успешной!");
        } catch (SQLException e) {
            System.out.println("Ошибка при аренде квартиры: " + e.getMessage());
            e.printStackTrace();
        }
    }
