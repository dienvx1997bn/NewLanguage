package com.hang.Definition;

import java.util.ArrayList;

public class Letter extends Definition {
    private ArrayList<String> letter;

    public Letter() {
        letter = new ArrayList<>();
    }

    public void addDefine(String letterDefinition) {
        letter.add(letterDefinition);
    }

    @Override
    public boolean isDef(String str) {
        int n = letter.size();
        for(int i=0; i<n; i++) {
            if (str.equals(letter.get(i))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void showList() {
        int n = letter.size();
        for(int i=0; i<n; i++) {
            System.out.println(letter.get(i));
        }
    }

}

