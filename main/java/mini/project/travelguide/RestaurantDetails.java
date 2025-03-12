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

import mini.project.adapters.AttractionAdapter;
import mini.project.adapters.HotelAdapter;
import mini.project.helpers.AssetLoader;
import mini.project.helpers.JsonParserHelper;
import mini.project.models.Attraction;
import mini.project.models.GAttraction;
import mini.project.models.GHotel;
import mini.project.models.GRestaurant;
import mini.project.models.Hotel;
import mini.project.models.Restaurant;

public class RestaurantDetails extends AppCompatActivity {
    private static final String FILE_HOTELS = "hotels.json";
    private static final String FILE_ATTRACTIONS = "attractions.json";
    private static final String FILE_RESTAURANTS = "restaurants.json";
    private String DISTRICT_NAME = "";

    private ImageView restaurantImageView;
    private TextView restaurantName, restaurantDescription, restaurantAddress;
    private TextView restaurantRating, restaurantPrices,restaurantPhnNum;
    private RecyclerView nearbyAttractionsRecyclerView, nearbyHotelsRecyclerView;
    private AttractionAdapter nearbyAttractionsAdapter;
    private HotelAdapter nearbyHotelsAdapter;
    private TextView emptyNearbyAttractionsTextView, emptyNearbyHotelsTextView;
    private Button viewImagesButton, viewOnMapButton;

    private Restaurant restaurant;
    private GRestaurant gRestaurant;
    private List<Attraction> attractionsList;
    private List<Hotel> hotelsList;

    private String[] commonDiningActivities = {
            "Try signature dishes",
            "Enjoy outdoor seating with scenic views",
            "Savor local Telangana flavors",
            "Explore fusion cuisines",
            "Relax with family dining options"
    };

