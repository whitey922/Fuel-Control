package Auto;

/*ODO
Пересмотреть весь процесс работы склада
* */
public class Storage implements java.io.Serializable {
    private static Storage instance;
    public String ID;
    /*Бензин, смешанный с маслом*/
    private double inJerrycans;
    private double countFuel;
    private double countOil;

    private Storage() {
    }

    public static Storage getInstance() {
        if (instance == null) instance = new Storage();
        return instance;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public double getInJerrycans() {
        return inJerrycans;
    }

    public void setInJerrycans(double inJerrycans) {
        this.inJerrycans = inJerrycans;
    }

    public double getCountOil() {
        return countOil;
    }

    public void setCountOil(double countOil) {
        this.countOil = countOil;
    }

    public double getCountFuel() {
        return countFuel;
    }

    public void setCountFuel(double countFuel) {
        this.countFuel = countFuel;
    }

    public void addFuel(double fuel) {
        countFuel += fuel;
        mixFuelAndOil();
    }

    public void addOil(double oil) {
        countOil += oil;
        mixFuelAndOil();
    }

    public void actionsOil(double oil) {
        countOil -= oil;
        mixFuelAndOil();
    }

    public void actionsFuel(double fuel) {
        countFuel -= fuel;
        mixFuelAndOil();
    }

    public void actionsJerrycan(double fuel) {
        if (inJerrycans - fuel >= 0) {
            inJerrycans -= fuel;
            countFuel -= fuel * 20 / 20.5;
            countOil -= fuel * 0.5 / 20.5;
        }
    }

    public void mixFuelAndOil() {
        if (countOil >= (countFuel * 0.5) / 20)//больше того, что позволяет добавить
        {
            inJerrycans = (countFuel * 0.5) / 20 + countFuel;

        }
        else inJerrycans = 0;
    }

}
