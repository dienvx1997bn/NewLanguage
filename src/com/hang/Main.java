package com.hang;

import java.util.*;

public class Main {

    public static void main(String[] args) {
	// write your code here
//        String fileSource = "src//com//hang//src_file//source.lang";
        String fileLex = "C:\\Users\\dienv\\Desktop\\TrinhBienDich\\NewLang\\src\\com\\hang\\src_file\\lex_definition.lex";
        ArrayList<FormDefinition> tokens;

        Lexer lexer = new Lexer();

        tokens = lexer.readLex(fileLex);
        lexer.showListToken();
    }

}
