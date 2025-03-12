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

import mini.project.models.Attraction;
import mini.project.models.Hotel;
import mini.project.travelguide.HotelDetails;
import mini.project.travelguide.R;
import mini.project.helpers.JsonParserHelper;

public class HotelFragment extends Fragment {
   private TextView headingText;
    private RecyclerView recyclerView;
    private List<Hotel> hotelList;
    private final String topplaces="topplaces";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_top_visit_main, container, false);

        recyclerView = view.findViewById(R.id.place_list_recyclerview);
        headingText=view.findViewById(R.id.headingText);
        headingText.setText(((CharSequence) "Best Hotels to Stay In >"));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Parse JSON to get the list of hotels
        hotelList = JsonParserHelper.parseLargeJson(getContext(), "top_hotels.json",Hotel.class);

        // Set inline adapter
        recyclerView.setAdapter(new RecyclerView.Adapter<RecyclerView.ViewHolder>() {

            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_top_visit_place_main, parent, false);
                return new HotelViewHolder(itemView);
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
                Hotel hotel = hotelList.get(position);

                // Bind data to views
                ((HotelViewHolder) holder).bind(hotel);

                // Set click listener for item
                holder.itemView.setOnClickListener(v -> {
                    // Handle item click, open details activity
                    Toast.makeText(getContext(),hotel.getName(),Toast.LENGTH_SHORT).show();
                   Intent intent = new Intent(getContext(), HotelDetails.class);
                    intent.putExtra("hotel", hotel);
                    intent.putExtra("district",topplaces);
                    startActivity(intent);
                });
            }

            @Override
            public int getItemCount() {
                return hotelList.size();
            }
        });

        return view;
    }

    // ViewHolder class for hotels
    public static class HotelViewHolder extends RecyclerView.ViewHolder {
        private TextView name, rating;
        private ImageView image;

        public HotelViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.place_name);
            rating = itemView.findViewById(R.id.place_rating);
            image = itemView.findViewById(R.id.place_image);
        }

        public void bind(Hotel hotel) {
            name.setText(hotel.getName());
            rating.setText("Rating : "+String.valueOf(hotel.getRating()));
            Glide.with(itemView.getContext())
                    .load(hotel.getImage())
                    .placeholder(R.drawable.tempty)
                    .into(image);
        }
    }
}