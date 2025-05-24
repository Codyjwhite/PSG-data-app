package db;

import model.Appointment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.List;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AppointmentDAO {

    //Formats LocalDate into a string that sqlite can store
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;



    //Method used to insert an appointment object into the DB
    public static boolean insertAppointment(Appointment appt) {
        //SQL insert statement. Inserts appointment object attributes. Ignores if booking_id already exists
        String sql = """
            INSERT OR IGNORE INTO appointments (
                booking_id, appointment_date, client_name, pet_name, service_size,
                gross_sales, duration_minutes, is_paid, last_updated
            ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;
        //Makes connection to the DB
        try (Connection conn = DBManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            //Replaces "?" placeholders with appointment attributes
            stmt.setString(1, appt.getBookingId());
            stmt.setString(2, appt.getAppointmentDate().format(DATE_FORMATTER));
            stmt.setString(3, appt.getClientName());
            stmt.setString(4, appt.getPetName());
            stmt.setString(5, appt.getServiceSize());
            stmt.setDouble(6, appt.getGrossSales());
            stmt.setInt(7, appt.getDurationMinutes());
            stmt.setInt(8, appt.isPaid() ? 1 : 0);
            stmt.setString(9, LocalDate.now().format(DATE_FORMATTER)); // last_updated = today

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            e.printStackTrace(); // FIXME Replace with GUI message later
            return false;
        }
    }

    //Method to get all appointments ordered by date decending
    public static List<Appointment> getAllAppointments() {
        //Creates list to store appointments
        List<Appointment> appointments = new ArrayList<>();
        //SQL statement to get appointments from DB
        String sql = "SELECT * FROM appointments ORDER BY appointment_date DESC";

        //Make a connection to the DB
        try (Connection conn = DBManager.getConnection();

             PreparedStatement stmt = conn.prepareStatement(sql);

             //ResultSet to store the query result
             ResultSet rs = stmt.executeQuery()) {
             appointments = AppointmentMapper.fromResultSet(rs);
        } catch (SQLException e){
            e.printStackTrace();
        }
        return appointments;
    }

    //Method to get all appointments for a given date range
    public static List<Appointment> getAppointmentsForDate(LocalDate startDate, LocalDate endDate) {
        List<Appointment> appointments = new ArrayList<>();
        String sql = "SELECT * FROM appointments WHERE appointment_date BETWEEN ? AND ?";
        try (Connection conn = DBManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, startDate.format(DATE_FORMATTER));
            stmt.setString(2, endDate.format(DATE_FORMATTER));

            ResultSet rs = stmt.executeQuery();
            appointments = AppointmentMapper.fromResultSet(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointments;
    }

    //Method to remove all record from database
    public static boolean deleteAllAppointments() {
        String sql = "DELETE FROM appointments";
        try (Connection conn = DBManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //Method to remove table from database
    public static boolean testRemoveTable() {
        String sql = "DROP TABLE appointments";
        try (Connection conn = DBManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
