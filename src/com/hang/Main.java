package com.hang;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public class Main {

    public static void main(String[] args) {
	// write your code here
        ArrayList<String> records;
        String filename = "src//com//hang//source.lang";
        ArrayList<Token> tokens;

        Main main = new Main();
        Lexer lexer = new Lexer();
        Parser parser = new Parser();

        records = main.open_file(filename);
        tokens = lexer.Lex(records);

        main.showTokens(tokens);
        System.out.println("------------");
        parser.parse(tokens);

    }

    public ArrayList<String> open_file(String filename) {
        ArrayList<String> records = new ArrayList<>();
        try
        {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = reader.readLine()) != null)
            {
                records.add(line);
            }
            reader.close();
            return records;
        }
        catch (Exception e)
        {
            System.err.format("Exception occurred trying to read '%s'.", filename);
            e.printStackTrace();
            return null;
        }
    }

    public void showTokens(ArrayList<Token> tokens) {
        int i;
        for(i=0; i<tokens.size(); i++) {
            System.out.println(tokens.get(i).getKey() + "\t" + tokens.get(i).getValue());
        }
    }
}
