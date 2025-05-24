package db;

import model.Appointment;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AppointmentMapper {

    public static List<Appointment> fromResultSet(ResultSet rs) throws SQLException {
        List<Appointment> list = new ArrayList<>();
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
            list.add(appt);
        }
        return list;
    }
}

