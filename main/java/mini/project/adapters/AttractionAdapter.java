package mini.project.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import android.widget.Toast;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import mini.project.models.Attraction;
import mini.project.models.District;
import mini.project.models.GAttraction;
import mini.project.travelguide.AttractionsList;
import mini.project.travelguide.R;


public class AttractionAdapter extends RecyclerView.Adapter<AttractionAdapter.ViewHolder> {
    private List<Attraction> attractionList;
    private List<GAttraction> gAttractionList;
    private boolean isGFilename;
    private final Context context;

    public AttractionAdapter(Context context, List<Attraction> attractionList) {
        this.context = context;
        this.attractionList = attractionList;
        this.isGFilename = false;
    }

    public AttractionAdapter(Context context, List<GAttraction> gAttractionList, Boolean isGFilename) {
        this.context = context;
        this.gAttractionList = gAttractionList;
        this.isGFilename = isGFilename;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_attraction_grid, parent, false);
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

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView attractionName, attractionRating;
        private final ImageView attractionImage;

        public ViewHolder(View itemView) {
            super(itemView);
            attractionName = itemView.findViewById(R.id.attraction_name_grid);
            attractionRating = itemView.findViewById(R.id.attraction_rating_grid);
            attractionImage = itemView.findViewById(R.id.attraction_image_grid);
            itemView.setOnClickListener(this);
        }

        public void bindAttraction(Attraction attraction) {
            attractionName.setText(attraction.getName());
             String rating=String.valueOf(attraction.getRating());
            if(rating.equals("null")||rating.isEmpty())
            rating="4.5";
            attractionRating.setText("Rating:"+rating);
            Glide.with(itemView.getContext())
                    .load(attraction.getImage())
                    .placeholder(R.drawable.tempty)
                    .error(R.drawable.tempty)
                    .into(attractionImage);
        }

        public void bindGAttraction(GAttraction gAttraction) {
            attractionName.setText(gAttraction.getTitle());
            String rating=String.valueOf(gAttraction.getTotalScore());
            if(rating.equals("null")||rating.isEmpty())
            rating="4.7";
            attractionRating.setText("Rating:"+rating);
            Glide.with(itemView.getContext())
                    .load(gAttraction.getImageUrl())
                    .placeholder(R.drawable.tempty)
                    .error(R.drawable.tempty)
                    .into(attractionImage);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                Intent intent = new Intent(context, AttractionsList.class);
                if (isGFilename) {
                    GAttraction gAttraction = gAttractionList.get(position);
                //   intent.putExtra("gAttraction", gAttraction);
                    Toast.makeText(context,gAttraction.getTitle(),Toast.LENGTH_SHORT).show();
                   
                    
                } else {
                    Attraction attraction = attractionList.get(position);
                    //intent.putExtra("attraction", attraction);
                    Toast.makeText(context,attraction.getName(),Toast.LENGTH_SHORT).show();
                }
               // context.startActivity(intent);
            }
        }

    }
    public void updateGList(List<GAttraction> newList,Boolean isGFilename) {
        this.gAttractionList = newList;
        this.isGFilename=isGFilename;
        notifyDataSetChanged();
    }
    public void updateList(List<Attraction> newList) {
        this.attractionList = newList;
        notifyDataSetChanged();
    }

}
