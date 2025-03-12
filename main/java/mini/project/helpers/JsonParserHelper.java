package mini.project.helpers;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.InputStreamReader;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.List;

public class JsonParserHelper {

    private static final String TAG = "JsonParserHelper";
    private static final Gson gson = new Gson();



    public static <T> List<T> parseJson(Context context, String fileName, String district, Class<T> modelClass) {
        List<T> result = null;
        try {
            String jsonString = null;

            // Construct potential file names
            String gFileName = "g" + fileName;
            String topFileName = "top_" + fileName;
            String plainFileName = fileName;

            // Check if district is provided and load from the appropriate folder
            if (district != null && !district.isEmpty()) {
                // Try gFileName first
                jsonString = AssetLoader.loadJSONFromAsset(context, district, gFileName);

                // If gFileName not found, try topFileName
                if (jsonString == null || jsonString.isEmpty()) {
                    jsonString = AssetLoader.loadJSONFromAsset(context, district, topFileName);
                }

                // If topFileName not found, try plainFileName
                if (jsonString == null || jsonString.isEmpty()) {
                    jsonString = AssetLoader.loadJSONFromAsset(context, district, plainFileName);
                }
            } else {
                // Try gFileName first
                jsonString = AssetLoader.loadJSONFromAsset(context, gFileName);

                // If gFileName not found, try topFileName
                if (jsonString == null || jsonString.isEmpty()) {
                    jsonString = AssetLoader.loadJSONFromAsset(context, topFileName);
                }

                // If topFileName not found, try plainFileName
                if (jsonString == null || jsonString.isEmpty()) {
                    jsonString = AssetLoader.loadJSONFromAsset(context, plainFileName);
                }
            }

            // Parse JSON if a valid file was found
            if (jsonString != null && !jsonString.isEmpty()) {
                // Get a parameterized Type for List<T>
                Type listType = TypeToken.getParameterized(List.class, modelClass).getType();
                result = gson.fromJson(jsonString, listType);
            } else {
                Log.w(TAG, "No valid JSON file found: " + gFileName + ", " + topFileName + ", or " + plainFileName);
            }
        } catch (Exception e) {
            Log.e(TAG, "Error parsing JSON file: " + fileName, e);
        }
        return result;
    }


    public static <T> List<T> parseLargeJson(Context context, String fileName, Class<T> modelClass) {
        List<T> result = null;
        try {
            // Open the file as a stream from assets
            InputStream inputStream = context.getAssets().open(fileName);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            JsonReader reader = new JsonReader(inputStreamReader);

            // Use Gson to parse it
            Gson gson = new Gson();

            // Get a parameterized Type for List<T>
            Type listType = TypeToken.getParameterized(List.class, modelClass).getType();
            result = gson.fromJson(reader, listType);

            // Close the reader after use
            reader.close();
        } catch (Exception e) {
            Log.e(TAG, "Error parsing large JSON file: " + fileName, e);
        }
        return result;
    }
    public static <T> List<T> parseDistrictLargeJson(Context context, String districtName, String fileName, Class<T> modelClass) {
        List<T> result = null;
        try {
            // Build the file path using districtName and fileName
            String filePath = districtName + "/" + fileName;

            // Open the file as a stream from assets
            InputStream inputStream = context.getAssets().open(filePath);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            JsonReader reader = new JsonReader(inputStreamReader);

            // Use Gson to parse it
            Gson gson = new Gson();

            // Get a parameterized Type for List<T>
            Type listType = TypeToken.getParameterized(List.class, modelClass).getType();
            result = gson.fromJson(reader, listType);

            // Close the reader after use
            reader.close();
        } catch (Exception e) {
            Log.e(TAG, "Error parsing large JSON file: " + fileName, e);
        }
        return result;
    }

}