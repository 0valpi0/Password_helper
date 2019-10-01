package com.example.sber_app1;

import java.util.Random;

public class PasswordGen {
    private final String[] symbols;

    public PasswordGen(String[] symbols) {
        this.symbols = symbols;
    }

    public String generate(String[] symbols){
        StringBuilder password = new StringBuilder();

        for (int i = 0; i < 10; i++){
            int index = (int) (Math.random() * symbols.length);
            password.append(symbols[index]);
        }

        return password.toString();
    }

    public String[] merge (String[] first, String[] second){
        String[] merged = new String[first.length + second.length];
        System.arraycopy(first, 0, merged, 0, first.length);
        System.arraycopy(second, 0, merged, first.length, second.length);
        return  merged;
    }

    public String[] randomize(String[] arr) {
        String[] randomizedArray = new String[arr.length];
        System.arraycopy(arr, 0, randomizedArray, 0, arr.length);
        Random rgen = new Random();

        for (int i = 0; i < randomizedArray.length; i++) {
            int randPos = rgen.nextInt(randomizedArray.length);
            String tmp = randomizedArray[i];
            randomizedArray[i] = randomizedArray[randPos];
            randomizedArray[randPos] = tmp;
        }

        return randomizedArray;
    }
}
