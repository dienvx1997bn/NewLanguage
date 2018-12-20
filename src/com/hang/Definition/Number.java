package com.hang.Definition;

import java.util.ArrayList;

public class Number extends Definition {
    private ArrayList<String> num;

    public Number() {
        num = new ArrayList<>();
    }


    @Override
    public void addDefine(String letterDefinition) {
        num.add(letterDefinition);
    }

    @Override
    public boolean isDef(String str) {
        int n = num.size();
        for(int i=0; i<n; i++) {
            if (str.equals(num.get(i))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void showList() {
        int n = num.size();
        for(int i=0; i<n; i++) {
            System.out.println(num.get(i));
        }
    }
}
