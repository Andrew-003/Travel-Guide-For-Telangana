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
import mini.project.adapters.RestaurantAdapter;
import mini.project.helpers.AssetLoader;
import mini.project.helpers.JsonParserHelper;
import mini.project.models.Attraction;
import mini.project.models.GAttraction;
import mini.project.models.GHotel;
import mini.project.models.GRestaurant;
import mini.project.models.Hotel;
import mini.project.models.Restaurant;

public class HotelDetails extends AppCompatActivity {
    private static final String FILE_HOTELS = "hotels.json";
    private static final String FILE_ATTRACTIONS = "attractions.json";
    private static final String FILE_RESTAURANTS = "restaurants.json";
    private String DISTRICT_NAME = "";

    private ImageView hotelImageView;
    private TextView hotelName, hotelDescription, hotelAddress, hotelAmenities,hotelPhone;
    private TextView hotelRating, hotelPrices;
    private RecyclerView nearbyAttractionsRecyclerView, nearbyRestaurantsRecyclerView;
    private AttractionAdapter nearbyAttractionsAdapter;
    private RestaurantAdapter nearbyRestaurantsAdapter;
    private TextView emptyNearbyAttractionsTextView, emptyNearbyRestaurantsTextView;
    private Button viewImagesButton, viewOnMapButton;

    private Hotel hotel;
    private GHotel gHotel;
    private List<Attraction> attractionsList;
    private List<Restaurant> restaurantsList;

    

