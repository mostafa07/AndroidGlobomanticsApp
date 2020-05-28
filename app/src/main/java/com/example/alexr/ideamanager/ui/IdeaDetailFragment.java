package com.example.alexr.ideamanager.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.alexr.ideamanager.R;
import com.example.alexr.ideamanager.models.Idea;
import com.example.alexr.ideamanager.services.IdeaService;
import com.example.alexr.ideamanager.services.builder.ServiceBuilder;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IdeaDetailFragment extends Fragment {

    public static final String ARG_ITEM_ID = "item_id";

    private Idea mIdea;

    public IdeaDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.idea_detail, container, false);

        Button updateIdea = rootView.findViewById(R.id.idea_update);
        Button deleteIdea = rootView.findViewById(R.id.idea_delete);

        final EditText ideaName = rootView.findViewById(R.id.idea_name);
        final EditText ideaDescription = rootView.findViewById(R.id.idea_description);
        final EditText ideaStatus = rootView.findViewById(R.id.idea_status);
        final EditText ideaOwner = rootView.findViewById(R.id.idea_owner);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            final CollapsingToolbarLayout appBarLayout = requireActivity().findViewById(R.id.toolbar_layout);

            IdeaService ideaService = ServiceBuilder.buildService(IdeaService.class);
            Call<Idea> call = ideaService.getIdea(getArguments().getInt(ARG_ITEM_ID));

            call.enqueue(new Callback<Idea>() {
                @Override
                public void onResponse(Call<Idea> call, Response<Idea> response) {
                    mIdea = response.body();

                    ideaName.setText(mIdea.getName());
                    ideaDescription.setText(mIdea.getDescription());
                    ideaOwner.setText(mIdea.getOwner());
                    ideaStatus.setText(mIdea.getStatus());

                    if (appBarLayout != null) {
                        appBarLayout.setTitle(mIdea.getName());
                    }
                }

                @Override
                public void onFailure(Call<Idea> call, Throwable t) {
                    Toast.makeText(requireContext(), R.string.error_request_failed, Toast.LENGTH_SHORT).show();
                }
            });
        }

        updateIdea.setOnClickListener(view -> {
            IdeaService ideaService = ServiceBuilder.buildService(IdeaService.class);
            Call<Idea> call = ideaService.updateIdea(mIdea.getId(),
                    ideaName.getText().toString(),
                    ideaDescription.getText().toString(),
                    ideaStatus.getText().toString(),
                    ideaOwner.getText().toString());

            call.enqueue(new Callback<Idea>() {
                @Override
                public void onResponse(Call<Idea> call, Response<Idea> response) {
                    Intent intent = new Intent(getContext(), IdeaListActivity.class);
                    startActivity(intent);
                }

                @Override
                public void onFailure(Call<Idea> call, Throwable t) {
                    Toast.makeText(requireContext(), R.string.error_request_failed, Toast.LENGTH_SHORT).show();
                }
            });
        });

        deleteIdea.setOnClickListener(view -> {
            IdeaService ideaService = ServiceBuilder.buildService(IdeaService.class);
            Call<Void> call = ideaService.deleteIdea(mIdea.getId());

            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    Intent intent = new Intent(getContext(), IdeaListActivity.class);
                    startActivity(intent);
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(requireContext(), R.string.error_request_failed, Toast.LENGTH_SHORT).show();
                }
            });
        });

        return rootView;
    }
}
