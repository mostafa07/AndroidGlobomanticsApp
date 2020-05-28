package com.example.alexr.ideamanager.services;

import com.example.alexr.ideamanager.models.Idea;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface IdeaService {

    @GET("ideas")
    Call<List<Idea>> getIdeas();

    @GET("ideas/{id}")
    Call<Idea> getIdea(@Path("id") int id);

    @POST("ideas")
    Call<Idea> createIdea(@Body Idea idea);
}
