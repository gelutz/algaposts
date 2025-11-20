package com.lutz.algaposts.api.controller.dto;

import java.util.Objects;

public record PostOutput(
    String id,
    String title,
    String body,
    String author,
    int wordCount,
    double calculatedValue
) {
    public PostOutput {
        // simple normalization/defensive defaults (optional)
        if (id == null) id = "";
        if (title == null) title = "";
        if (body == null) body = "";
        if (author == null) author = "";
    }

    @Override
    public String toString() {
        return "PostOutput[id=" + id + ", title=" + title + ", author=" + author + ", wordCount=" + wordCount + ", calculatedValue=" + calculatedValue + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PostOutput other)) return false;
        return wordCount == other.wordCount
            && Double.compare(other.calculatedValue, calculatedValue) == 0
            && Objects.equals(id, other.id)
            && Objects.equals(title, other.title)
            && Objects.equals(body, other.body)
            && Objects.equals(author, other.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, body, author, wordCount, calculatedValue);
    }
}
