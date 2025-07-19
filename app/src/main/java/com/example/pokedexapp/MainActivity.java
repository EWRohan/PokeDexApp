package com.example.pokedexapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.BitmapRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
RecyclerView recyclerView;
LinearLayout linearLayout;
FloatingActionButton randomButton;
ArrayList<Pokemon>pokemons=new ArrayList<>();
SearchView search_bar;
RecyclerPokemonAdapter adapter=new RecyclerPokemonAdapter(MainActivity.this,pokemons);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        randomButton=findViewById(R.id.randomButton);
        recyclerView=findViewById(R.id.recyclerView);
        search_bar=findViewById(R.id.search_bar);
        linearLayout=findViewById(R.id.splashLayout);
        randomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Random rand=new Random();
                UniqueRandomGenerator randomGenerator = new UniqueRandomGenerator();
                Intent randomIntent=new Intent(MainActivity.this,Random_Pokemon.class);
//                Intent compIntent=new Intent(MainActivity.this,Competitive_Pokemon.class);
//                Pokemon pokemon1=pokemons.get((int)(Math.random()*149));
//                randomIntent.putExtra("pokemon1Pair",new Pair(pokemon1.getName(),pokemon1.getImgUrl(),pokemon1.getUrl()));
//                Pokemon pokemon2=pokemons.get((int)(Math.random()*149));
//                randomIntent.putExtra("pokemon2Pair",new Pair(pokemon2.getName(),pokemon2.getImgUrl(),pokemon2.getUrl()));
//                Pokemon pokemon3=pokemons.get((int)(Math.random()*149));
//                randomIntent.putExtra("pokemon3Pair",new Pair(pokemon3.getName(),pokemon3.getImgUrl(),pokemon3.getUrl()));
//                Pokemon pokemon4=pokemons.get((int)(Math.random()*149));
//                randomIntent.putExtra("pokemon4Pair",new Pair(pokemon4.getName(),pokemon4.getImgUrl(),pokemon4.getUrl()));
//                Pokemon pokemon5=pokemons.get((int)(Math.random()*149));
//                randomIntent.putExtra("pokemon5Pair",new Pair(pokemon5.getName(),pokemon5.getImgUrl(),pokemon5.getUrl()));
//                Pokemon pokemon6=pokemons.get((int)(Math.random()*149));
//                randomIntent.putExtra("pokemon6Pair",new Pair(pokemon6.getName(),pokemon6.getImgUrl(),pokemon6.getUrl()));

                Pokemon pokemon1=pokemons.get(randomGenerator.getNextUniqueNumber());
                randomIntent.putExtra("pokemon1Pair",new Pair(pokemon1.getName(),pokemon1.getImgUrl(),pokemon1.getUrl()));
                Pokemon pokemon2=pokemons.get(randomGenerator.getNextUniqueNumber());
                randomIntent.putExtra("pokemon2Pair",new Pair(pokemon2.getName(),pokemon2.getImgUrl(),pokemon2.getUrl()));
                Pokemon pokemon3=pokemons.get(randomGenerator.getNextUniqueNumber());
                randomIntent.putExtra("pokemon3Pair",new Pair(pokemon3.getName(),pokemon3.getImgUrl(),pokemon3.getUrl()));
                Pokemon pokemon4=pokemons.get(randomGenerator.getNextUniqueNumber());
                randomIntent.putExtra("pokemon4Pair",new Pair(pokemon4.getName(),pokemon4.getImgUrl(),pokemon4.getUrl()));
                Pokemon pokemon5=pokemons.get(randomGenerator.getNextUniqueNumber());
                randomIntent.putExtra("pokemon5Pair",new Pair(pokemon5.getName(),pokemon5.getImgUrl(),pokemon5.getUrl()));
                Pokemon pokemon6=pokemons.get(randomGenerator.getNextUniqueNumber());
                randomIntent.putExtra("pokemon6Pair",new Pair(pokemon6.getName(),pokemon6.getImgUrl(),pokemon6.getUrl()));
                startActivity(randomIntent);
            }
        });
        search_bar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return true;
            }
        });
        recyclerView.setLayoutManager(new GridLayoutManager(this,3));
//        String url="https://pokeapi.co/api/v2/pokemon/";
        String url="https://pokeapi.co/api/v2/pokemon?limit=150&offset=0";
