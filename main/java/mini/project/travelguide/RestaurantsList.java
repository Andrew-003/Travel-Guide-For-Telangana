package mini.project.travelguide;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import mini.project.helpers.AssetLoader;
import mini.project.helpers.JsonParserHelper;
import mini.project.models.Restaurant;
import mini.project.models.GRestaurant;

public class RestaurantsList extends AppCompatActivity {

    private static final String FILE_RESTAURANTS = "restaurants.json";
    private static String DISTRICT_NAME = "";
    private String fileName;
    private LinearLayout sortByContent;
    private LinearLayout budgetContent;
    private ImageView arrowSortBy;
    private ImageView arrowBudget;
    private FloatingActionButton fab;
    private RecyclerView restaurantListRecyclerView;
    private SearchView searchViewRestaurant;
    private FusedLocationProviderClient fusedLocationClient;
    private Button mostVisitedBtn,popularBtn,topRatedBtn;
    private Button lowtoHigh,hightoLow;

    private List<GRestaurant> gRestaurantsList;
    private List<Restaurant> restaurantsList;

    private RestaurantAdapter restaurantsAdapter, gRestaurantsAdapter;

    private ActivityResultLauncher<String> permissionResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_list);

        // UI Elements
        sortByContent = findViewById(R.id.sort_by_content);
        budgetContent = findViewById(R.id.budget_content);
        arrowSortBy = findViewById(R.id.arrow_sort_by);
        arrowBudget = findViewById(R.id.arrow_budget);
        searchViewRestaurant = findViewById(R.id.search_view);
        restaurantListRecyclerView = findViewById(R.id.recycler_view_place_list);
        mostVisitedBtn=findViewById(R.id.btn_most_visited);
        popularBtn=findViewById(R.id.btn_popular);
        topRatedBtn=findViewById(R.id.btn_top_rated);
        lowtoHigh=findViewById(R.id.btn_low_to_high);
        hightoLow=findViewById(R.id.btn_high_to_low);
        fab = findViewById(R.id.fab_add);
       popularBtn.setVisibility(View.GONE);
        // Get District Name
        if (getIntent() != null) {
            DISTRICT_NAME = getIntent().getStringExtra("district");
        } else {
            Toast.makeText(this, "District Data Not Found", Toast.LENGTH_SHORT).show();
        }

        fileName = getFileName(FILE_RESTAURANTS);
        if (!fileName.isEmpty()) {
            if (fileName.toLowerCase().startsWith("g")) {
                gRestaurantsList = JsonParserHelper.parseDistrictLargeJson(this, DISTRICT_NAME, fileName, GRestaurant.class);
                if (gRestaurantsList != null && !gRestaurantsList.isEmpty()) {
                    int subListSize = Math.min(20, gRestaurantsList.size());
                    gRestaurantsAdapter = new RestaurantAdapter(this, gRestaurantsList.subList(0, subListSize), true);
                    restaurantListRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL,false));
                    restaurantListRecyclerView.setAdapter(gRestaurantsAdapter);
                } else {
                    Toast.makeText(this, "No top gRestaurants available", Toast.LENGTH_SHORT).show();
                }
            } else {
                restaurantsList = JsonParserHelper.parseDistrictLargeJson(this, DISTRICT_NAME, fileName, Restaurant.class);
                if (restaurantsList != null && !restaurantsList.isEmpty()) {
                    int subListSize = Math.min(20, restaurantsList.size());
                    restaurantsAdapter = new RestaurantAdapter(this, restaurantsList.subList(0, subListSize));
                    restaurantListRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL,false));
                    restaurantListRecyclerView.setAdapter(restaurantsAdapter);
                } else {
                    Toast.makeText(this, "No top restaurants available", Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            Toast.makeText(this, "File not Found", Toast.LENGTH_SHORT).show();
        }

        // Search Logic
        searchViewRestaurant.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterRestaurants(newText);
                return true;
            }
        });
    mostVisitedBtn.setOnClickListener(v -> {
    if (fileName.toLowerCase().startsWith("g") && gRestaurantsAdapter != null) {
        gRestaurantsAdapter.sortGRestaurants((r1, r2) -> Integer.compare(r2.getImagesCount(), r1.getImagesCount()));
    } else if (restaurantsAdapter != null) {
        restaurantsAdapter.sortRestaurants((r1, r2) -> Integer.compare(r2.getPhotoCount(), r1.getPhotoCount()));
    }
});
/*
popularBtn.setOnClickListener(v -> {
    if (fileName.toLowerCase().startsWith("g") && gRestaurantsAdapter != null) {
        gRestaurantsAdapter.sortGRestaurants((r1, r2) -> {
            int photoCompare = Integer.compare(r2.getRank(), r1.getRank());
            return photoCompare != 0 ? photoCompare : Double.compare(r2.getTotalScore(), r1.getTotalScore());
        });
    } else if (restaurantsAdapter != null) {
        restaurantsAdapter.sortRestaurants((r1, r2) -> {
            int photoCompare = Integer.compare(r2.getPhotoCount(), r1.getPhotoCount());
            return photoCompare != 0 ? photoCompare : Double.compare(r2.getRating(), r1.getRating());
        });
    }
});
*/
topRatedBtn.setOnClickListener(v -> {
    if (fileName.toLowerCase().startsWith("g") && gRestaurantsAdapter != null) {
        gRestaurantsAdapter.sortGRestaurants((r1, r2) -> Double.compare(r2.getTotalScore(), r1.getTotalScore()));
    } else if (restaurantsAdapter != null) {
        restaurantsAdapter.sortRestaurants((r1, r2) -> Double.compare(r2.getRating(), r1.getRating()));
    }
});

