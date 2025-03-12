package mini.project.travelguide;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import  java.util.List;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import mini.project.adapters.AttractionAdapter;
import mini.project.adapters.HotelAdapter;
import mini.project.adapters.RestaurantAdapter;
import mini.project.helpers.AssetLoader;
import mini.project.helpers.JsonParserHelper;
import mini.project.models.Attraction;
import mini.project.models.District;
import mini.project.models.GAttraction;
import mini.project.models.GHotel;
import mini.project.models.GRestaurant;
import mini.project.models.Hotel;
import mini.project.models.Restaurant;

import mini.project.travelguide.HotelsList;
import mini.project.travelguide.RestaurantsList;

public class DistrictDetailsActivity extends AppCompatActivity {

    private District districtDetails;
    private String fileName;
    private static  final String FILE_ATTRACTIONS="attractions.json";
    private static  final String FILE_HOTELS="hotels.json";
    private static  final String FILE_RESTAURANTS="restaurants.json";
    private TextView districtName,districtDescription;
    private TextView transportBusDesc,busOperator,busPickUpPoints,busTravelTime,busNearByCities;
    private TextView transportTrainDesc,trainDirectTrains,nearestMajorStation,stationName;

    private ImageView districtBg;
    private RecyclerView topAttractionsRecyclerView,topHotelsRecyclerView,topRestaurantsRecyclerView;
    private AttractionAdapter topAttractionsAdapter;
    private HotelAdapter topHotelsAdapter;
    private RestaurantAdapter topRestaurantsAdapter;
    private TextView seeAllAttractionsTextView,seeAllHotelsTextView,seeAllRestaurantsTextView;


    List<Attraction> AttractionsList;
    List<GAttraction> gAttractionsList;
    List<Hotel> HotelsList;
    List<GHotel> gHotelsList;
    List<Restaurant> RestaurantsList;
    List<GRestaurant> gRestaurantsList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_district_details);

        if(getIntent()!=null) {
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.TIRAMISU) {
                districtDetails = getIntent().getParcelableExtra("district", District.class);
            }else{
                districtDetails = getIntent().getParcelableExtra("district");
            }
        }
        // Bind views
         districtName = findViewById(R.id.districtName);
        districtBg=findViewById(R.id.districtBg);
        districtDescription=findViewById(R.id.districtDescription);
        topAttractionsRecyclerView=findViewById(R.id.topAttractionsRecyclerViewDistrict);
        topHotelsRecyclerView=findViewById(R.id.topHotelsRecyclerViewDistrict);
        topRestaurantsRecyclerView=findViewById(R.id.topRestaurantsRecyclerViewDistrict);
        seeAllAttractionsTextView=findViewById(R.id.seeAllAttractionsDistrict);
        seeAllHotelsTextView=findViewById(R.id.seeAllHotelsDistrict);
        seeAllRestaurantsTextView=findViewById(R.id.seeAllRestaurantsDistrict);
        transportBusDesc=findViewById(R.id.tv_bus_description);
        busOperator=findViewById(R.id.tv_operator);
        busPickUpPoints=findViewById(R.id.tv_pickup_points);
        busNearByCities=findViewById(R.id.tv_nearby_cities);
        busTravelTime=findViewById(R.id.tv_journey_time);
        transportTrainDesc=findViewById(R.id.tv_train_description);
        stationName=findViewById(R.id.tv_station_name);
        trainDirectTrains=findViewById(R.id.tv_direct_trains);
        nearestMajorStation=findViewById(R.id.tv_nearest_station);


        // Set data
        districtName.setText(districtDetails.getName());
        Glide.with(districtBg.getContext())
                .load(districtDetails.getBgImageUrl())
                .placeholder(R.drawable.tempty)
                .into(districtBg);
        districtDescription.setText(districtDetails.getMainDescription());
        if(districtDetails.getTransport()!=null){
            transportBusDesc.setText(districtDetails.getTransport().getByBus().getDescription());
            transportTrainDesc.setText(districtDetails.getTransport().getByTrain().getDescription());
        }

////busOperator.setText("Operator : "+districtDetails.getTransport().getByBus().getFromHyderabad().getOperator());
////busPickUpPoints.setText("Pickup Points : "+String.join(",",districtDetails.getTransport().getByBus().getFromHyderabad().getPickupPoints()));
//busNearByCities.setText(String.join(",",districtDetails.getTransport().getByBus().getFromNearbyCities()));
//busTravelTime.setText("Journey Time : "+districtDetails.getTransport().getByBus().getFromHyderabad().getJourneyTime());

