package com.example.alexr.ideamanager.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.alexr.ideamanager.R;
import com.example.alexr.ideamanager.helpers.SampleContent;
import com.example.alexr.ideamanager.models.Idea;
import com.example.alexr.ideamanager.services.IdeaService;
import com.example.alexr.ideamanager.services.builder.ServiceBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IdeaCreateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idea_create);
        Toolbar toolbar = findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);
        final Context context = this;

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Button createIdea = findViewById(R.id.idea_create);
        final EditText ideaName = findViewById(R.id.idea_name);
        final EditText ideaDescription = findViewById(R.id.idea_description);
        final EditText ideaOwner = findViewById(R.id.idea_owner);
        final EditText ideaStatus = findViewById(R.id.idea_status);

        createIdea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Idea newIdea = new Idea();
                newIdea.setName(ideaName.getText().toString());
                newIdea.setDescription(ideaDescription.getText().toString());
                newIdea.setStatus(ideaStatus.getText().toString());
                newIdea.setOwner(ideaOwner.getText().toString());

                IdeaService ideaService = ServiceBuilder.buildService(IdeaService.class);
                Call<Idea> call = ideaService.createIdea(newIdea);

                call.enqueue(new Callback<Idea>() {
                    @Override
                    public void onResponse(Call<Idea> call, Response<Idea> response) {
                        Intent intent = new Intent(context, IdeaListActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<Idea> call, Throwable t) {
                        Toast.makeText(IdeaCreateActivity.this, R.string.error_request_failed, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
