import java.io.*;
import java.nio.file.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;


//Zaeem edits
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


public class CalorieTracker {

    private static final String FILE_PATH_TEMPLATE = "tracking/calorie_intakes_%s.json";

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    // Call this method when a user creates an account
    public static void createUserFile(String username) throws IOException {
        Path directoryPath = Paths.get("tracking");
        if (!Files.exists(directoryPath)) {
            Files.createDirectories(directoryPath); // Create the directory if it doesn't exist
        }

        Path filePath = directoryPath.resolve(String.format("calorie_intakes_%s.txt", username));
        if (!Files.exists(filePath)) {
            Files.createFile(filePath);
        }
    }

    //Call this method to add or update calorie intake
    public static void addOrUpdateCalories(String username, int caloriesToAdd) throws IOException {
        Path path = Paths.get(String.format(FILE_PATH_TEMPLATE, username));
        JSONParser parser = new JSONParser();
        JSONArray calorieArray;

        if (Files.exists(path)) {
            //Parse the existing file into a JSON array
            try (Reader reader = Files.newBufferedReader(path)) {
                calorieArray = (JSONArray) parser.parse(reader);
            } catch (Exception e) {
                //If parsing fails, log an error and create a new array
                System.err.println("Error parsing the calorie file: " + e.getMessage());
                calorieArray = new JSONArray();
            }
        } else {
            //If the file doesn't exist, create a new JSON array
            calorieArray = new JSONArray();
        }

        LocalDate today = LocalDate.now();
        JSONObject todayEntry = new JSONObject();
        todayEntry.put("date", DATE_FORMATTER.format(today));
        todayEntry.put("calories", caloriesToAdd);

        //Add or update today's entry
        //Assuming that the last entry in the array is always for the latest date
        if (!calorieArray.isEmpty()) {
            JSONObject lastEntry = (JSONObject) calorieArray.get(calorieArray.size() - 1);
            String lastDate = (String) lastEntry.get("date");
            if (lastDate.equals(DATE_FORMATTER.format(today))) {
                //Update today's calories
                long currentCalories = (long) lastEntry.get("calories");
                lastEntry.put("calories", currentCalories + caloriesToAdd);
            } else {
                //Add a new entry for today
                calorieArray.add(todayEntry);
            }
        } else {
            //If the array is empty, add today's entry
            calorieArray.add(todayEntry);
        }

        //Write the JSON array back to the file
        try (Writer writer = Files.newBufferedWriter(path)) {
            writer.write(calorieArray.toJSONString());
        }
    }


    private static LocalDate getLastEntryDate(String username) throws IOException {
        Path path = Paths.get(String.format(FILE_PATH_TEMPLATE, username));
        JSONParser parser = new JSONParser();
        LocalDate lastEntryDate = null;

        if (Files.exists(path)) {
            //Parse the existing file into a JSON array
            try (Reader reader = Files.newBufferedReader(path)) {
                JSONArray calorieArray = (JSONArray) parser.parse(reader);
                if (!calorieArray.isEmpty()) {
                    //Assuming that the last entry in the array is for the latest date
                    JSONObject lastEntry = (JSONObject) calorieArray.get(calorieArray.size() - 1);
                    String lastDateStr = (String) lastEntry.get("date");
                    lastEntryDate = LocalDate.parse(lastDateStr, DATE_FORMATTER);
                }
            } catch (Exception e) {
                //If parsing fails, log an error
                System.err.println("Error parsing the calorie file: " + e.getMessage());
            }
        }

        return lastEntryDate;
    }


    public static int getLatestCalorieValues(String username) throws IOException {
        Path path = Paths.get(String.format(FILE_PATH_TEMPLATE, username));
        JSONParser parser = new JSONParser();
        int latestCalories = 0;

        if (Files.exists(path)) {
            //Parse the existing file into a JSON array
            try (Reader reader = Files.newBufferedReader(path)) {
                JSONArray calorieArray = (JSONArray) parser.parse(reader);
                if (!calorieArray.isEmpty()) {
                    //Assuming that the last entry in the array is for the latest calories
                    JSONObject lastEntry = (JSONObject) calorieArray.get(calorieArray.size() - 1);
                    latestCalories = ((Long) lastEntry.get("calories")).intValue();
                }
            } catch (Exception e) {
                //If parsing fails, log an error
                System.err.println("Error parsing the calorie file: " + e.getMessage());
            }
        }

        return latestCalories;
    }

}