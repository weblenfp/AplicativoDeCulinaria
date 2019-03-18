package dev.weblen.aplicativodeculinaria.ui.activities;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import dev.weblen.aplicativodeculinaria.R;
import dev.weblen.aplicativodeculinaria.ui.fragments.RecipesFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
