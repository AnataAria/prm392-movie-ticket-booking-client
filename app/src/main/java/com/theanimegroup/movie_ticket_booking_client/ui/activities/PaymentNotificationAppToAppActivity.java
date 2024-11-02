package com.theanimegroup.movie_ticket_booking_client.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.theanimegroup.movie_ticket_booking_client.R;

public class PaymentNotificationAppToAppActivity extends AppCompatActivity {

    private TextView txtNotification;
    private Button backBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_notification);

        txtNotification = findViewById(R.id.textViewNotify);
        backBtn = findViewById(R.id.backBtn);
        Intent intent = getIntent();
        String result = intent.getStringExtra("result");
        txtNotification.setText(result);
        backBtn.setOnClickListener(v -> {
            Intent intentHome = new Intent(PaymentNotificationAppToAppActivity.this, HomeActivity.class);
            startActivity(intentHome);
        });
    }
}
