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
import mini.project.models.Attraction;
import mini.project.models.GAttraction;
import mini.project.travelguide.AttractionDetails;

public class AttractionsList extends AppCompatActivity {

    private static final String FILE_ATTRACTIONS = "attractions.json";
    public static String DISTRICT_NAME = "";
    private String fileName;
    private LinearLayout sortByContent;
    private LinearLayout budgetContent;
    private ImageView arrowSortBy;
    private ImageView arrowBudget;
    private FloatingActionButton fab;
    private RecyclerView attractionListRecyclerView;
    private SearchView searchViewAttraction;
    private FusedLocationProviderClient fusedLocationClient;
    private Button mostVisitedBtn,popularBtn,topRatedBtn;
    private Button lowtoHigh,hightoLow;
    

    private List<GAttraction> gAttractionsList;
    private List<Attraction> AttractionsList;

    private AttractionAdapter AttractionsAdapter, gAttractionsAdapter;

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
        searchViewAttraction = findViewById(R.id.search_view);
        attractionListRecyclerView = findViewById(R.id.recycler_view_place_list);
        mostVisitedBtn=findViewById(R.id.btn_most_visited);
        popularBtn=findViewById(R.id.btn_popular);
        topRatedBtn=findViewById(R.id.btn_top_rated);
        lowtoHigh=findViewById(R.id.btn_low_to_high);
        hightoLow=findViewById(R.id.btn_high_to_low);
        
        popularBtn.setVisibility(View.GONE);
        fab = findViewById(R.id.fab_add);

        // Get District Name
        if (getIntent() != null) {
            DISTRICT_NAME = getIntent().getStringExtra("district");
        } else {
            Toast.makeText(this, "District Data Not Found", Toast.LENGTH_SHORT).show();
        }

