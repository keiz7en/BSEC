package com.bsec.oop.sadman.Company;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class Appendable extends ObjectOutputStream {
    public Appendable(OutputStream out) throws IOException {
        super(out);
    }
    @Override
    public void writeStreamHeader() throws IOException {
        reset();
    }
}