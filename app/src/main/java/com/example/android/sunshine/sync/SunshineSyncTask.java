package com.example.android.sunshine.sync;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;

import com.example.android.sunshine.data.WeatherContract;
import com.example.android.sunshine.utilities.NetworkUtils;
import com.example.android.sunshine.utilities.OpenWeatherJsonUtils;

import java.net.URL;

//  COMPLETED (1) Create a class called SunshineSyncTask
public class SunshineSyncTask {
    //  COMPLETED (2) Within SunshineSyncTask, create a synchronized public static void method called syncWeather
    public static synchronized void syncWeather(Context context) {
//      COMPLETED (3) Within syncWeather, fetch new weather data
        try {
            URL weatherData = NetworkUtils.getUrl(context);
            String json = NetworkUtils.getResponseFromHttpUrl(weatherData);
            ContentValues[] values = OpenWeatherJsonUtils
                    .getWeatherContentValuesFromJson(context, json);
            if (values != null && values.length != 0) {
                //      COMPLETED (4) If we have valid results, delete the old data and insert the new
                ContentResolver sunshineContentResolver = context.getContentResolver();
                sunshineContentResolver.delete(
                        WeatherContract.WeatherEntry.CONTENT_URI,
                        null,
                        null);
                sunshineContentResolver.bulkInsert(
                        WeatherContract.WeatherEntry.CONTENT_URI,
                        values);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}