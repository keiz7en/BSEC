package com.bsec.bsec;

import java.sql.*;
import java.io.File;

public class DatabaseManager {
    private static final String DB_NAME = "bsec_users.db";
    private static final String DB_URL = "jdbc:sqlite:" + DB_NAME;

    // Create users table if it doesn't exist
    public static void initializeDatabase() {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {

            String createTableSQL = "CREATE TABLE IF NOT EXISTS users (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "user_id TEXT UNIQUE NOT NULL, " +
                    "full_name TEXT NOT NULL, " +
                    "password TEXT NOT NULL, " +
                    "user_type TEXT NOT NULL, " +
                    "created_at DATETIME DEFAULT CURRENT_TIMESTAMP" +
                    ")";

            stmt.execute(createTableSQL);
            System.out.println("Database initialized successfully.");

        } catch (SQLException e) {
            System.err.println("Error initializing database: " + e.getMessage());
        }
    }

    // Register a new user
    public static boolean registerUser(String userId, String fullName, String password, String userType) {
        String insertSQL = "INSERT INTO users (user_id, full_name, password, user_type) VALUES (?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {

            pstmt.setString(1, userId);
            pstmt.setString(2, fullName);
            pstmt.setString(3, password);
            pstmt.setString(4, userType);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            if (e.getMessage().contains("UNIQUE constraint failed")) {
                System.err.println("User ID already exists: " + userId);
            } else {
                System.err.println("Error registering user: " + e.getMessage());
            }
            return false;
        }
    }

    // Authenticate user login
    public static User authenticateUser(String userId, String password, String userType) {
        String selectSQL = "SELECT * FROM users WHERE user_id = ? AND password = ? AND user_type = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(selectSQL)) {

            pstmt.setString(1, userId);
            pstmt.setString(2, password);
            pstmt.setString(3, userType);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new User(
                        rs.getInt("id"),
                        rs.getString("user_id"),
                        rs.getString("full_name"),
                        rs.getString("password"),
                        rs.getString("user_type"),
                        rs.getString("created_at")
                );
            }

        } catch (SQLException e) {
            System.err.println("Error authenticating user: " + e.getMessage());
        }

        return null; // Authentication failed
    }

    public static boolean userExists(String userId) {
        String selectSQL = "SELECT COUNT(*) FROM users WHERE user_id = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(selectSQL)) {

            pstmt.setString(1, userId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            System.err.println("Error checking user existence: " + e.getMessage());
        }

        return false;
    }

    // Get all users (for admin purposes)
    public static void printAllUsers() {
        String selectSQL = "SELECT * FROM users ORDER BY created_at DESC";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(selectSQL)) {

            System.out.println("\n=== All Registered Users ===");
            while (rs.next()) {
                System.out.printf("ID: %s, Name: %s, Type: %s, Created: %s%n",
                        rs.getString("user_id"),
                        rs.getString("full_name"),
                        rs.getString("user_type"),
                        rs.getString("created_at")
                );
            }
            System.out.println("============================\n");

        } catch (SQLException e) {
            System.err.println("Error fetching users: " + e.getMessage());
        }
    }
}
