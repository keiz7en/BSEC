package com.bsec.oop.sadman.Auditor;

import com.bsec.oop.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GetIPOInfo {
    private static final String dataFileName = "IPOdetails.bin";

    static List<User> getUserList() {
        List<User> userList = new ArrayList<>();

        try (ObjectInputStream stream = new ObjectInputStream(
                new FileInputStream(dataFileName)
        )) {
            userList = (ArrayList) stream.readObject();
            return userList;

        } catch (InvalidClassException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("Invalid data");
        } catch (IOException e) {
            // handle exception
            e.printStackTrace();
            throw new RuntimeException("Error reading from file!");
        }
    }

    public static void saveUserList(List<User> userList) {
        try (ObjectOutputStream stream = new ObjectOutputStream(
                new FileOutputStream(dataFileName)
        )) {

            ArrayList<User> tempList = new ArrayList<>(userList);
            stream.writeObject(tempList);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Could not save data to file");
        }
    }
}
