package com.passengers.friend.data.reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class SqlReader {

    public static String getStatement(final InputStream stream) {

        StringBuilder recipient = new StringBuilder();
        String newLine = "\n";

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                recipient.append(line).append(newLine);
            }
            return recipient.toString();
        } catch (IOException e) {
            return null;
        }
    }
}
