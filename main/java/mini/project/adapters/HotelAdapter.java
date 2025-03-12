package mini.project.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import mini.project.models.Hotel;
import mini.project.models.GHotel;
import mini.project.travelguide.R;


public class HotelAdapter extends RecyclerView.Adapter<HotelAdapter.ViewHolder> {
    private List<Hotel> hotelsList;
    private List<GHotel> gHotelsList;
    private final boolean isGFilename;
    private final Context context;

    public HotelAdapter(Context context, List<Hotel> hotelsList) {
        this.context = context;
        this.hotelsList = hotelsList;
        this.isGFilename = false;
    }

    public HotelAdapter(Context context, List<GHotel> gHotelsList, Boolean isGFilename) {
        this.context = context;
        this.gHotelsList = gHotelsList;
        this.isGFilename = isGFilename;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hotel_grid, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (isGFilename) {
            GHotel gHotel = gHotelsList.get(position);
            holder.bindGHotel(gHotel);
        } else {
            Hotel hotel = hotelsList.get(position);
            holder.bindHotel(hotel);
        }
    }

    @Override
    public int getItemCount() {
        return isGFilename ? gHotelsList.size() : hotelsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView hotelName, hotelRating;
        private final ImageView hotelImage;
        private final RatingBar hotelRatingBar;


        public ViewHolder(View itemView) {
            super(itemView);
            hotelName = itemView.findViewById(R.id.hotel_name_grid);
            hotelRating = itemView.findViewById(R.id.hotel_rating_grid);
            hotelImage = itemView.findViewById(R.id.hotel_image_grid);
            hotelRatingBar=itemView.findViewById(R.id.hotel_rating_bar_grid);
            itemView.setOnClickListener(this);
        }

        public void bindHotel(Hotel hotel) {
            hotelName.setText(hotel.getName());
            hotelRating.setText(String.valueOf(hotel.getRating()));
            hotelRatingBar.setRating(hotel.getRating());
            Glide.with(itemView.getContext())
                    .load(hotel.getImage())
                    .placeholder(R.drawable.thotel)
                    .error(R.drawable.thotel)
                    .into(hotelImage);
        }

        public void bindGHotel(GHotel gHotel) {
            hotelName.setText(gHotel.getTitle());
            hotelRating.setText(String.valueOf(gHotel.getTotalScore()));
            hotelRatingBar.setRating((float)gHotel.getTotalScore());
            Glide.with(itemView.getContext())
                    .load(gHotel.getImageUrl())
                    .placeholder(R.drawable.thotel)
                    .error(R.drawable.thotel)
                    .into(hotelImage);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
               // Intent intent = new Intent(context, DetailsActivity.class);
                if (isGFilename) {
                    GHotel gHotel = gHotelsList.get(position);
                   // intent.putExtra("gHotel", gHotel);
                    Toast.makeText(context,gHotel.getTitle(),Toast.LENGTH_SHORT).show();
                } else {
                    Hotel hotel = hotelsList.get(position);
                   // intent.putExtra("hotel", hotel);
                    Toast.makeText(context,hotel.getName(),Toast.LENGTH_SHORT).show();
                }
               // context.startActivity(intent);
            }
        }
    }
}