    private static final String RATING = "4.5";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_hotel_details);

        // Get the district and hotel/gHotel data from the intent
        if (getIntent() != null) {
            DISTRICT_NAME = getIntent().getStringExtra("district");
            if (getIntent().getParcelableExtra("hotel") != null) {
                hotel = getIntent().getParcelableExtra("hotel");
            } else {
                gHotel = getIntent().getParcelableExtra("ghotel");
            }
        } else {
            Toast.makeText(this, "Hotel Data Not Found", Toast.LENGTH_SHORT).show();
        }

        // Initialize views
        initializeViews();

        // Set data based on the type of hotel (Hotel or GHotel)
        if (hotel != null) {
            setHotelData(hotel);
        } else if (gHotel != null) {
            setGHotelData(gHotel);
        }

        // Handle Images Button Click
        viewImagesButton.setOnClickListener(view -> {
            Intent intent = new Intent(HotelDetails.this, ImageActivity.class);
            List<String> imageUrls = hotel != null ? hotel.getPhotos() : gHotel.getImageUrls();
                if(gHotel!=null){
                    List<GHotel.Image> imageurls=gHotel.getImages();
                    for(GHotel.Image url:imageurls){
                        imageUrls.add(url.getImageUrl());
                    }
                }

            if (imageUrls != null && !imageUrls.isEmpty()) {
                intent.putStringArrayListExtra("imageurls", new ArrayList<>(imageUrls));
            } else {
                String url = hotel != null ? hotel.getWebUrl() : gHotel.getUrl();
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
                if(hotel!=null){
                    openMap(hotel.getLatitude() + ","+hotel.getLongitude());
                }
                else{
                    openMap(gHotel.getLocation().getLat() + ","+gHotel.getLocation().getLng());
                }
            
        });

        // Load nearby attractions and restaurants
        loadNearbyAttractionsAndRestaurants();
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
        hotelImageView = findViewById(R.id.hotel_detail_image);
        hotelName = findViewById(R.id.hotel_detail_name);
        hotelDescription = findViewById(R.id.hotel_detail_desc);
        hotelRating = findViewById(R.id.hotel_detail_rating);
        hotelPrices = findViewById(R.id.hotel_detail_price);
        hotelPhone=findViewById(R.id.hotel_detail_ph_num);
        hotelAddress = findViewById(R.id.hotel_detail_address);
        hotelAmenities = findViewById(R.id.hotel_detail_amenities);
        viewImagesButton = findViewById(R.id.hotel_detail_show_imagesHotel);
        viewOnMapButton = findViewById(R.id.hotel_detail_show_Map);
        nearbyAttractionsRecyclerView = findViewById(R.id.hotel_detail_nearby_attarctions_recyclerview);
        nearbyRestaurantsRecyclerView = findViewById(R.id.hotel_detail__nearby_restaurants_recyclerview);
        emptyNearbyAttractionsTextView = findViewById(R.id.hotel_detail_nearby_attractions_empty_tv);
        emptyNearbyRestaurantsTextView = findViewById(R.id.hotel_detail_nearby_restaurants_empty_tv);
    }

    private void setHotelData(Hotel hotel) {
        Glide.with(hotelImageView.getContext())
                .load(hotel.getImage())
                .placeholder(R.drawable.tempty)
                .error(R.drawable.tempty)
                .into(hotelImageView);
        hotelName.setText(hotel.getName());

        String desc = hotel.getDescription() != null ? hotel.getDescription() :
                hotel.getName() + " is part of " + DISTRICT_NAME + ". It offers excellent amenities for a comfortable stay.";
        hotelDescription.setText(desc);

        hotelRating.setText("Rating: " + hotel.getRating());
        if(hotel.getPhone()!=null){
            hotelPhone.setText("Phone number: "+hotel.getPhone());
        }

        String price = "Average Price: " + (new Random().nextInt(5000) + 2000) + "\n" +
                "Price Range: " + (new Random().nextInt(2000) + 3000) + " - " + (new Random().nextInt(2000) + 5000) + "\n";
        hotelPrices.setText(price);

        hotelAddress.setText(hotel.getAddress());
        String amenities=String.join(",",hotel.getAmenities());
        hotelAmenities.setText(amenities);
    }

    private void setGHotelData(GHotel gHotel) {
        Glide.with(hotelImageView.getContext())
                .load(gHotel.getImageUrl())
                .placeholder(R.drawable.tempty)
                .error(R.drawable.tempty)
                .into(hotelImageView);
        hotelName.setText(gHotel.getTitle());
        String desc;
if (gHotel.getHotelDescription() != null) {
    desc = gHotel.getHotelDescription().toString();
} else {
    desc = gHotel.getTitle() + " is part of " + DISTRICT_NAME + ". It offers excellent amenities for a comfortable stay.";
}
        
        hotelDescription.setText(desc);

        hotelRating.setText("Rating: " + (gHotel.getTotalScore() == 0 ? RATING : gHotel.getTotalScore()));

        String price = "Average Price: " + (new Random().nextInt(5000) + 2000) + "\n" +
                "Price Range: " + (new Random().nextInt(2000) + 3000) + " - " + (new Random().nextInt(2000) + 5000) + "\n";
        hotelPrices.setText(price);
        if(gHotel.getPhone()!=null){
            hotelPhone.setText("Phone number: "+gHotel.getPhone());
        }
        hotelAddress.setText(gHotel.getAddress());
        hotelAmenities.setText(gHotel.getCategoryName());
    }

    private void loadNearbyAttractionsAndRestaurants() {
        loadNearbyAttractions();
        loadNearbyRestaurants();
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

    private void loadNearbyRestaurants() {
    String fileName = getFileName(FILE_RESTAURANTS);
    if (!fileName.isEmpty()) {
        if (fileName.toLowerCase().startsWith("g")) {
            List<GRestaurant> gRestaurantsList = JsonParserHelper.parseDistrictLargeJson(this, DISTRICT_NAME, fileName, GRestaurant.class);
            if (gRestaurantsList != null && !gRestaurantsList.isEmpty()) {
                int subListSize = Math.min(3, gRestaurantsList.size());
                nearbyRestaurantsAdapter = new RestaurantAdapter(this, gRestaurantsList.subList(0, subListSize), true);
                nearbyRestaurantsRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
                nearbyRestaurantsRecyclerView.setAdapter(nearbyRestaurantsAdapter);
            } else {
                emptyNearbyRestaurantsTextView.setVisibility(View.VISIBLE);
                Toast.makeText(this, "No Restaurants Available", Toast.LENGTH_SHORT).show();
            }
        } else {
            List<Restaurant> restaurantsList = JsonParserHelper.parseDistrictLargeJson(this, DISTRICT_NAME, fileName, Restaurant.class);
            if (restaurantsList != null && !restaurantsList.isEmpty()) {
                int subListSize = Math.min(3, restaurantsList.size());
                nearbyRestaurantsAdapter = new RestaurantAdapter(this, restaurantsList.subList(0, subListSize));
                nearbyRestaurantsRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
                nearbyRestaurantsRecyclerView.setAdapter(nearbyRestaurantsAdapter);
            } else {
                emptyNearbyRestaurantsTextView.setVisibility(View.VISIBLE);
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