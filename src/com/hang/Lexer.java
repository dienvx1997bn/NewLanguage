package com.hang;

import com.sun.org.apache.bcel.internal.generic.NEW;

import java.io.EOFException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Lexer {
    private ArrayList<Token> tokens ;

    public Lexer() {
        tokens = new ArrayList<>();
    }

    public ArrayList<Token> Lex(ArrayList<String> records) {
        StringBuilder tok = new StringBuilder();
        StringBuilder stringValue = new StringBuilder();
        StringBuilder var = new StringBuilder();
//        StringBuilder expr = new StringBuilder();
        StringBuilder num = new StringBuilder();

        int count = 0;
        int sizeRecord = 0;
        int i = 0;
        int n = 0;
        int state = 0;
        boolean varStarted = false;
        boolean isExpr;
        boolean isNum;

        if(records == null) {
            return null;
        }
        sizeRecord = records.size();

        for (count=0; count < sizeRecord; count++){
            isExpr = false;
            varStarted = false;
            isNum = false;
            state = 0;
            char dataLine[] = records.get(count).toCharArray();
            n = dataLine.length;

            for(i=0; i<n; i++) {    //loop for each element of line
                tok.append(dataLine[i]);
                if (tok.toString().equals(" ") || tok.toString().equals(";") == true) {
//                    if(expr.toString().isEmpty() == false && isExpr == true) {
//                        tokens.add(new Token("EXPR", expr.toString()));
//                        expr.delete(0, expr.length());
//                    }
//                    else if(expr.toString().isEmpty() == false && isExpr == false){
//                        tokens.add(new Token("NUM", expr.toString()));
//                        expr.delete(0, expr.length());
//                    }
//                    else
                    if (var.toString().isEmpty() == false) {
                        tokens.add(new Token("VAR",var.toString()));
                        var.delete(0, var.length());
                        varStarted = false;
                        tok.delete(0, tok.length());
                    }
                    else if (num.toString().isEmpty() == false) {
                        tokens.add(new Token("NUM",num.toString()));
                        num.delete(0, num.length());
                        tok.delete(0, tok.length());
                        isNum = false;
                    }

                    if(state == 0) {
                        tok.delete(0, tok.length());
                    }
                }
//                else if (tok.toString().equals(";") == true) {  //end of line
//                    if(expr.toString().isEmpty() == false && isExpr == true) {
//                        tokens.add(new Token("EXPR", expr.toString()));
//                        expr.delete(0, expr.length());
//                    }
//                    else if(expr.toString().isEmpty() == false && isExpr == false){
//                        tokens.add(new Token("NUM", expr.toString()));
//                        expr.delete(0, expr.length());
//                    }
//                    else if (var.toString().isEmpty() == false) {
//                        tokens.add(new Token("VAR",var.toString()));
//                        var.delete(0, var.length());
//                        varStarted = false;
//                    }
//                    tok.delete(0, tok.length());
//                }
                else if (tok.toString().equals("=") && state == 0) {
                    if (var.toString().isEmpty() == false) {
                        tokens.add(new Token("VAR",var.toString()));
                        var.delete(0, var.length());
                        varStarted = false;
                    }
                    tokens.add(new Token("EQUAL",""));
                    tok.delete(0, tok.length());
                }
                else if (tok.toString().equalsIgnoreCase("$") && state == 0) {
                    varStarted = true;
//                    var.append(tok);
                    tok.delete(0, tok.length());
                }
                else if (varStarted == true) {
                    var.append(tok);
                    tok.delete(0, tok.length());
                }
                else if (Character.isDigit(tok.toString().charAt(0)) && state == 0){
                    isNum = true;
                    num.append(tok);
                    tok.delete(0, tok.length());
                }
                else if (tok.toString().equals("+") || tok.toString().equals("-") || tok.toString().equals("*") || tok.toString().equals("\\") || tok.toString().equals("(") || tok.toString().equals(")")) {
//                    isExpr = true;
//                    expr.append(tok);
                    if (var.toString().isEmpty() == false) {
                        tokens.add(new Token("VAR",var.toString()));
                        var.delete(0, var.length());
                        varStarted = false;
                    }
                    tokens.add(new Token("EXPR", tok.toString()));
                    tok.delete(0, tok.length());
                }

                else if (tok.toString().equalsIgnoreCase("PRINT")) {
                    tokens.add(new Token("PRINT", ""));
                    tok.delete(0, tok.length());
                }
                else if (tok.toString().equals("\"")) {
                    if(state == 0) {
                        state = 1;
                        tok.delete(0, tok.length());
                    }
                    else if (state == 1) {
                        tokens.add(new Token("STRING", stringValue.toString()));
                        state = 0;
                        tok.delete(0, tok.length());
                        stringValue.delete(0, stringValue.length());
                    }
                }
                else if(state == 1) {
                    stringValue.append(tok);
                    tok.delete(0, tok.length());
                }


            }
        }

        return tokens;
    }
}
