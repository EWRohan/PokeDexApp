package com.example.pokedexapp;

import static com.example.pokedexapp.Pokemon_Details.capitalizeFirst;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

public class Random_Pokemon extends AppCompatActivity {
    ImageView imgPokemon1,imgPokemon2,imgPokemon3,imgPokemon4,imgPokemon5,imgPokemon6;
    TextView textPokemon1,textPokemon2,textPokemon3,textPokemon4,textPokemon5,textPokemon6;
    RelativeLayout layout1,layout2,layout3,layout4,layout5,layout6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_random_pokemon);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        imgPokemon1=findViewById(R.id.imgPokemon1);
        imgPokemon2=findViewById(R.id.imgPokemon2);
        imgPokemon3=findViewById(R.id.imgPokemon3);
        imgPokemon4=findViewById(R.id.imgPokemon4);
        imgPokemon5=findViewById(R.id.imgPokemon5);
        imgPokemon6=findViewById(R.id.imgPokemon6);

        textPokemon1=findViewById(R.id.textPokemon1);
        textPokemon2=findViewById(R.id.textPokemon2);
        textPokemon3=findViewById(R.id.textPokemon3);
        textPokemon4=findViewById(R.id.textPokemon4);
        textPokemon5=findViewById(R.id.textPokemon5);
        textPokemon6=findViewById(R.id.textPokemon6);

        layout1=findViewById(R.id.layout1);
        layout2=findViewById(R.id.layout2);
        layout3=findViewById(R.id.layout3);
        layout4=findViewById(R.id.layout4);
        layout5=findViewById(R.id.layout5);
        layout6=findViewById(R.id.layout6);



        Intent formMain=getIntent();
        Pair pokemon1=(Pair) formMain.getSerializableExtra("pokemon1Pair");
        Pair pokemon2=(Pair) formMain.getSerializableExtra("pokemon2Pair");
        Pair pokemon3=(Pair) formMain.getSerializableExtra("pokemon3Pair");
        Pair pokemon4=(Pair) formMain.getSerializableExtra("pokemon4Pair");
        Pair pokemon5=(Pair) formMain.getSerializableExtra("pokemon5Pair");
        Pair pokemon6=(Pair) formMain.getSerializableExtra("pokemon6Pair");

        assert pokemon1 != null;
        textPokemon1.setText(pokemon1.name);
        assert pokemon2 != null;
        textPokemon2.setText(pokemon2.name);
        assert pokemon3 != null;
        textPokemon3.setText(pokemon3.name);
        assert pokemon4 != null;
        textPokemon4.setText(pokemon4.name);
        assert pokemon5 != null;
        textPokemon5.setText(pokemon5.name);
        assert pokemon6 != null;
        textPokemon6.setText(pokemon6.name);


        loadImage(pokemon1.imgUrl,imgPokemon1);
        loadImage(pokemon2.imgUrl,imgPokemon2);
        loadImage(pokemon3.imgUrl,imgPokemon3);
        loadImage(pokemon4.imgUrl,imgPokemon4);
        loadImage(pokemon5.imgUrl,imgPokemon5);
        loadImage(pokemon6.imgUrl,imgPokemon6);


        layout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap bitmap=((BitmapDrawable)imgPokemon1.getDrawable()).getBitmap();
                next(pokemon1.name,pokemon1.url,bitmap);
            }
        });

        layout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap bitmap=((BitmapDrawable)imgPokemon2.getDrawable()).getBitmap();
                next(pokemon2.name,pokemon2.url,bitmap);
            }
        });

        layout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap bitmap=((BitmapDrawable)imgPokemon3.getDrawable()).getBitmap();
                next(pokemon3.name,pokemon3.url,bitmap);
            }
        });

        layout4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap bitmap=((BitmapDrawable)imgPokemon4.getDrawable()).getBitmap();
                next(pokemon4.name,pokemon4.url,bitmap);
            }
        });

        layout5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap bitmap=((BitmapDrawable)imgPokemon5.getDrawable()).getBitmap();
                next(pokemon5.name,pokemon5.url,bitmap);
            }
        });

        layout6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap bitmap=((BitmapDrawable)imgPokemon6.getDrawable()).getBitmap();
                next(pokemon6.name,pokemon6.url,bitmap);
            }
        });

    }

    public void loadImage(String imgUrl, ImageView imageView) {
        Glide.with(this)
                .load(imgUrl)
                .placeholder(R.drawable.ic_launcher_foreground) // optional: shown while loading
                .error(R.drawable.ic_launcher_background)       // optional: shown on error
                .into(imageView);
    }
    public void next(String name, String url, Bitmap bitmap)
    {
        Intent toPokemonDetails=new Intent(Random_Pokemon.this,Pokemon_Details.class);
        toPokemonDetails.putExtra("img",bitmap);
        toPokemonDetails.putExtra("name",name);
        toPokemonDetails.putExtra("url",url);
        startActivity(toPokemonDetails);
    }

}