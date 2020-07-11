package me.amitnave.voting.WAInterface.Message;

import java.util.ArrayList;
import java.util.List;

public class MessageStructure {
    private List<String> rows;
    public void addRow(String row){
        rows.add("\n");
        rows.add(row);
    }
    public  void addToLastRow(String s){

        rows.add(s);
    }
    public String getString(){
        String s = "";

        StringBuilder sb = new StringBuilder();

        int i = 0;
        while (i < rows.size() - 1) {
            sb.append(rows.get(i));

            i++;
        }
        sb.append(rows.get(i));
        return sb.toString();
    }
    public MessageStructure(){
        rows=new ArrayList<>();
    }
}
