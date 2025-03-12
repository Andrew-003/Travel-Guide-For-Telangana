package mini.project.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Button;

import android.widget.Toast;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.Calendar;
import java.util.List;
import java.util.Random;

import mini.project.models.Restaurant;
import mini.project.models.GRestaurant;
import mini.project.travelguide.R;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.ViewHolder> {
    private List<Restaurant> restaurantsList;
    private List<GRestaurant> gRestaurantsList;
    private final boolean isGFilename;
    private final Context context;
    Calendar calendar = Calendar.getInstance();
    String timeOfDayOpen = " AM";
    String timeOpen = "";
    String timeOfDayClose = " PM";
    String timeClose = "";

    public RestaurantAdapter(Context context, List<Restaurant> restaurantsList) {
        this.context = context;
        this.restaurantsList = restaurantsList;
        this.isGFilename = false;
    }

    public RestaurantAdapter(Context context, List<GRestaurant> gRestaurantsList, boolean isGFilename) {
        this.context = context;
        this.gRestaurantsList = gRestaurantsList;
        this.isGFilename = isGFilename;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rest_grid, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (isGFilename && gRestaurantsList != null) {
            GRestaurant gRestaurant = gRestaurantsList.get(position);
            holder.bindGRestaurant(gRestaurant);
        } else if (restaurantsList != null) {
            Restaurant restaurant = restaurantsList.get(position);
            holder.bindRestaurant(restaurant);
        }
    }

    @Override
    public int getItemCount() {
        return isGFilename ? (gRestaurantsList != null ? gRestaurantsList.size() : 0) 
                           : (restaurantsList != null ? restaurantsList.size() : 0);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView restaurantName, restaurantRatingCount, restaurantRating;
        private final ImageView restaurantImage;
        private final RatingBar restaurantRatingBar;
        private final Button openingHours, closingHours;

        public ViewHolder(View itemView) {
            super(itemView);
            restaurantName = itemView.findViewById(R.id.rest_name_grid);
            restaurantRating = itemView.findViewById(R.id.rest_rating_grid);
            restaurantRatingBar = itemView.findViewById(R.id.rest_rating_bar_grid);
            restaurantRatingCount = itemView.findViewById(R.id.rest_rating_count_grid);
            restaurantImage = itemView.findViewById(R.id.rest_image_grid);
            openingHours = itemView.findViewById(R.id.rest_opening_hours_grid);
            closingHours = itemView.findViewById(R.id.rest_closing_hours_grid);
            itemView.setOnClickListener(this);
        }

        public void bindRestaurant(Restaurant restaurant) {
            if (restaurant != null) {
                restaurantName.setText(restaurant.getName() != null ? restaurant.getName() : "N/A");
                String checkRating=String.valueOf(restaurant.getRating());
                float rating = (checkRating.equals("null")||checkRating.equals("0.0"))? 4.7f: Float.valueOf(checkRating);
                restaurantRating.setText(String.valueOf(rating));
                 restaurantRatingBar.setRating(rating);

int photoCount =String.valueOf(restaurant.getPhotoCount()).equals("null") ? new Random().nextInt(23) : restaurant.getPhotoCount();
restaurantRatingCount.setText(String.valueOf(photoCount));
                int arrOpen[] = {9,10,11};
                timeOpen = arrOpen[new Random().nextInt(arrOpen.length)] + " :00";
                int arrClose[] = {6,7,8,9,10,11,12};
                timeClose = arrClose[(new Random().nextInt(arrClose.length))]+" :00";
                openingHours.setText(timeOpen+" AM");
                closingHours.setText(timeClose+" PM");
                String imageUrl = restaurant.getImage();
                if (imageUrl != null) {
                    Glide.with(itemView.getContext())
                            .load(imageUrl)
                            .placeholder(R.drawable.trestaurant)
                            .error(R.drawable.trestaurant)
                            .into(restaurantImage);
                }
            }
        }

        public void bindGRestaurant(GRestaurant gRestaurant) {
            if (gRestaurant != null) {
                restaurantName.setText(gRestaurant.getTitle() != null ? gRestaurant.getTitle() : "N/A");
                restaurantRating.setText(String.valueOf(gRestaurant.getTotalScore()));
                float rating=4.7f;
                String checkRating=String.valueOf(gRestaurant.getTotalScore());
                rating=((checkRating!=null)&&(!checkRating.isEmpty()))?(float)gRestaurant.getTotalScore():rating;
                restaurantRatingBar.setRating((float) gRestaurant.getTotalScore());
                restaurantRatingCount.setText("(" + (new Random().nextInt(456) + 37) + ")");
               int arrOpen[] = {9,10,11};
                timeOpen = arrOpen[new Random().nextInt(arrOpen.length)] + " :00";
                int arrClose[] = {6,7,8,9,10,11,12};
                timeClose = arrClose[(new Random().nextInt(arrClose.length))]+" :00";
                openingHours.setText(timeOpen+" AM");
                closingHours.setText(timeClose+" PM");
                String imageUrl = gRestaurant.getImageUrl();
                if (imageUrl != null) {
                    Glide.with(itemView.getContext())
                            .load(imageUrl)
                            .placeholder(R.drawable.trestaurant)
                            .error(R.drawable.trestaurant)
                            .into(restaurantImage);
                }
            }
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                //Intent intent = new Intent(context, DetailsActivity.class);
                if (isGFilename && gRestaurantsList != null) {
                    GRestaurant gRestaurant = gRestaurantsList.get(position);
                   // intent.putExtra("gRestaurant", gRestaurant);
                    Toast.makeText(context,gRestaurant.getTitle(),Toast.LENGTH_SHORT).show();
                } else if (restaurantsList != null) {
                    Restaurant restaurant = restaurantsList.get(position);
                    //intent.putExtra("restaurant", restaurant);
                    Toast.makeText(context,restaurant.getName(),Toast.LENGTH_SHORT).show();
                }
                //context.startActivity(intent);
            }
        }
    }
}