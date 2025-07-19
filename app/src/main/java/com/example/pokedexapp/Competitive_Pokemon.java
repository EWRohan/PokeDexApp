package com.example.pokedexapp;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import androidx.core.content.ContextCompat;
import androidx.navigation.ui.AppBarConfiguration;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.pokedexapp.databinding.ActivityCompetetivePokemonBinding;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Competitive_Pokemon extends AppCompatActivity {
    Button compButton,compCopyButton;
    Spinner compSpinner;
    TextView compTextPokemon1,compTextPokemon2,compTextPokemon3,compTextPokemon4,compTextPokemon5,compTextPokemon6;
    ImageView compImagePokemon1,compImagePokemon2,compImagePokemon3,compImagePokemon4,compImagePokemon5,compImagePokemon6;
    String selectedApi="https:// data. pkmn. cc/ sets/ gen1ou. json";
    ArrayList<String> apis;
    ArrayList<Pokemon> pokemons= new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_competetive_pokemon);

        initVariable();
        ArrayAdapter<String> spinnerAdapter=new ArrayAdapter<>(Competitive_Pokemon.this, android.R.layout.simple_spinner_dropdown_item,apis);
        compSpinner.setAdapter(spinnerAdapter);
        compButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedApi=compSpinner.getSelectedItem().toString();
                Log.d("selectedApi",selectedApi);
//                loadMoves(selectedApi);
                String Pokemon1=pokemons.get(0).name+"\n-"+pokemons.get(0).move1+"\n-"+pokemons.get(0).move2+"\n-"+pokemons.get(0).move3+"\n-"+pokemons.get(0).move4;
                String Pokemon2=pokemons.get(1).name+"\n-"+pokemons.get(1).move1+"\n-"+pokemons.get(1).move2+"\n-"+pokemons.get(1).move3+"\n-"+pokemons.get(1).move4;
                String Pokemon3=pokemons.get(2).name+"\n-"+pokemons.get(2).move1+"\n-"+pokemons.get(2).move2+"\n-"+pokemons.get(2).move3+"\n-"+pokemons.get(2).move4;
                String Pokemon4=pokemons.get(3).name+"\n-"+pokemons.get(3).move1+"\n-"+pokemons.get(3).move2+"\n-"+pokemons.get(3).move3+"\n-"+pokemons.get(3).move4;
                String Pokemon5=pokemons.get(4).name+"\n-"+pokemons.get(4).move1+"\n-"+pokemons.get(4).move2+"\n-"+pokemons.get(4).move3+"\n-"+pokemons.get(4).move4;
                String Pokemon6=pokemons.get(5).name+"\n-"+pokemons.get(5).move1+"\n-"+pokemons.get(5).move2+"\n-"+pokemons.get(5).move3+"\n-"+pokemons.get(5).move4;
                compTextPokemon1.setText(Pokemon1);
                compTextPokemon2.setText(Pokemon2);
                compTextPokemon3.setText(Pokemon3);
                compTextPokemon4.setText(Pokemon4);
                compTextPokemon5.setText(Pokemon5);
                compTextPokemon6.setText(Pokemon6);
            }
        });

    }

    public void initVariable()
    {
        apis=new ArrayList<>();
        apis.add("https://data.pkmn.cc/sets/gen1ou.json");
        apis.add("https://data.pkmn.cc/sets/gen2ou.json");
        apis.add("https://data.pkmn.cc/sets/gen3ou.json");
        apis.add("https://data.pkmn.cc/sets/gen4ou.json");
        apis.add("https://data.pkmn.cc/sets/gen5ou.json");
        apis.add("https://data.pkmn.cc/sets/gen6ou.json");
        apis.add("https://data.pkmn.cc/sets/gen7ou.json");
        apis.add("https://data.pkmn.cc/sets/gen8ou.json");
        apis.add("https://data.pkmn.cc/sets/gen9ou.json");
        compSpinner=findViewById(R.id.copmSpinner);
        compButton=findViewById(R.id.compButton);
        compCopyButton=findViewById(R.id.compCopyButton);
        compTextPokemon1=findViewById(R.id.comptextPokemon1);
        compTextPokemon2=findViewById(R.id.comptextPokemon2);
        compTextPokemon3=findViewById(R.id.comptextPokemon3);
        compTextPokemon4=findViewById(R.id.comptextPokemon4);
        compTextPokemon5=findViewById(R.id.comptextPokemon5);
        compTextPokemon6=findViewById(R.id.comptextPokemon6);

        compImagePokemon1=findViewById(R.id.compImgPokemon1);
        compImagePokemon2=findViewById(R.id.compImgPokemon2);
        compImagePokemon3=findViewById(R.id.compImgPokemon3);
        compImagePokemon4=findViewById(R.id.compImgPokemon4);
        compImagePokemon5=findViewById(R.id.compImgPokemon5);
        compImagePokemon6=findViewById(R.id.compImgPokemon6);




    }
    private void loadMoves(String url) {
        AndroidNetworking.get(url).setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("ResponseCompApi",response.toString());
                        try{
                            Iterator<String> Keys=response.keys();
                            List<String> keyList=new ArrayList<>();
                            while(Keys.hasNext())
                            {
                                keyList.add(Keys.next());
                            }
                            Collections.shuffle(keyList);
                            int maxItems = Math.min(6, keyList.size());
                            List<String> selectedKeys = keyList.subList(0, maxItems);
                            for(String pokemon:selectedKeys) {
                                JSONObject pokemon1moveset = response.getJSONObject(capitalizeFirst(pokemon));
                                Iterator<String> keys = pokemon1moveset.keys();
                                String key = keys.next();
                                JSONObject moveset = pokemon1moveset.getJSONObject(key);
                                JSONArray moves = moveset.getJSONArray("moves");
                                ArrayList<String> Pokemon_moves = new ArrayList<>();
                                for (int i = 0; i < moves.length(); i++) {
                                    if (moves.get(i) instanceof JSONArray) {

                                        Random rand = new Random();
                                        JSONArray fourthmove = moves.getJSONArray(i);
                                        Pokemon_moves.add(fourthmove.getString(rand.nextInt(fourthmove.length())));
                                    } else {
                                        String move = moves.getString(i);
                                        Pokemon_moves.add(move);
                                    }
                                }
                                pokemons.add(new Pokemon(pokemon,Pokemon_moves.get(0),Pokemon_moves.get(1),Pokemon_moves.get(2),Pokemon_moves.get(3)));
                                compCopyButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                        Toast.makeText(Competitive_Pokemon.this, "Copied to clipboard", Toast.LENGTH_SHORT).show();

                                        String team = capitalizeFirst(pokemon) + "\n" + "-" + Pokemon_moves.get(0) + "\n" + "-" + Pokemon_moves.get(1) + "\n" + "-" + Pokemon_moves.get(2) + "\n" + "-" + Pokemon_moves.get(3);

                                        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                                        ClipData clip = ClipData.newPlainText("label", team);
                                        clipboard.setPrimaryClip(clip);
                                        Drawable ok = ContextCompat.getDrawable(Competitive_Pokemon.this, R.drawable.ic_check_mark);
                                        compCopyButton.setForeground(ok);
                                        Log.d("moves",Pokemon_moves.get(0)+" "+Pokemon_moves.get(1)+" "+Pokemon_moves.get(2)+" "+Pokemon_moves.get(3));
                                    }
                                });
                            }


                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }

    public String removeSpace(String value)
    {
        return value.replaceAll("\\s+"," ");
    }
    public static String capitalizeFirst(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }

        return input.substring(0, 1).toUpperCase() + input.substring(1);
    }
}