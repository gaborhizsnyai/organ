package org.codex.organ.app.dto;

import org.codex.organ.domain.model.Employee;

import java.io.PrintWriter;
import java.util.List;

/**
 * A basic report that can be printed to a {@link PrintWriter}.
 */
public record Report(String title, List<Item> items) {

    public void print(PrintWriter writer) {
        writer.println(title);
        if (items.isEmpty()) {
            writer.println("No results found");
        } else {
            items.forEach(writer::println);
        }
        writer.println();
    }

    public record Item(Employee employee, String message) {
        public String toString() {
            return employee + " " + message;
        }
    }

}
