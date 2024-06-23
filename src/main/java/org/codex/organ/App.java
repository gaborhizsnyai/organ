package org.codex.organ;

import org.codex.organ.common.ApplicationConfig;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class App {
    private static final Logger log = Logger.getLogger(App.class.getSimpleName());

    public static void main(String[] args) {
        var params = Parameters.from(args);

        if (!params.isValid()) {
            log.log(Level.SEVERE, "Invalid parameters. Expected: <source>");
            return;
        }

        var buffer = new StringWriter();
        try (var output = new PrintWriter(buffer);
             var source = new FileInputStream(params.source())
        ) {
            var config = new ApplicationConfig();

            new Analyzer(config).run(params, source, output);

            output.flush();
            log.log(Level.INFO, "\n{0}", buffer);
        } catch (Exception e) {
            log.log(Level.SEVERE, e.getMessage());
        }
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
