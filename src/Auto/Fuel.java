package Auto;

import java.time.LocalDate;

/**
 * Created by Yegorov on 12.05.2016.
 */
public class Fuel {
    public String ID;
    private String time;
    private LocalDate date;
    private String surname;
    private double countFuel;
    private double price;
    public Fuel(String ID, String time, LocalDate date, String surname, double countFuel, double price) {
        this.time = time;
        this.date = date;

        this.countFuel = countFuel;
        this.surname = surname;
        this.price = price;
        this.ID = ID;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getSurname() {
        return surname;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }


    public double getCountFuel() {
        return countFuel;
    }

    public void setCountFuel(double countFuel) {
        this.countFuel = countFuel;
    }
}
