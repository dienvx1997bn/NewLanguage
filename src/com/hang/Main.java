package com.hang;

import java.util.*;

public class Main {

    public static void main(String[] args) {
	// write your code here
        String fileSource = "src//com//hang//src_file//source.lang";
        String fileLex = "C:\\Users\\dienv\\Desktop\\TrinhBienDich\\NewLang\\src\\com\\hang\\src_file\\lex_definition.lex";
        ArrayList<FormDefinition> frmDef;
        ArrayList<Token> tokens;

        Lexer lexer = new Lexer();
        Main main = new Main();
        // 1. Đọc danh sách định nghĩa chính quy trong file đầu vào
        frmDef = lexer.readLex(fileLex);
        // hiển thị ds
//        lexer.showListDef();

        // 2. Đọc file mã nguồn, trả về tokens
        tokens = lexer.getTokens(fileSource);
        main.shoTokens(tokens);
    }

    void shoTokens(ArrayList<Token> tokens) {
        int n = tokens.size();
        for(int i=0; i<n; i++) {
            System.out.println("key" + tokens.get(i).getKey() + "  " + tokens.get(i).getValue());
        }
    }

}
