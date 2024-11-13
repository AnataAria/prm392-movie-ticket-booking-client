package com.theanimegroup.movie_ticket_booking_client.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import com.theanimegroup.movie_ticket_booking_client.R;
import com.theanimegroup.movie_ticket_booking_client.api.APIUnit;
import com.theanimegroup.movie_ticket_booking_client.api.AuthenticationService;
import com.theanimegroup.movie_ticket_booking_client.models.request.EditProfileRequest;
import com.theanimegroup.movie_ticket_booking_client.models.response.AccountResponseBasic;
import com.theanimegroup.movie_ticket_booking_client.models.response.ResponseObject;
import com.theanimegroup.movie_ticket_booking_client.util.TokenUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileActivity extends AppCompatActivity {
    private AuthenticationService authenticationService;
    EditText edtAddress, edtPhone;
    TextView btnBackHome;
    String name;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_profile);

        initComponent();
    }

    private void initComponent() {
        authenticationService = APIUnit.getInstance().getAuthenticationService();
        edtAddress = findViewById(R.id.addressTextView);
        edtPhone = findViewById(R.id.phoneTextView);
        btnBackHome = findViewById(R.id.backBtn);
        btnBackHome.setOnClickListener(v -> handleEditProfileCall());

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                finish();
            }
        });
    }

    private void handleEditProfileCall(){
        String token = TokenUtils.getAuthToken(EditProfileActivity.this);
        if (token.isEmpty()) {
            Intent intent = new Intent(EditProfileActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        String accessToken = String.format("Bearer %s", token);
        authenticationService.me(accessToken).enqueue(new Callback<ResponseObject<AccountResponseBasic>>() {
            @Override
            public void onResponse(Call<ResponseObject<AccountResponseBasic>> call, Response<ResponseObject<AccountResponseBasic>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    AccountResponseBasic acc = response.body().getData();
                    id = acc.getId();
                    name = acc.getName();
                } else {
                    TokenUtils.removeAuthToken(EditProfileActivity.this);
                    Intent intent = new Intent(EditProfileActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<ResponseObject<AccountResponseBasic>> call, Throwable t) {
                Toast.makeText(EditProfileActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });

        String address = edtAddress.getText().toString().trim();
        String phone = edtPhone.getText().toString().trim();
        if (address.isEmpty()) {
            edtAddress.setError("Address is required");
            edtAddress.requestFocus();
            return;
        }
        if (phone.isEmpty()) {
            edtPhone.setError("Phone is required");
            edtPhone.requestFocus();
            return;
        }
        EditProfileRequest request = EditProfileRequest.builder()
                .id(id)
                .name(name)
                .address(address)
                .phone(phone)
                .build();
        authenticationService.editProfile(id,request).enqueue(new Callback<ResponseObject<Object>>() {
            @Override
            public void onResponse(Call<ResponseObject<Object>> call, Response<ResponseObject<Object>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(EditProfileActivity.this, "Edit Success", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(EditProfileActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<ResponseObject<Object>> call, Throwable t) {
                Toast.makeText(EditProfileActivity.this, "Edit Failed, Please Check The Input Field", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
