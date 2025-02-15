package com.apartmentrental.repositories;

import com.apartmentrental.data.PostgresDB;
import com.apartmentrental.models.*;
import com.apartmentrental.repositories.interfaces.IUserRepository;

import java.sql.*;

public class UserRepository implements IUserRepository {
    PostgresDB postgresDB = PostgresDB.getInstance();

    public void registerUser(String firstName, String lastName, String phone, String username, String password) {
        String sql = "INSERT INTO users (first_name, last_name, phone_number, username, password, wallet_balance, role) VALUES (?, ?, ?, ?, ?, 0, ?)";

        try (Connection conn = postgresDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, firstName);
            stmt.setString(2, lastName);
            stmt.setString(3, phone);
            stmt.setString(4, username);
            stmt.setString(5, password);
            stmt.setString(6, Role.USER.name());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean changeUserRole(int userId, String role) {
        String sql = "UPDATE users SET role = ? WHERE id = ?";
        try (Connection conn = postgresDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, role);
            stmt.setInt(2, userId);
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println("Ошибка при обновлении роли пользователя: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public User loginUser(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";

        try (Connection conn = postgresDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("id");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String phoneNumber = rs.getString("phone_number");
                String userUsername = rs.getString("username");
                String userPassword = rs.getString("password");
                double walletBalance = rs.getDouble("wallet_balance");
                String roleString = rs.getString("role");
                Role role = Role.valueOf(roleString);

                switch (role) {
                    case USER:
                        return new RegularUser(id, firstName, lastName, userUsername, phoneNumber, userPassword, walletBalance);
                    case ADMIN:
                        return new AdminUser(id, firstName, lastName, userUsername, phoneNumber, userPassword, walletBalance);
                    case MANAGER:
                        return new ManagerUser(id, firstName, lastName, userUsername, phoneNumber, userPassword, walletBalance);
                    default:

                        return new RegularUser(id, firstName, lastName, userUsername, phoneNumber, userPassword, walletBalance);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            System.out.println("Неизвестная роль пользователя: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}