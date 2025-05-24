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

            // Loop that takes each returned row and creates an appointment object.
            while (rs.next()) {
                Appointment appt = new Appointment(
                        rs.getString("booking_id"),
                        LocalDate.parse(rs.getString("appointment_date")),
                        rs.getString("client_name"),
                        rs.getString("pet_name"),
                        rs.getString("service_size"),
                        rs.getDouble("gross_sales"),
                        rs.getInt("duration_minutes"),
                        rs.getInt("is_paid") == 1
                );
                //Stores object in arrayList
                appointments.add(appt);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return appointments;
    }



}
