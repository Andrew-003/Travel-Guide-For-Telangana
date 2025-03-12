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

import mini.project.models.District;
import mini.project.travelguide.DistrictDetailsActivity;
import mini.project.travelguide.R;


public class TopDestinationAdapter extends RecyclerView.Adapter<TopDestinationAdapter.ViewHolder> {
    private List<District> districtList;
    private final Context context;

    public TopDestinationAdapter(Context context, List<District> districtList) {
        this.context = context;
        this.districtList = districtList;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_district_grid, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        District district = districtList.get(position);
        holder.bind(district);
    }

    @Override
    public int getItemCount() {
        return districtList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView districtName;
        private final ImageView districtImage;

        public ViewHolder(View itemView) {
            super(itemView);
            districtName = itemView.findViewById(R.id.district_name_grid);
            districtImage = itemView.findViewById(R.id.district_image_grid);
            itemView.setOnClickListener(this);
        }

        public void bind(District district) {
            districtName.setText(district.getName());
            Glide.with(itemView.getContext()).load(district.getImageUrl()).into(districtImage);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                String name=districtList.get(position).getName()+"is clicked";
                Toast.makeText(context, name, Toast.LENGTH_SHORT).show();
               Intent intent = new Intent(context, DistrictDetailsActivity.class);
                intent.putExtra("district",  districtList.get(position));
                context.startActivity(intent);
            }
        }
    }
}
