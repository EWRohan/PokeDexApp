package com.example.pokedexapp;

import static androidx.core.content.ContextCompat.startActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RecyclerPokemonAdapter extends RecyclerView.Adapter<RecyclerPokemonAdapter.ViewHolder> implements Filterable {
    Context context;
    ArrayList<Pokemon>pokemons;
    ArrayList<Pokemon>pokemonsFilterable;
    RecyclerPokemonAdapter(Context context,ArrayList<Pokemon>pokemons){
        this.pokemons=pokemons;
        this.context=context;
        this.pokemonsFilterable=pokemons;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View pokemoncard=LayoutInflater.from(context).inflate(R.layout.pokemoncard,parent,false);
        return new ViewHolder(pokemoncard);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.pokemonName.setText(pokemons.get(position).name);
        holder.pokemonImage.setImageBitmap(pokemons.get(position).img);
        holder.pokemonLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent next= new Intent(context,Pokemon_Details.class);
                next.putExtra("name",pokemons.get(holder.getAdapterPosition()).name);
                next.putExtra("img",pokemons.get(holder.getAdapterPosition()).img);
                next.putExtra("url",pokemons.get(holder.getAdapterPosition()).url);
                startActivity(context,next,null);

//                Dialog dialog=new Dialog(context);
//                dialog.setContentView(R.layout.pokedetails);
//                ArrayList<String> statNames=new ArrayList<>();
//                ImageView imageView=dialog.findViewById(R.id.dialog_img);
//                TextView textView=dialog.findViewById(R.id.dialog_txt);
//                TextView Type1=dialog.findViewById(R.id.type1);
//                TextView Type2=dialog.findViewById(R.id.type2);
//                TextView Height=dialog.findViewById(R.id.pokemonHeight);
//                TextView Weight=dialog.findViewById(R.id.pokemonWeight);
//                TextView Hp=dialog.findViewById(R.id.pokemonHp);
//                TextView Attack=dialog.findViewById(R.id.pokemonAttack);
//                TextView Defense=dialog.findViewById(R.id.pokemonDefense);
//                TextView SpAtk=dialog.findViewById(R.id.pokemonSpAttack);
//                TextView SpDef=dialog.findViewById(R.id.pokemonSpDefense);
//                TextView Speed=dialog.findViewById(R.id.pokemonSpeed);
//                TextView Overall=dialog.findViewById(R.id.pokemonOverall);
//                AndroidNetworking.initialize(context);
//                AndroidNetworking.get(pokemons.get(holder.getAdapterPosition()).url).setPriority(Priority.HIGH).build()
//                                .getAsJSONObject(new JSONObjectRequestListener() {
//                                    @Override
//                                    public void onResponse(JSONObject response) {
//                                        try {
//                                            String PokemonHeight=response.getString("height");
//                                            String PokemonWeight=response.getString("weight");
//                                            JSONArray types=response.getJSONArray("types");
//                                            JSONObject typesObj1=types.getJSONObject(0);
//                                            JSONObject typesName1=typesObj1.getJSONObject("type");
//                                            String type1=typesName1.getString("name");
//                                            String type2;
//                                            if (types.length()==2) {
//                                                JSONObject typesObj2=types.getJSONObject(1);
//                                                JSONObject typesName2=typesObj2.getJSONObject("type");
//                                                type2=typesName2.getString("name");
//                                            }
//                                            else type2="None";
//
//                                            JSONArray stats=response.getJSONArray("stats");
//                                            for (int i=0;i<stats.length();i++) {
//                                                JSONObject statName = stats.getJSONObject(i);
//                                                String StatName = statName.getString("base_stat");
//                                                statNames.add(StatName);
//                                            }
//                                            int sum=0;
//                                            for (int i=0;i<6;i++)
//                                            {
//                                                sum+=Integer.parseInt(statNames.get(i));
//                                            }
//                                            Height.setText("Height : "+PokemonHeight);
//                                            Weight.setText("Weight : "+PokemonWeight);
//                                            Type1.setText(type1);
//                                            Type2.setText(type2);
//                                            Hp.setText("Hp : "+statNames.get(0));
//                                            Attack.setText("Attack : "+statNames.get(1));
//                                            Defense.setText("Defense : "+statNames.get(2));
//                                            SpAtk.setText("SpAtk : "+statNames.get(3));
//                                            SpDef.setText("SpDef : "+statNames.get(4));
//                                            Speed.setText("Speed : "+statNames.get(5));
//                                            Overall.setText("Overall : "+sum);
//                                            imageView.setImageBitmap(pokemons.get(holder.getAdapterPosition()).img);
//                                            textView.setText(pokemons.get(holder.getAdapterPosition()).name);
//                                            dialog.show();
//
//                                        } catch (JSONException e) {
//                                            throw new RuntimeException(e);
//                                        }
//                                    }
//
//                                    @Override
//                                    public void onError(ANError anError) {
//                                        Log.e("PokemonApiError",anError.toString());
//                                    }
//                                });
//                imageView.setImageBitmap(pokemons.get(holder.getAdapterPosition()).img);
//                textView.setText(pokemons.get(holder.getAdapterPosition()).name);
//                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return pokemons.size();
    }

    @Override
    public Filter getFilter() {
        Filter filter=new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults=new FilterResults();
                if(constraint==null||constraint.length()==0)
                {
                    filterResults.count=pokemonsFilterable.size();
                    filterResults.values=pokemonsFilterable;
                }
                else {
                    String charVal=constraint.toString().toLowerCase();
                    ArrayList<Pokemon> arrayList=new ArrayList<>();
                    for (Pokemon pokemon:pokemonsFilterable) {
                        if(pokemon.name.toLowerCase().contains(charVal))
                        {
                            arrayList.add(pokemon);
                        }
                        filterResults.count=arrayList.size();
                        filterResults.values=arrayList;
                    }
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                pokemons= (ArrayList<Pokemon>) results.values;
                notifyDataSetChanged();
            }
        };
        return filter;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
    TextView pokemonName;
    ImageView pokemonImage;
    RelativeLayout pokemonLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            pokemonImage=itemView.findViewById(R.id.pokemonImage);
            pokemonName=itemView.findViewById(R.id.pokemonName);
            pokemonLayout=itemView.findViewById(R.id.pokemonLayout);
        }
    }
}
