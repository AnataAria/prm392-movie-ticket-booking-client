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
<<<<<<< Updated upstream
=======
        this.listView = findViewById(R.id.movie_auth_list_home);
        this.menuBtn.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(this, v);
            popupMenu.getMenuInflater().inflate(R.menu.drawer_menu, popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(menuItem -> {
                int id = menuItem.getItemId();
                if (id == R.id.nav_home) {
                    Toast.makeText(getApplicationContext(), "Action One selected", Toast.LENGTH_SHORT).show();
                    return true;
                } else if (id == R.id.nav_community) {
                    Toast.makeText(getApplicationContext(), "Action Two selected", Toast.LENGTH_SHORT).show();
                    return true;
                } else if (id == R.id.nav_contact_us) {
                    Toast.makeText(getApplicationContext(), "Action Three selected", Toast.LENGTH_SHORT).show();
                    return true;
                } else {
                    return false;
                }
            });
            popupMenu.show();
        });
        this.profileBtn.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(this, v);
            popupMenu.getMenuInflater().inflate(R.menu.profile_menu, popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(menuItem -> {
                int id = menuItem.getItemId();
                if (id == R.id.view_profile) {
                    Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
                    startActivity(intent);
                    return true;
                } else if (id == R.id.edit_profile) {
                    Intent intent = new Intent(HomeActivity.this, EditProfileActivity.class);
                    startActivity(intent);
                    return true;
                } else if (id == R.id.logout) {
                    Toast.makeText(getApplicationContext(), "Action Three selected", Toast.LENGTH_SHORT).show();
                    TokenUtils.removeAuthToken(HomeActivity.this);
                    Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    return true;
                } else if (id == R.id.transaction_history_menu) {
                    Intent intent = new Intent(HomeActivity.this, TransactionHistoryActivity.class);
                    startActivity(intent);
                    return true;
                } else if (id == R.id.wallet_recharge_menu) {
                    Intent intent = new Intent(HomeActivity.this, WalletImportActivity.class);
                    startActivity(intent);
                    return true;
                }else {
                    return false;
                }
            });
            popupMenu.show();
        });
    }

    private void loadMovies() {
        Call<ResponseObject<List<Movie>>> call = movieService.getAllMovies();
        call.enqueue(new Callback<ResponseObject<List<Movie>>>() {
            @Override
            public void onResponse(Call<ResponseObject<List<Movie>>> call, Response<ResponseObject<List<Movie>>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    movieList.clear();
                    movieList.addAll(response.body().getData());
                    movieAdapter = new MovieAdapter(HomeActivity.this, movieList);
                    listView.setAdapter(movieAdapter);
                    Log.i("MovieActivity", "Movies loaded successfully");
                } else {
                    Log.e("MovieActivity", "Response error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ResponseObject<List<Movie>>> call, Throwable t) {
                Log.e("MovieActivity", "API call failed", t);
                Toast.makeText(HomeActivity.this, "Load Failed: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
>>>>>>> Stashed changes
    }
}
