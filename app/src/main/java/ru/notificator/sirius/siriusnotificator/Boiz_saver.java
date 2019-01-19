package ru.notificator.sirius.siriusnotificator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import ru.notificator.sirius.siriusnotificator.Recyclik.Boi;

public class Boiz_saver {
    static void save_boiz(List<Boi> boez,File file){
        try {
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(boez);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    static List<Boi> get_boiz(File file){
        try {
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream oin = new ObjectInputStream(fis);
            return (List<Boi>) oin.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
