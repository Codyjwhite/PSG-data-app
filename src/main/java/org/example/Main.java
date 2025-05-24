package org.example;

import model.Appointment;
import service.CsvParser;
import db.DBManager;
import db.AppointmentDAO;

import java.util.List;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        CsvParser csvParser = new CsvParser();
        List<Appointment> appointments = csvParser.parseCsv("data/mock_appointments.csv");

        appointments.forEach(System.out::println);

        DBManager.initializeDatabase();

        Appointment testAppt = new Appointment(
                "#100001",
                LocalDate.of(2024, 8, 15),
                "Jane Smith",
                "Bella",
                "Groom - Small",
                85.00,
                60,
                true
        );
        boolean inserted = AppointmentDAO.insertAppointment(testAppt);
        System.out.println("Insert successful? " + inserted);

        List<Appointment> allAppointments = AppointmentDAO.getAllAppointments();
        System.out.println("All Appointments in DB:");
        allAppointments.forEach(System.out::println);

        }
    }