lowtoHigh.setOnClickListener(v -> {
    if (fileName.toLowerCase().startsWith("g") && gRestaurantsAdapter != null) {
        Collections.reverse(gRestaurantsList);
        gRestaurantsAdapter.notifyDataSetChanged();
    } else if (restaurantsAdapter != null) {
        Collections.reverse(restaurantsList);
        restaurantsAdapter.notifyDataSetChanged();
    }
});

hightoLow.setOnClickListener(v -> {
    if (fileName.toLowerCase().startsWith("g") && gRestaurantsAdapter != null) {
        gRestaurantsAdapter.sortGRestaurants((r1, r2) -> Double.compare(r2.getTotalScore(), r1.getTotalScore()));
    } else if (restaurantsAdapter != null) {
        restaurantsAdapter.sortRestaurants((r1, r2) -> Double.compare(r2.getRating(), r1.getRating()));
    }
});
        // Location Client
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // Permission Launcher
        permissionResultLauncher = registerForActivityResult(
                new ActivityResultContracts.RequestPermission(),
                isGranted -> {
                    if (isGranted) {
                        getLocation(findViewById(R.id.textCoordinates));
                    } else {
                        Toast.makeText(RestaurantsList.this, "Permission denied, can't get location", Toast.LENGTH_SHORT).show();
                    }
                });

        // Floating Action Button Click Logic (Modified Section)
        fab.setOnClickListener(view -> {
            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(RestaurantsList.this);
            View bottomSheetView = LayoutInflater.from(RestaurantsList.this).inflate(R.layout.bottom_sheet_layout, null);
            bottomSheetDialog.setContentView(bottomSheetView);
            bottomSheetDialog.show();

            Button buttonGetLocation = bottomSheetView.findViewById(R.id.buttonGetLocation);
            TextView textCoordinates = bottomSheetView.findViewById(R.id.textCoordinates);
            Button buttonFilter = bottomSheetView.findViewById(R.id.buttonFilter);
            TextView textBudget = bottomSheetView.findViewById(R.id.editTextPrice);
            TextView textTime = bottomSheetView.findViewById(R.id.editTextTime);

            buttonGetLocation.setOnClickListener(v -> {
                if (ContextCompat.checkSelfPermission(RestaurantsList.this, Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {
                    getLocation(textCoordinates);
                } else {
                    requestLocationPermission();
                }
            });

            buttonFilter.setOnClickListener(v -> {
                String budgetInput = textBudget.getText().toString().trim();
                String timeInput = textTime.getText().toString().trim();

                if (TextUtils.isEmpty(budgetInput) || TextUtils.isEmpty(timeInput)) {
                    Toast.makeText(RestaurantsList.this, "Please provide budget and time", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    double maxBudget = Double.parseDouble(budgetInput);
                    int maxTime = Integer.parseInt(timeInput);
                    applyFilters();
                    bottomSheetDialog.dismiss();
                } catch (NumberFormatException e) {
                    Toast.makeText(RestaurantsList.this, "Invalid budget or time format", Toast.LENGTH_SHORT).show();
                }
            });

            bottomSheetDialog.setOnDismissListener(dialogInterface ->
                    Toast.makeText(RestaurantsList.this, "Bottom sheet dismissed", Toast.LENGTH_SHORT).show());
        });
    }

    // Filter Logic
  private void filterRestaurants(String query) { 
    if (fileName.toLowerCase().startsWith("g")) {   
        // Filtering for GRestaurant       
        if (gRestaurantsAdapter != null) {          
            List<GRestaurant> filteredList = new ArrayList<>();    
            if (!TextUtils.isEmpty(query)) {            
                for (GRestaurant restaurant : gRestaurantsList) {     
                    if (restaurant.getTitle().toLowerCase().contains(query.toLowerCase())) {        
                        filteredList.add(restaurant);           
                    }         
                }         
            } else {         
                filteredList.addAll(gRestaurantsList);      
            } 
            gRestaurantsAdapter.updateGList(filteredList);     
        } else {        
            Log.e("Filter", "gRestaurantsAdapter is null. Cannot filter.");    
        }     
    } else {         
        // Filtering for Restaurant     
        if (restaurantsAdapter != null) {        
            List<Restaurant> filteredList = new ArrayList<>();     
            if (!TextUtils.isEmpty(query)) {          
                for (Restaurant restaurant : restaurantsList) {     
                    if (restaurant.getName().toLowerCase().contains(query.toLowerCase())) {        
                        filteredList.add(restaurant);           
                    }          
                }        
            } else {           
                filteredList.addAll(restaurantsList);      
            }             
            restaurantsAdapter.updateList(filteredList);   
        } else {   
            Log.e("Filter", "RestaurantsAdapter is null. Cannot filter.");      
        }    
    } 
}

    // Apply Filters (New Method)
    private void applyFilters() {
        getCurrentLocation(new OnLocationReceivedCallback() {
            @Override
            public void onLocationReceived(Location location) {
                // Handle the location data here
                double userLatitude = location.getLatitude();
                double userLongitude = location.getLongitude();

                if (fileName.toLowerCase().startsWith("g")) {
                    // gRestaurants filter logic
                    List<GRestaurant> filteredList = new ArrayList<>();
                    Calendar calendar = Calendar.getInstance();
                    int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
                    int currentMinute = calendar.get(Calendar.MINUTE);
                    int currentTimeInMinutes = currentHour * 60 + currentMinute; // Convert current time to minutes

                    // Closing time is 5:30 PM, which is 17:30 (5*60 + 30)
                    int closingTimeInMinutes = 17 * 60 + 30;

                    for (GRestaurant restaurant : gRestaurantsList) {
                        // Convert the restaurant's closing hour and minute to minutes
                        int restaurantClosingTimeInMinutes = closingTimeInMinutes;

                        // Check if the restaurant is within the allowed distance and the current time is before closing time
                        if (isWithinDistance(restaurant.getLocation().getLatitude(), restaurant.getLocation().getLongitude(), userLatitude, userLongitude, 12) &&
                                currentTimeInMinutes < closingTimeInMinutes &&
                                currentTimeInMinutes < restaurantClosingTimeInMinutes) {
                            filteredList.add(restaurant);
                        }
                    }

                    // Sort by distance
                    Collections.sort(filteredList, new Comparator<GRestaurant>() {
                        @Override
                        public int compare(GRestaurant restaurant1, GRestaurant restaurant2) {
                            double distance1 = calculateDistance(userLatitude, userLongitude, restaurant1.getLocation().getLatitude(), restaurant1.getLocation().getLongitude());
                            double distance2 = calculateDistance(userLatitude, userLongitude, restaurant2.getLocation().getLatitude(), restaurant2.getLocation().getLongitude());
                            return Double.compare(distance1, distance2);
                        }
                    });

                    // Show filtered results
                    gRestaurantsAdapter.updateGList(filteredList);
                } else {
                    // Restaurants filter logic
                    List<Restaurant> filteredList = new ArrayList<>();
                    for (Restaurant restaurant : restaurantsList) {
                        if (isWithinDistance(restaurant.getLatitude(), restaurant.getLongitude(), userLatitude, userLongitude, 12)) {
                            filteredList.add(restaurant);
                        }
                    }

                    // Show filtered results
                    restaurantsAdapter.updateList(filteredList);
                }
            }
        });
    }
   // Toggle Sort By Section
	public void toggleSortBy(View view) {
		if (sortByContent.getVisibility() == View.VISIBLE) {
			sortByContent.setVisibility(View.GONE);
			arrowSortBy.setImageResource(R.drawable.ic_arrow_down);
			} else {
			sortByContent.setVisibility(View.VISIBLE);
			arrowSortBy.setImageResource(R.drawable.ic_arrow_up);
		}
	}
	
	// Toggle Budget Section
	public void toggleBudget(View view) {
		if (budgetContent.getVisibility() == View.VISIBLE) {
			budgetContent.setVisibility(View.GONE);
			arrowBudget.setImageResource(R.drawable.ic_arrow_down);
			} else {
			budgetContent.setVisibility(View.VISIBLE);
			arrowBudget.setImageResource(R.drawable.ic_arrow_up);
		}
	}

    // Distance check
    private boolean isWithinDistance(double lat1, double lon1, double lat2, double lon2, double distanceThreshold) {
        double distance = calculateDistance(lat1, lon1, lat2, lon2);
        return distance <= distanceThreshold;
    }

    // Calculate distance between two points
    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        float[] results = new float[1];
        Location.distanceBetween(lat1, lon1, lat2, lon2, results);
        return results[0] / 1000; // Convert to kilometers
    }
   private void getCurrentLocation(OnLocationReceivedCallback callback) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.getLastLocation().addOnSuccessListener(location -> {
                if (location != null) {
                    callback.onLocationReceived(location);
                } else {
                    Toast.makeText(this, "Location unavailable", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            requestLocationPermission();
        }
    }


    // Location Permissions
    private void requestLocationPermission() {
        permissionResultLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION);
    }

    // Get current location
    private void getLocation(TextView textView) {
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, location -> {
                    if (location != null) {
                        textView.setText("Latitude: " + location.getLatitude() + ", Longitude: " + location.getLongitude());
                    }
                });
    }

    // Get file name for JSON
    private String getFileName(String plainFileName) {
        if (plainFileName == null || plainFileName.isEmpty()) {
            Toast.makeText(this, "File name is empty", Toast.LENGTH_SHORT).show();
            return " ";
        }

        String fileName = plainFileName.toLowerCase();
        String fileContent = AssetLoader.loadJSONFromAsset(this,DISTRICT_NAME.toLowerCase(), fileName);

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

        // If no file is found, return an empty string
        return " ";
    }
   

    public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.ViewHolder> {
        private List<Restaurant> restaurantList = new ArrayList<>();
        private List<GRestaurant> gRestaurantList = new ArrayList<>();
        private boolean isGFilename;
        private final Context context;

        // Constructor for Restaurant List
        public RestaurantAdapter(Context context, List<Restaurant> restaurantList) {
            this.context = context;
            this.restaurantList = new ArrayList<>(restaurantList);
            this.isGFilename = false;
        }

        // Constructor for GRestaurant List
        public RestaurantAdapter(Context context, List<GRestaurant> gRestaurantList, boolean isGFilename) {
            this.context = context;
            this.gRestaurantList = new ArrayList<>(gRestaurantList);
            this.isGFilename = isGFilename;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_rest_grid, parent, false);
            return new ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            if (isGFilename) {
                GRestaurant gRestaurant = gRestaurantList.get(position);
                holder.bindGRestaurant(gRestaurant);
            } else {
                Restaurant restaurant = restaurantList.get(position);
                holder.bindRestaurant(restaurant);
            }
        }

        @Override
        public int getItemCount() {
            return isGFilename ? gRestaurantList.size() : restaurantList.size();
        }

        // ViewHolder Class
        public class ViewHolder extends RecyclerView.ViewHolder {
            private final TextView restaurantName, restaurantRating;
            private final ImageView restaurantImage;

            public ViewHolder(View itemView) {
                super(itemView);
                restaurantName = itemView.findViewById(R.id.rest_name_grid);
                restaurantRating = itemView.findViewById(R.id.rest_rating_grid);
                restaurantImage = itemView.findViewById(R.id.rest_image_grid);
                itemView.setOnClickListener(v->onClick(v));
            }

            public void bindRestaurant(Restaurant restaurant) {
                restaurantName.setText(restaurant.getName());
                restaurantRating.setText(String.valueOf(restaurant.getRating()));
                Glide.with(itemView.getContext())
                        .load(restaurant.getImage())
                        .into(restaurantImage);
               
            }

            public void bindGRestaurant(GRestaurant gRestaurant) {
                restaurantName.setText(gRestaurant.getTitle());
                restaurantRating.setText("Rating: " + gRestaurant.getTotalScore());
                Glide.with(itemView.getContext())
                        .load(gRestaurant.getImageUrl())
                        .placeholder(R.drawable.tempty)
                        .into(restaurantImage);
                
            }
            public void onClick(View view) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                Intent intent = new Intent(context, RestaurantDetails.class);
                if (isGFilename) {
                    GRestaurant gRestaurant = gRestaurantList.get(position);
                    intent.putExtra("grestaurant", gRestaurant);
                        intent.putExtra("district",DISTRICT_NAME);
                } else {
                    Restaurant restaurant = restaurantList.get(position);
                    intent.putExtra("restaurant", restaurant);
                        intent.putExtra("district",DISTRICT_NAME);
                }
                context.startActivity(intent);
            }
        }
        }

        // Method to update Restaurant List
        public void updateList(List<Restaurant> newList) {
            this.restaurantList.clear();
            this.restaurantList.addAll(newList);
            notifyDataSetChanged();
        }

        // Method to update GRestaurant List
        public void updateGList(List<GRestaurant> newList) {
            this.gRestaurantList.clear();
            this.gRestaurantList.addAll(newList);
            notifyDataSetChanged();
        }

        // Method to sort Restaurant List
        public void sortRestaurants(Comparator<Restaurant> comparator) {
            Collections.sort(restaurantList, comparator);
            notifyDataSetChanged();
        }

        // Method to sort GRestaurant List
        public void sortGRestaurants(Comparator<GRestaurant> comparator) {
            Collections.sort(gRestaurantList, comparator);
            notifyDataSetChanged();
        }

        // Predefined Comparators
        // public static final Comparator<Restaurant> PRICE_LOW_TO_HIGH =
        // (r1, r2) -> Double.compare(r1.getPrice(), r2.getPrice());

        // public static final Comparator<Restaurant> PRICE_HIGH_TO_LOW =
        // (r1, r2) -> Double.compare(r2.getPrice(), r1.getPrice());
    }
    interface OnLocationReceivedCallback {
        void onLocationReceived(Location location);
    }

}
