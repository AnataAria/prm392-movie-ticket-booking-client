package com.theanimegroup.movie_ticket_booking_client.ui.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.theanimegroup.movie_ticket_booking_client.R;
import com.theanimegroup.movie_ticket_booking_client.api.APIUnit;
import com.theanimegroup.movie_ticket_booking_client.api.AuthenticationService;
import com.theanimegroup.movie_ticket_booking_client.api.CreateOrderService;
import com.theanimegroup.movie_ticket_booking_client.models.response.ResponseObject;
import com.theanimegroup.movie_ticket_booking_client.util.TokenUtils;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.zalopay.sdk.Environment;
import vn.zalopay.sdk.ZaloPayError;
import vn.zalopay.sdk.ZaloPaySDK;
import vn.zalopay.sdk.listeners.PayOrderListener;

public class WalletImportActivity extends AppCompatActivity {
    private EditText amountTxt;
    private Button submitBtn;
    private AuthenticationService authenticationService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_import);
        initField();
    }

    public void initField () {
        this.amountTxt = findViewById(R.id.editTextAmount);
        this.submitBtn = findViewById(R.id.btn_import_wallet);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        authenticationService = APIUnit.getInstance().getAuthenticationService();
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                CreateOrderService orderApi = APIUnit.getInstance().getCreateOrderService();
                ZaloPaySDK.init(2553, Environment.SANDBOX);
                Double amount = Double.valueOf(amountTxt.getText().toString());
                if (amount <= 0) {
                    Toast.makeText(WalletImportActivity.this, "Please enter valid quantity", Toast.LENGTH_SHORT).show();
                    return;
                }
                @SuppressLint("DefaultLocale") String totalString = String.format("%.0f", amount);
                try {
                    JSONObject data = orderApi.createOrder(totalString);
                    String code = data.getString("return_code");
                    if (code.equals("1")) {
                        String token = data.getString("zp_trans_token");
                        //app co 3 loai: success, cancel error
                        ZaloPaySDK.getInstance().payOrder(WalletImportActivity.this, token, "mtb://movieticket", new PayOrderListener() {
                            @Override
                            public void onPaymentSucceeded(String s, String s1, String s2) {
                                Intent intent1 = new Intent(WalletImportActivity.this, PaymentNotificationAppToAppActivity.class);
                                Double ammountCallback = amount;
                                String apiToken = TokenUtils.getAuthToken(WalletImportActivity.this);
                                String accessToken = String.format("Bearer %s", apiToken);
                                authenticationService.updateWallet(accessToken, ammountCallback).enqueue(new Callback<ResponseObject<Object>>() {
                                    @Override
                                    public void onResponse(Call<ResponseObject<Object>> call, Response<ResponseObject<Object>> response) {
                                        if (response.isSuccessful()) {
                                            intent1.putExtra("result","Payment Success");
                                            intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(intent1);
                                        }else {
                                            intent1.putExtra("result","Payment Failed");
                                            intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(intent1);
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<ResponseObject<Object>> call, Throwable t) {
                                        intent1.putExtra("result",String.format("Error: %s", t.getMessage()));
                                        intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent1);
                                    }
                                });

                            }

                            @Override
                            public void onPaymentCanceled(String s, String s1) {
                                Intent intent1 = new Intent(WalletImportActivity.this, PaymentNotificationAppToAppActivity.class);
                                intent1.putExtra("result","Payment Cancel");
                                intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent1);
                            }

                            @Override
                            public void onPaymentError(ZaloPayError zaloPayError, String s, String s1) {
                                Intent intent1 = new Intent(WalletImportActivity.this, PaymentNotificationAppToAppActivity.class);
                                intent1.putExtra("result","lỗi, thanh toán thất bại");
                                intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent1);
                            }
                        });
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        ZaloPaySDK.getInstance().onResult(intent);
    }
}
