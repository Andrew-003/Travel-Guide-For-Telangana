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

import java.util.ArrayList;
import java.util.List;

import mini.project.models.Attraction;
import mini.project.travelguide.R;
import mini.project.helpers.JsonParserHelper;
import mini.project.travelguide.AttractionDetails;
//import mini.project.activities.AttractionDetailsActivity;

public class AttractionFragment extends Fragment {

    private RecyclerView recyclerView;
    private TextView headingText;
    private List<Attraction> attractionList;
    private final String topplaces="topplaces";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_top_visit_main, container, false);

        recyclerView = view.findViewById(R.id.place_list_recyclerview);
        headingText=view.findViewById(R.id.headingText);
        headingText.setText(((CharSequence)"Best Tourist Places to Visit>"));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Parse JSON to get the list of attractions
        attractionList = JsonParserHelper.parseLargeJson(getContext(), "top_attractions.json",  Attraction.class);

        // Limit the list to a maximum of 5 items
        if (attractionList != null && attractionList.size() > 5) {
            attractionList = new ArrayList<>(attractionList.subList(0, 5));
        }

        // Set inline adapter
        recyclerView.setAdapter(new RecyclerView.Adapter<RecyclerView.ViewHolder>() {

            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_top_visit_place_main, parent, false);
                return new AttractionViewHolder(itemView);
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
                Attraction attraction = attractionList.get(position);
                ((AttractionViewHolder) holder).bind(attraction);
                // Set click listener for item
                holder.itemView.setOnClickListener(v -> {
                            Toast.makeText(getContext(),attraction.getName(),Toast.LENGTH_SHORT).show();
                    // Handle item click, open details activity
                   Intent intent = new Intent(getContext(), AttractionDetails.class);
                    intent.putExtra("attraction", attraction);
                    intent.putExtra("district",topplaces);
                    startActivity(intent);
                });
            }

            @Override
            public int getItemCount() {
                return attractionList != null ? attractionList.size() : 0;
            }
        });

        return view;
    }

    // ViewHolder class for attractions
    public static class AttractionViewHolder extends RecyclerView.ViewHolder {
        private TextView name, rating;
        private ImageView image;

        public AttractionViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.place_name);
            rating = itemView.findViewById(R.id.place_rating);
            image = itemView.findViewById(R.id.place_image);
        }

        public void bind(Attraction attraction) {
            name.setText(attraction.getName());
            rating.setText("Rating : "+String.valueOf(attraction.getRating()));

            Glide.with(itemView.getContext())
                    .load(attraction.getImage())
                    .placeholder(R.drawable.tempty)
                    .into(image);
        }

    }
}