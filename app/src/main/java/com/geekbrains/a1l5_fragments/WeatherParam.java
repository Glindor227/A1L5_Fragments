package com.geekbrains.a1l5_fragments;

import java.io.Serializable;

public class WeatherParam implements Serializable {
    public boolean isWind;
    public boolean isHum;
    public boolean isOver;
    public boolean isPress;

    public WeatherParam(boolean isWind, boolean isHum, boolean isOver, boolean isPress) {
        this.isWind = isWind;
        this.isHum = isHum;
        this.isOver = isOver;
        this.isPress = isPress;
    }

    public String info(){
        return "WeatherParam isWind("+isWind+") isHum("+isHum+") isOver("+isOver+") isPress("+isPress+")";
    }

}
