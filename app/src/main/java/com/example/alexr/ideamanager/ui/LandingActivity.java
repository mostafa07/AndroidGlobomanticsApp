package com.example.alexr.ideamanager.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.alexr.ideamanager.R;
import com.example.alexr.ideamanager.services.MessageService;
import com.example.alexr.ideamanager.services.builder.ServiceBuilder;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LandingActivity extends AppCompatActivity {

    private static final String LOG_TAG = LandingActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        final TextView messageTextView = findViewById(R.id.message);

        MessageService taskService = ServiceBuilder.buildService(MessageService.class);
        Call<String> call = taskService.getMessages();

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    messageTextView.setText(response.body());
                } else if (response.code() == 401) {
                    Toast.makeText(LandingActivity.this, R.string.error_session_expired, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LandingActivity.this, R.string.error_failed_to_retrieve_items, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                if (t instanceof IOException) {
                    Toast.makeText(LandingActivity.this, R.string.error_connection, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LandingActivity.this, R.string.error_request_failed, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void GetStarted(View view) {
        Intent intent = new Intent(this, IdeaListActivity.class);
        startActivity(intent);
        this.finish();
    }
}
