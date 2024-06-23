package org.codex.organ;

import org.codex.organ.common.ApplicationConfig;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

public class App {
    private static final Logger log = Logger.getLogger(App.class.getName());

    public static void main(String[] args) {
        var params = Parameters.from(args);

        if (!params.isValid()) {
            log.log(Level.SEVERE, "Invalid parameters. Expected: <source>");
            return;
        }

        var buffer = new StringWriter();
        try (var output = new PrintWriter(buffer)) {
            var config = new ApplicationConfig();

            new App(config).run(params, output);

            output.flush();
            log.log(Level.INFO, "\n{0}", buffer);
        } catch (Exception e) {
            log.log(Level.SEVERE, e.getMessage());
        }
    }

    private final ApplicationConfig config;

    public App(ApplicationConfig config) {
        this.config = config;
    }

    public void run(Parameters params, PrintWriter output) {
        var count = config.importEmployeesUseCase().exec(params.source());
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

    public record Parameters(
            String source,
            int lowEarningsThreshold,
            int highEarningsThreshold,
            int reportingLineThreshold
    ) {
        public static Parameters from(String[] args) {
            var source = args.length > 0 ? args[0] : null;
            return new Parameters(source, 20, 50, 4);
        }

        public boolean isValid() {
            return source != null;
        }
    }
}
