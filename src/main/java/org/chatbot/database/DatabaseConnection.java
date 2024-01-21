package org.chatbot.database;

import java.sql.*;

public class DatabaseConnection implements IDatabaseConnection {
    private final Connection connection;

    public DatabaseConnection(String url, String user, String password) throws SQLException {
        connection = DriverManager.getConnection(url, user, password);
    }

    @Override
    public void addReservation(String customerName, String reservationTime, int numberOfGuests) throws SQLException {
        String sql = "INSERT INTO reservations (customer_name, reservation_time, number_of_guests) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, customerName);
            statement.setString(2, reservationTime);
            statement.setInt(3, numberOfGuests);
            statement.executeUpdate();
        }
    }

    @Override
    public void deleteReservation(int reservationId) throws SQLException {
        String sql = "DELETE FROM reservations WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, reservationId);
            statement.executeUpdate();
        }
    }

    @Override
    public ResultSet listReservations() throws SQLException {
        String sql = "SELECT * FROM reservations";
        return connection.createStatement().executeQuery(sql);
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
