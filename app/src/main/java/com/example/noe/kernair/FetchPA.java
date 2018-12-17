package com.example.noe.kernair;

import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class FetchPA extends AsyncTask<URL, Void, Void> {


    // Using this YouTube link: https://www.youtube.com/watch?v=Vcn4OuV4Ixg

    String pAData;  //The raw data from the Purple Air JSON
    JSONObject JO;
    JSONArray JA;

    @Override
    protected Void doInBackground(URL... URL) {
        try {
            URL purpleAirUrl = new URL("https://www.purpleair.com/json");
            try {
                HttpURLConnection purpleAirUrlConnection = (HttpURLConnection) purpleAirUrl.openConnection();
                InputStream inputStream = purpleAirUrlConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String purpleAirStr = "";
                while((purpleAirStr = bufferedReader.readLine()) != null){
                    purpleAirStr = bufferedReader.readLine();
                    pAData = pAData + purpleAirStr;
                }
                
                JA = new JSONArray(pAData);
                JO = new JSONObject(pAData);
                /**
                for(int i = 0; i < JA.length(); i++){
                    JO = (JSONObject) JA.get(i);
                }
                 */

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        try {
            MainActivity.AQITxtView.setText(JO.getInt("ID"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets the background color and
     * the display value into the text view
     * @param value: The AQI reading
     * (Local variables) AQITxtView: The text view that is being edited
     *                   AQITxtDisplay: The value being shown in the text view
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void AQIDisplay(int value){

        String AQITxtDisplay = "AQI\n" + value;
        MainActivity.AQITxtView.setText(AQITxtDisplay);

        // if AQI is good
        if(value >= 0 && value <= 50){
            MainActivity.AQITxtView.setBackgroundColor(0xe07fa94c);  // Color value is hard coded for now
            // if AQI is moderate
        }else if(value >= 51 && value <= 100){
            MainActivity.AQITxtView.setBackgroundColor(0xe0ffff00);
            // if AQI is unhealthy for sensitive groups
        }else if(value >= 101 && value <= 150){
            MainActivity.AQITxtView.setBackgroundColor(0xe0FF9900);
            // if AQI is unhealthy
        }else if(value >= 151 && value <= 200){
            MainActivity.AQITxtView.setBackgroundColor(0xe0FF3333);
            // if AQI is very unhealthy
        }else if(value >= 201 && value <= 300){
            MainActivity.AQITxtView.setBackgroundColor(0xe0572D57);
            // if AQI is hazardous
        }else if(value >= 301){
            MainActivity.AQITxtView.setBackgroundColor(0xe0800000);
        }
    }
}
