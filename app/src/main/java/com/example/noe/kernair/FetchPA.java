package com.example.noe.kernair;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class FetchPA extends AsyncTask<Void, Void, Void> {

    private static final String TAG = "FetchPA";

    public byte[] getURLBytes(String urlSpec) throws IOException {
        URL url = new URL("https://www.purpleair.com/json");
        HttpURLConnection pAConnection = (HttpURLConnection) url.openConnection();

        try{
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = pAConnection.getInputStream();

            if(pAConnection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new IOException(pAConnection.getResponseMessage() + ": with " + urlSpec);
            }

            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            while((bytesRead = in.read(buffer)) > 0) {
                out.write(buffer, 0 , bytesRead);
            }
            out.close();
            return out.toByteArray();
        }finally {
            pAConnection.disconnect();
        }
    }

    public String getUrlString(String urlSpec) throws IOException {
        return new String(getURLBytes(urlSpec));
    }

    public List<PurpleAirSensor> fetchItems() {

        List<PurpleAirSensor> sensorItems = new ArrayList<>();

        try {
            String url = Uri.parse("https://www.purpleair.com/json")
                    .buildUpon()
                    .appendQueryParameter("format", "json")
                    .appendQueryParameter("nojsoncallback", "1")
                    .appendQueryParameter("extras", "url_s")
                    .build().toString();
            String jsonString = getUrlString(url);
            Log.i(TAG, "Received JSON: " + jsonString);
            JSONObject jsonBody = new JSONObject(jsonString);
            parseItems(sensorItems, jsonBody);
        } catch (IOException ioe) {
            Log.e( TAG, "Failed to fetch items", ioe);
        } catch (JSONException je) {
            Log.e(TAG, "Failed to parse JSON", je);
        }
        return sensorItems;
    }

    private void parseItems(List<PurpleAirSensor> items, JSONObject jsonBody)
            throws IOException, JSONException {

        JSONArray sensorJsonArray = jsonBody.getJSONArray("results");
        for(int i = 0; i < sensorJsonArray.length(); i++){
            JSONObject sensorObject = sensorJsonArray.getJSONObject(i);

            PurpleAirSensor sensorItem = new PurpleAirSensor();
            sensorItem.setmId("ID");
            sensorItem.setmParentId("ParentID");
            sensorItem.setMthingspeakPrimaryId("THINGSPEAK_PRIMARY_ID");
            sensorItem.setMthingspeakPrimaryIdReadKey("THINGSPEAK_PRIMARY_ID_READ_KEY");
            sensorItem.setmLable("Label");  // The Name of the sensor
            sensorItem.setmLat("Lat");      // The latitude of the sensor
            sensorItem.setmLon("Lon");      // The Longitude of the sensor
            sensorItem.setmPM2_5Value("PM2_5Value");
            sensorItem.setmType("Type");
            sensorItem.setmHidden("Hidden");
            sensorItem.setmFlag("Flag");
            sensorItem.setmTempF("temp_f");
            sensorItem.setmHumidit("humidity");
            sensorItem.setmPressure("pressure");
            sensorItem.setMthingspeakSecondaryId("THINGSPEAK_SECONDARY_ID");
            sensorItem.setMthingspeakSecondaryIdReadKey("THINGSPEAK_SECONDARY_ID_READ_KEY");
            sensorItem.setmStats("Stats");

            if(!sensorObject.has("url_s")) {
                continue;
            }

            items.add(sensorItem);

        }
    }



    @Override
    protected Void doInBackground(Void... Void) {
        new FetchPA().fetchItems();
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        

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
