package com.theanimegroup.movie_ticket_booking_client.logger;

import android.util.Log;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.Response;

public class LoggingInterceptor implements Interceptor {
    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        long startTime = System.nanoTime();

        Response response = chain.proceed(chain.request());

        long endTime = System.nanoTime();
        long duration = TimeUnit.NANOSECONDS.toMillis(endTime - startTime);

        Log.d("RetrofitTiming", "Request took " + duration + " ms");

        return response;
    }
}
