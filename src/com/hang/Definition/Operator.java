package com.hang.Definition;

import java.util.ArrayList;

public class Operator extends Definition {
    private ArrayList<String> ope;

    public Operator() {
        ope = new ArrayList<>();
    }

    public void addDefine(String letterDefinition) {
        ope.add(letterDefinition);
    }

    @Override
    public boolean isDef(String str) {
        int n = ope.size();
        for(int i=0; i<n; i++) {
            if (str.equals(ope.get(i))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void showList() {
        int n = ope.size();
        for(int i=0; i<n; i++) {
            System.out.println(ope.get(i));
        }
    }
}
