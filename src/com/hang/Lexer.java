package com.hang;

import com.sun.org.apache.bcel.internal.generic.RETURN;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class Lexer {
    private ArrayList<FormDefinition> frm ; //tap dinh nghia chinh quy
    private ArrayList<Token> tokens ;
    private static final int ERROR = -1;

    public Lexer() {
        frm = new ArrayList<>();
        tokens = new ArrayList<>();
    }

    public ArrayList<FormDefinition> readLex(String filename) { //doc file de lay dc dinh nghia chinh quy
        boolean isNewDef = true;    //dinh nghia chinh quy moi
        int count = 0;
        try
        {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = reader.readLine()) != null)
            {
//                line = line.trim();
                if(isNewDef == true && (line.equals("{") == false || line.equals("}") == false)) { //them dinh nghia chinh quy moi
                    FormDefinition token = new FormDefinition();    //tao moi 1 dinh nghia chinh quy
                    frm.add(token);                                 //them vao danh sach
                    frm.get(count).getFrmDefData().setName(line);   //set name cho dinh nghia do
                    isNewDef = false;                               //ket thuc phan ten dinh nghia
                }
                else if (isNewDef == false && line.equals("{") == false && line.equals("}") == false) { //các giá trị của định nghĩa chính quy đó nằm trong cặp {}
                    frm.get(count).getFrmDefData().add(line);                                           //thêm phần tử
                }
                else if (line.equals("{") == true) {
                    continue;
                }
                else if (line.equals("}") == true) {    //kết thúc định nghĩa chính quy này
                    isNewDef = true;
                    count = count + 1;              //tăng count để chuyển qua định nghĩa tiếp theo
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
    return frm;
    }

    public void showListDef() {
        int n = frm.size();
        for(int i=0; i<n; i++) {
            frm.get(i).showFrmDefData();
        }
    }

    public ArrayList<Token> getTokens(String filename) {
        ArrayList<String> records = open_file(filename);
        StringBuilder tok = new StringBuilder();
        StringBuilder undef = new StringBuilder();

        int sizeRecord = records.size();
        int count = 0;
        int n;
        int ret;
        int old_Type;

        for (count=0; count < sizeRecord; count++) {    //đọc toàn bộ số dòng thu được
            char dataLine[] = records.get(count).toCharArray();
            old_Type = -2;      //phần tử nhớ khởi tạo =-2, sau sẽ gán lại =ret mỗi lần add thêm tokens
            n = dataLine.length;
            int i;
            for(i=0; i<n; i++) {    //đọc từng phần tử mỗi dòng
                tok.append(dataLine[i]);
                if(i == n-1) {      //ending line
                    if(isFrmDef(tok.toString()) == -1)  {
                        undef.append(tok.toString());
                    }
                    if(undef.toString().isEmpty() == false) {
                        System.out.println("SYNTAX ERROR LINE " + i);
                        return null;
                    }
                }
                else {
                    ret = isFrmDef(tok.toString());
                    if(ret >=0) {       //găp 1 định nghĩa chính quy mới
                        if(undef.toString().isEmpty() == false) {   //kiểm tra undefine có dữ liệu hay không
                            int result = isFrmDef(undef.toString());    //kiểm tra dữ liệu đó có là định nghĩa chính quy không
                            if(result >=0) {
                                tokens.add(new Token(frm.get(result).getFrmDefData().getName(), undef.toString()));
                                undef.delete(0, undef.length());
                            }
                            else {
                                System.out.println("SYNTAX ERROR LINE " + count);
                                return null;
                            }
                        }
                        else {
                            tokens.add(new Token(frm.get(ret).getFrmDefData().getName(), tok.toString()));
                            tok.delete(0, tok.length());
                        }
                    }
                    else if(ret == ERROR) {         //nếu là lỗi
                        undef.append(tok.toString());
                        tok.delete(0, tok.length());

                    }
                }
                //TODO: chưa xong đoạn này nhé
            }
        }
        //TODO: chú ý return chưa đúng
        return tokens;
    }

    private int isFrmDef(String str) {
        int n = frm.size();
        for(int i=0; i<n; i++) {    //duyệt toàn bộ ds tập định nghĩa chính quy
            int m = frm.get(i).getFrmDefData().size();  //duyệt từng phần tử của định nghĩa chính quy
            for(int j=0; j<m; j++) {
                if (str.equals(frm.get(i).getFrmDefData().getCharDef().get(j))) {   //nếu trùng với phần tử nào đó trong tập định nghĩa chính quy
                    return i;           // thì trả về chỉ số i là id của định nghĩa chính quy đó trong danh sách
                }
            }
        }

        return ERROR;
    }


    private ArrayList<String> open_file(String filename) {
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
}
