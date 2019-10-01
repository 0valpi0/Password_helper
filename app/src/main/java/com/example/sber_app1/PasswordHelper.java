package com.example.sber_app1;

public class PasswordHelper {
    private final String[] russian;
    private final String[] latin;

    public PasswordHelper(String[] russian, String[] latin) {
        this.russian = russian;
        this.latin = latin;

        if (russian.length != latin.length){
            throw new IllegalArgumentException();
        }
    }

    public String convert(CharSequence source){
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < source.length(); i++){
            char c = source.charAt(i);
            String s = String.valueOf(c);
            boolean found = false;

            for (int j = 0; j < russian.length; j++) {
                if (russian[j].equals(s)){
                    result.append(latin[j]);
                    found = true;
                    break;
                }
            }
            
            if (!found){
                result.append(c);
            }
        }

        return result.toString();
    }
}
