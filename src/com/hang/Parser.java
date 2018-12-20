package com.hang;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.security.Key;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class Parser {
    private Map<String, String> symbols;
    private Stack<String> func;
    private Stack<String> argv;
    StringBuilder s;

    public Parser() {

        symbols = new HashMap<String, String>();
        func = new Stack<>();
        argv = new Stack<>();
        s = new StringBuilder();
    }

    private int Infix(String x) {
        if (x == "*" || x == "/" || x == "%"||x=="^") return 2;
        else if (x == "+" || x == "-") return 1;
        else if (x == "(")
        {
            return 0;
        }
        return -1;
    }

    public boolean syntax(ArrayList<Token> tokens) {
    //https://www.stdio.vn/articles/ung-dung-stack-bieu-thuc-trung-to-infix-470
        int i = 0;
        while (i < tokens.size()) {
            if (tokens.get(i).getKey().equalsIgnoreCase("PRINT")) {
                func.push("PRINT");
            }
            else if (tokens.get(i).getKey().equalsIgnoreCase("EXPR")) {
                if (tokens.get(i).getValue().equals("+") || tokens.get(i).getValue().equals("-") ||
                        tokens.get(i).getValue().equals("*") || tokens.get(i).getValue().equals("//")) {

                    if(Infix(tokens.get(i).getValue()) > Infix(func.peek())) {
                    } else {
                        s.append(argv.pop());
                        s.append(tokens.get(i).getValue());
                        s.append(argv.pop());
                        argv.push(calC(s.toString()));
//                        float value1 = Float.parseFloat(argv.pop());
//                        float value2 = Float.parseFloat(argv.pop());
//                        switch (tokens.get(i).getValue()) {
//                            case "+" :
//                                argv.push(String.valueOf(value1 + value2));
//                                break;
//                            case "-" :
//                                argv.push(String.valueOf(value1 - value2));
//                                break;
//                            case "*" :
//                                argv.push(String.valueOf(value1 * value2));
//                                break;
//                            case "//" :
//                                argv.push(String.valueOf(value1 / value2));
//                                break;
//                            case "^" :
//                                argv.push(String.valueOf(Math.pow(value2, value1)));
//                                break;
//                        }
                        func.push(tokens.get(i).getValue());
                    }


                }
//                else if (tokens.get(i).getValue().equals("(")) {
//                    bracket.push("(");
//                }
//                else if (tokens.get(i).getValue().equals(")")) {
//                    if (bracket.peek().equals("(") == true) {
//                        bracket.pop();
//                    } else {
//                        return false;
//                    }
//                }
                else if (tokens.get(i).getKey().equalsIgnoreCase("NUM")) {
                    argv.push(tokens.get(i).getValue());
                }
                else if (tokens.get(i).getKey().equalsIgnoreCase("VAR")) {
                    argv.push(symbols.get(tokens.get(i).getValue()));
                }
            }
            i++;
        }
        return true;
    }

    public void parse(ArrayList<Token> tokens) {

        int i=0;
        while (i < tokens.size()) {
            if(tokens.get(i).getKey().equalsIgnoreCase("PRINT")) {
                if(tokens.get(i+1).getKey().equalsIgnoreCase("STRING")) {
//                    System.out.println("FOUNT A STRING");
                    doPrint(tokens.get(i+1).getValue());
                    i++;
                }
                else if(tokens.get(i+1).getKey().equalsIgnoreCase("NUM")) {
//                    System.out.println("FOUNT A NUMBER");
                    doPrint(tokens.get(i+1).getValue());
                    i++;
                }
                else if(tokens.get(i+1).getKey().equalsIgnoreCase("EXPR")) {
//                    System.out.println("FOUNT A EXPR");
                    String value = calC(tokens.get(i+1).getValue());
                    tokens.get(i+1).setValue(value);
                    doPrint(tokens.get(i+1).getValue());
                    i++;
                }
                else if(tokens.get(i+1).getKey().equalsIgnoreCase("VAR")) {
                    doPrint(symbols.get(tokens.get(i + 1).getValue()));
                    i++;
                }
            }
            else if(tokens.get(i).getKey().equalsIgnoreCase("EXPR")) {
                String value = calC(tokens.get(i).getValue());
                tokens.get(i).setValue(value);
//                System.out.println("result " + tokens.get(i).getValue());
                i++;
            }
            else if(tokens.get(i).getKey().equalsIgnoreCase("VAR")) {
                if(i + 1 >= tokens.size()) {    //no more
                    if(getVariable(tokens.get(i).getValue()) == null) {
                        doAssign(tokens.get(i).getValue(), "");
                    }
                }
                else if(tokens.get(i+1).getKey().equalsIgnoreCase("EQUAL")) {
                    if(tokens.get(i+2).getKey().equalsIgnoreCase("EXPR")) {
                        String value = calC(tokens.get(i + 2).getValue());
                        tokens.get(i+2).setValue(value);
                        doAssign(tokens.get(i).getValue(), value);
                    }
                    else {
                        doAssign(tokens.get(i).getValue(), tokens.get(i+2).getValue());
                    }
//                    System.out.println(tokens.get(i).getValue() + "-" + symbols.get(tokens.get(i).getValue()));
                    i = i+2;
                }
                else {
                    if(getVariable(tokens.get(i).getValue()) == null) { //existed
                        doAssign(tokens.get(i).getValue(), "");
//                        System.out.println(tokens.get(i).getValue() + "-" + symbols.get(tokens.get(i).getValue()));
                    }
                }
            }
            else if(tokens.get(i).getKey().equalsIgnoreCase("NUM")) {
                System.out.println(tokens.get(i).getValue());

            }
            else if(tokens.get(i).getKey().equalsIgnoreCase("EXPR")) {
                String value = calC(tokens.get(i).getValue());
                tokens.get(i).setValue(value);
                System.out.println(tokens.get(i).getValue());
            }
            i++;

        }
    }

    private String getVariable(String key) {
        return symbols.get(key);
    }

    private void doAssign(String key, String value) {
            symbols.put(key,value);
    }

    private void doPrint(String str) {
        System.out.println(str);
    }

    private String calC(String input) {
        String result = "";
        ScriptEngineManager mgr = new ScriptEngineManager();
        ScriptEngine engine = mgr.getEngineByName("JavaScript");
        try {
            result = engine.eval(input).toString();
        } catch (ScriptException e) {
            e.printStackTrace();
        }
        return result;
    }
}
