package dev.weblen.aplicativodeculinaria.api;

import java.util.List;

import dev.weblen.aplicativodeculinaria.models.Recipe;
import retrofit2.Call;
import retrofit2.http.GET;

interface APIInterface {
    @GET("topher/2017/May/59121517_baking/baking.json")
    Call<List<Recipe>> doGetRecipes();
}
