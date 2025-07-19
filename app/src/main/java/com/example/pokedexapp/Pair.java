package com.example.pokedexapp;

import java.io.Serializable;

public class Pair implements Serializable {
    String name,imgUrl,url;
    Pair(String name,String ingUrl,String url)
    {
        this.name=name;
        this.imgUrl=ingUrl;
        this.url=url;
    }
}

