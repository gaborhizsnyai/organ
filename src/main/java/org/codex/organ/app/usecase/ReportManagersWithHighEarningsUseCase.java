package org.codex.organ.app.usecase;

import org.codex.organ.app.dto.Report;

/**
 * Produce report that lists managers with earnings above a certain threshold.
 * The threshold is expressed as a percentage above the average earnings of the subordinates of the manager.
 * For example, if the threshold is 50, the report should list all managers whose earnings are more than 150% of the average earnings of their subordinates.
 */
public interface ReportManagersWithHighEarningsUseCase {
    Report exec(Integer thresholdPercentage);
}
