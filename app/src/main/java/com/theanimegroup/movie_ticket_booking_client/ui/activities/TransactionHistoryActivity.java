package com.theanimegroup.movie_ticket_booking_client.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import com.theanimegroup.movie_ticket_booking_client.R;
import com.theanimegroup.movie_ticket_booking_client.api.APIUnit;
import com.theanimegroup.movie_ticket_booking_client.api.TransactionHistoryService;
import com.theanimegroup.movie_ticket_booking_client.models.response.ResponseObject;
import com.theanimegroup.movie_ticket_booking_client.models.response.TransactionHistoryResponse;
import com.theanimegroup.movie_ticket_booking_client.ui.adapters.TransHistoryAdapter;
import com.theanimegroup.movie_ticket_booking_client.util.TokenUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransactionHistoryActivity extends AppCompatActivity {
    private final List<TransactionHistoryResponse> transactionResponseList = new ArrayList<>();
    private ListView listView;
    private TransactionHistoryService transactionHistoryService;
    private TransHistoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_history);
        initField();
        fetchTransactionHistories();
    }

    private void initField() {
        transactionHistoryService = APIUnit.getInstance().getTransactionHistoryService();
        adapter = new TransHistoryAdapter(TransactionHistoryActivity.this, transactionResponseList);
        listView = findViewById(R.id.list_view_transaction_history);
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                finish();
            }
        });
    }

    private void fetchTransactionHistories() {
        String token = TokenUtils.getAuthToken(TransactionHistoryActivity.this);
        if (token.isEmpty()) {
            Intent intent = new Intent(TransactionHistoryActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        String accessToken = String.format("Bearer %s", token);
        Call<ResponseObject<List<TransactionHistoryResponse>>> call = transactionHistoryService.getUserTransaction(accessToken);
        call.enqueue(new Callback<ResponseObject<List<TransactionHistoryResponse>>>() {
            @Override
            public void onResponse(Call<ResponseObject<List<TransactionHistoryResponse>>> call, Response<ResponseObject<List<TransactionHistoryResponse>>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    transactionResponseList.clear();
                    transactionResponseList.addAll(response.body().getData());
                    adapter = new TransHistoryAdapter(TransactionHistoryActivity.this, transactionResponseList);
                    listView.setAdapter(adapter);
                    Log.i("Transaction History Activity", "Transaction History loaded successfully");
                } else {
                    Log.e("Transaction History Activity", "Response error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ResponseObject<List<TransactionHistoryResponse>>> call, Throwable t) {
                Log.e("Transaction History Activity", "API call failed", t);
                Toast.makeText(TransactionHistoryActivity.this, "Load Failed: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

}
