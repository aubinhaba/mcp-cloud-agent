package com.bino.mcp.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SummaryTest {

    @Test
    void create_withValidData_succeeds() {
        Summary summary = new Summary("Test Title", "Test summary content");

        assertEquals("Test Title", summary.title());
        assertEquals("Test summary content", summary.summary());
    }

    @Test
    void create_withNullTitle_throwsException() {
        assertThrows(NullPointerException.class, () -> new Summary(null, "Summary"));
    }

    @Test
    void create_withNullSummary_throwsException() {
        assertThrows(NullPointerException.class, () -> new Summary("Title", null));
    }

    @Test
    void create_withBlankTitle_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> new Summary("   ", "Summary"));
    }

    @Test
    void create_withBlankSummary_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> new Summary("Title", "  "));
    }

    @Test
    void equals_withSameData_returnsTrue() {
        Summary s1 = new Summary("Title", "Summary");
        Summary s2 = new Summary("Title", "Summary");

        assertEquals(s1, s2);
        assertEquals(s1.hashCode(), s2.hashCode());
    }
}
