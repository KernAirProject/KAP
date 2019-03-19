package com.example.noe.kernair;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class NotificationActivity extends AppCompatActivity {
    private Button addEmailBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        addEmailBtn = findViewById(R.id.addEmailBtn);
        addEmailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NotificationActivity.this, AddEmailActivity.class);
                startActivity(intent);
            }
        });
    }
//    public void openAddEmailActivity() {
//        Intent intent = new Intent(NotificationActivity.this, AddEmailActivity.class);
//        startActivity(intent);
    }
//}
