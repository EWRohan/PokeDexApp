package com.example.pokedexapp;

import android.graphics.Bitmap;

import java.io.Serializable;

public class Pokemon implements Serializable {
    Bitmap img;
    String name,url,imgUrl;
    String move1,move2,move3,move4;
    Pokemon(Bitmap img,String name,String url,String imgUrl){
        this.name=name;
        this.img=img;
        this.url=url;
        this.imgUrl=imgUrl;
    }
    Pokemon(String name,String move1,String move2,String move3,String move4)
    {
        this.name=name;
        this.move1=move1;
        this.move2=move2;
        this.move3=move3;
        this.move4=move4;
    }

    public Bitmap getImg() {
        return img;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public String getImgUrl() {
        return imgUrl;
    }
}
