package net.gegy1000.slyther.game;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

public final class ProfanityHandler {
    private ProfanityHandler() {}

    private static final Map<String, List<String>> FILTERS = new HashMap<>();

    static {
        try {
            URL url = new URL("http://slither.io/filters.txt");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent", "Slyther");
            Scanner scanner = new Scanner(connection.getInputStream());
            String currentCategory = "";
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.endsWith(":")) {
                    currentCategory = line.split(":")[0];
                } else if (line.trim().length() > 0) {
                    List<String> filters = FILTERS.get(currentCategory);
                    if (filters == null) {
                        filters = new ArrayList<>();
                    }
                    filters.add(line);
                    FILTERS.put(currentCategory, filters);
                }
            }
            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isClean(String text) {
        StringBuilder correctlySpaced = new StringBuilder();
        StringBuilder withoutSpacing = new StringBuilder();
        StringBuilder withSpacing = new StringBuilder();
        int numberLength = 0;
        boolean isNumber;
        boolean requiresCharacter = false;
        for (int i = 0; i < text.length(); i++) {
            char character = text.charAt(i);
            if (character == ' ') {
                if (!requiresCharacter) {
                    requiresCharacter = true;
                    correctlySpaced.append(' ');
                }
            } else {
                requiresCharacter = false;
                correctlySpaced.append(character);
            }
        }
        requiresCharacter = false;
        for (int i = 0; i < text.length(); i++) {
            char character = text.charAt(i);
            if ((isNumber = character >= '0' && character <= '9') || (character >= 'A' && character <= 'Z' || character >= 'a' && character <= 'z')) {
                withoutSpacing.append(character);
                withSpacing.append(character);
                requiresCharacter = false;
                if (isNumber) {
                    if (numberLength++ >= 7) {
                        return false;
                    }
                } else {
                    numberLength = 0;
                }
            } else if (!requiresCharacter) {
                requiresCharacter = true;
                withSpacing.append(' ');
            }
        }
        text = correctlySpaced.toString().toLowerCase();
        if (FILTERS.containsKey("flt_a")) {
            for (String filter : FILTERS.get("flt_a")) {
                if (text.contains(filter)) {
                    return false;
                }
            }
        }
        text = withoutSpacing.toString().toLowerCase();
        if (FILTERS.containsKey("flt_g")) {
            for (String filter : FILTERS.get("flt_g")) {
                if (text.contains(filter)) {
                    return false;
                }
            }
        }
        if (FILTERS.containsKey("flt_w")) {
            for (String word : withSpacing.toString().toLowerCase().split(" ")) {
                for (String filter : FILTERS.get("flt_w")) {
                    if (word.equals(filter)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
