package dev.weblen.aplicativodeculinaria.api;

import android.util.Log;

import java.io.Serializable;
import java.util.List;

import dev.weblen.aplicativodeculinaria.Constants;
import dev.weblen.aplicativodeculinaria.models.Recipe;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public final class APIRecipes implements Serializable {

    private static volatile APIRecipes instance = new APIRecipes();
    private APIInterface               recipesApiService;

    public static APIRecipes getInstance() {
        if (instance == null) {
            synchronized (APIRecipes.class) {
                if (instance == null) instance = new APIRecipes();
            }
        }

        return instance;
    }

    private APIRecipes() {

        if (instance != null) {
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        recipesApiService = retrofit.create(APIInterface.class);
    }

    public void getRecipes(final APICallback<List<Recipe>> recipesApiCallback) {
        recipesApiService.doGetRecipes().enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                recipesApiCallback.onResponse(response.body());
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                if (call.isCanceled()) {
                    Log.d("APIRecipes","Request was cancelled");
                    recipesApiCallback.onCancel();
                } else {
                    Log.d("APIRecipes", t.getMessage());
                    recipesApiCallback.onResponse(null);
                }
            }
        });
    }
}