    private static final String RATING = "4.5";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_restaurant_details);

        // Get the district and restaurant/gRestaurant data from the intent
        if (getIntent() != null) {
            DISTRICT_NAME = getIntent().getStringExtra("district");
            if (getIntent().getParcelableExtra("restaurant") != null) {
                restaurant = getIntent().getParcelableExtra("restaurant");
            } else {
                gRestaurant = getIntent().getParcelableExtra("grestaurant");
            }
        } else {
            Toast.makeText(this, "Restaurant Data Not Found", Toast.LENGTH_SHORT).show();
        }

        // Initialize views
        initializeViews();

        // Set data based on the type of restaurant (Restaurant or GRestaurant)
        if (restaurant != null) {
            setRestaurantData(restaurant);
        } else {
            setGRestaurantData(gRestaurant);
        }

        // Handle Images Button Click
        viewImagesButton.setOnClickListener(view -> {
                List<String> imageUrls=new ArrayList<>();
            Intent intent = new Intent(RestaurantDetails.this, ImageActivity.class);
             if(restaurant != null){
                  imageUrls=restaurant.getPhotos();

             }
                 
            if (imageUrls != null && !imageUrls.isEmpty()) {
                intent.putStringArrayListExtra("imageurls", new ArrayList<>(imageUrls));
            } else {
                String url = restaurant!= null ? restaurant.getWebUrl() : gRestaurant.getSearchPageUrl();
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
             if(restaurant!=null){
                    openMap(restaurant.getLatitude() + ","+restaurant.getLongitude());
                }
                else{
                    openMap(gRestaurant.getLocation().getLatitude() + ","+gRestaurant.getLocation().getLongitude());
                }
        });

        
        // Load nearby attractions and hotels
        loadNearbyAttractionsAndHotels();
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
        restaurantImageView = findViewById(R.id.rest_detail_main_image);
        restaurantName = findViewById(R.id.rest_detail_location_title);
        restaurantDescription = findViewById(R.id.rest_detail_description);
        restaurantRating = findViewById(R.id.rest_detail_average_rating);
        restaurantPrices = findViewById(R.id.rest_detail_price);
        restaurantAddress = findViewById(R.id.rest_detail_address);
        restaurantPhnNum=findViewById(R.id.rest_detail_phone_number);
        viewImagesButton = findViewById(R.id.rest_detail_show_images);
        viewOnMapButton = findViewById(R.id.rest_detail_show_map);
        nearbyAttractionsRecyclerView = findViewById(R.id.rest_detail_nearby_attractions_recyclerview);
        nearbyHotelsRecyclerView = findViewById(R.id.rest_detail_nearby_hotels_rescyclerview);
        emptyNearbyAttractionsTextView = findViewById(R.id.rest_detail_nearby_attractions_empty_tv);
        emptyNearbyHotelsTextView = findViewById(R.id.rest_detail_nearby_hotels_empty_tv);
    }

    private void setRestaurantData(Restaurant restaurant) {
        Glide.with(restaurantImageView.getContext())
                .load(restaurant.getImage())
                .placeholder(R.drawable.tempty)
                .error(R.drawable.tempty)
                .into(restaurantImageView);
        restaurantName.setText(restaurant.getName());

        String desc = restaurant.getDescription() != null ? restaurant.getDescription() :
                restaurant.getName() + " is part of " + DISTRICT_NAME + ". Enjoy delicious meals and excellent service.";
        restaurantDescription.setText(desc);

        restaurantRating.setText("Rating: " + restaurant.getRating());

        String price = "Average Meal Price: " + (new Random().nextInt(800) + 200) + " Rs\n";
        restaurantPrices.setText(price);
        if(restaurant.getPhone()!=null){
            restaurantPhnNum.setText("Phone Number: "+restaurant.getPhone());
        }

        restaurantAddress.setText(restaurant.getAddress());
    }

    private void setGRestaurantData(GRestaurant gRestaurant) {
        String url="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTbKFGOhLChr21ytyGoHYz31Wopj49fM-iX0A&usqp=CAU";
        if(gRestaurant!=null){
            url=gRestaurant.getImageUrl();
        }

        
        Glide.with(restaurantImageView.getContext())
                .load(url)
                .placeholder(R.drawable.tempty)
                .error(R.drawable.tempty)
                .into(restaurantImageView);
        String name=gRestaurant.getTitle();
        if(name.equals("null")){
            name=gRestaurant.getSubTitle();
        }
        restaurantName.setText(name);

        String desc = gRestaurant.getDescription() != null ? gRestaurant.getDescription() :
                gRestaurant.getSubTitle() + " is part of " + DISTRICT_NAME + ". Enjoy delicious meals and excellent service.";
        restaurantDescription.setText(desc);

        restaurantRating.setText("Rating: " + (gRestaurant.getTotalScore() == 0 ? RATING : gRestaurant.getTotalScore()));

        String price = "Average Meal Price: " + (new Random().nextInt(800) + 200) + " Rs\n";
        restaurantPrices.setText(price);
        if(gRestaurant.getPhone()!=null){
            restaurantPhnNum.setText("Phone Number: "+gRestaurant.getPhone());
        }
        restaurantAddress.setText(gRestaurant.getAddress());
    }

    private void loadNearbyAttractionsAndHotels() {
        loadNearbyAttractions();
        loadNearbyHotels();
    }

    private void loadNearbyAttractions() {
        String fileName = getFileName(FILE_ATTRACTIONS);
        if (!fileName.isEmpty()) {
            if (fileName.toLowerCase().startsWith("g")) {
                List<GAttraction> gAttractionsList = JsonParserHelper.parseDistrictLargeJson(this, DISTRICT_NAME, fileName, GAttraction.class);
                if (gAttractionsList != null && !gAttractionsList.isEmpty()) {
                    int subListSize = Math.min(3, gAttractionsList.size());
                    nearbyAttractionsAdapter = new AttractionAdapter(this, gAttractionsList.subList(0, subListSize), true);
                    nearbyAttractionsRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
                    nearbyAttractionsRecyclerView.setAdapter(nearbyAttractionsAdapter);
                } else {
                    emptyNearbyAttractionsTextView.setVisibility(View.VISIBLE);
                    Toast.makeText(this, "No Attractions Available", Toast.LENGTH_SHORT).show();
                }
            } else {
                List<Attraction> attractionsList = JsonParserHelper.parseDistrictLargeJson(this, DISTRICT_NAME, fileName, Attraction.class);
                if (attractionsList != null && !attractionsList.isEmpty()) {
                    int subListSize = Math.min(3, attractionsList.size());
                    nearbyAttractionsAdapter = new AttractionAdapter(this, attractionsList.subList(0, subListSize));
                    nearbyAttractionsRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
                    nearbyAttractionsRecyclerView.setAdapter(nearbyAttractionsAdapter);
                } else {
                    emptyNearbyAttractionsTextView.setVisibility(View.VISIBLE);
                    Toast.makeText(this, "No Attractions Available", Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            Toast.makeText(this, "File not Found", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadNearbyHotels() {
        String fileName = getFileName(FILE_HOTELS);
        if (!fileName.isEmpty()) {
            if (fileName.toLowerCase().startsWith("g")) {
                List<GHotel> gHotelsList = JsonParserHelper.parseDistrictLargeJson(this, DISTRICT_NAME, fileName, GHotel.class);
                if (gHotelsList != null && !gHotelsList.isEmpty()) {
                    int subListSize = Math.min(3, gHotelsList.size());
                    nearbyHotelsAdapter = new HotelAdapter(this, gHotelsList.subList(0, subListSize), true);
                    nearbyHotelsRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
                    nearbyHotelsRecyclerView.setAdapter(nearbyHotelsAdapter);
                } else {
                    emptyNearbyHotelsTextView.setVisibility(View.VISIBLE);
                    Toast.makeText(this, "No Hotels Available", Toast.LENGTH_SHORT).show();
                }
            } else {
                List<Hotel> hotelsList = JsonParserHelper.parseDistrictLargeJson(this, DISTRICT_NAME, fileName, Hotel.class);
                if (hotelsList != null && !hotelsList.isEmpty()) {
                    int subListSize = Math.min(3, hotelsList.size());
                    nearbyHotelsAdapter = new HotelAdapter(this, hotelsList.subList(0, subListSize));
                    nearbyHotelsRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
                    nearbyHotelsRecyclerView.setAdapter(nearbyHotelsAdapter);
                } else {
                    emptyNearbyHotelsTextView.setVisibility(View.VISIBLE);
                    Toast.makeText(this, "No Hotels Available", Toast.LENGTH_SHORT).show();
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
            String alternateFileName = "g" + fileName;
            fileContent = AssetLoader.loadJSONFromAsset(this, DISTRICT_NAME, alternateFileName);
            if (fileContent != null && !fileContent.isEmpty()) {
                return alternateFileName;
            }
        } else {
            return fileName;
        }

        return " ";
    }
}