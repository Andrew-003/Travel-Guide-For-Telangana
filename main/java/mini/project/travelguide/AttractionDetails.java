package mini.project.travelguide;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import mini.project.adapters.HotelAdapter;
import mini.project.adapters.RestaurantAdapter;
import mini.project.helpers.AssetLoader;
import mini.project.helpers.JsonParserHelper;
import mini.project.models.Attraction;
import mini.project.models.GAttraction;
import mini.project.models.GHotel;
import mini.project.models.GRestaurant;
import mini.project.models.Hotel;
import mini.project.models.Restaurant;



public class AttractionDetails extends AppCompatActivity {
    private static final String FILE_ATTRACTIONS = "attractions.json";
    private static final String FILE_HOTELS = "hotels.json";
    private static final String FILE_RESTAURANTS = "restaurants.json";
    private String DISTRICT_NAME = "";

    private ImageView attractionImageView;
    private TextView attractionName, attractionDescription, attractionDetails, attractionAddress;
    private TextView attractionActivities, attractionAmenities;
    private TextView attractionRating, attractionBestVisitTime, attractionPrices;
    private RecyclerView nearByHotelsRecyclerView, nearByRestaurantsRecyclerView;
    private HotelAdapter nearByHotelsAdapter;
    private RestaurantAdapter nearByRestaurantsAdapter;
    private TextView emptyNearByHotelsTextView, emptyNearByRestaurantsTextView;
    private Button viewImagesButton, viewOnMapButton;

    private Attraction attraction;
    private GAttraction gAttraction;
    private List<Hotel> HotelsList;
    private List<GHotel> gHotelsList;
    private List<Restaurant> RestaurantsList;
    private List<GRestaurant> gRestaurantsList;

    private String[] commonTouristActivities = {
            "Explore historical forts and monuments",
            "Visit famous temples and spiritual sites",
            "Try local Telangana cuisine",
            "Enjoy shopping at local markets (e.g., for pearls, handicrafts)",
            "Take heritage walks in old city areas",
            "Participate in boating or other water sports",
            "Capture scenic views and sunsets at iconic viewpoints"
    };

    private String bestMonthsToVisit[] = {
            "November to December",
            "October to November",
            "March to May",
            "July to September",
            "December to January"
    };

