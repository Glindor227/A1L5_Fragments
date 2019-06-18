package com.geekbrains.a1l5_fragments.Internet;

import com.geekbrains.a1l5_fragments.Internet.entites.WeatherRequestRestModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IOpenWeather {
    @GET("data/2.5/weather")
    Call<WeatherRequestRestModel> loadWeather(@Query("lat") double _lat,
                                              @Query("lon") double _lon,
                                              @Query("appid") String keyApi,
                                              @Query("units") String units);

}
