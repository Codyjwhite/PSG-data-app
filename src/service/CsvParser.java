package service;

import com.opencsv.CSVReader;
import model.Appointment;

import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

//Class to parse data from CSV and turn it into Appointment objects
public class CsvParser {

    //Method to parse Data from CSV
    public List<Appointment> parseCsv(String filePath) {

        //Create a Arraylist to hold appointments parsed from CSV
        List<Appointment> appointments = new ArrayList<>();

        /*
            ***Reader to access CSV***
        * Create array of String denoting CSV columns
        * Sets First row as true at the start of the method
        * Loop goes row by creating appointment objects from parsed data ignoring first row and any unpaid appointments
        * Loop adds appointment objects to appointment list.
        * Upon loop completion a list of appointment objects is returned.

        */
        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            String[] line;
            boolean isFirstRow = true;

            while ((line = reader.readNext()) != null) {
                if (isFirstRow) {
                    isFirstRow = false;
                    continue;
                }
                String bookingId = line[0].trim();
                LocalDate date = LocalDate.parse(line[1].trim());
                String clientName = line[2].trim();
                String petName = line[3].trim();
                String service = line[4].trim();
                double grossSales = parseDollars(line[5]);
                int duration = parseMinutes(line[6]);
                boolean isPaid = line[7].equalsIgnoreCase("Fully paid");

                //Skip unpaid
                if (!isPaid) {
                    continue;
                }
                appointments.add(new Appointment(
                        bookingId, date, clientName, petName, service, grossSales, duration, isPaid
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return appointments;
    }
    private double parseDollars(String dollarString) {
        try {
            return Double.parseDouble(dollarString.replace("$", "").trim());
        } catch (Exception e) {
            return 0.0;
        }
    }
    private int parseMinutes(String minuteString) {
        try {
            return (int) Double.parseDouble(minuteString.replace("mins", "").trim());
        } catch (Exception e) {
            return 0;
        }
    }
}
