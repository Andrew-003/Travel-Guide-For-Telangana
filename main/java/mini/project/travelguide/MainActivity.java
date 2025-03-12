package mini.project.travelguide;

import android.annotation.SuppressLint;
import android.util.Log;
import androidx.annotation.NonNull;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mini.project.adapters.AttractionAdapter;
import mini.project.adapters.ImageSliderAdapter;
import mini.project.adapters.TopDestinationAdapter;
import mini.project.fragments.AttractionFragment;
import mini.project.fragments.HotelFragment;
import mini.project.fragments.RestaurantFragment;
import mini.project.helpers.FullScreenUtils;
import mini.project.helpers.JsonParserHelper;
import mini.project.models.District;
import mini.project.models.GAttraction;


public class MainActivity extends AppCompatActivity {

    private ViewPager2 bestPlacesViewPager, topPlacesViewPager;
    private ImageSliderAdapter imgAdapter;
    private TopDestinationAdapter topDestinationsAdapter;
    private AttractionAdapter topAttractionsAdapter;
    private RecyclerView topDestinationsRecyclerView, topAttractionsRecyclerView;
    private Button exploreButton;
    private TextView seeAllDestinationsTextView, seeAllAttractionsTextView;

    private ArrayList<Integer> images = new ArrayList<>(Arrays.asList(
            R.drawable.charminar1,
            R.drawable.nagarjuna_sagar,
            R.drawable.bhadrachalam_temples1,
            R.drawable.warangal1,
            R.drawable.adilabad_waterfalls1
    ));

    private final ArrayList<String> imageNames = new ArrayList<>(Arrays.asLisgt(
            "Charminar",
            "Nagarjuna Sagar",
            "Bhadrachalam Temples",
            "Warangal",
            "Adilabad Waterfalls"
    ));
    private final ArrayList<String> imgRating = new ArrayList<>(Arrays.asList(
            "Rating:4.7", "Rating:4.6", "Rating:4.5", "Rating:4.3", "Rating:4.5"
    ));

    private ArrayList<String> imgDetails;
    private int currentSlide = 0;
    private boolean isUserInteracting = false;
    private Handler handler;
    private Runnable slideRunnable;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FullScreenUtils.enableFullScreen(MainActivity.this);

        // Initialize Views
        bestPlacesViewPager = findViewById(R.id.viewPagerSlideShow);
        exploreButton = findViewById(R.id.exploreNowButton);
        topPlacesViewPager = findViewById(R.id.topDestinationsViewPager);
        topDestinationsRecyclerView = findViewById(R.id.topDestinationsRecyclerView);
        seeAllDestinationsTextView = findViewById(R.id.seeAllDestinations);
        seeAllAttractionsTextView = findViewById(R.id.seeAllAttractions);
        topAttractionsRecyclerView = findViewById(R.id.topAttractionsRecyclerView);

        // Load slide descriptions
        String[] slideDescriptions = getResources().getStringArray(R.array.slides_desc);
        imgDetails = new ArrayList<>(Arrays.asList(slideDescriptions));

        // Initialize ImageSliderAdapter
        imgAdapter = new ImageSliderAdapter(images, imageNames, imgDetails, imgRating);
        bestPlacesViewPager.setAdapter(imgAdapter);

        // Slideshow logic
        handler = new Handler();
        slideRunnable = () -> {
            if (!isUserInteracting) {
                currentSlide = (currentSlide + 1) % images.size();
                bestPlacesViewPager.setCurrentItem(currentSlide, true);
                handler.postDelayed(slideRunnable, 2000);
            }
        };

