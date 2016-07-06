package Auto;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yegorov on 08.05.2016.
 */
public class Driver {
    public String ID;

    public String getID() {
        return ID;
    }
    private String surname;
    private String name;

    public static List<Driver> driverList = new ArrayList<>();

    public String getSurname() {
        return surname;
    }

    public String getName() {
        return name;
    }

    public static Driver getObjDriver(String surname) {
        Driver driver = null;
        for (int i = 0; i < driverList.size(); i++) {
            if (driverList.get(i).surname.equals(surname)) driver = driverList.get(i);
        }
        return driver;
    }
    public static void removeDriver(String surname) {
        for (Driver driver:
                driverList
             ) {
            if(driver.surname.equals(surname)) driverList.remove(driver);
        }
    }


    public Driver(String ID, String surname, String name) {
        this.ID = ID;
        this.surname = surname;
        this.name = name;
    }
}
