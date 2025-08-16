package com.bsec.oop.sadman.Company;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.time.LocalDate;

public class DummyObjectCreator {
    public void createDummyObjects() throws IOException {

        UserSadman company1 = new UserSadman("Pran LTD", 12345, "qweasd", 100,100);
        UserSadman company2 = new UserSadman("Walton LTD", 12346, "qweasdzxc", 100,90);

        IPODetails companyipo1 = new IPODetails(LocalDate.of(2025, 5, 14), "Pran BD", 2000000, "Huge Investment");
        IPODetails companyipo2 = new IPODetails(LocalDate.of(2015, 5, 14), "Walton Bd", 6000000, "No");
        IPODetails companyipo3 = new IPODetails(LocalDate.of(2023, 5, 24), "Milkvita LTD", 2000000, "Huge Investment");
        IPODetails companyipo4 = new IPODetails(LocalDate.of(2005, 7, 17), "Walton LTD", 6000000, "No Tolerance");



        FileOutputStream fos = new FileOutputStream("User.bin");
        ObjectOutputStream oos = new ObjectOutputStream(fos);

        oos.writeObject(company1);
        oos.writeObject(company2);
        oos.close();

        // Write them to Doctor.bin
        FileOutputStream fos1 = new FileOutputStream("IPODetails.bin");
        ObjectOutputStream oos1 = new ObjectOutputStream(fos1);

        oos1.writeObject(companyipo1);
        oos1.writeObject(companyipo4);
        oos1.close();




    }
}