        bestPlacesViewPager.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                isUserInteracting = true;
                stopSlideshow();
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                isUserInteracting = false;
                startSlideshow();
            }
            return false;
        });
        startSlideshow();

        // Explore Button
        exploreButton.setOnClickListener(v -> openDistrictsList());

        // ViewPager2 for Top Places
        FragmentStateAdapter pagerAdapter = new FragmentStateAdapter(this) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                switch (position) {
                    case 0:
                        return new AttractionFragment();
                    case 1:
                        return new HotelFragment();
                    case 2:
                        return new RestaurantFragment();
                    default:
                        return new AttractionFragment();
                }
            }

            @Override
            public int getItemCount() {
                return 3;
            }
        };
        topPlacesViewPager.setAdapter(pagerAdapter);

        // RecyclerView for Top Destinations
    /*    
        // Load the districts.json file from assets
String jsonString = AssetLoader.loadJSONFromAsset(MainActivity.this, "districts.json");

if (jsonString != null && !jsonString.isEmpty()) {
    Toast.makeText(MainActivity.this, "File is not empty", Toast.LENGTH_SHORT).show();

    // Use Gson to parse the JSON string into a List<District>
    Gson gson = new Gson();

    // Define the type for the list of District objects
    Type listType = new TypeToken<List<District>>() {}.getType();

    // Parse the JSON string into a List<District>
    List<District> topDestinationsList = gson.fromJson(jsonString, listType);

    // Log each district's details
    for (District district : topDestinationsList) {
        Log.d("DistrictInfo", "District Name: " + district.getName());
        Log.d("DistrictInfo", "Description: " + district.getSubDescription());
        Log.d("DistrictInfo", "Image URL: " + district.getImageUrl());
        Log.d("DistrictInfo", "-------------------------------");
    }

} else {
    Toast.makeText(MainActivity.this, "File is empty", Toast.LENGTH_SHORT).show();
}*/
        List<District> topDestinationsList = JsonParserHelper.parseJson(this,"districts.json",null,District.class);

        
        if (topDestinationsList != null && !topDestinationsList.isEmpty()) {
            topDestinationsAdapter = new TopDestinationAdapter(this, topDestinationsList.subList(0, Math.min(5, topDestinationsList.size())));
            topDestinationsRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
            topDestinationsRecyclerView.setAdapter(topDestinationsAdapter);
        }

        // RecyclerView for Top Attractions
      List<GAttraction> topAttractionsList = JsonParserHelper.parseLargeJson(this, "gattractions.json",  GAttraction.class);
     /*  for (GAttraction item : topAttractionsList) {
        Log.d("DistrictInfo", "Name: " + item.getTitle());
        Log.d("DistrictInfo", "Address: " + item.getAddress());
        Log.d("DistrictInfo", "Image URL: " + item.getImageUrl());
        Log.d("DistrictInfo", "-------------------------------");
    }*/

if (topAttractionsList != null && !topAttractionsList.isEmpty()) {
    // Ensure the sublist is within valid bounds
    int subListSize = Math.min(60, topAttractionsList.size());
    topAttractionsAdapter = new AttractionAdapter(this, topAttractionsList.subList(55, subListSize), true);
    topAttractionsRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
    topAttractionsRecyclerView.setAdapter(topAttractionsAdapter);
} else {
    // Handle case where topAttractionsList is null or empty
    Toast.makeText(MainActivity.this, "No top attractions available", Toast.LENGTH_SHORT).show();
}
        // See All Listeners
        seeAllDestinationsTextView.setOnClickListener(v -> openDistrictsList());
        seeAllAttractionsTextView.setOnClickListener(v -> openAttractionsList());
        /*
        List<Restaurant> restList=JsonParserHelper.parseLargeJson(this,"top_restaurants.json",Restaurant.class);
        if(restList!=null&&!restList.isEmpty()){
            for (Restaurant item : restList) {
        Log.d("DistrictInfo", "Name: " + item.getName());
        Log.d("DistrictInfo", "Address: " + item.getAddress());
        Log.d("DistrictInfo", "Image URL: " + item.getImage());
        Log.d("DistrictInfo", "-------------------------------");
        }
    }
        else {
    Toast.makeText(MainActivity.this, "File is empty", Toast.LENGTH_SHORT).show();
}*/
        }

    private void openAttractionsList() {
        Intent attractionsListIntent = new Intent(this, AttractionsList.class);
        String district="allattractions";
        attractionsListIntent.putExtra("district",district);
        startActivity(attractionsListIntent);
    }

    private void openDistrictsList() {
        Intent districtListIntent = new Intent(this, DistrictListActivity.class);
        startActivity(districtListIntent);
    }

    private void startSlideshow() {
        handler.postDelayed(slideRunnable, 2000);
    }

    private void stopSlideshow() {
        handler.removeCallbacks(slideRunnable);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopSlideshow();
    }
}