package com.hang;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class Lexer {
    private ArrayList<FormDefinition> tokens ;

    public Lexer() {
        tokens = new ArrayList<>();
    }

    public ArrayList<FormDefinition> readLex(String filename) {
        boolean isNewDef = true;

        int count = 0;
        try
        {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = reader.readLine()) != null)
            {
                if(isNewDef == true && (line.equals("{") == false || line.equals("}") == false)) { //add new token to list
                    FormDefinition token = new FormDefinition();
                    tokens.add(token);
                    tokens.get(count).getFrmDefData().setName(line);
                    isNewDef = false;
                }
                else if (isNewDef == false && line.equals("{") == false && line.equals("}") == false) { //add list definition
                    tokens.get(count).getFrmDefData().add(line);
                }
                else if (line.equals("{") == true) {
                    continue;
                }
                else if (line.equals("}") == true) {
                    isNewDef = true;
                    count = count + 1;
                }
            }
            reader.close();
        }
        catch (Exception e)
        {
            System.err.format("Exception occurred trying to read '%s'.", filename);
            e.printStackTrace();
            return null;
        }
    return tokens;
    }

    public void showListToken() {
        int n = tokens.size();
        for(int i=0; i<n; i++) {
            tokens.get(i).showFrmDefData();
        }
    }
}
