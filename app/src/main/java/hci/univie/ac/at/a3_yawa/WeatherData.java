package hci.univie.ac.at.a3_yawa;

import android.text.format.DateFormat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;


/**
 * Created by asilcetin on 19/04/18.
 */

public class WeatherData {

    public String getMinTemp(String response, int dayNumber) throws JSONException {

        Calendar cal = Calendar .getInstance();
        SimpleDateFormat dateFt = new SimpleDateFormat("yyyy-MM-dd");
        dayNumber = dayNumber - 1;
        cal.add(Calendar.DATE, dayNumber);
        String formattedDate = dateFt.format(cal.getTime());

        JSONObject responseObject = new JSONObject(response);
        JSONArray listArray = responseObject.getJSONArray("list");

        double minimum = 0.0;
        double dayMinTemp = 0.0;
        String dayTxt = "";

        for (int i = 0 ; i < listArray.length(); i++) {
            JSONObject dayObject = listArray.getJSONObject(i);
            dayTxt = dayObject.getString("dt_txt").substring(0, 10);
            if (formattedDate.equals(dayTxt)) {
               JSONObject dayMain = dayObject.getJSONObject("main");
               dayMinTemp = Double.parseDouble(dayMain.getString("temp_min"));
                // Find the minimum
                if (minimum == 0.0){
                    minimum = dayMinTemp;
                }
                if (dayMinTemp < minimum) {
                    minimum = dayMinTemp;
                }
            }
        }

        return Double.toString(minimum) + " °C";

    }


    public String getMaxTemp(String response, int dayNumber) throws JSONException {

        Calendar cal = Calendar .getInstance();
        SimpleDateFormat dateFt = new SimpleDateFormat("yyyy-MM-dd");
        dayNumber = dayNumber - 1;
        cal.add(Calendar.DATE, dayNumber);
        String formattedDate = dateFt.format(cal.getTime());

        JSONObject responseObject = new JSONObject(response);
        JSONArray listArray = responseObject.getJSONArray("list");

        double maximum = 0.0;
        double dayMaxTemp = 0.0;
        String dayTxt = "";

        for (int i = 0 ; i < listArray.length(); i++) {
            JSONObject dayObject = listArray.getJSONObject(i);
            dayTxt = dayObject.getString("dt_txt").substring(0, 10);
            if (formattedDate.equals(dayTxt)) {
                JSONObject dayMain = dayObject.getJSONObject("main");
                dayMaxTemp = Double.parseDouble(dayMain.getString("temp_max"));
                // Find the maximum
                if (maximum == 0.0){
                    maximum = dayMaxTemp;
                }
                if (dayMaxTemp > maximum) {
                    maximum = dayMaxTemp;
                }
            }
        }

        return Double.toString(maximum) + " °C";
    }


    public String getAvTemp(String response, int dayNumber) throws JSONException {

        Calendar cal = Calendar .getInstance();
        SimpleDateFormat dateFt = new SimpleDateFormat("yyyy-MM-dd");
        dayNumber = dayNumber - 1;
        cal.add(Calendar.DATE, dayNumber);
        String formattedDate = dateFt.format(cal.getTime());

        JSONObject responseObject = new JSONObject(response);
        JSONArray listArray = responseObject.getJSONArray("list");

        int num = 0;
        double dayTempTotal = 0.0;
        String dayTxt = "";

        for (int i = 0 ; i < listArray.length(); i++) {
            JSONObject dayObject = listArray.getJSONObject(i);
            dayTxt = dayObject.getString("dt_txt").substring(0, 10);
            if (formattedDate.equals(dayTxt)) {
                num = num + 1;
                JSONObject dayMain = dayObject.getJSONObject("main");
                dayTempTotal = dayTempTotal + Double.parseDouble(dayMain.getString("temp"));
            }
        }

        double dayAvTemp = dayTempTotal / num;
        dayAvTemp = Math.floor(dayAvTemp * 100) / 100;

        return Double.toString(dayAvTemp) + " °C";
    }

    public String getDescription(String response, int dayNumber) throws JSONException {

        Calendar cal = Calendar .getInstance();
        SimpleDateFormat dateFt = new SimpleDateFormat("yyyy-MM-dd");
        dayNumber = dayNumber - 1;
        cal.add(Calendar.DATE, dayNumber);
        String formattedDate = dateFt.format(cal.getTime());

        JSONObject responseObject = new JSONObject(response);
        JSONArray listArray = responseObject.getJSONArray("list");

        String dayTxt = "";
        String dayDescription = "";

        for (int i = 0 ; i < listArray.length(); i++) {
            JSONObject dayObject = listArray.getJSONObject(i);
            dayTxt = dayObject.getString("dt_txt").substring(0, 10);
            if (formattedDate.equals(dayTxt)) {
                JSONArray dayMain = dayObject.getJSONArray("weather");
                JSONObject dayMainObj = dayMain.getJSONObject(0);
                dayDescription = dayMainObj.getString("description");
            }
        }

        return dayDescription.toUpperCase();
    }


}