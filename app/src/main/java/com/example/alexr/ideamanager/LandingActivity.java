package com.example.alexr.ideamanager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.alexr.ideamanager.services.MessageService;
import com.example.alexr.ideamanager.services.ServiceBuilder;

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
                messageTextView.setText(response.body());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(LandingActivity.this, R.string.error_request_failed, Toast.LENGTH_SHORT).show();
                Log.e(LOG_TAG, t.getMessage());
            }
        });
    }

    public void GetStarted(View view) {
        Intent intent = new Intent(this, IdeaListActivity.class);
        startActivity(intent);
    }
}
