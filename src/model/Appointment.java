package model;

import java.time.LocalDate;

public class Appointment {

    private String bookingId;
    private LocalDate appointmentDate;
    private String clientName;
    private String petName;
    private String serviceSize;     // e.g., "Groom - XSmall"
    private double grossSales;      // Parsed from "$85.00"
    private int durationMinutes;    // Parsed from "90.00 mins"
    private boolean isPaid;
    private int appointmentSize;
    private String appointmentType;

    public Appointment(String bookingId, LocalDate appointmentDate, String clientName, String petName, String serviceSize, double grossSales, int durationMinutes, boolean isPaid) {
        this.bookingId = bookingId;
        this.appointmentDate = appointmentDate;
        this.clientName = clientName;
        this.petName = petName;
        this.serviceSize = serviceSize;
        this.grossSales = grossSales;
        this.durationMinutes = durationMinutes;
        this.isPaid = isPaid;

        this.appointmentSize = computeSizeFromPetName(petName);
        this.appointmentType = computeTypeFromService(serviceSize);
    }

    // Getters
    public String getBookingId() { return bookingId; }
    public LocalDate getAppointmentDate() { return appointmentDate; }
    public String getClientName() { return clientName; }
    public String getPetName() { return petName; }
    public String getServiceSize() { return serviceSize; }
    public double getGrossSales() { return grossSales; }
    public int getDurationMinutes() { return durationMinutes; }
    public boolean isPaid() { return isPaid; }

    // Convenience method to get CPM
    public double getCostPerMinute() {
        return durationMinutes > 0 ? grossSales / durationMinutes : 0;
    }

    public int getAppointmentSize() {
        return appointmentSize;
    }

    public String getAppointmentType() {
        return appointmentType;
    }

    private int computeSizeFromPetName(String petName) {
        if (petName == null || petName.trim().isEmpty()) return 0;
        return petName.split(",").length;
    }

    private String computeTypeFromService(String serviceText) {
        boolean hasGroom = serviceText.toLowerCase().contains("groom") || serviceText.toLowerCase().contains("fff");
        boolean hasBath = serviceText.toLowerCase().contains("bath");

        if (hasGroom && hasBath) return "Mixed";
        if (hasGroom) return "Groom";
        if (hasBath) return "Bath";
        return "Unknown";
    }
    @Override
    public String toString() {
        return "Appointment{" +
                "bookingId='" + bookingId + '\'' +
                ", date=" + appointmentDate +
                ", client='" + clientName + '\'' +
                ", pet='" + petName + '\'' +
                ", service='" + serviceSize + '\'' +
                ", cost=$" + grossSales +
                ", duration=" + durationMinutes + " mins" +
                ", paid=" + isPaid +
                '}';
    }
}

