package org.codex.organ.app.usecase;

import org.codex.organ.app.dto.Report;

/**
 * Produce a report that lists managers with earnings below a certain threshold.
 * The threshold is expressed as a percentage above the average earnings of the subordinates of the manager.
 * For example, if the threshold is 20, the report should list all managers whose earnings are less than 120% of the average earnings of their subordinates.
 */
public interface ReportManagersWithLowEarningsUseCase {
    Report exec(Integer thresholdPercentage);
}
