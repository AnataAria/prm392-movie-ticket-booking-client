package com.theanimegroup.movie_ticket_booking_client.ui.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.theanimegroup.movie_ticket_booking_client.R;
import com.theanimegroup.movie_ticket_booking_client.api.APIUnit;
import com.theanimegroup.movie_ticket_booking_client.api.AuthenticationService;
import com.theanimegroup.movie_ticket_booking_client.models.response.AccountResponseBasic;
import com.theanimegroup.movie_ticket_booking_client.models.response.ResponseObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {
    private TextView emailTxt, addressTxt, phoneTxt, roleTxt, statusTxt, balanceTxt;
    private AuthenticationService authenticationService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        initComponent();
        loadProfile();
    }

    private void initComponent() {
        this.emailTxt = findViewById(R.id.emailTextView);
        this.addressTxt = findViewById(R.id.addressTextView);
        this.phoneTxt = findViewById(R.id.phoneTextView);
        this.roleTxt = findViewById(R.id.roleTextView);
        this.statusTxt = findViewById(R.id.statusTextView);
        this.balanceTxt = findViewById(R.id.walletTextView);
        authenticationService = APIUnit.getInstance().getAuthenticationService();
    }

    public void loadProfile() {
        SharedPreferences sharedPreferences = getSharedPreferences("MovieAppPrefsToken", MODE_PRIVATE);
        String token = sharedPreferences.getString("auth_token", null);
        String accessToken = String.format("Bearer %s", token);
        if (token != null) {
            authenticationService.me(accessToken).enqueue(new Callback<ResponseObject<AccountResponseBasic>>() {
                @Override
                public void onResponse(Call<ResponseObject<AccountResponseBasic>> call, Response<ResponseObject<AccountResponseBasic>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        AccountResponseBasic acc = response.body().getData();
                        emailTxt.setText(String.format("Email: %s", acc.getEmail()));
                        addressTxt.setText(String.format("Address: %s", acc.getAddress()));
                        phoneTxt.setText(String.format("Phone: %s", acc.getPhone()));
                        roleTxt.setText(String.format("Role: %s", "User"));
                        statusTxt.setText(String.format("Status: %s", acc.getStatus() != 1 ? "ACTIVE" : "DISABLE"));
                        balanceTxt.setText(String.format("Balance: %s", acc.getWallet()));
                    } else {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove("auth_token");
                        editor.apply();
                    }
                }

                @Override
                public void onFailure(Call<ResponseObject<AccountResponseBasic>> call, Throwable t) {
                    Toast.makeText(ProfileActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(this, "User is not authenticated", Toast.LENGTH_SHORT).show();
        }
    }
}
