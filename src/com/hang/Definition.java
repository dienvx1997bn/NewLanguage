package com.hang;

import java.util.ArrayList;

public class Definition {
    private String name;
    private ArrayList<String> charDef;
    public Definition() {
        charDef = new ArrayList<>();
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void add(String data) {
        charDef.add(data);
    }

    public ArrayList<String> getCharDef() {
        return charDef;
    }

    public int size() {
        return charDef.size();
    }
}
