package com.example.noe.kernair;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class FetchPA extends AsyncTask<Void, Void, Void> {


    // Using this YouTube link: https://www.youtube.com/watch?v=Vcn4OuV4Ixg
    String pAData;
    @Override
    protected Void doInBackground(Void... voids) {
        try {
            URL purpleAirUrl = new URL("https://www.purpleair.com/json");
            try {
                HttpURLConnection purpleAirUrlConnection = (HttpURLConnection) purpleAirUrl.openConnection();
                InputStream inputStream = purpleAirUrlConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String purpleAirStr = "";
                while(purpleAirStr != null){
                    purpleAirStr = bufferedReader.readLine();
                    pAData = pAData + purpleAirStr;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }
}
