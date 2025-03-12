package mini.project.travelguide;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import mini.project.helpers.FullScreenUtils;
import mini.project.helpers.JsonParserHelper;
import mini.project.models.District;

public class DistrictListActivity extends AppCompatActivity {

    private SearchView searchViewDistricts;
    private RecyclerView recyclerViewDistrictList;
    private final String fileName = "districts.json";
    private DistrictListAdapter adapter;
    private List<District> districtList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_district_list);
        
        FullScreenUtils.enableFullScreen(DistrictListActivity.this);
        // Set the title of the ActionBar
    if (getSupportActionBar() != null) {
        getSupportActionBar().setTitle("All Districts of Telangana");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  // Enable the Up button
    }


        // Initialize views
        searchViewDistricts = findViewById(R.id.search_view_district);
        @SuppressLint("RestrictedApi") SearchView.SearchAutoComplete searchAutoComplete;
//        searchAutoComplete = searchViewDistricts.findViewById(androidx.appcompat.R.id.search_src_text);
//        searchAutoComplete.setTextColor(Color.BLACK);
        recyclerViewDistrictList = findViewById(R.id.district_list_recyclerview);
        

        // Parse districts JSON
        districtList = JsonParserHelper.parseJson(DistrictListActivity.this, fileName, null, District.class);

        if (districtList != null) {
            adapter = new DistrictListAdapter(districtList);
            recyclerViewDistrictList.setLayoutManager(new LinearLayoutManager(this));
            recyclerViewDistrictList.setAdapter(adapter);
        } else {
            Toast.makeText(this, "Failed to load districts", Toast.LENGTH_SHORT).show();
        }

        // Add SearchView functionality
        searchViewDistricts.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false; // We don't need to handle the submit action
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterDistricts(newText);
                return true;
            }
        });
    }

    private void filterDistricts(String query) {
        List<District> filteredList = new ArrayList<>();
        if (!TextUtils.isEmpty(query)) {
            for (District district : districtList) {
                if (district.getName().toLowerCase().contains(query.toLowerCase())) {
                    filteredList.add(district);
                }
            }
        } else {
            filteredList.addAll(districtList); // Reset to full list if query is empty
        }
        adapter.updateList(filteredList);
    }

    // ViewHolder class for District
    public static class DistrictViewHolder extends RecyclerView.ViewHolder {
        private final TextView name;
        private final ImageView thumbnail;
        private final TextView subDescription;

        public DistrictViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.district_name_list);
            thumbnail = itemView.findViewById(R.id.district_image_list);
            subDescription=itemView.findViewById(R.id.district_sub_description_list);
            
        }

        public void bind(District district) {
            name.setText(district.getName());
            subDescription.setText(district.getSubDescription());
            // Load thumbnail image using Glide
            Glide.with(itemView.getContext())
                    .load(district.getImageUrl())
                    .placeholder(R.drawable.tempty) 
                    .into(thumbnail);
        }
    }

    // Adapter for DistrictList
    private class DistrictListAdapter extends RecyclerView.Adapter<DistrictViewHolder> {
        private List<District> districtList;

        public DistrictListAdapter(List<District> districtList) {
            this.districtList = districtList;
        }

        @NonNull
        @Override
        public DistrictViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_district_list, parent, false);
            return new DistrictViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull DistrictViewHolder holder, int position) {
            District district = districtList.get(position);
            holder.bind(district);

            // Set click listener
            holder.itemView.setOnClickListener(v -> {
                Toast.makeText(DistrictListActivity.this, district.getName(), Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(DistrictListActivity.this,DistrictDetailsActivity.class);
                intent.putExtra("district",district);
                    startActivity(intent);
           } 
            );
        }

        @Override
        public int getItemCount() {
            return districtList != null ? districtList.size() : 0;
        }

        public void updateList(List<District> newList) {
            this.districtList = newList;
            notifyDataSetChanged();
        }
    }

    
}