package service;

import db.AppointmentDAO;
import model.Appointment;

import java.util.List;

public class CsvImporter {
    public static void importAppointment(String filePath) {
        CsvParser parser = new CsvParser();
        List<Appointment> appointments = parser.parseCsv(filePath);

        int total = appointments.size();
        int inserted = 0;

        for (Appointment appt : appointments) {
            if (AppointmentDAO.insertAppointment(appt)) {
                inserted++;
            }
        }
        System.out.printf("Imported %d of %d appointments.%n", inserted, total);
    }
}
