package com.theanimegroup.movie_ticket_booking_client.ui.activities;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.theanimegroup.movie_ticket_booking_client.R;

public class ProfileActivity extends AppCompatActivity {
    private TextView emailTxt, addressTxt, phoneTxt, roleTxt, statusTxt, balanceTxt;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        initComponent();
        loadProfile();
    }
    private void initComponent () {
        this.emailTxt = findViewById(R.id.emailTextView);
        this.addressTxt = findViewById(R.id.addressTextView);
        this.phoneTxt = findViewById(R.id.phoneTextView);
        this.roleTxt = findViewById(R.id.roleTextView);
        this.statusTxt = findViewById(R.id.statusTextView);
        this.balanceTxt = findViewById(R.id.walletTextView);
    }

    public void loadProfile () {
        SharedPreferences sharedPreferences = getSharedPreferences("MovieAppPrefsToken", MODE_PRIVATE);
        String token = sharedPreferences.getString("auth_token", null);
    }
}
