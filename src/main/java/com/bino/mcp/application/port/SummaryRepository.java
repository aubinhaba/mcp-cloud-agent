package com.bino.mcp.application.port;

import com.bino.mcp.domain.Summary;

import java.util.List;

/**
 * Output port (hexagonal): persistence of summaries.
 */
public interface SummaryRepository {

    Summary save(Summary summary);

    List<Summary> findAll();
}