        fileName = getFileName(FILE_ATTRACTIONS);
        if (!fileName.isEmpty()) {
            if (fileName.toLowerCase().startsWith("g")) {
                gAttractionsList = JsonParserHelper.parseDistrictLargeJson(this, DISTRICT_NAME, fileName, GAttraction.class);
                if (gAttractionsList != null && !gAttractionsList.isEmpty()) {
                    int subListSize = Math.min(20, gAttractionsList.size());
                    gAttractionsAdapter = new AttractionAdapter(this, gAttractionsList.subList(0, subListSize), true);
                    attractionListRecyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
                    attractionListRecyclerView.setAdapter(gAttractionsAdapter);
                } else {
                    Toast.makeText(this, "No top gAttractions available", Toast.LENGTH_SHORT).show();
                }
            } else {
                AttractionsList = JsonParserHelper.parseDistrictLargeJson(this, DISTRICT_NAME, fileName, Attraction.class);
                if (AttractionsList != null && !AttractionsList.isEmpty()) {
                    int subListSize = Math.min(20, AttractionsList.size());
                    AttractionsAdapter = new AttractionAdapter(this, AttractionsList.subList(0, subListSize));
                    attractionListRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL,false));
                    attractionListRecyclerView.setAdapter(AttractionsAdapter);
                } else {
                    Toast.makeText(this, "No top attractions available", Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            Toast.makeText(this, "File not Found", Toast.LENGTH_SHORT).show();
        }

        // Search Logic
        searchViewAttraction.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterAttractions(newText);
                return true;
            }
        });
        

            mostVisitedBtn.setOnClickListener(v -> {
                if (fileName.toLowerCase().startsWith("g") && gAttractionsAdapter != null) {
                    gAttractionsAdapter.sortGAttractions((a1, a2) -> Integer.compare(a2.getImagesCount(), a1.getImagesCount()));
                } else if (AttractionsAdapter != null) {
                    AttractionsAdapter.sortAttractions((a1, a2) -> Integer.compare(a2.getRankingPosition(), a1.getRankingPosition()));
                }
                
            });

           /* popularBtn.setOnClickListener(v -> {
                if (fileName.toLowerCase().startsWith("g") && gAttractionsAdapter != null) {
                    gAttractionsAdapter.sortGAttractions((a1, a2) -> {
                        int photoCompare = Integer.compare(a2.getImagesCount(), a1.getImagesCount());
                        return photoCompare != 0 ? photoCompare : Double.compare(a2.getTotalScore(), a1.getTotalScore());
                    });
                } else if (AttractionsAdapter != null) {
                    AttractionsAdapter.sortAttractions((a1, a2) -> {
                        int photoCompare = Integer.compare(a2.getRankingPosition(), a1.getRankingPosition());
                        return photoCompare != 0 ? photoCompare : Double.compare(a2.getRating(), a1.getRating());
                    });
                }
                
            });*/

            topRatedBtn.setOnClickListener(v -> {
                if (fileName.toLowerCase().startsWith("g") && gAttractionsAdapter != null) {
                    gAttractionsAdapter.sortGAttractions((a1, a2) -> Double.compare(a2.getTotalScore(), a1.getTotalScore()));
                } else if (AttractionsAdapter != null) {
                    AttractionsAdapter.sortAttractions((a1, a2) -> Double.compare(a2.getRating(), a1.getRating()));
                }
                
            });

        lowtoHigh.setOnClickListener(v -> {
                if (fileName.toLowerCase().startsWith("g") && gAttractionsAdapter != null) {
                    Collections.reverse(gAttractionsList);
                    gAttractionsAdapter.notifyDataSetChanged();
                } else if (AttractionsAdapter != null) {
                    Collections.reverse(AttractionsList);
                    AttractionsAdapter.notifyDataSetChanged();
                }
                
            });

            hightoLow.setOnClickListener(v -> {
                if (fileName.toLowerCase().startsWith("g") && gAttractionsAdapter != null) {
                    gAttractionsAdapter.sortGAttractions((a1, a2) -> Double.compare(a2.getTotalScore(), a1.getTotalScore()));
                } else if (AttractionsAdapter != null) {
                    AttractionsAdapter.sortAttractions((a1, a2) -> Double.compare(a2.getRating(), a1.getRating()));
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
                        Toast.makeText(AttractionsList.this, "Permission denied, can't get location", Toast.LENGTH_SHORT).show();
                    }
                });

        // Floating Action Button Click Logic (Modified Section)
        fab.setOnClickListener(view -> {
            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(AttractionsList.this);
            View bottomSheetView = LayoutInflater.from(AttractionsList.this).inflate(R.layout.bottom_sheet_layout, null);
            bottomSheetDialog.setContentView(bottomSheetView);
            bottomSheetDialog.show();

            Button buttonGetLocation = bottomSheetView.findViewById(R.id.buttonGetLocation);
            TextView textCoordinates = bottomSheetView.findViewById(R.id.textCoordinates);
            Button buttonFilter = bottomSheetView.findViewById(R.id.buttonFilter);
            TextView textBudget = bottomSheetView.findViewById(R.id.editTextPrice);
            TextView textTime = bottomSheetView.findViewById(R.id.editTextTime);
            

            buttonGetLocation.setOnClickListener(v -> {
                if (ContextCompat.checkSelfPermission(AttractionsList.this, Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {
                    getLocation(textCoordinates);
                } else {
                            Toast.makeText(this,"Turn on Location",Toast.LENGTH_SHORT).show();
                    requestLocationPermission();
                }
            });

            buttonFilter.setOnClickListener(v -> {
                String budgetInput = textBudget.getText().toString().trim();
                String timeInput = textTime.getText().toString().trim();

                if (TextUtils.isEmpty(budgetInput) || TextUtils.isEmpty(timeInput)) {
                    Toast.makeText(AttractionsList.this, "Please provide budget and time", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    double maxBudget = Double.parseDouble(budgetInput);
                    int maxTime = Integer.parseInt(timeInput);
                    applyFilters();
                    bottomSheetDialog.dismiss();
                } catch (NumberFormatException e) {
                    Toast.makeText(AttractionsList.this, "Invalid budget or time format", Toast.LENGTH_SHORT).show();
                }
            });

            bottomSheetDialog.setOnDismissListener(dialogInterface ->
                    Toast.makeText(AttractionsList.this, "Bottom sheet dismissed", Toast.LENGTH_SHORT).show());
        });
                
    }

    // Filter Logic
    private void filterAttractions(String query) { 
            if (fileName.toLowerCase().startsWith("g")) {   
                  // Filtering for GAttraction       
              if (gAttractionsAdapter != null) {          
                   List<GAttraction> filteredList = new ArrayList<>();    
                         if (!TextUtils.isEmpty(query)) {            
                         for (GAttraction attraction : gAttractionsList) {     
                                        if (attraction.getTitle().toLowerCase().contains(query.toLowerCase())) {        
                                             filteredList.add(attraction);           
                                      }         
                                }         
                        } else {         
                            filteredList.addAll(gAttractionsList);      
                           } gAttractionsAdapter.updateGList(filteredList);     
                    } else {        
                     Log.e("Filter", "gAttractionsAdapter is null. Cannot filter.");    
                     }     } else {         // Filtering for Attraction     
                if (AttractionsAdapter != null) {        
                     List<Attraction> filteredList = new ArrayList<>();     
                        if (!TextUtils.isEmpty(query)) {          
                           for (Attraction attraction : AttractionsList) {     
                                        if (attraction.getName().toLowerCase().contains(query.toLowerCase())) {        
                                             filteredList.add(attraction);           
                                      }          
                               }        
                         } else {           
                          filteredList.addAll(AttractionsList);      
                           }             AttractionsAdapter.updateList(filteredList);   
                      } else {   
                  Log.e("Filter", "AttractionsAdapter is null. Cannot filter.");      
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
                // gAttractions filter logic
                List<GAttraction> sortedList = new ArrayList<>(gAttractionsList);
                Calendar calendar = Calendar.getInstance();
                int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
                int currentMinute = calendar.get(Calendar.MINUTE);
                int currentTimeInMinutes = currentHour * 60 + currentMinute;

                // Sort gAttractions by distance
                sortedList.sort((a, b) -> {
                    float[] resultsA = new float[1];
                    float[] resultsB = new float[1];
                    Location.distanceBetween(userLatitude, userLongitude, a.getLocation().getLatitude(), a.getLocation().getLongitude(), resultsA);
                    Location.distanceBetween(userLatitude, userLongitude, b.getLocation().getLatitude(), b.getLocation().getLongitude(), resultsB);
                    return Float.compare(resultsA[0], resultsB[0]);
                });

                gAttractionsAdapter.updateGList(sortedList);
            } else {
                // Attractions filter logic
                List<Attraction> sortedList = new ArrayList<>(AttractionsList);
                Calendar calendar = Calendar.getInstance();
                int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
                int currentMinute = calendar.get(Calendar.MINUTE);
                int currentTimeInMinutes = currentHour * 60 + currentMinute;

                // Sort attractions by distance
                sortedList.sort((a, b) -> {
                    float[] resultsA = new float[1];
                    float[] resultsB = new float[1];
                    Location.distanceBetween(userLatitude, userLongitude, a.getLatitude(), a.getLongitude(), resultsA);
                    Location.distanceBetween(userLatitude, userLongitude, b.getLatitude(), b.getLongitude(), resultsB);
                    return Float.compare(resultsA[0], resultsB[0]);
                });

                AttractionsAdapter.updateList(sortedList);
            }
        }
    });
}
    
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

    private void requestLocationPermission() {
        permissionResultLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION);
    }

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

    
  

    public class AttractionAdapter extends RecyclerView.Adapter<AttractionAdapter.ViewHolder> {
        private List<Attraction> attractionList = new ArrayList<>();
        private List<GAttraction> gAttractionList = new ArrayList<>();
        private boolean isGFilename;
        private final Context context;

        // Constructor for Attraction List
        public AttractionAdapter(Context context, List<Attraction> attractionList) {
            this.context = context;
            this.attractionList = new ArrayList<>(attractionList);
            this.isGFilename = false;
        }

        // Constructor for GAttraction List
        public AttractionAdapter(Context context, List<GAttraction> gAttractionList, boolean isGFilename) {
            this.context = context;
            this.gAttractionList = new ArrayList<>(gAttractionList);
            this.isGFilename = isGFilename;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_attraction_grid, parent, false);
            return new ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            if (isGFilename) {
                GAttraction gAttraction = gAttractionList.get(position);
                holder.bindGAttraction(gAttraction);

            } else {
                Attraction attraction = attractionList.get(position);
                holder.bindAttraction(attraction);
            }
        }

        @Override
        public int getItemCount() {
            return isGFilename ? gAttractionList.size() : attractionList.size();
        }

        // ViewHolder Class
        public class ViewHolder extends RecyclerView.ViewHolder {
            private final TextView attractionName, attractionRating;
            private final ImageView attractionImage;

            public ViewHolder(View itemView) {
                super(itemView);
                attractionName = itemView.findViewById(R.id.attraction_name_grid);
                attractionRating = itemView.findViewById(R.id.attraction_rating_grid);
                attractionImage = itemView.findViewById(R.id.attraction_image_grid);
                itemView.setOnClickListener(v->onClick(v));
            }

            public void bindAttraction(Attraction attraction) {
                attractionName.setText(attraction.getName());
                attractionRating.setText(String.valueOf(attraction.getRating()));
                Glide.with(itemView.getContext())
                        .load(attraction.getImage())
                        .into(attractionImage);
                
            }

            public void bindGAttraction(GAttraction gAttraction) {
                attractionName.setText(gAttraction.getTitle());
                attractionRating.setText("Rating: " + gAttraction.getTotalScore());
                Glide.with(itemView.getContext())
                        .load(gAttraction.getImageUrl())
                        .placeholder(R.drawable.tempty)
                        .into(attractionImage);
              
            } 
        public void onClick(View view) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                Intent intent = new Intent(context, AttractionDetails.class);
                if (isGFilename) {
                    GAttraction gAttraction = gAttractionList.get(position);
                    intent.putExtra("gattraction", gAttraction);
                        intent.putExtra("district",DISTRICT_NAME);
                } else {
                    Attraction attraction = attractionList.get(position);
                    intent.putExtra("attraction", attraction);
                        intent.putExtra("district",DISTRICT_NAME);
                }
                context.startActivity(intent);
            }
        }

    }
            
        

        // Method to update Attraction List
        public void updateList(List<Attraction> newList) {
            this.attractionList.clear();
            this.attractionList.addAll(newList);
            notifyDataSetChanged();
        }

        // Method to update GAttraction List
        public void updateGList(List<GAttraction> newList) {
            this.gAttractionList.clear();
            this.gAttractionList.addAll(newList);
            notifyDataSetChanged();
        }

        // Method to sort Attraction List
        public void sortAttractions(Comparator<Attraction> comparator) {
            Collections.sort(attractionList, comparator);
            notifyDataSetChanged();
        }

        // Method to sort GAttraction List
        public void sortGAttractions(Comparator<GAttraction> comparator) {
            Collections.sort(gAttractionList, comparator);
            notifyDataSetChanged();
        }

        // Predefined Comparators
        //public static final Comparator<Attraction> PRICE_LOW_TO_HIGH =
        //(a1, a2) -> Double.compare(a1.getPrice(), a2.getPrice());

        // public static final Comparator<Attraction> PRICE_HIGH_TO_LOW =
        //   (a1, a2) -> Double.compare(a2.getPrice(), a1.getPrice());



    }
    public interface OnLocationReceivedCallback {
        void onLocationReceived(Location location);
    }
}

