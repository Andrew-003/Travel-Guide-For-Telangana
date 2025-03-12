package mini.project.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;


import mini.project.models.Restaurant;
import mini.project.travelguide.R;
import mini.project.helpers.JsonParserHelper;
import mini.project.travelguide.RestaurantDetails;
//import mini.project.activities.AttractionDetailsActivity;

public class RestaurantFragment extends Fragment {
    private TextView headingText;
    private RecyclerView recyclerView;
    private List<Restaurant> restaurantList;
    private final String topplaces="topplaces";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_top_visit_main, container, false);

        recyclerView = view.findViewById(R.id.place_list_recyclerview);
        headingText=view.findViewById(R.id.headingText);
        headingText.setText(((CharSequence)"Best Restaurants to Dine In"));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Parse JSON to get the list of restaurants
        restaurantList = JsonParserHelper.parseLargeJson(getContext(), "top_restaurants.json",Restaurant.class);

        // Set inline adapter
        recyclerView.setAdapter(new RecyclerView.Adapter<RecyclerView.ViewHolder>() {

            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_top_visit_place_main, parent, false);
                return new RestaurantViewHolder(itemView);
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
                Restaurant restaurant = restaurantList.get(position);
                ((RestaurantViewHolder) holder).bind(restaurant);

                // Set click listener for item
                holder.itemView.setOnClickListener(v -> {
                    // Handle item click, open details activity
                   Toast.makeText(getContext(),restaurant.getName(),Toast.LENGTH_SHORT).show();
                      Intent intent = new Intent(getContext(), RestaurantDetails.class);
                    intent.putExtra("restaurant", restaurant);
                    intent.putExtra("district",topplaces);
                    startActivity(intent);
                });
            }

            @Override
            public int getItemCount() {
                return restaurantList.size();
            }
        });

        return view;
    }

    // ViewHolder class for restaurants
    public static class RestaurantViewHolder extends RecyclerView.ViewHolder {
        private TextView name, rating;
        private ImageView image;

        public RestaurantViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.place_name);
            rating = itemView.findViewById(R.id.place_rating);
            image = itemView.findViewById(R.id.place_image);
        }

        public void bind(Restaurant restaurant) {
            name.setText(restaurant.getName());
            rating.setText("Rating : "+String.valueOf(restaurant.getRating()));
            Glide.with(itemView.getContext())
                    .load(restaurant.getImage())
                    .placeholder(R.drawable.tempty)
                    .into(image);
        }
    }
}