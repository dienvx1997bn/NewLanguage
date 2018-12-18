package com.hang;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.security.Key;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Parser {
    private Map<String, String> symbols;
    public Parser() {
        symbols = new HashMap<String, String>();
    }

    public void parse(ArrayList<Token> tokens) {

        int i=0;
        while (i < tokens.size()) {
            if(tokens.get(i).getKey().equalsIgnoreCase("PRINT")) {
                if(tokens.get(i+1).getKey().equalsIgnoreCase("STRING")) {
//                    System.out.println("FOUNT A STRING");
                    i++;
                }
                else if(tokens.get(i+1).getKey().equalsIgnoreCase("NUM")) {
//                    System.out.println("FOUNT A NUMBER");
                    System.out.println(tokens.get(i+1).getValue());
                    i++;
                }
                else if(tokens.get(i+1).getKey().equalsIgnoreCase("EXPR")) {
//                    System.out.println("FOUNT A EXPR");
                    System.out.println(tokens.get(i).getValue());
                    i++;
                }
                else if(tokens.get(i+1).getKey().equalsIgnoreCase("VAR")) {
                    System.out.println("print value  " + getVariable(tokens.get(i+1).getKey().toString()));
                    i++;
                }
            }
            else if(tokens.get(i).getKey().equalsIgnoreCase("EXPR")) {
                String value = calC(tokens.get(i).getValue().toString());
                tokens.get(i).setValue(value);
                System.out.println("result " + tokens.get(i).getValue());
                i++;
            }
            else if(tokens.get(i).getKey().equalsIgnoreCase("VAR")) {
                if(tokens.get(i+1).getKey().equalsIgnoreCase("EQUAL")) {
                    if(tokens.get(i+2).getKey().equalsIgnoreCase("EXPR")) {
                        String value = calC(tokens.get(i + 2).getValue());
                        tokens.get(i+2).setValue(value);
                        doAssign(tokens.get(i).getValue(), value);
//                        System.out.println("value  " + value);
                    }
                    else {
                        doAssign(tokens.get(i).getValue(), tokens.get(i+2).getValue());
//                        System.out.println("symbols  " + tokens.get(i+2).getValue());
                    }
                    System.out.println(tokens.get(i).getValue() + "-" + symbols.get(tokens.get(i).getValue()));
                    i = i+2;
                }
            }
            else if(tokens.get(i).getKey().equalsIgnoreCase("NUM")) {
                System.out.println(tokens.get(i).getValue());

            }
            else if(tokens.get(i).getKey().equalsIgnoreCase("EXPR")) {
                String value = calC(tokens.get(i).getValue().toString());
                tokens.get(i).setValue(value);
                System.out.println(tokens.get(i).getValue());
            }
            i++;

        }
    }

    public String getVariable(String key) {
        System.out.println("getKey" + symbols.get(key));
        return symbols.get(key);
    }

    public void doAssign(String key, String value) {
            symbols.put(key,value);
    }

    public String calC(String input) {
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