    private static final String RATING = "4.5";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_attraction_details);

        // Getting the district and attraction/gAttraction from the intent
        if (getIntent() != null) {
            DISTRICT_NAME = getIntent().getStringExtra("district");
            if (getIntent().getParcelableExtra("attraction") != null) {
                attraction = getIntent().getParcelableExtra("attraction");
            } else {
                gAttraction = getIntent().getParcelableExtra("gattraction");
            }
        } else {
            Toast.makeText(this, "Attraction Data Not Found", Toast.LENGTH_SHORT).show();
        }

        // Initialize views
        initializeViews();

        // Set data based on the type of attraction (Attraction or GAttraction)
        if (attraction != null) {
            setAttractionData(attraction);
        } else if (gAttraction != null) {
            setGAttractionData(gAttraction);
        }

        // Handle Images Button Click
        viewImagesButton.setOnClickListener(view -> {
           Intent intent = new Intent(AttractionDetails.this, ImageActivity.class);
            List<String> imageUrls = attraction != null ? attraction.getPhotos() : gAttraction.getImageUrls();
            if(gAttraction!=null){
                List<GAttraction.Image> imageurls=gAttraction.getImages();
                    for(GAttraction.Image url:imageurls){
                        imageUrls.add(url.getImageUrl());
                    }
            }
            if (imageUrls != null) {
                intent.putStringArrayListExtra("imageurls", new ArrayList<>(imageUrls));
            } else {
                String url = attraction != null ? attraction.getWebUrl() : gAttraction.getUrl();
if (url != null && !url.isEmpty()) {
    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
    this.startActivity(browserIntent); // Replace 'context' with the appropriate Context instance.
} else {
    Toast.makeText(this,"No Images Found",Toast.LENGTH_SHORT).show();
}
            }

            startActivity(intent);
        });

        // Handle Map Button Click
       viewOnMapButton.setOnClickListener(view -> {
//            // Logic to show attraction on map
//            Intent intent=new Intent(this,ImageActivity.class);
//            intent.putExtra("webUrl",attraction.getLatitude() + ","+attraction.getLongitude());
//            startActivity(intent);
                if(attraction!=null){
                   openMap(attraction.getLatitude() + ","+attraction.getLongitude());
                }else{
                    openMap(String.valueOf(gAttraction.getLocation().getLatitude())+","+String.valueOf(gAttraction.getLocation().getLongitude()));
                }
            
        });

        
        loadNearbyHotelsAndRestaurants();
    }
    private void openMap(String geolocation) {
        // Construct the URI for Google Maps
        String uri = "http://maps.google.com/maps?q=" + geolocation; // Use q parameter for a marker
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
        // Check if there is an app that can handle this intent

    }

        
    private void initializeViews() {
        attractionImageView = findViewById(R.id.attraction_detail_image);
        attractionName = findViewById(R.id.attraction_detail_name);
        attractionDescription = findViewById(R.id.attraction_detail_desc);
        attractionRating = findViewById(R.id.attraction_detail_rating);
        attractionBestVisitTime = findViewById(R.id.attraction_detail_best_time_visit);
        attractionPrices = findViewById(R.id.attraction_detail_price);
        attractionAddress = findViewById(R.id.attraction_detail_address);
        attractionActivities = findViewById(R.id.attraction_detail_activities);
        viewImagesButton = findViewById(R.id.attraction_detail_show_images);
        viewOnMapButton = findViewById(R.id.attraction_detail_show_map);
        nearByHotelsRecyclerView = findViewById(R.id.attraction_detail_nearby_hotels_recyclerview);
        nearByRestaurantsRecyclerView = findViewById(R.id.attraction_detail_nearby_restaurants_recyclerview);
        emptyNearByHotelsTextView = findViewById(R.id.attraction_detail_nearby_hotels_empty_tv);
        emptyNearByRestaurantsTextView = findViewById(R.id.attraction_detail_nearby_restaurants_empty_tv);
    }

    private void setAttractionData(Attraction attraction) {
        Glide.with(attractionImageView.getContext())
                .load(attraction.getImage())
                .placeholder(R.drawable.tempty)
                .error(R.drawable.tempty)
                .into(attractionImageView);
        attractionName.setText(attraction.getName());

        String desc = attraction.getDescription() != null ? attraction.getDescription() :
                attraction.getName() + " is part of " + DISTRICT_NAME + ". It enhances its beauty and presents Traditional, Historical, and Culture of Telangana.";
        attractionDescription.setText(desc);

        attractionRating.setText("Rating: "+attraction.getRating());

        attractionBestVisitTime.setText("Best Time to Visit: "+bestMonthsToVisit[new Random().nextInt(5)]);

        String price = "Average Price: " + (new Random().nextInt(6420) + 2340) + "\n" +
                "Price Range: " + (new Random().nextInt(2340) + 5400) + " - " + (new Random().nextInt(2340) + 5400) + "\n" +
                "Average Activities Price: " + (new Random().nextInt(3780) + 1100) + "\n";
        attractionPrices.setText(price);

        attractionAddress.setText(attraction.getAddress());
        attractionActivities.setText(getRandomConcatenatedString(commonTouristActivities));
        
    }

    private void setGAttractionData(GAttraction gAttraction) {
        Glide.with(attractionImageView.getContext())
                .load(gAttraction.getImageUrl())
                .placeholder(R.drawable.tempty)
                .error(R.drawable.tempty)
                .into(attractionImageView);
        attractionName.setText(gAttraction.getTitle());

        String desc = gAttraction.getDescription() != null ? gAttraction.getDescription() :
                gAttraction.getTitle() + " is part of " + DISTRICT_NAME + ". It enhances its beauty and presents Traditional, Historical, and Culture of Telangana.";
        attractionDescription.setText(desc);

        attractionRating.setText("Rating: " + (gAttraction.getTotalScore() == 0 ? RATING : gAttraction.getTotalScore()));

        attractionBestVisitTime.setText(bestMonthsToVisit[new Random().nextInt(5)]);

        String price = "Average Price: " + (new Random().nextInt(6420) + 2340) + "\n" +
                "Price Range: " + (new Random().nextInt(2340) + 5400) + " - " + (new Random().nextInt(2340) + 5400) + "\n" +
                "Average Activities Price: " + (new Random().nextInt(3780) + 1100) + "\n";
        attractionPrices.setText(price);

        attractionAddress.setText(gAttraction.getAddress());
        attractionActivities.setText(getRandomConcatenatedString(commonTouristActivities));
       
    }

    private void loadNearbyHotelsAndRestaurants() {
        // Load nearby Hotels (same logic for both types of attractions)
        loadNearbyHotels();

        // Load nearby Restaurants (same logic for both types of attractions)
        loadNearbyRestaurants();
    }

    private void loadNearbyHotels() {
        String fileName = getFileName(FILE_HOTELS);
        if (!fileName.isEmpty()) {
            if (fileName.toLowerCase().startsWith("g")) {
                gHotelsList = JsonParserHelper.parseDistrictLargeJson(this, DISTRICT_NAME, fileName, GHotel.class);
                if (gHotelsList != null && !gHotelsList.isEmpty()) {
                    int subListSize = Math.min(3, gHotelsList.size());
                    nearByHotelsAdapter = new HotelAdapter(this, gHotelsList.subList(0, subListSize), true);
                    nearByHotelsRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
                    nearByHotelsRecyclerView.setAdapter(nearByHotelsAdapter);
                } else {
                    emptyNearByHotelsTextView.setVisibility(View.VISIBLE);
                    Toast.makeText(this, "No Hotels Available", Toast.LENGTH_SHORT).show();
                }
            } else {
                HotelsList = JsonParserHelper.parseDistrictLargeJson(this, DISTRICT_NAME, fileName, Hotel.class);
                if (HotelsList != null && !HotelsList.isEmpty()) {
                    int subListSize = Math.min(2, HotelsList.size());
                    nearByHotelsAdapter = new HotelAdapter(this, HotelsList.subList(0, subListSize));
                    nearByHotelsRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
                    nearByHotelsRecyclerView.setAdapter(nearByHotelsAdapter);
                } else {
                    emptyNearByHotelsTextView.setVisibility(View.VISIBLE);
                    Toast.makeText(this, "No Hotels Available", Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            Toast.makeText(this, "File not Found", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadNearbyRestaurants() {
        String fileName = getFileName(FILE_RESTAURANTS);
        if (!fileName.isEmpty()) {
            if (fileName.toLowerCase().startsWith("g")) {
                gRestaurantsList = JsonParserHelper.parseDistrictLargeJson(this, DISTRICT_NAME, fileName, GRestaurant.class);
                if (gRestaurantsList != null && !gRestaurantsList.isEmpty()) {
                    int subListSize = Math.min(3, gRestaurantsList.size());
                    nearByRestaurantsAdapter = new RestaurantAdapter(this, gRestaurantsList.subList(0, subListSize), true);
                    nearByRestaurantsRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
                    nearByRestaurantsRecyclerView.setAdapter(nearByRestaurantsAdapter);
                } else {
                    emptyNearByRestaurantsTextView.setVisibility(View.VISIBLE);
                    Toast.makeText(this, "No Restaurants Available", Toast.LENGTH_SHORT).show();
                }
            } else {
                RestaurantsList = JsonParserHelper.parseDistrictLargeJson(this, DISTRICT_NAME, fileName, Restaurant.class);
                if (RestaurantsList != null && !RestaurantsList.isEmpty()) {
                    int subListSize = Math.min(3, RestaurantsList.size());
                    nearByRestaurantsAdapter = new RestaurantAdapter(this, RestaurantsList.subList(0, subListSize));
                    nearByRestaurantsRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
                    nearByRestaurantsRecyclerView.setAdapter(nearByRestaurantsAdapter);
                } else {
                    emptyNearByRestaurantsTextView.setVisibility(View.VISIBLE);
                    Toast.makeText(this, "No Restaurants Available", Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            Toast.makeText(this, "File not Found", Toast.LENGTH_SHORT).show();
        }
    }

    private String getFileName(String plainFileName) {
        if (plainFileName == null || plainFileName.isEmpty()) {
            Toast.makeText(this, "File name is empty", Toast.LENGTH_SHORT).show();
            return " ";
        }

        String fileName = plainFileName.toLowerCase();
        String fileContent = AssetLoader.loadJSONFromAsset(this, DISTRICT_NAME, fileName);

        if (fileContent == null || fileContent.isEmpty()) {
            // Try the alternate "g" + fileName
            String alternateFileName = "g" + fileName;
            fileContent = AssetLoader.loadJSONFromAsset(this, DISTRICT_NAME, alternateFileName);
            if (fileContent != null && !fileContent.isEmpty()) {
                return alternateFileName; // Return the found alternate file name
            }
        } else {
            return fileName; // Return the original file name
        }

        return " ";
    }

    public static String getRandomConcatenatedString(String[] inputArray) {
        // Convert the array to a list for shuffling
        List<String> list = new ArrayList<>();
        Collections.addAll(list, inputArray);

        // Shuffle the list to randomize the order
        Collections.shuffle(list);
        int selectionCount = 5;
        List<String> selectedStrings = list.subList(0, selectionCount);

        // Concatenate each string with a newline
        StringBuilder result = new StringBuilder();
        for (String str : list) {
            if (result.length() > 0) {
                result.append("\n"); // Add a newline after the previous string
            }
            result.append(str);
        }

        return result.toString();
    }
}