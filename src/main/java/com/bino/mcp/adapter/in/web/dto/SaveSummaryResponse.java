package com.bino.mcp.adapter.in.web.dto;


public record SaveSummaryResponse(String status, String message) {

    public static SaveSummaryResponse success() {
        return new SaveSummaryResponse("OK", "Summary saved");
    }
}
