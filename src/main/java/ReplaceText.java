import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class ReplaceText {

    public static void main(String[] args) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            // Prompt user for original text
            System.out.println("Please enter the original text (press Enter to finish):");
            String text = readMultiLineInput(reader);

            // Prompt user for JSON replacement rules
            System.out.println("Please enter the JSON replacement rules (type END and press Enter to finish):");
            String jsonStr = readMultiLineInputWithEndMarker(reader, "END");

            JsonObject config = parseJson(jsonStr);
            if (config == null) {
                System.out.println("❌ Invalid JSON format. Please run again with valid JSON.");
                return;
            }

            Map<String, List<String>> nounsToReplace = parseJsonMap(config.get("nouns"));
            Map<String, List<String>> verbsToReplace = parseJsonMap(config.get("verbs"));

            printOriginalInfo(text, nounsToReplace, verbsToReplace);
            String replacedText = replaceWordsInText(text, nounsToReplace, verbsToReplace);
            System.out.println("Replaced Text:");
            System.out.println(replacedText);

        } catch (IOException e) {
            System.err.println("❌ Failed to read input: " + e.getMessage());
        }
    }

    private static String readMultiLineInput(BufferedReader reader) throws IOException {
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null && !line.trim().isEmpty()) {
            sb.append(line).append("\n");
        }
        return sb.toString().trim();
    }

    private static String readMultiLineInputWithEndMarker(BufferedReader reader, String endMarker) throws IOException {
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.trim().equals(endMarker)) {
                break;
            }
            sb.append(line).append("\n");
        }
        return sb.toString().trim();
    }

    private static JsonObject parseJson(String jsonStr) {
        try {
            return new Gson().fromJson(jsonStr, JsonObject.class);
        } catch (JsonSyntaxException e) {
            System.err.println("❌ JSON parsing error: " + e.getMessage());
            return null;
        }
    }

    private static Map<String, List<String>> parseJsonMap(JsonElement element) {
        Map<String, List<String>> map = new HashMap<>();
        if (element != null && element.isJsonObject()) {
            for (Map.Entry<String, JsonElement> entry : ((JsonObject) element).entrySet()) {
                map.put(entry.getKey(), Arrays.asList(entry.getValue().getAsJsonArray().toString().replaceAll("[\\[\\]]", "").split(", ")));
            }
        }
        return map;
    }

    private static String replaceWordsInText(String text, Map<String, List<String>> nouns, Map<String, List<String>> verbs) {
        List<String> words = Arrays.asList(text.split(" "));
        StringBuilder result = new StringBuilder();

        for (String word : words) {
            String baseWord = word.replaceAll("\\p{Punct}", "");
            String replacement = getRandomReplacement(baseWord, nouns, verbs);
            if (replacement == null) {
                result.append(word).append(" ");
            } else {
                result.append(replacement).append(word.substring(baseWord.length())).append(" ");
            }
        }

        return result.toString().trim();
    }

    private static String getRandomReplacement(String word, Map<String, List<String>> nouns, Map<String, List<String>> verbs) {
        List<String> replacements = new ArrayList<>();

        if (nouns.containsKey(word)) {
            replacements.addAll(nouns.get(word));
        }
        if (verbs.containsKey(word)) {
            replacements.addAll(verbs.get(word));
        }

        if (!replacements.isEmpty()) {
            return replacements.get(ThreadLocalRandom.current().nextInt(replacements.size()));
        }
        return null;
    }

    private static void printOriginalInfo(String text, Map<String, List<String>> nouns, Map<String, List<String>> verbs) {
        System.out.println("Original text: " + text);
        System.out.println("    Nouns to replace: " + nouns);
        System.out.println("    Verbs to replace: " + verbs);
        System.out.println();
    }
}
