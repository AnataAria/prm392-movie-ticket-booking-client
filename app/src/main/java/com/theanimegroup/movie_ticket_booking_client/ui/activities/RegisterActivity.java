package com.theanimegroup.movie_ticket_booking_client.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.theanimegroup.movie_ticket_booking_client.R;
import com.theanimegroup.movie_ticket_booking_client.api.APIUnit;
import com.theanimegroup.movie_ticket_booking_client.api.AuthenticationService;
import com.theanimegroup.movie_ticket_booking_client.api.RetrofitClient;
import com.theanimegroup.movie_ticket_booking_client.models.request.RegisterRequest;
import com.theanimegroup.movie_ticket_booking_client.models.response.ResponseObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    private EditText fullNameTxt;
    private EditText emailTxt;
    private EditText passwordTxt;
    private EditText repassTxt;
    private Button registerBtn;
    private TextView loginLinkTxt;
    private AuthenticationService authenticationService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        registerField();
    }
    private void registerField () {
        authenticationService = APIUnit.getInstance().getAuthenticationService();
        fullNameTxt = findViewById(R.id.fullName_input);
        emailTxt = findViewById(R.id.email_input);
        passwordTxt = findViewById(R.id.password_input);
        repassTxt = findViewById(R.id.confirm_password_input);
        registerBtn = findViewById(R.id.register_button);
        loginLinkTxt = findViewById(R.id.login_link);
        registerBtn.setOnClickListener(v -> handleRegisterCall());
        loginLinkTxt.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void handleRegisterCall () {
        String fullName = fullNameTxt.getText().toString().trim();
        String email = emailTxt.getText().toString().trim();
        String password = passwordTxt.getText().toString().trim();
        String confirmPassword = repassTxt.getText().toString().trim();
        if (fullName.isEmpty()) {
            fullNameTxt.setError("Full Name is required");
            fullNameTxt.requestFocus();
            return;
        }
        if (email.isEmpty()) {
            emailTxt.setError("Email is required");
            emailTxt.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            passwordTxt.setError("Password is required");
            passwordTxt.requestFocus();
            return;
        }
        if (!password.equals(confirmPassword)) {
            repassTxt.setError("Passwords do not match");
            repassTxt.requestFocus();
            return;
        }
        RegisterRequest request = RegisterRequest.builder()
                .email(email)
                .fullName(fullName)
                .roleId(1)
                .password(password)
                .build();
        authenticationService.register(request).enqueue(new Callback<ResponseObject<Object>>() {
            @Override
            public void onResponse(Call<ResponseObject<Object>> call, Response<ResponseObject<Object>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(RegisterActivity.this, "Register Success", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
            @Override
            public void onFailure(Call<ResponseObject<Object>> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Register Failed, Please Check The Input Field", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
