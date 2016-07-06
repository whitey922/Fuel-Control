package Auto;

import java.time.LocalDate;

/**
 * Created by Yegorov on 13.05.2016.
 */
public class Oil {
    public String ID;

    public void setID(String ID) {
        this.ID = ID;
    }

    private String time;
    private LocalDate date;
    private double countOil;
    private double price;

    public Oil(String ID, String time, LocalDate date, double countOil, double price) {
        this.ID = ID;
        this.time = time;
        this.date = date;
        this.countOil = countOil;
        this.price = price;
    }

    public String getID() {
        return ID;
    }

    public static void removeBuyingOil(Oil oil) {

        Storage.getInstance().actionsOil(oil.countOil);
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

    public double getCountOil() {
        return countOil;
    }

    public void setCountOil(double countOil) {
        this.countOil = countOil;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
