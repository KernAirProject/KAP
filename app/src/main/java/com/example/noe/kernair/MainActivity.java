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

    public static TextView AQITxtView;
    private FetchPA process = new FetchPA();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AQITxtView = findViewById(R.id.AQITxtView);

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

        process.execute();
    }
}