//trainDirectTrains.setText(String.join(",","Direct trains : "+districtDetails.getTransport().getByTrain().getDirectTrains()));
//nearestMajorStation.setText("Nearest Major Sation : "+districtDetails.getTransport().getByTrain().getNearestMajorStation());
stationName.setText("Station : "+districtDetails.getName()+" Station");

//        try{
//            District.Bus busDetails= districtDetails.getTransport().getByBus();
//            Log.d("Bus Info", " Description bus: " + busDetails.getDescription());
//        } catch (Exception e) {
//            Log.e("Error Info", e.toString());
//        }


        //check if plainFileName!=gFileName then use plainFileName else gFileName
       fileName=getFileName(FILE_ATTRACTIONS);
        if(!fileName.isEmpty()){
            if(fileName.toLowerCase().startsWith("g")){
                // Start of Top Attractions RecyclerView Logic
                 gAttractionsList= JsonParserHelper.parseDistrictLargeJson(this,districtDetails.getName().toLowerCase(),fileName,GAttraction.class);
                if (gAttractionsList != null && !gAttractionsList.isEmpty()) {

                    int subListSize = Math.min(5, gAttractionsList.size());
                     topAttractionsAdapter = new AttractionAdapter(this, gAttractionsList.subList(0, subListSize), true);
                    topAttractionsRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
                    topAttractionsRecyclerView.setAdapter(topAttractionsAdapter);
                } else {
                    // Handle case where topAttractionsList is null or empty
                    Toast.makeText(this, "No top gattractions available", Toast.LENGTH_SHORT).show();
                }

            }else{
                 AttractionsList= JsonParserHelper.parseDistrictLargeJson(this,districtDetails.getName().toLowerCase(),fileName,Attraction.class);
                if (AttractionsList != null && !AttractionsList.isEmpty()) {

                    int subListSize = Math.min(5, AttractionsList.size());
                    topAttractionsAdapter = new AttractionAdapter(this, AttractionsList.subList(0,subListSize));
                    topAttractionsRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
                    topAttractionsRecyclerView.setAdapter(topAttractionsAdapter);
                } else {
                    // Handle case where topAttractionsList is null or empty
                    Toast.makeText(this, "No top attractions available", Toast.LENGTH_SHORT).show();
                }
            }
            // End of Top Attractions RecyclerView Logic
        }else{
            Toast.makeText(this, "File not Found", Toast.LENGTH_SHORT).show();
        }

