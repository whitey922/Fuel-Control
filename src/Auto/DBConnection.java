package Auto;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yegorov on 29.05.2016.
 */
public class DBConnection {
    private Connection connection;

    public DBConnection(String dbURL) throws SQLException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        Class.forName("org.sqlite.JDBC").newInstance();
        connection = DriverManager.getConnection(dbURL);
    }

    public void shutdown() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }

    public List<Moto> getMotoList() throws SQLException {
        try (
                Statement stmnt = connection.createStatement();
                ResultSet rs = stmnt.executeQuery("select * from moto");
        ) {
            List<Moto> motoList = new ArrayList<>();
            while (rs.next()) {
                String moto = rs.getString("name");

                motoList.add(new Moto(moto));
            }
            return motoList;
        }
    }

    /****
     * ПОЕЗДКИ
     *****/
    public List<Trip> getTripList() throws SQLException {
        try (
                Statement stmnt = connection.createStatement();
                ResultSet rs = stmnt.executeQuery("select * from trips");
        ) {
            List<Trip> personList = new ArrayList<>();
            while (rs.next()) {
                String id = rs.getString("id");
                String moto = rs.getString("moto");
                String driver = rs.getString("driver");
                String fuel = rs.getString("fuel");
                String date = rs.getString("date");
                String odometr_begin = rs.getString("odometr_begin");
                String odometr_finish = rs.getString("odometr_finish");
                String time_start = rs.getString("time_start");
                String time_finish = rs.getString("time_finish");
                String login = rs.getString("login");


                Trip trip = new Trip(id, moto, driver, Double.parseDouble(fuel), Integer.parseInt(odometr_finish), LocalDate.parse(date),
                        Integer.parseInt(odometr_begin), time_start, time_finish, login);
                personList.add(trip);
            }
            return personList;
        }
    }

    public void addTrip(String moto, String driver, double fuel, int odometrFinish, LocalDate date,
                        int odometrBegin, String timeBegin, String timeFinish, String user) throws SQLException {
        String query = "INSERT INTO trips(moto, driver, fuel, date, odometr_begin, time_start, odometr_finish, time_finish, login)"
                + " values (?, ?, ?, ?, ?, ?, ?,?, ?)";

        // create the mysql insert preparedstatement
        PreparedStatement preparedStmt = connection.prepareStatement(query);
        preparedStmt.setString(1, moto);
        preparedStmt.setString(2, driver);
        preparedStmt.setString(3, String.valueOf(fuel));
        preparedStmt.setString(4, String.valueOf(date));
        preparedStmt.setString(5, String.valueOf(odometrBegin));
        preparedStmt.setString(6, timeBegin);
        preparedStmt.setString(7, String.valueOf(odometrFinish));
        preparedStmt.setString(8, timeFinish);
        preparedStmt.setString(9, user);


        // execute the preparedstatement
        preparedStmt.execute();

    }

    public void removeTrip(String ID) throws SQLException {
        String query = "DELETE FROM trips WHERE ID=?";

        // create the mysql insert preparedstatement
        PreparedStatement preparedStmt = connection.prepareStatement(query);
        preparedStmt.setString(1, ID);


        // execute the preparedstatement
        preparedStmt.execute();

    }

    public void editTrip(String ID, String moto, String driver, double fuel, int odometrFinish, LocalDate date,
                         int odometrBegin, String timeBegin, String timeFinish) throws SQLException {
        String query = "UPDATE `trips` SET " +
                "`moto`=?," +
                "`driver`=?," +
                "`fuel`=?," +
                "`date`=?," +
                "`odometr_begin`=?," +
                "`time_start`=?," +
                "`odometr_finish`=?," +
                "`time_finish`=? WHERE ID=?";

        // create the mysql insert preparedstatement
        PreparedStatement preparedStmt = connection.prepareStatement(query);
        preparedStmt.setString(1, moto);
        preparedStmt.setString(2, driver);
        preparedStmt.setString(3, String.valueOf(fuel));
        preparedStmt.setString(4, String.valueOf(date));
        preparedStmt.setString(5, String.valueOf(odometrBegin));
        preparedStmt.setString(6, timeBegin);
        preparedStmt.setString(7, String.valueOf(odometrFinish));
        preparedStmt.setString(8, timeFinish);
        preparedStmt.setString(9, ID);


        // execute the preparedstatement
        preparedStmt.execute();

    }


    /****
     * ВОДИТЕЛИ
     *****/
    public List<Driver> getDriversList() throws SQLException {
        try (
                Statement stmnt = connection.createStatement();
                ResultSet rs = stmnt.executeQuery("select * from drivers");
        ) {
            List<Driver> driverList = new ArrayList<>();
            while (rs.next()) {
                String ID = rs.getString("id");
                String surname = rs.getString("surname");
                String name = rs.getString("name");

                driverList.add(new Driver(ID, surname, name));
            }
            return driverList;
        }
    }

    public void addDriver(String surname, String name) throws SQLException {
        String query = "INSERT INTO drivers(surname, name)"
                + " values (?, ?)";

        // create the mysql insert preparedstatement
        PreparedStatement preparedStmt = connection.prepareStatement(query);
        preparedStmt.setString(1, surname);
        preparedStmt.setString(2, name);

        // execute the preparedstatement
        preparedStmt.execute();

    }

    public void removeDriver(String ID) throws SQLException {
        String query = "DELETE FROM drivers WHERE ID=?";

        // create the mysql insert preparedstatement
        PreparedStatement preparedStmt = connection.prepareStatement(query);
        preparedStmt.setString(1, ID);


        // execute the preparedstatement
        preparedStmt.execute();
    }

    public void editDriver(String ID, String surname, String name) throws SQLException {
        String query = "UPDATE `drivers` SET " +
                "`surname`=?," +
                "`name`=?" + "WHERE ID=?";

        // create the mysql insert preparedstatement
        PreparedStatement preparedStmt = connection.prepareStatement(query);
        preparedStmt.setString(1, surname);
        preparedStmt.setString(2, name);
        preparedStmt.setString(3, ID);

        // execute the preparedstatement
        preparedStmt.execute();

    }


    /****
     * ПОЛЬЗОВАТЕЛИ
     *****/
    public List<User> getUserList() throws SQLException {
        try (
                Statement stmnt = connection.createStatement();
                ResultSet rs = stmnt.executeQuery("select * from users");
        ) {
            List<User> userList = new ArrayList<>();
            while (rs.next()) {
                String ID = rs.getString("id");
                String login = rs.getString("login");
                String password = rs.getString("password");

                userList.add(new User(ID, login, password));
            }
            return userList;
        }
    }

    public void addUser(String login, String password) throws SQLException {
        String query = "INSERT INTO users (login, password)"
                + " values (?, ?)";

        // create the mysql insert preparedstatement
        PreparedStatement preparedStmt = connection.prepareStatement(query);
        preparedStmt.setString(1, login);
        preparedStmt.setString(2, password);

        preparedStmt.execute();
    }

    public void removeUser(String ID) throws SQLException {
        if (Integer.parseInt(ID) != 1) {
            String query = "DELETE FROM users WHERE ID=?";

            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = connection.prepareStatement(query);
            preparedStmt.setString(1, ID);


            // execute the preparedstatement
            preparedStmt.execute();
        }
    }

    public void editUser(String ID, String login, String password) throws SQLException {

        PreparedStatement preparedStmt = null;
        if (Integer.parseInt(ID) != 1) {
            String query = "UPDATE `users` SET " +
                    "`login`=?," +
                    "`password`=?" + "WHERE ID=?";

            // create the mysql insert preparedstatement
            preparedStmt = connection.prepareStatement(query);
            preparedStmt.setString(1, login);
            preparedStmt.setString(2, password);
            preparedStmt.setString(3, ID);

            // execute the preparedstatement
            preparedStmt.execute();
        } else {
            String query = "UPDATE `users` SET " +
                    "`password`=?" + "WHERE ID=?";

            preparedStmt = connection.prepareStatement(query);
            preparedStmt.setString(1, password);
            preparedStmt.setString(2, ID);


        }
        preparedStmt.execute();

    }

    /*    ***
         * БЕНЗИН
         *****/
    public List<Fuel> getBuyingFuelList() throws SQLException {
        try (
                Statement stmnt = connection.createStatement();
                ResultSet rs = stmnt.executeQuery("select * from fuel");
        ) {
            List<Fuel> fuelList = new ArrayList<>();
            while (rs.next()) {
                String ID = rs.getString("id");
                String time = rs.getString("time");
                String date = rs.getString("date");
                String surname = rs.getString("driver");
                String countFuel = rs.getString("count");
                String price = rs.getString("price");


                fuelList.add(new Fuel(ID, time, LocalDate.parse(date),
                        surname, Double.parseDouble(countFuel), Double.parseDouble(price)));
            }
            return fuelList;
        }
    }

    public void addFuel(String time, LocalDate date, String surname, double countFuel, double price) throws SQLException {

        String query = "INSERT INTO fuel (driver, time, date, count, price)"
                + " values (?, ?,?, ?,?)";

        // create the mysql insert preparedstatement
        PreparedStatement preparedStmt = connection.prepareStatement(query);
        preparedStmt.setString(1, String.valueOf(surname));
        preparedStmt.setString(2, time);
        preparedStmt.setString(3, String.valueOf(date));
        preparedStmt.setString(4, String.valueOf(countFuel));
        preparedStmt.setString(5, String.valueOf(price));

        preparedStmt.execute();
    }

    public void removeFuel(String ID) throws SQLException {
        if (Integer.parseInt(ID) != 1) {
            String query = "DELETE FROM fuel WHERE ID=?";

            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = connection.prepareStatement(query);
            preparedStmt.setString(1, ID);


            // execute the preparedstatement
            preparedStmt.execute();
        }
    }

    public void editFuel(String ID, String time, LocalDate date, double countOil, double price, String driver) throws SQLException {

        PreparedStatement preparedStmt = null;
        String query = "UPDATE `fuel` SET " +
                "`date`=?," +
                "`time`=?," +
                "`count`=?," +
                "`price`=?," +
                "`driver`=?" +
                "WHERE ID=?";

        // create the mysql insert preparedstatement
        preparedStmt = connection.prepareStatement(query);

        preparedStmt.setString(1, String.valueOf(date));
        preparedStmt.setString(2, String.valueOf(time));
        preparedStmt.setString(3, String.valueOf(countOil));
        preparedStmt.setString(4, String.valueOf(price));
        preparedStmt.setString(5, String.valueOf(driver));
        preparedStmt.setString(6, String.valueOf(ID));


        // execute the preparedstatement
        preparedStmt.execute();

    }


    /*    ***
     * МАСЛО
     *****/
    public List<Oil> getBuyingOilList() throws SQLException {
        List<Oil> buyingoOilList = new ArrayList<>();
        try (
                Statement stmnt = connection.createStatement();
                ResultSet rs = stmnt.executeQuery("select * from oil");
        ) {

            while (rs.next()) {
                String ID = rs.getString("id");
                String time = rs.getString("time");
                String date = rs.getString("date");
                String countFuel = rs.getString("count");
                String price = rs.getString("price");


                buyingoOilList.add(new Oil(ID, time, LocalDate.parse(date), Double.parseDouble(countFuel),
                        Double.parseDouble(price)));
            }
            return buyingoOilList;
        }
    }

    public void addOil(String time, LocalDate date, double countFuel, double price) throws SQLException {

        String query = "INSERT INTO oil ( time, date, count, price)"
                + " values ( ?,?, ?,?)";

        // create the mysql insert preparedstatement
        PreparedStatement preparedStmt = connection.prepareStatement(query);
        preparedStmt.setString(1, time);
        preparedStmt.setString(2, String.valueOf(date));
        preparedStmt.setString(3, String.valueOf(countFuel));
        preparedStmt.setString(4, String.valueOf(price));

        preparedStmt.execute();
    }

    public void removeOil(String ID) throws SQLException {
        String query = "DELETE FROM oil WHERE ID=?";

        // create the mysql insert preparedstatement
        PreparedStatement preparedStmt = connection.prepareStatement(query);
        preparedStmt.setString(1, ID);


        // execute the preparedstatement
        preparedStmt.execute();
    }

    public void editOil(String ID, String time, LocalDate date, double countOil, double price) throws SQLException {

        PreparedStatement preparedStmt = null;
        String query = "UPDATE `oil` SET " +
                "`date`=?," +
                "`time`=?," +
                "`count`=?," +
                "`price`=?" + "WHERE ID=?";

        // create the mysql insert preparedstatement
        preparedStmt = connection.prepareStatement(query);
        preparedStmt.setString(1, String.valueOf(date));
        preparedStmt.setString(2, String.valueOf(time));
        preparedStmt.setString(3, String.valueOf(countOil));
        preparedStmt.setString(4, String.valueOf(price));
        preparedStmt.setString(5, String.valueOf(ID));


        // execute the preparedstatement
        preparedStmt.execute();


    }


    /*СКЛАД*/
    public Storage getStorage() throws SQLException {
        try (
                Statement stmnt = connection.createStatement();
                ResultSet rs = stmnt.executeQuery("select * from storage");
        ) {


            while (rs.next()) {
                String mixed = rs.getString("mixed");
                String fuel = rs.getString("fuel");
                String oil = rs.getString("oil");

                Storage.getInstance().setInJerrycans(Double.parseDouble(mixed));
                Storage.getInstance().setCountFuel(Double.parseDouble(fuel));
                Storage.getInstance().setCountOil(Double.parseDouble(oil));
            }
            return Storage.getInstance();
        }
    }


    public void update() throws SQLException {
        PreparedStatement preparedStmt = null;
        String query = "UPDATE `storage` SET " +
                "`mixed`=?," +
                "`fuel`=?," +
                "`oil`=?";

        // create the mysql insert preparedstatement
        preparedStmt = connection.prepareStatement(query);
        preparedStmt.setString(1, String.valueOf(Storage.getInstance().getInJerrycans()));
        preparedStmt.setString(2, String.valueOf(Storage.getInstance().getCountFuel()));
        preparedStmt.setString(3, String.valueOf(Storage.getInstance().getCountOil()));

        // execute the preparedstatement
        preparedStmt.execute();
    }

}