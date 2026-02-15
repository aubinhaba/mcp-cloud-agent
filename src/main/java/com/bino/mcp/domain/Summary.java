package com.bino.mcp.domain;

import java.util.Objects;

/**
 * Domain model: article summary (immutable record).
 */
public record Summary(String title, String summary) {

    public Summary {
        Objects.requireNonNull(title, "title must not be null");
        Objects.requireNonNull(summary, "summary must not be null");
        if (title.isBlank()) {
            throw new IllegalArgumentException("title must not be blank");
        }
        if (summary.isBlank()) {
            throw new IllegalArgumentException("summary must not be blank");
        }
    }
}
