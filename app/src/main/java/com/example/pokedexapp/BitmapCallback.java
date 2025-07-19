package com.example.pokedexapp;

import android.graphics.Bitmap;

public interface BitmapCallback {
    void onBitmapLoaded(Bitmap bitmap);
    void onBitmapLoadFailed(Exception e);
}
