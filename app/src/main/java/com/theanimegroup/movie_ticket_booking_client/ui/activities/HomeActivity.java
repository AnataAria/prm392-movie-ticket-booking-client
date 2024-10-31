package com.theanimegroup.movie_ticket_booking_client.ui.activities;

import android.os.Bundle;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.theanimegroup.movie_ticket_booking_client.R;

public class HomeActivity extends AppCompatActivity {
    private ImageButton menuBtn;
    private ImageButton profileBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home_login);
        this.menuBtn = findViewById(R.id.menu_button);
        this.profileBtn = findViewById(R.id.profile_button);
    }
}
