package com.example.LinkShortener.Models;

import java.util.Random;

public class CodeGenerator {
    final protected String symbols = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";


    public String generateNewLink(){
        String result = "";

        Random rndIndex = new Random();
        for(int i = 0; i < 6; i++){
            result += symbols.charAt(rndIndex.nextInt(62));
        }

        return result;
    }
}
