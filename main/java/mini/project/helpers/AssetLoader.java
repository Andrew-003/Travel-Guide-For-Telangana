package mini.project.helpers;

import android.content.Context;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class AssetLoader {

    public static String loadJSONFromAsset(Context context, String district, String fileName) {
        StringBuilder json = new StringBuilder();

        try {
            // Open the specified file in the district's folder
            InputStream is = context.getAssets().open(district + "/" + fileName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = reader.readLine()) != null) {
                json.append(line);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return json.toString(); // Return the JSON data from the specified file
    }
   public static String loadJSONFromAsset(Context context, String fileName) {
        StringBuilder json = new StringBuilder();

        try {
            // Open the specified file in the district's folder
            InputStream is = context.getAssets().open(fileName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = reader.readLine()) != null) {
                json.append(line);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return json.toString(); // Return the JSON data from the specified file
    }
    
}
/*
// Load data from touristplaces.json
String touristPlacesJson = AssetLoader.loadJSONFromAsset(context, "adilabad", "touristplaces.json");

// Load data from hotels.json
String hotelsJson = AssetLoader.loadJSONFromAsset(context, "adilabad", "hotels.json");

// Load data from restaurants.json
String restaurantsJson = AssetLoader.loadJSONFromAsset(context, "adilabad", "restaurants.json");
*/