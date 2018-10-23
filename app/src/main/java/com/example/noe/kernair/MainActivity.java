package com.example.noe.kernair;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Attempts to launch the Kern Air Project twitter
        Button twitterBtn = findViewById(R.id.twitterBtn);
        twitterBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                String twitter = "https://twitter.com/air_kern";
                Uri kernAirTwitter = Uri.parse(twitter);

                Intent goToTwitter = new Intent(Intent.ACTION_VIEW, kernAirTwitter);
                if(goToTwitter.resolveActivity(getPackageManager()) != null){
                    startActivity(goToTwitter);
                }
            }
        });

        //Attempts to launch the Kern Air Project Facebook page
        Button facebookBtn = findViewById(R.id.facebookBtn);
        facebookBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                String facebook = "https://www.facebook.com/KernAirProject/";
                Uri kernAirFacebook = Uri.parse(facebook);

                Intent goToFacebook = new Intent(Intent.ACTION_VIEW, kernAirFacebook);
                if(goToFacebook.resolveActivity(getPackageManager()) != null){
                    startActivity(goToFacebook);
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onResume() {
        super.onResume();
        int aqi = 51;
        AQIDisplay(aqi);
    }

    /**
     * Sets the background color and
     * the display value into the text view
     * @param value: The AQI reading
     * (Local variables) AQITxtView: The text view that is being edited
     *                   AQITxtDisplay: The value being shown in the text view
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void AQIDisplay(int value){

        TextView AQITxtView = findViewById(R.id.AQITxtView);
        String AQITxtDisplay = "AQI\n" + value;
        AQITxtView.setText(AQITxtDisplay);

        // if AQI is good
        if(value >= 0 && value <= 50){
            AQITxtView.setBackgroundColor(0xe07fa94c);  // Color value is hard coded for now
        // if AQI is moderate
        }else if(value >= 51 && value <= 100){
            AQITxtView.setBackgroundColor(0xe0ffff00);
        // if AQI is unhealthy for sensitive groups
        }else if(value >= 101 && value <= 150){
            AQITxtView.setBackgroundColor(0xe0FF9900);
        // if AQI is unhealthy
        }else if(value >= 151 && value <= 200){
            AQITxtView.setBackgroundColor(0xe0FF3333);
        // if AQI is very unhealthy
        }else if(value >= 201 && value <= 300){
            AQITxtView.setBackgroundColor(0xe0572D57);
        // if AQI is hazardous
        }else if(value >= 301){
            AQITxtView.setBackgroundColor(0xe0800000);
        }
    }
}
