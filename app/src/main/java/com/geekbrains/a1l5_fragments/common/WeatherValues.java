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

    public WeatherValues(float tempI, float windI, int humI, int pressI) {
        this.temp = String.format("%.2f", tempI) + "\u2103";
        this.wind = String.format("%.1f", windI) + "m/s";
        this.hum = humI+"%";
        this.press = pressI+ "hPa";
    }

    public WeatherValues() {
        this.temp = "нет данных";
        this.wind = "нет данных";
        this.hum = "нет данных";
        this.press = "нет данных";
    }

    public String Info(){
        StringBuilder stringBuilder = new StringBuilder("Погода: ");
        stringBuilder.append(" temp: ").append(temp);
        stringBuilder.append(" wind: ").append(wind);
        stringBuilder.append(" hum: ").append(hum);
        stringBuilder.append(" press: ").append(press);
        return stringBuilder.toString();
    }
}
