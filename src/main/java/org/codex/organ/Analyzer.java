package org.codex.organ;

import org.codex.organ.common.ApplicationConfig;

import java.io.InputStream;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Analyzer {
    private static final Logger log = Logger.getLogger(Analyzer.class.getSimpleName());
    private final ApplicationConfig config;

    public Analyzer(ApplicationConfig config) {
        this.config = config;
    }

    public void run(App.Parameters params, InputStream source, PrintWriter output) {
        var count = config.importEmployeesUseCase().exec(source);
        log.log(Level.INFO, "Processing {0} employee(s)", count);

        config.reportManagersWithLowEarningsUseCase()
                .exec(params.lowEarningsThreshold())
                .print(output);

        config.reportManagersWithHighEarningsUseCase()
                .exec(params.highEarningsThreshold())
                .print(output);

        config.reportTooLongReportingLineUseCase()
                .exec(params.reportingLineThreshold())
                .print(output);

    }

}
