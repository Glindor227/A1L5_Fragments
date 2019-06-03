package com.geekbrains.a1l5_fragments.common;

import java.io.Serializable;

public class WeatherValues  implements Serializable {
    public String temp;
    public String wind;
    public String hum;
    public String press;

    public WeatherValues(String temp, String wind, String hum, String press) {
        this.temp = temp;
        this.wind = wind;
        this.hum = hum;
        this.press = press;
    }

    public String info(){
        return "WeatherValue Temp("+temp+") Wind("+wind+") Hum("+hum+") Press("+press+")";
    }
}
