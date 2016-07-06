package Auto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yegorov on 04.05.2016.
 */
public class Moto implements java.io.Serializable {
    private String name;
    private int fuel;
    private int odometr;


    public static List<Moto> motoList = new ArrayList<>();

    public void setName(String name) {
        this.name = name;
    }

    public void setFuel(int fuel) {
        this.fuel = fuel;
    }

    public void setOdometr(int odometr) {
        this.odometr = odometr;
    }

    public String getName() {

        return name;
    }

    public int getFuel() {
        return fuel;
    }

    public int getOdometr() {
        return odometr;
    }

/*
    public static void serialize() throws IOException {
        String str = "Moto.info";
        File file = new File(str);
        String absolutePathOfFirstFile = file.getAbsolutePath();
        ObjectOutputStream outputStream =
                new ObjectOutputStream(new FileOutputStream(absolutePathOfFirstFile));

        outputStream.writeObject(motoList);
        outputStream.close();
    }

    public static void deserialize() throws IOException, ClassNotFoundException {
        String str = "Moto.info";
        File file = new File(str);
        String absolutePathOfFirstFile = file.getAbsolutePath();
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(absolutePathOfFirstFile));
        motoList = (List<Moto>) ois.readObject();
    }
*/

    public static Moto getObjAuto(String name) {
        Moto moto = null;
        for (int i = 0; i < motoList.size(); i++) {
            if (motoList.get(i).name.equals(name)) moto = motoList.get(i);
        }
        return moto;
    }

    public Moto(String name) {
        this.name = name;

    }
}
