package com.hang;

import java.util.ArrayList;

public class FormDefinition {
    private Definition defData;

    public FormDefinition() {
        defData = new Definition();
    }

    public void showFrmDefData() {
        int n = defData.size();
        System.out.println("Name:  " + defData.getName());

        for(int i=0; i<n; i++) {
            System.out.print(defData.getCharDef().get(i) + ", ");
        }
        System.out.println();
    }

    public Definition getFrmDefData() {
        return defData;
    }
}
