package mini.project.adapters;

import android.graphics.Color;
import android.graphics.fonts.Font;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.StyleRes;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import mini.project.travelguide.R;

public class ImageSliderAdapter extends RecyclerView.Adapter<ImageSliderAdapter.SliderViewHolder> {
    private final List<Integer> images;
    private final List<String> imageNames;
    private final List<String> imgDetails;
    private final List<String> imgRating;

    // Constructor to initialize the adapter with required data
    public ImageSliderAdapter(List<Integer> images, List<String> imageNames, List<String> imgDetails, List<String> imgRating) {
        this.images = images;
        this.imageNames = imageNames;
        this.imgDetails = imgDetails;
        this.imgRating = imgRating;
    }

    @NonNull
    @Override
    public SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image_layout, parent, false);
        return new SliderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SliderViewHolder holder, int position) {
        // Setting data for each item in the RecyclerView
        holder.imageView.setImageResource(images.get(position));
        // Define HSV values for transparent light black
float[] hsv = new float[]{0f, 0f, 0.2f};  // Hue = 0, Saturation = 0, Value = 0.2 for light black

// Set background color for imageName with full transparency (alpha = 0)
holder.imageName.setBackgroundColor(Color.HSVToColor(1, hsv));  // 0 for fully transparent

// Set text for imageName
holder.imageName.setText(imageNames.get(position));

// Set background color for imageRating to black
holder.imageRating.setBackgroundColor(Color.HSVToColor(1, hsv));

// Set text for imageRating
holder.imageRating.setText(imgRating.get(position));

// Set background color for imageDetails to black
holder.imageDetails.setBackgroundColor(Color.HSVToColor(1, hsv));

// Set text for imageDetails
holder.imageDetails.setText(imgDetails.get(position));
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public static class SliderViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView imageName;
        TextView imageRating;
        TextView imageDetails;

        public SliderViewHolder(@NonNull View itemView) {
            super(itemView);
            // Binding views
            imageView = itemView.findViewById(R.id.imageView);
            imageName = itemView.findViewById(R.id.titleText);
            imageRating = itemView.findViewById(R.id.ratingText);
            imageDetails = itemView.findViewById(R.id.imageDetailsText);
        }
    }
}