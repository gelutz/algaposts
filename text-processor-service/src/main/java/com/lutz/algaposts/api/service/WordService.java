package com.lutz.algaposts.api.service;

import org.springframework.stereotype.Service;

@Service
public class WordService {
    private final Double COST_PER_WORD = 0.10;

    public int countWords(String body) {
        int words = 0;

        String[] lines = body.split("\n");
        for (String line : lines) {
            words += line.split(" ").length;
        }

        return words;
    }

    public Double calculateCost(int words) {
        return words * COST_PER_WORD;
    }
}