//check if plainFileName!=gFileName then use plainFileName else gFileName
        fileName=getFileName(FILE_HOTELS);
        if(!fileName.isEmpty()){
            if(fileName.toLowerCase().startsWith("g")){
                // Start of Top Hotels RecyclerView Logic
                gHotelsList= JsonParserHelper.parseDistrictLargeJson(this,districtDetails.getName().toLowerCase(),fileName,GHotel.class);
                if (gHotelsList != null && !gHotelsList.isEmpty()) {
                    int subListSize = Math.min(5, gHotelsList.size());
                    topHotelsAdapter = new HotelAdapter(this, gHotelsList.subList(0, subListSize), true);
                    topHotelsRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
                    topHotelsRecyclerView.setAdapter(topHotelsAdapter);
                } else {
                    // Handle case where topAttractionsList is null or empty
                    Toast.makeText(this, "No top ghotels available", Toast.LENGTH_SHORT).show();
                }

            }else{
                HotelsList= JsonParserHelper.parseDistrictLargeJson(this,districtDetails.getName().toLowerCase(),fileName,Hotel.class);
                if (HotelsList != null && !HotelsList.isEmpty()) {

                    int subListSize = Math.min(5, HotelsList.size());
                    topHotelsAdapter = new HotelAdapter(this, HotelsList.subList(0,subListSize));
                    topHotelsRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
                    topHotelsRecyclerView.setAdapter(topHotelsAdapter);
                } else {
                    // Handle case where topAttractionsList is null or empty
                    Toast.makeText(this, "No top hotels available", Toast.LENGTH_SHORT).show();
                }
            }
            // End of Top Hotels RecyclerView Logic
        }else{
            Toast.makeText(this, "File not Found", Toast.LENGTH_SHORT).show();
        }
        /*
        fileName=getFileName(FILE_RESTAURANTS);
        if(fileName.toLowerCase().startsWith("g")){
            gRestaurantsList=JsonParserHelper.parseDistrictLargeJson(this,districtDetails.getName().toLowerCase(),fileName,GRestaurant.class);
            for (GRestaurant district : gRestaurantsList) {
        Log.d("G Info", "G Name: " + district.getTitle());
        Log.d("G Info", "G Address: " + district.getAddress());
        Log.d("G Info", " GImage URL: " + district.getImageUrl());
        Log.d("G Info", "-------------------------------");
    }}
        else{
            RestaurantsList=JsonParserHelper.parseDistrictLargeJson(this,districtDetails.getName().toLowerCase(),fileName,Restaurant.class);
            for (Restaurant district : RestaurantsList) {
        Log.d("P Info", "P Name: " + district.getName());
        Log.d("P Info", "P Address: " + district.getAddress());
        Log.d("P Info", " PImage URL: " + district.getImage());
        Log.d("P Info", "-------------------------------");
        }
            }*/


        //check if plainFileName!=gFileName then use plainFileName else gFileName
        fileName=getFileName(FILE_RESTAURANTS);
        if(!fileName.isEmpty()){
            if(fileName.toLowerCase().startsWith("g")){
                // Start of Top Restaurants RecyclerView Logic
                gRestaurantsList= JsonParserHelper.parseDistrictLargeJson(this,districtDetails.getName().toLowerCase(),fileName,GRestaurant.class);
                if (gRestaurantsList != null && !gRestaurantsList.isEmpty()) {

                    int subListSize = Math.min(5, gRestaurantsList.size());
                    topRestaurantsAdapter = new RestaurantAdapter(this, gRestaurantsList.subList(0, subListSize), true);
                    topRestaurantsRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
                    topRestaurantsRecyclerView.setAdapter(topRestaurantsAdapter);
                } else {
                    // Handle case where topAttractionsList is null or empty
                    Toast.makeText(this, "No top grestaurants available", Toast.LENGTH_SHORT).show();
                }

            }else{
                RestaurantsList= JsonParserHelper.parseDistrictLargeJson(this,districtDetails.getName().toLowerCase(),fileName,Restaurant.class);
                if (RestaurantsList != null && !RestaurantsList.isEmpty()) {

                    int subListSize = Math.min(5, RestaurantsList.size());
                    topRestaurantsAdapter = new RestaurantAdapter(this, RestaurantsList.subList(0,subListSize));
                    topRestaurantsRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
                    topRestaurantsRecyclerView.setAdapter(topRestaurantsAdapter);
                } else {
                    // Handle case where topAttractionsList is null or empty
                    Toast.makeText(this, "No top restaurants available", Toast.LENGTH_SHORT).show();
                }
            }
            // End of Top Restaurants RecyclerView Logic
        }else{
            Toast.makeText(this, "File not Found", Toast.LENGTH_SHORT).show();
        }
        seeAllAttractionsTextView.setOnClickListener(v->openAttractionsList());
       seeAllHotelsTextView.setOnClickListener(v->openHotelsList());
      seeAllRestaurantsTextView.setOnClickListener(v->openRestaurantsList());

    }

    private void openAttractionsList() {
        Intent intent=new Intent(DistrictDetailsActivity.this,AttractionsList.class);
        intent.putExtra("district",districtDetails.getName().toLowerCase());
        startActivity(intent);
    }
   private void openHotelsList() {
        Intent intent=new Intent(this,HotelsList.class);
        intent.putExtra("district",districtDetails.getName().toLowerCase());
        startActivity(intent);
    }
   private void openRestaurantsList() {
        Intent intent=new Intent(this,RestaurantsList.class);
        intent.putExtra("district",districtDetails.getName().toLowerCase());
        startActivity(intent);
    }

    private String getFileName(String plainFileName) {
        if (plainFileName == null || plainFileName.isEmpty()) {
            Toast.makeText(this, "File name is empty", Toast.LENGTH_SHORT).show();
            return " ";
        }

        String fileName = plainFileName.toLowerCase();
        String fileContent = AssetLoader.loadJSONFromAsset(this, districtDetails.getName().toLowerCase(), fileName);

        if (fileContent == null || fileContent.isEmpty()) {
            // Try the alternate "g" + fileName
            String alternateFileName = "g" + fileName;
            fileContent = AssetLoader.loadJSONFromAsset(this, districtDetails.getName().toLowerCase(), alternateFileName);
            if (fileContent != null && !fileContent.isEmpty()) {
                return alternateFileName; // Return the found alternate file name
            }
        } else {
            return fileName; // Return the original file name
        }

        // If no file is found, return an empty string
        return " ";
    }

}