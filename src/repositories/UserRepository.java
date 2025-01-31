package com.apartmentrental.repositories;

import com.apartmentrental.data.PostgresDB;
import com.apartmentrental.models.User;
import com.apartmentrental.repositories.interfaces.IUserRepository;

import java.sql.*;

public class UserRepository implements IUserRepository {
    PostgresDB postgresDB = new PostgresDB("localhost:5432", "postgres","Nurislam2006","RentApartments");

    public void registerUser(String firstName, String lastName, String phone, String username, String password) {
        String sql = "INSERT INTO users (first_name, last_name, phone_number, username, password, wallet_balance) VALUES (?, ?, ?, ?, ?, 0)";

        try (Connection conn = postgresDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, firstName);
            stmt.setString(2, lastName);
            stmt.setString(3, phone);
            stmt.setString(4, username);
            stmt.setString(5, password);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
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
                return new User(
                        rs.getInt("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("phone_number"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getDouble("wallet_balance")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
