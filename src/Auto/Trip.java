package Auto;

import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yegorov on 05.05.2016.
 */
public class Trip implements java.io.Serializable {
    public static List<Trip> tripList = new ArrayList<>();
    private String id;
    private String moto;
    private String driver;
    private double fuel;
    private int odometrFinish;
    private LocalDate date;
    private int odometrBegin;
    private String timeBegin;
    private String timeFinish;
    private double fuelPerHundredKm;
    private String user;

    public Trip(String id, String moto, String driver, double fuel, int odometrFinish, LocalDate date,
                int odometrBegin, String timeBegin, String timeFinish, String user) {
        this.id = id;
        this.moto = moto;
        this.driver = driver;
        this.fuel = fuel;
        this.odometrFinish = odometrFinish;
        this.date = date;
        this.odometrBegin = odometrBegin;
        this.timeBegin = timeBegin;
        this.timeFinish = timeFinish;
        this.user = user;
        countfuelPerHundredKm();
    }

    public static void removeTrip(Trip trip) {
        tripList.remove(trip);
    }

    public static Trip getTripObj(Trip trip) {

        return Trip.tripList.get(tripList.indexOf(trip));
    }

    public String getId() {
        return id;
    }

    public String getUser() {
        return user;
    }

    public int getOdometrFinish() {
        return odometrFinish;
    }

    public void setOdometrFinish(int odometrFinish) {
        this.odometrFinish = odometrFinish;
    }

    public int getOdometrBegin() {
        return odometrBegin;
    }

    public void setOdometrBegin(int odometrBegin) {
        this.odometrBegin = odometrBegin;
    }

    public String getTimeBegin() {
        return timeBegin;
    }

    public void setTimeBegin(String timeBegin) {
        this.timeBegin = timeBegin;
    }

    public String getTimeFinish() {
        return timeFinish;
    }

    public void setTimeFinish(String timeFinish) {
        this.timeFinish = timeFinish;
    }

    public double getFuelPerHundredKm() {
        return fuelPerHundredKm;
    }

    public void setFuelPerHundredKm(double fuelPerHundredKm) {
        this.fuelPerHundredKm = fuelPerHundredKm;
    }

    public String getMoto() {

        return moto;
    }

    public void setMoto(String moto) {
        this.moto = moto;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public double getFuel() {
        return fuel;
    }

    public void setFuel(double fuel) {
        this.fuel = fuel;
    }

    public ChronoLocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    /*Подсчет литров на 100км*/
    public void countfuelPerHundredKm() {

        this.fuelPerHundredKm = fuel * 100 / (odometrFinish - odometrBegin);
    }
}
