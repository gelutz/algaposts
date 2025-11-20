package com.lutz.algaposts.api.controller.dto;

import java.util.Objects;

public record PostSummaryOutput(
    String id,
    String title,
    String summary,
    String author
) {
    public PostSummaryOutput {
        if (id == null) id = "";
        if (title == null) title = "";
        if (summary == null) summary = "";
        if (author == null) author = "";
    }

    @Override
    public String toString() {
        return "PostSummaryOutput[id=" + id + ", title=" + title + ", author=" + author + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PostSummaryOutput other)) return false;
        return Objects.equals(id, other.id)
            && Objects.equals(title, other.title)
            && Objects.equals(summary, other.summary)
            && Objects.equals(author, other.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, summary, author);
    }
}
