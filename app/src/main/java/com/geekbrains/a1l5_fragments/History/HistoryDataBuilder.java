package com.geekbrains.a1l5_fragments.History;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class HistoryDataBuilder {
    private Calendar cal;
    private int temp;

    public HistoryDataBuilder() {
        temp = 22;
        cal=Calendar.getInstance();
    }

    public List<HistoryDataClass> buildFakeTemp(){

        List<HistoryDataClass> data2= new ArrayList<>();
        for (int i=0;i<10;i++){
            data2.add(addOneDay());
        }
        return data2;
    }

    public HistoryDataClass addOneDay(){
        cal.add(Calendar.DATE,-1);
        temp += (int)(3- Math.random()*6);
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        return new HistoryDataClass(df.format(cal.getTime()), temp +" С");

    }


}
