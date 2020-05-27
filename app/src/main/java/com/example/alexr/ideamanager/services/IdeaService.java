package com.example.alexr.ideamanager.services;

import com.example.alexr.ideamanager.models.Idea;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface IdeaService {

    @GET("ideas")
    Call<List<Idea>> getIdeas();
}
