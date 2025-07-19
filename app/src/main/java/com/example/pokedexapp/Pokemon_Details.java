package com.example.pokedexapp;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.palette.graphics.Palette;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class Pokemon_Details extends AppCompatActivity {
ImageView pokemonDetailImg;
String selectedPokemonNames = "Alakazam Arbok Articuno Chansey Charizard Clefable Cloyster Dodrio Dragonair Dragonite Exeggutor Flareon Gengar Golem Gyarados Hypno Jolteon Jynx Kabutops Kangaskhan Kingler Lapras Lickitung Machamp Moltres Omastar Persian Pinsir Poliwrath Porygon Raichu Raticate Rhydon Sandslash Slowbro Snorlax Starmie Tauros Venusaur Victreebel Zapdos";
String Pokemon_name;
Button addToClipBoard;
LinearLayout moves;
TextView move1,move2,move3,move4;
TextView DetainScrnPkmName,statNameHp,statNameAtk,statNameDef,statNameSpAtk,statNameSpDef,statNameSpeed,statNameOverAll,pokedexEntry,DetailScrnHeight,DetailScrnWeight,DetailScrnAbilities,DetailScrnTypes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pokemon_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageView img=findViewById(R.id.pokemonDetailImg);
        TextView pokedexEntry=findViewById(R.id.pokedexEntry);
        TextView name=findViewById(R.id.DetainScrnPkmName);
        TextView Height=findViewById(R.id.DetailScrnHeight);
        TextView Weight=findViewById(R.id.DetailScrnWeight);
        TextView Type=findViewById(R.id.DetailScrnTypes);
        TextView hp=findViewById(R.id.statNameHp);
        TextView attack=findViewById(R.id.statNameAtk);
        TextView defense=findViewById(R.id.statNameDef);
        TextView spatk=findViewById(R.id.statNameSpAtk);
        TextView spdef=findViewById(R.id.statNameSpDef);
        TextView speed=findViewById(R.id.statNameSpeed);
        TextView overall=findViewById(R.id.statNameOverAll);
        TextView ability=findViewById(R.id.DetailScrnAbilities);

        addToClipBoard=findViewById(R.id.addToClipBoard);

        move1=findViewById(R.id.PokemonMove1);
        move2=findViewById(R.id.PokemonMove2);
        move3=findViewById(R.id.PokemonMove3);
        move4=findViewById(R.id.PokemonMove4);
//
        LinearLayout moves=findViewById(R.id.moves);


        ArrayList<String> statNames=new ArrayList<>();
        Intent intent=getIntent();
        Pokemon_name=intent.getStringExtra("name");
        Bitmap pokemonimg=intent.getParcelableExtra("img");
//        img.setImageBitmap(intent.getParcelableExtra("img"));
        name.setText(capitalizeFirst(Pokemon_name));

        assert pokemonimg != null;
        Palette.from(pokemonimg).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                // Extract primary and secondary colors
                int primaryColor = palette.getVibrantColor(Color.GRAY);
                int secondaryColor = palette.getLightVibrantColor(Color.DKGRAY);

                // Create gradient drawable with extracted colors
                GradientDrawable gradientDrawable = new GradientDrawable(
                        GradientDrawable.Orientation.TOP_BOTTOM,
                        new int[] { primaryColor, secondaryColor }
                );

                // Option 1: Set gradient as background
//                imageView.setBackground(gradientDrawable);

                // Option 2: Layer the gradient behind the image
                Drawable imageDrawable = new BitmapDrawable(getResources(), pokemonimg);
                Drawable[] layers = new Drawable[] { gradientDrawable, imageDrawable };
                LayerDrawable layerDrawable = new LayerDrawable(layers);
                img.setImageDrawable(layerDrawable);
            }
        });


        AndroidNetworking.get(intent.getStringExtra("url")).setPriority(Priority.HIGH).build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                                            String PokemonHeight=response.getString("height");
                                            String PokemonWeight=response.getString("weight");
                                            JSONArray types=response.getJSONArray("types");
                                            JSONObject typesObj1=types.getJSONObject(0);
                                            JSONObject typesName1=typesObj1.getJSONObject("type");
                                            String type1=typesName1.getString("name");
                                            String type2;
                                            if (types.length()==2) {
                                                JSONObject typesObj2=types.getJSONObject(1);
                                                JSONObject typesName2=typesObj2.getJSONObject("type");
                                                type2=typesName2.getString("name");
                                            }
                                            else type2="None";
                                            JSONArray stats=response.getJSONArray("stats");
                                            for (int i=0;i<stats.length();i++) {
                                                JSONObject statName = stats.getJSONObject(i);
                                                String StatName = statName.getString("base_stat");
                                                statNames.add(StatName);
                                            }
                                            StringBuilder ablitiystring= new StringBuilder();
                                            JSONArray abilities=response.getJSONArray("abilities");
                                            for(int i=0;i< abilities.length();i++)
                                            {
                                                JSONObject abilityObj=abilities.getJSONObject(i);
                                                JSONObject abilityName=abilityObj.getJSONObject("ability");
                                                ablitiystring.append("  ").append(abilityName.getString("name"));
                                            }



                                            //for overall score
                                            int sum=0;
                                            for (int i=0;i<6;i++)
                                            {
                                                sum+=Integer.parseInt(statNames.get(i));
                                            }
                                            JSONObject species=response.getJSONObject("species");
                                            AndroidNetworking.get(species.getString("url")).setPriority(Priority.HIGH)
                                                            .build().getAsJSONObject(new JSONObjectRequestListener() {
                                                        @Override
                                                        public void onResponse(JSONObject response) {
                                                            try {
                                                                JSONArray entry=response.getJSONArray("flavor_text_entries");
                                                                JSONObject PokemonDes=entry.getJSONObject(1);
                                                                String Description=PokemonDes.getString("flavor_text");
                                                                pokedexEntry.setText(removeSpace(Description));

//                                                                Log.d("Descripion",removeSpace(Description));
                                                            }catch (Exception e) {
                                                                throw new RuntimeException(e);
                                                            }
                                                        }

                                                        @Override
                                                        public void onError(ANError anError) {
                                                            Log.e("ErrorPokedexEntry",anError.toString());
                                                        }
                                                    });
