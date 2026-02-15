package com.bino.mcp.application.port;

import java.util.List;
import java.util.Map;

/**
 * Output port (hexagonal): provides the list of articles (MCP resource).
 */
public interface ArticlesProvider {

    List<Map<String, String>> getArticles();
}
