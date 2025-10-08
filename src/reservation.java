import jdk.internal.org.objectweb.asm.tree.analysis.Value;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;
import java.util.Scanner;
import java.sql.Statement;
import java.sql.ResultSet;

import java.sql.*;
import java.sql.*;
import java.util.Scanner;
 class Reservation {
    private static final String url = "jdbc:mysql://localhost:3306/hotel_db";
    private static final String username = "root";
    private static final String password = "";

    public static void main(String[] args) throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL Driver not found: " + e.getMessage());
            return;
        }

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            Scanner sc = new Scanner(System.in);

            while (true) {
                System.out.println("\nHOTEL MANAGEMENT SYSTEM");
                System.out.println("1. Reserve a Room");
                System.out.println("2. View Reservations");
                System.out.println("3. Get Room Number");
                System.out.println("4. Update Reservation");
                System.out.println("5. Delete Reservation");
                System.out.println("6. Exit");
                System.out.print("Choose an option: ");

                int choice = sc.nextInt();

                switch (choice) {
                    case 1:
                        reserveRoom(connection, sc);
                        break;
                    case 2:
                        viewReservations(connection);
                        break;
                    case 3:
                        getRoomNumber(connection, sc);
                        break;
                    case 4:
                        updateReservation(connection, sc);
                        break;
                    case 5:
                        deleteReservation(connection, sc);
                        break;
                    case 6:
                        exit();
                        return;
                    default:
                        System.out.println("Invalid choice!");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static void reserveRoom(Connection connection, Scanner sc) {
        try {
            System.out.print("Enter guest name: ");
            sc.nextLine(); // consume leftover newline
            String guestName = sc.nextLine();

            System.out.print("Enter room number: ");
            int roomNumber = sc.nextInt();

            System.out.print("Enter contact number: ");
            String contactNumber = sc.next();

            String sql = "INSERT INTO reservation (guest_name, room_number, contact_number) VALUES (?, ?, ?)";

            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, guestName);
                stmt.setInt(2, roomNumber);
                stmt.setString(3, contactNumber);

                int affectedRows = stmt.executeUpdate();

                if (affectedRows > 0)
                    System.out.println("Reservation successful!");
                else
                    System.out.println("Reservation failed!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void viewReservations(Connection connection) {
        String sql = "SELECT reservation_id, guest_name, room_number, contact_number, reservation_date FROM reservation";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("\n--- All Reservations ---");
            while (rs.next()) {
                int id = rs.getInt("reservation_id");
                String name = rs.getString("guest_name");
                int room = rs.getInt("room_number");
                String contact = rs.getString("contact_number");
                String date = rs.getTimestamp("reservation_date").toString();

                System.out.println(id + " | " + name + " | " + room + " | " + contact + " | " + date);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void getRoomNumber(Connection connection, Scanner sc) {
        try {
            System.out.print("Enter reservation ID: ");
            int reservationId = sc.nextInt();
            System.out.print("Enter guest name: ");
            String guestName = sc.next();

            String sql = "SELECT room_number FROM reservation WHERE reservation_id = ? AND guest_name = ?";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setInt(1, reservationId);
                stmt.setString(2, guestName);

                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    int roomNumber = rs.getInt("room_number");
                    System.out.println("Room number for reservation ID " + reservationId + " and guest " + guestName + " is: " + roomNumber);
                } else {
                    System.out.println("Reservation not found for given ID and name.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void updateReservation(Connection connection, Scanner sc) {
        try {
            System.out.print("Enter reservation ID to update: ");
            int reservationId = sc.nextInt();

            if (!reservationExists(connection, reservationId)) {
                System.out.println("Reservation not found for given ID.");
                return;
            }

            sc.nextLine();
            System.out.print("Enter new guest name: ");
            String newGuestName = sc.nextLine();

            System.out.print("Enter new room number: ");
            int newRoomNumber = sc.nextInt();

            System.out.print("Enter new contact number: ");
            String newContactNumber = sc.next();

            String sql = "UPDATE reservation SET guest_name = ?, room_number = ?, contact_number = ? WHERE reservation_id = ?";

            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, newGuestName);
                stmt.setInt(2, newRoomNumber);
                stmt.setString(3, newContactNumber);
                stmt.setInt(4, reservationId);

                int affectedRows = stmt.executeUpdate();

                if (affectedRows > 0)
                    System.out.println("Reservation updated successfully!");
                else
                    System.out.println("Reservation update failed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void deleteReservation(Connection connection, Scanner sc) {
        try {
            System.out.print("Enter reservation ID to delete: ");
            int reservationId = sc.nextInt();

            if (!reservationExists(connection, reservationId)) {
                System.out.println("Reservation not found for the given ID.");
                return;
            }

            String sql = "DELETE FROM reservation WHERE reservation_id = ?";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setInt(1, reservationId);
                int affectedRows = stmt.executeUpdate();

                if (affectedRows > 0)
                    System.out.println("Reservation deleted successfully!");
                else
                    System.out.println("Reservation deletion failed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static boolean reservationExists(Connection connection, int reservationId) {
        String sql = "SELECT reservation_id FROM reservation WHERE reservation_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, reservationId);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void exit() throws InterruptedException {
        System.out.print("Exiting System");
        int i = 5;
        while (i != 0) {
            System.out.print(".");
            Thread.sleep(500);
            i--;
        }
        System.out.println("\nThank you for using the Hotel Reservation System!");
    }
}

