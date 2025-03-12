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
import mini.project.models.Hotel;
import mini.project.models.GHotel;

public class HotelsList extends AppCompatActivity {

    private static final String FILE_HOTELS = "hotels.json";
    private static String DISTRICT_NAME = "";
    private String fileName;
    private LinearLayout sortByContent;
    private LinearLayout budgetContent;
    private ImageView arrowSortBy;
    private ImageView arrowBudget;
    private FloatingActionButton fab;
    private RecyclerView hotelListRecyclerView;
    private SearchView searchViewHotel;
    private FusedLocationProviderClient fusedLocationClient;
    private Button mostVisitedBtn,popularBtn,topRatedBtn;
    private Button lowtoHigh,hightoLow;

    private List<GHotel> gHotelsList;
    private List<Hotel> hotelsList;

    private HotelAdapter hotelsAdapter, gHotelsAdapter;

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
        searchViewHotel = findViewById(R.id.search_view);
        hotelListRecyclerView = findViewById(R.id.recycler_view_place_list);
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

        fileName = getFileName(FILE_HOTELS);
        if (!fileName.isEmpty()) {
            if (fileName.toLowerCase().startsWith("g")) {
                gHotelsList = JsonParserHelper.parseDistrictLargeJson(this, DISTRICT_NAME, fileName, GHotel.class);
                if (gHotelsList != null && !gHotelsList.isEmpty()) {
                    int subListSize = Math.min(20, gHotelsList.size());
                    gHotelsAdapter = new HotelAdapter(this, gHotelsList.subList(0, subListSize), true);
                    hotelListRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL,false));
                    hotelListRecyclerView.setAdapter(gHotelsAdapter);
                } else {
                    Toast.makeText(this, "No top gHotels available", Toast.LENGTH_SHORT).show();
                }
            } else {
                hotelsList = JsonParserHelper.parseDistrictLargeJson(this, DISTRICT_NAME, fileName, Hotel.class);
                if (hotelsList != null && !hotelsList.isEmpty()) {
                    int subListSize = Math.min(20, hotelsList.size());
                    hotelsAdapter = new HotelAdapter(this, hotelsList.subList(0, subListSize));
                    hotelListRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL,false));
                    hotelListRecyclerView.setAdapter(hotelsAdapter);
                } else {
                    Toast.makeText(this, "No top hotels available", Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            Toast.makeText(this, "File not Found", Toast.LENGTH_SHORT).show();
        }

        // Search Logic
        searchViewHotel.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterHotels(newText);
                return true;
            }
        });
        
        mostVisitedBtn.setOnClickListener(v -> {
    if (fileName.toLowerCase().startsWith("g") && gHotelsAdapter != null) {
        gHotelsAdapter.sortGHotels((h1, h2) -> Integer.compare(h2.getRank(), h1.getRank()));
    } else if (hotelsAdapter != null) {
        hotelsAdapter.sortHotels((h1, h2) -> Integer.compare(h2.getRankingPosition(), h1.getRankingPosition()));
    }
});
/*
popularBtn.setOnClickListener(v -> {
    if (fileName.toLowerCase().startsWith("g") && gHotelsAdapter != null) {
        gHotelsAdapter.sortGHotels((h1, h2) -> {
            int photoCompare = Integer.compare(h2.getRank(), h1.getRank());
            return photoCompare != 0 ? photoCompare : Double.compare(h2.getTotalScore(), h1.getTotalScore());
        });
    } else if (hotelsAdapter != null) {
        hotelsAdapter.sortHotels((h1, h2) -> {
            int photoCompare = Integer.compare(h2.getRankingPosition(), h1.getRankingPosition());
            return photoCompare != 0 ? photoCompare : Double.compare(h2.getRating(), h1.getRating());
        });
    }
});*/

topRatedBtn.setOnClickListener(v -> {
    if (fileName.toLowerCase().startsWith("g") && gHotelsAdapter != null) {
        gHotelsAdapter.sortGHotels((h1, h2) -> Double.compare(h2.getTotalScore(), h1.getTotalScore()));
    } else if (hotelsAdapter != null) {
        hotelsAdapter.sortHotels((h1, h2) -> Double.compare(h2.getRating(), h1.getRating()));
    }
});

lowtoHigh.setOnClickListener(v -> {
    if (fileName.toLowerCase().startsWith("g") && gHotelsAdapter != null) {
        Collections.reverse(gHotelsList);
        gHotelsAdapter.notifyDataSetChanged();
    } else if (hotelsAdapter != null) {
        Collections.reverse(hotelsList);
        hotelsAdapter.notifyDataSetChanged();
    }
});

