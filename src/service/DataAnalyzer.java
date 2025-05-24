package service;

import model.Appointment;

import java.util.ArrayList;
import java.util.List;

//Class to analyze data
public class DataAnalyzer {

    private final List<Appointment> appointments;

    //Constructor used to get all appointments needed for analysis
    public DataAnalyzer(List<Appointment> rawAppointments) {
        this.appointments = new ArrayList<Appointment>(rawAppointments);
        for (Appointment appt : rawAppointments) {
            if (appt.getDurationMinutes() > 0 && appt.getGrossSales() > 0) {
                appointments.add(appt);
            }
        }
    }
    //Method to get Global Cost-per-minute
    public double getGlobalAverageCPM(){
        if (appointments.isEmpty()) {
            return 0.0;
        }
        double totalCPM = 0.0;
        for (Appointment appt : appointments) {
            totalCPM += appt.getCostPerMinute();
        }
        return totalCPM / appointments.size();
    }

    public double getGlobalStdDevCPM(){
        if (appointments.isEmpty()) {
            return 0.0;
        }

        double avg = getGlobalAverageCPM();
        double sumSquaredDiffs = 0.0;

        for (Appointment appt : appointments) {
            double diff = appt.getCostPerMinute() - avg;
            sumSquaredDiffs += diff * diff;
        }
        return Math.sqrt(sumSquaredDiffs/ appointments.size());
    }

    public int getSampleSize() {
        return appointments.size();
    }
}
