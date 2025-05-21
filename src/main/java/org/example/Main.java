package org.example;

import model.Appointment;
import service.CsvParser;

import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        CsvParser csvParser = new CsvParser();
        List<Appointment> appointments = csvParser.parseCsv("data/mock_appointments.csv");

        appointments.forEach(System.out::println);

        }
    }
