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
    //    @Override
//    public void rentApartment(int userId, int apartmentId, String startDate, String durationType) {
//        String sql = "INSERT INTO rentals (user_id, apartment_id, start_date, end_date, status) VALUES (?, ?, CAST(? AS DATE), CAST(? AS DATE), 'Active')";
//
//        try (Connection conn = postgresDB.getConnection();
//             PreparedStatement stmt = conn.prepareStatement(sql)) {
//            stmt.setInt(1, userId);
//            stmt.setInt(2, apartmentId);
//            stmt.setString(3, startDate);
//            String endDate = calculateEndDate(startDate, durationType);
//
//            stmt.setString(4, endDate);
//
//            stmt.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
    public List<FullRentalDescription> getFullRentalDescriptionByUserId(int userId) {

        List<FullRentalDescription> fullDescriptions = new ArrayList<>();
        String sql = "SELECT r.id, r.user_id, u.first_name || ' ' || u.last_name AS user_name, " +
                "r.apartment_id, a.name AS apartment_name, r.start_date, r.end_date " +
                "FROM rentals r " +
                "JOIN users u ON r.user_id = u.id " +
                "JOIN apartments a ON r.apartment_id = a.id " +
                "WHERE r.user_id = ?";

        try (Connection conn = postgresDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                FullRentalDescription description = new FullRentalDescription(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getString("user_name"),
                        rs.getInt("apartment_id"),
                        rs.getString("apartment_name"),
                        rs.getString("start_date"),
                        rs.getString("end_date")
                );
                fullDescriptions.add(description);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fullDescriptions;
    }

    @Override
    public List<Rental> getRentalsByUserId(int userId) {
        List<Rental> rentals = new ArrayList<>();
        String sql = "SELECT * FROM rentals WHERE user_id = ?";

        try (Connection conn = postgresDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
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
//    private String calculateEndDate(String startDate, String durationType) {
//        String sql = "SELECT (CAST(? AS DATE) + INTERVAL '1 " + durationType + "')::DATE";
//        try (Connection conn = postgresDB.getConnection();
//             PreparedStatement stmt = conn.prepareStatement(sql)) {
//            stmt.setString(1, startDate);
//            ResultSet rs = stmt.executeQuery();
//            if (rs.next()) {
//                return rs.getString(1);
//            }
//        } catch (SQLException e) {
//            System.out.println("Ошибка в calculateEndDate: " + e.getMessage());
//            e.printStackTrace();
//        }
//        System.out.println("calculateEndDate вернул исходную дату: " + startDate);
//        return startDate;
//    }

}