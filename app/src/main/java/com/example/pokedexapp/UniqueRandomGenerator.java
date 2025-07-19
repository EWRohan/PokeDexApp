package com.example.pokedexapp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UniqueRandomGenerator {

    private List<Integer> numberPool;
    private int currentIndex;

    public UniqueRandomGenerator() {
        numberPool = new ArrayList<>();
        for (int i = 1; i <= 150; i++) {
            numberPool.add(i);
        }
        Collections.shuffle(numberPool); // Shuffle for randomness
        currentIndex = 0;
    }

    public Integer getNextUniqueNumber() {
        if (currentIndex < numberPool.size()) {
            return numberPool.get(currentIndex++);
        } else {
            // All numbers have been used
            return null;
        }
    }
}

