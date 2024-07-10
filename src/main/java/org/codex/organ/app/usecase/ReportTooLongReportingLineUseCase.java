package org.codex.organ.app.usecase;

import org.codex.organ.app.dto.Report;

/**
 * Produce report that contains the employees with a reporting line that is too long.
 * The threshold is the maximum number of managers between the employee and the CEO.
 */
public interface ReportTooLongReportingLineUseCase {
    Report exec(Integer threshold);
}