//        JSONObject json= loadJSONFromAsset(this, "Pokemon.json");
//        loadPokemon(json);
        ApiCall(url);
    }

    private void ApiCall(String url) {
        AndroidNetworking.initialize(getApplicationContext());
        AndroidNetworking.get(url).setPriority(Priority.HIGH).build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Response",response.toString());
                        try {
                            JSONArray pokearr=response.getJSONArray("results");
                            for (int i = 0; i < pokearr.length(); i++) {
                                JSONObject pokenames=pokearr.getJSONObject(i);
                                String name=pokenames.getString("name");
                                String pokemonUrl=pokenames.getString("url");
                                AndroidNetworking.get(pokenames.getString("url")).setPriority(Priority.HIGH).build()
                                        .getAsJSONObject(new JSONObjectRequestListener() {
                                            @Override
                                            public void onResponse(JSONObject response) {
                                                try {
                                                    JSONObject sprites=response.getJSONObject("sprites");

                                                    String pokeImgUrl=sprites.getString("front_default");


//                                                    ImageLoader imageLoader=new ImageLoader(name);
//                                                    imageLoader.loadBitmapFromUrl(getApplicationContext(),pokeImgUrl, new BitmapCallback() {
//                                                        @Override
//                                                        public void onBitmapLoaded(Bitmap bitmap) {
//                                                            pokemons.add(new Pokemon(bitmap,name));
//                                                            Log.d("ImageLoaderName",name);
//                                                        }
//
//                                                        @Override
//                                                        public void onBitmapLoadFailed(Exception e) {
//                                                            Log.e("BitmapError",e.toString());
//                                                        }
//                                                    });
                                                    AndroidNetworking.get(pokeImgUrl)
                                                            .setTag("imageRequestTag")
                                                            .setPriority(Priority.MEDIUM)
                                                            .setBitmapMaxHeight(100)
                                                            .setBitmapMaxWidth(100)
                                                            .setBitmapConfig(Bitmap.Config.ARGB_8888)
                                                            .build()
                                                            .getAsBitmap(new BitmapRequestListener() {
                                                                @Override
                                                                public void onResponse(Bitmap bitmap) {
                                                                    // do anything with bitmap
                                                                    pokemons.add(new Pokemon(bitmap,name,pokemonUrl,pokeImgUrl));
                                                                    adapter.notifyDataSetChanged();
                                                                }
                                                                @Override
                                                                public void onError(ANError error) {
                                                                    // handle error
                                                                    Log.e("ImageUrlError",error.toString());
                                                                }
                                                            });
                                                } catch (JSONException e) {
                                                    throw new RuntimeException(e);
                                                }
                                            }

                                            @Override
                                            public void onError(ANError anError) {
                                                Log.e("ErrorSprites",anError.toString());
                                            }
                                        });
//                                ImageLoader imageLoader=new ImageLoader(name);
//                                imageLoader.loadBitmapFromUrl(getApplicationContext(),pokeImgUrl, new BitmapCallback() {
//                                    @Override
//                                    public void onBitmapLoaded(Bitmap bitmap) {
//                                        pokemons.add(new Pokemon(bitmap,name));
//                                        Log.d("ImageLoaderName",name);
//                                    }
//
//                                    @Override
//                                    public void onBitmapLoadFailed(Exception e) {
//                                        Log.e("BitmapError",e.toString());
//                                    }
//                                });
                            }
//                            new Handler().postDelayed(() -> setRecyclerView(),10000);
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    setRecyclerView();
                                    linearLayout.setVisibility(View.GONE);
                                }
                            },10000);
//                                setRecyclerView();
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("PokemonApiError",anError.toString());
                    }
                });
    }
    public static JSONObject loadJSONFromAsset(Context context, String filename) {
        String jsonString;
        try {
            InputStream is = context.getAssets().open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            jsonString = new String(buffer, StandardCharsets.UTF_8);
            return new JSONObject(jsonString);

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    public void loadPokemon(JSONObject response)
    {
        try {
            JSONArray pokearr=response.getJSONArray("results");
            for (int i = 0; i < pokearr.length(); i++) {
                JSONObject pokenames=pokearr.getJSONObject(i);
                String name=pokenames.getString("name");
                String pokemonUrl=pokenames.getString("url");
                String num=pokemonUrl.substring(pokemonUrl.length()-2,pokemonUrl.length()-1);
                String pokeImgUrl="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/"+num+".png";
                AndroidNetworking.get(pokeImgUrl)
                        .setTag("imageRequestTag")
                        .setPriority(Priority.MEDIUM)
                        .setBitmapMaxHeight(100)
                        .setBitmapMaxWidth(100)
                        .setBitmapConfig(Bitmap.Config.ARGB_8888)
                        .build()
                        .getAsBitmap(new BitmapRequestListener() {
                            @Override
                            public void onResponse(Bitmap bitmap) {
                                // do anything with bitmap
                                pokemons.add(new Pokemon(bitmap,name,pokemonUrl,pokeImgUrl));
                                adapter.notifyDataSetChanged();
                            }
                            @Override
                            public void onError(ANError error) {
                                // handle error
                                Log.e("ImageUrlError",error.toString());
                            }
                        });
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
    public void setRecyclerView()
    {
        recyclerView.setAdapter(adapter);
    }
    public static class ImageLoader {
        String name;
        ImageLoader(String name)
        {
            this.name=name;
        }
        public void loadBitmapFromUrl(Context context, String url, BitmapCallback callback) {
            Glide.with(context)
                    .asBitmap()
                    .load(url)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(new CustomTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            callback.onBitmapLoaded(resource);
                        }

                        @Override
                        public void onLoadCleared(@Nullable Drawable placeholder) {
                            // Handle cleanup if needed
                        }

                        @Override
                        public void onLoadFailed(@Nullable Drawable errorDrawable) {
                            callback.onBitmapLoadFailed(new Exception("Image load failed"));
                        }
                    });
        }
    }
}