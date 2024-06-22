package org.codex.organ;

import org.codex.organ.common.ApplicationConfig;

public class App {

    public static void main(String[] args) {
        var config = new ApplicationConfig();

        config.importEmployeesUseCase().exec(args[0]);

        try (var writer = System.console().writer()) {

            config.reportManagersWithLowEarningsUseCase()
                    .exec(20)
                    .print(writer);

            config.reportManagersWithHighEarningsUseCase()
                    .exec(50)
                    .print(writer);

            config.reportTooLongReportingLineUseCase()
                    .exec(4)
                    .print(writer);

        }
    }
}
