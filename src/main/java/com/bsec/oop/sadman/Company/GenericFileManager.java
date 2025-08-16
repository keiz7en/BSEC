package com.bsec.oop.sadman.Company;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;

public class GenericFileManager {


    public static <T extends Serializable> ObservableList<T> readAll(Class<T> clazz, String fileName) {
        ObservableList<T> list = FXCollections.observableArrayList();

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            while (true) {
                try {
                    Object obj = ois.readObject();
                    if (clazz.isInstance(obj)) {
                        list.add(clazz.cast(obj));
                    }
                } catch (EOFException eof) {
                    break; // reached end of file
                }
            }
        } catch (FileNotFoundException e) {
            // No file yet — ignore
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return list;
    }


    public static <T extends Serializable> void writeAll(ObservableList<T> list, String fileName) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            for (T obj : list) {
                oos.writeObject(obj);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static <T> void append(T object, String fileName) {
        try {
            boolean append = new File(fileName).exists();
            ObjectOutputStream oos;

            if (append) {
                oos = new Appendable(new FileOutputStream(fileName, true));
            } else {
                oos = new ObjectOutputStream(new FileOutputStream(fileName, true));
            }

            oos.writeObject(object);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}