hightoLow.setOnClickListener(v -> {
    if (fileName.toLowerCase().startsWith("g") && gHotelsAdapter != null) {
        gHotelsAdapter.sortGHotels((h1, h2) -> Double.compare(h2.getTotalScore(), h1.getTotalScore()));
    } else if (hotelsAdapter != null) {
        hotelsAdapter.sortHotels((h1, h2) -> Double.compare(h2.getRating(), h1.getRating()));
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
                        Toast.makeText(HotelsList.this, "Permission denied, can't get location", Toast.LENGTH_SHORT).show();
                    }
                });

        // Floating Action Button Click Logic (Modified Section)
        fab.setOnClickListener(view -> {
            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(HotelsList.this);
            View bottomSheetView = LayoutInflater.from(HotelsList.this).inflate(R.layout.bottom_sheet_layout, null);
            bottomSheetDialog.setContentView(bottomSheetView);
            bottomSheetDialog.show();

            Button buttonGetLocation = bottomSheetView.findViewById(R.id.buttonGetLocation);
            TextView textCoordinates = bottomSheetView.findViewById(R.id.textCoordinates);
            Button buttonFilter = bottomSheetView.findViewById(R.id.buttonFilter);
            TextView textBudget = bottomSheetView.findViewById(R.id.editTextPrice);
            TextView textTime = bottomSheetView.findViewById(R.id.editTextTime);

            buttonGetLocation.setOnClickListener(v -> {
                if (ContextCompat.checkSelfPermission(HotelsList.this, Manifest.permission.ACCESS_FINE_LOCATION)
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
                    Toast.makeText(HotelsList.this, "Please provide budget and time", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    double maxBudget = Double.parseDouble(budgetInput);
                    int maxTime = Integer.parseInt(timeInput);
                    applyFilters();
                    bottomSheetDialog.dismiss();
                } catch (NumberFormatException e) {
                    Toast.makeText(HotelsList.this, "Invalid budget or time format", Toast.LENGTH_SHORT).show();
                }
            });

            bottomSheetDialog.setOnDismissListener(dialogInterface ->
                    Toast.makeText(HotelsList.this, "Bottom sheet dismissed", Toast.LENGTH_SHORT).show());
        });
    }

    // Filter Logic
  private void filterHotels(String query) { 
    if (fileName.toLowerCase().startsWith("g")) {   
        // Filtering for GHotel       
        if (gHotelsAdapter != null) {          
            List<GHotel> filteredList = new ArrayList<>();    
            if (!TextUtils.isEmpty(query)) {            
                for (GHotel hotel : gHotelsList) {     
                    if (hotel.getTitle().toLowerCase().contains(query.toLowerCase())) {        
                        filteredList.add(hotel);           
                    }         
                }         
            } else {         
                filteredList.addAll(gHotelsList);      
            } 
            gHotelsAdapter.updateGList(filteredList);     
        } else {        
            Log.e("Filter", "gHotelsAdapter is null. Cannot filter.");    
        }     
    } else {         
        // Filtering for Hotel     
        if (hotelsAdapter != null) {        
            List<Hotel> filteredList = new ArrayList<>();     
            if (!TextUtils.isEmpty(query)) {          
                for (Hotel hotel : hotelsList) {     
                    if (hotel.getName().toLowerCase().contains(query.toLowerCase())) {        
                        filteredList.add(hotel);           
                    }          
                }        
            } else {           
                filteredList.addAll(hotelsList);      
            }             
            hotelsAdapter.updateList(filteredList);   
        } else {   
            Log.e("Filter", "HotelsAdapter is null. Cannot filter.");      
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
                    // gHotels filter logic
                    List<GHotel> filteredList = new ArrayList<>();
                    Calendar calendar = Calendar.getInstance();
                    int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
                    int currentMinute = calendar.get(Calendar.MINUTE);
                    int currentTimeInMinutes = currentHour * 60 + currentMinute; // Convert current time to minutes

                    // Closing time is 5:30 PM, which is 17:30 (5*60 + 30)
                    int closingTimeInMinutes = 17 * 60 + 30;

                    for (GHotel hotel : gHotelsList) {
                        // Convert the hotel's closing hour and minute to minutes
                        int hotelClosingTimeInMinutes = closingTimeInMinutes;

                        // Check if the hotel is within the allowed distance and the current time is before closing time
                        if (isWithinDistance(hotel.getLocation().getLat(), hotel.getLocation().getLng(), userLatitude, userLongitude, 12) &&
                                currentTimeInMinutes < closingTimeInMinutes &&
                                currentTimeInMinutes < hotelClosingTimeInMinutes) {
                            filteredList.add(hotel);
                        }
                    }

                    // Sort by distance
                    Collections.sort(filteredList, new Comparator<GHotel>() {
                        @Override
                        public int compare(GHotel hotel1, GHotel hotel2) {
                            double distance1 = calculateDistance(userLatitude, userLongitude, hotel1.getLocation().getLat(), hotel1.getLocation().getLng());
                            double distance2 = calculateDistance(userLatitude, userLongitude, hotel2.getLocation().getLat(), hotel2.getLocation().getLng());
                            return Double.compare(distance1, distance2);
                        }
                    });

                    // Show filtered results
                    gHotelsAdapter.updateGList(filteredList);
                } else {
                    // Hotels filter logic
                    List<Hotel> filteredList = new ArrayList<>();
                    for (Hotel hotel : hotelsList) {
                        if (isWithinDistance(hotel.getLatitude(), hotel.getLongitude(), userLatitude, userLongitude, 12)) {
                            filteredList.add(hotel);
                        }
                    }

                    // Show filtered results
                    hotelsAdapter.updateList(filteredList);
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
    private void getLocation(TextView textCoordinates) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(location -> {
                        if (location != null) {
                            textCoordinates.setText("Latitude: " + location.getLatitude() + " Longitude: " + location.getLongitude());
                        }
                    });
        } else {
            permissionResultLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION);
        }
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
  
    public  class HotelAdapter extends RecyclerView.Adapter<HotelAdapter.ViewHolder> {
        private List<Hotel> hotelList = new ArrayList<>();
        private List<GHotel> gHotelList = new ArrayList<>();
        private boolean isGFilename;
        private final Context context;

        // Constructor for Hotel List
        public HotelAdapter(Context context, List<Hotel> hotelList) {
            this.context = context;
            this.hotelList = new ArrayList<>(hotelList);
            this.isGFilename = false;
        }

        // Constructor for GHotel List
        public HotelAdapter(Context context, List<GHotel> gHotelList, boolean isGFilename) {
            this.context = context;
            this.gHotelList = new ArrayList<>(gHotelList);
            this.isGFilename = isGFilename;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_hotel_grid, parent, false);
            return new ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            if (isGFilename) {
                GHotel gHotel = gHotelList.get(position);
                holder.bindGHotel(gHotel);
            } else {
                Hotel hotel = hotelList.get(position);
                holder.bindHotel(hotel);
            }
        }

        @Override
        public int getItemCount() {
            return isGFilename ? gHotelList.size() : hotelList.size();
        }

        // ViewHolder Class
        public class ViewHolder extends RecyclerView.ViewHolder {
            private final TextView hotelName, hotelRating;
            private final ImageView hotelImage;

            public ViewHolder(View itemView) {
                super(itemView);
                hotelName = itemView.findViewById(R.id.hotel_name_grid);
                hotelRating = itemView.findViewById(R.id.hotel_rating_grid);
                hotelImage = itemView.findViewById(R.id.hotel_image_grid);
                itemView.setOnClickListener(v->onClick(v));
            }

            public void bindHotel(Hotel hotel) {
                hotelName.setText(hotel.getName());
                hotelRating.setText(String.valueOf(hotel.getRating()));
                Glide.with(itemView.getContext())
                        .load(hotel.getImage())
                        .into(hotelImage);
               
            }

            public void bindGHotel(GHotel gHotel) {
                hotelName.setText(gHotel.getTitle());
                hotelRating.setText("Rating: " + gHotel.getTotalScore());
                Glide.with(itemView.getContext())
                        .load(gHotel.getImageUrl())
                        .placeholder(R.drawable.tempty)
                        .into(hotelImage);
               
            }
            public void onClick(View view) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                Intent intent = new Intent(context, HotelDetails.class);
                if (isGFilename) {
                    GHotel gHotel = gHotelList.get(position);
                    intent.putExtra("ghotel", gHotel);
                        intent.putExtra("district",DISTRICT_NAME);
                } else {
                    Hotel hotel = hotelList.get(position);
                    intent.putExtra("hotel", hotel);
                        intent.putExtra("district",DISTRICT_NAME);
                }
                context.startActivity(intent);
            }
        }
        }

        // Method to update Hotel List
        public void updateList(List<Hotel> newList) {
            this.hotelList.clear();
            this.hotelList.addAll(newList);
            notifyDataSetChanged();
        }

        // Method to update GHotel List
        public void updateGList(List<GHotel> newList) {
            this.gHotelList.clear();
            this.gHotelList.addAll(newList);
            notifyDataSetChanged();
        }

        // Method to sort Hotel List
        public void sortHotels(Comparator<Hotel> comparator) {
            Collections.sort(hotelList, comparator);
            notifyDataSetChanged();
        }

        // Method to sort GHotel List
        public void sortGHotels(Comparator<GHotel> comparator) {
            Collections.sort(gHotelList, comparator);
            notifyDataSetChanged();
        }

        // Predefined Comparators
        // public static final Comparator<Hotel> PRICE_LOW_TO_HIGH =
        // (h1, h2) -> Double.compare(h1.getPrice(), h2.getPrice());

        // public static final Comparator<Hotel> PRICE_HIGH_TO_LOW =
        // (h1, h2) -> Double.compare(h2.getPrice(), h1.getPrice());
    }
    interface OnLocationReceivedCallback {
        void onLocationReceived(Location location);
    }

}