//                                            Height.setText("Height : "+PokemonHeight);
//                                            Weight.setText("Weight : "+PokemonWeight);
//                                            Type.setText("Types : "+type1+" "+type2);
//                                            hp.setText("Hp : "+statNames.get(0));
//                                            attack.setText("Attack : "+statNames.get(1));
//                                            defense.setText("Defense : "+statNames.get(2));
//                                            spatk.setText("SpAtk : "+statNames.get(3));
//                                            spdef.setText("SpDef : "+statNames.get(4));
//                                            speed.setText("Speed : "+statNames.get(5));
//                                            overall.setText("Overall : "+sum);
                                            Height.setText(String.valueOf(PokemonHeight));
                                            Weight.setText(String.valueOf(PokemonWeight));
                                            if(type2.equals("None"))Type.setText(type1);
                                            else Type.setText(String.format("%s   %s",capitalizeFirst(type1),capitalizeFirst(type2)));
                                            hp.setText(String.format("%s",statNames.get(0)));
                                            attack.setText(String.format("%s",statNames.get(1)));
                                            defense.setText(String.format("%s",statNames.get(2)));
                                            spatk.setText(String.format("%s",statNames.get(3)));
                                            spdef.setText(String.format("%s",statNames.get(4)));
                                            speed.setText(String.format("%s",statNames.get(5)));
                                            overall.setText("Total : "+sum);
                                            ability.setText(ablitiystring.toString());
//                                            imageView.setImageBitmap(pokemons.get(holder.getAdapterPosition()).img);
//                                            textView.setText(pokemons.get(holder.getAdapterPosition()).name);
//                                            dialog.show();
                        }catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("ErrorDetailScreen",anError.toString());
                    }
                });
        if(selectedPokemonNames.contains(capitalizeFirst(Pokemon_name))) {
            loadMoves();
            moves.setVisibility(View.VISIBLE);
        }

    }

    private void loadMoves() {
        AndroidNetworking.get(getResources().getString(R.string.PokemonTeamApi)).setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            JSONObject pokemon1moveset=response.getJSONObject(capitalizeFirst(Pokemon_name));
                            Iterator<String> keys=pokemon1moveset.keys();
                            String key=keys.next();
                            JSONObject moveset=pokemon1moveset.getJSONObject(key);
                            JSONArray moves=moveset.getJSONArray("moves");
                            ArrayList<String> Pokemon_moves=new ArrayList<>();
                            for (int i=0;i<moves.length();i++)
                            {
                                if(moves.get(i) instanceof JSONArray)
                                {

                                    Random rand=new Random();
                                    JSONArray fourthmove=moves.getJSONArray(i);
                                    Pokemon_moves.add(fourthmove.getString(rand.nextInt(fourthmove.length())));
                                }
                                else {
                                    String move = moves.getString(i);
                                    Pokemon_moves.add(move);
                                }
                            }
                            move1.setText(String.valueOf(Pokemon_moves.get(0)));
                            move2.setText(String.valueOf(Pokemon_moves.get(1)));
                            move3.setText(String.valueOf(Pokemon_moves.get(2)));
                            move4.setText(String.valueOf(Pokemon_moves.get(3)));

                            addToClipBoard.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    Toast.makeText(Pokemon_Details.this, "Copied to clipboard", Toast.LENGTH_SHORT).show();

                                    String team=capitalizeFirst(Pokemon_name)+"\n"+"-"+Pokemon_moves.get(0)+"\n"+"-"+Pokemon_moves.get(1)+"\n"+"-"+Pokemon_moves.get(2)+"\n"+"-"+Pokemon_moves.get(3);

                                    ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                                    ClipData clip = ClipData.newPlainText("label", team);
                                    clipboard.setPrimaryClip(clip);
                                    Drawable ok= ContextCompat.getDrawable(Pokemon_Details.this,R.drawable.ic_check_mark);
                                    addToClipBoard.setForeground(ok);


                                }
                            });

                            Log.d("moves",Pokemon_moves.get(0)+" "+Pokemon_moves.get(1)+" "+Pokemon_moves.get(2)+" "+Pokemon_moves.get(3));
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