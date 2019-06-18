package com.geekbrains.a1l5_fragments.common;

import android.annotation.SuppressLint;

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

    @SuppressLint("DefaultLocale")
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
        return "Погода: " + " temp: " + temp +
                " wind: " + wind +
                " hum: " + hum +
                " press: " + press;
    }
}
