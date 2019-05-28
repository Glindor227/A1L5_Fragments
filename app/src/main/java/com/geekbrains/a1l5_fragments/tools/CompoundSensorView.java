package com.geekbrains.a1l5_fragments.tools;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.TextView;
import java.util.Objects;
import com.geekbrains.a1l5_fragments.R;

public class CompoundSensorView extends ConstraintLayout {
    private TextView textViewTemp;
    private TextView textViewHum;

    public CompoundSensorView(Context context) {
        super(context);
        initCompound(context);
    }

    public CompoundSensorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initCompound(context);
    }

    public CompoundSensorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initCompound(context);
    }

    private void initCompound(Context context) {
        initInflater(context);
        initView();
        initSensor(context);
    }

    private void initInflater(Context context){
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Objects.requireNonNull(inflater).inflate(R.layout.sensor_compound_view, this);
    }

    private void initView() {
        textViewTemp = findViewById(R.id.textViewTempSensor);
        textViewHum = findViewById(R.id.textViewHumSensor);
    }

    private void initSensor(Context context) {
        SensorManager sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);

        // Датчик температуры внешней
        Sensor sensorTemp = Objects.requireNonNull(sensorManager)
                .getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        // Датчик влажности(указанного в задании TYPE_ABSOLUTE_HUMIDITY - у меня нет такой константы)
        Sensor sensorHum = Objects.requireNonNull(sensorManager)
                .getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);
        // Регистрируем слушатели датчиков
        if(sensorTemp!=null){
            sensorManager.registerListener(listenerTemp, sensorTemp,
                    SensorManager.SENSOR_DELAY_UI);}
        if(sensorHum!=null){
            sensorManager.registerListener(listenerHum, sensorHum,
                    SensorManager.SENSOR_DELAY_UI);
        }
    }

    private enum SensorType {
        Temp,
        Hum
    }

    SensorEventListener listenerTemp = new SensorEventListener() {
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {}
        @Override
        public void onSensorChanged(SensorEvent event) { showSensors(SensorType.Temp,event); }
    };

    SensorEventListener listenerHum = new SensorEventListener() {
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {}
        @Override
        public void onSensorChanged(SensorEvent event) {
            showSensors(SensorType.Hum,event);
        }
    };

    private void showSensors(SensorType type,SensorEvent event){
        StringBuilder stringBuilder = new StringBuilder();

        if(type==SensorType.Hum)stringBuilder.append("Hum = ");
        else if (type==SensorType.Temp)stringBuilder.append("Temp = ");

        stringBuilder.append(event.values[0]);

        if(type==SensorType.Hum) textViewHum.setText(stringBuilder);
        else if (type==SensorType.Temp)textViewTemp.setText(stringBuilder);
    }
}
