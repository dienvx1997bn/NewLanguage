package com.hang;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class Lexer {
    private ArrayList<FormDefinition> frm ; //tap dinh nghia chinh quy
    private ArrayList<Token> tokens ;

    public Lexer() {
        frm = new ArrayList<>();
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
                line = line.replace('\t',' ');
                line = line.trim();
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
        int sizeRecord = records.size();
        int count = 0;
        int n;
        int ret;

        for (count=0; count < sizeRecord; count++) {    //đọc toàn bộ số dòng thu được
            char dataLine[] = records.get(count).toCharArray();
            n = dataLine.length;
            int i;
            for(i=0; i<n; i++) {    //đọc từng phần tử mỗi dòng
                tok.append(dataLine[i]);
                if(i == n-1) {      //ending line

                }
                ret = isFrmDef(tok.toString());
                if (ret >=0 ) {
                    tokens.add(new Token(frm.get(ret).getFrmDefData().getName(), tok.toString()));
                }
                //TODO: chưa xong đoạn này nhé

            }
        }
        //TODO: chú ý return chưa đúng
        return null;
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

        return -1;
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
