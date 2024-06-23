package org.codex.organ.infra.csv;

import org.codex.organ.common.WrappingException;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Spliterator;
import java.util.function.Consumer;

/**
 * A spliterator for CSV files. Each element is a map of column names to values.
 * The first row of the CSV file is assumed to be the header row.
 */
public class CSVSpliterator implements Spliterator<Map<String, String>> {
    private final BufferedReader input;
    private final CSVRowMapper mapper;
    private int line = 1;

    public CSVSpliterator(InputStream  inputStream) {
        try {
            this.input = new BufferedReader(new InputStreamReader(inputStream));
            this.mapper = new CSVRowMapper(input.readLine());
        } catch (Exception e) {
            throw new WrappingException(e);
        }
    }

    @Override
    public boolean tryAdvance(Consumer<? super Map<String, String>> action) {
        try {
            String row = input.readLine();
            if (row == null)
                return false;

            action.accept(mapper.map(row));
            line++;
        } catch (Exception e) {
            throw new IllegalArgumentException("Error reading line %d - %s".formatted(line, e.getMessage()));
        }
        return true;
    }

    @Override
    public Spliterator<Map<String, String>> trySplit() {
        return null;
    }

    @Override
    public long estimateSize() {
        return 0;
    }

    @Override
    public int characteristics() {
        return Spliterator.NONNULL | Spliterator.IMMUTABLE;
    }
}
