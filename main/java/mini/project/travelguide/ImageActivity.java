package mini.project.travelguide;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import android.content.Intent;
import android.net.Uri;



public class ImageActivity extends AppCompatActivity {
    private List<String> imageurls=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_image);
        RecyclerView rv=findViewById(R.id.detail_view_images);
        if(!getIntent().getStringArrayListExtra("imageurls").isEmpty()){
            imageurls=getIntent().getStringArrayListExtra("imageurls");
            ImageAdapter adapter=new ImageAdapter(this,imageurls);
            rv.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
            rv.setAdapter(adapter);
        }else{
            String url=getIntent().getStringExtra("webUrl");
                Intent intent = new Intent(Intent.ACTION_VIEW);
    intent.setData(Uri.parse(url));
    if (intent.resolveActivity(getPackageManager()) != null) {
        startActivity(intent);
    } else {
        // Handle the case where no browser is available
        Toast.makeText(this, "No application can handle this request.", Toast.LENGTH_SHORT).show();
    }
} 
    

            
        
        
        
    }
    public  class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {
    private final List<String> imageUrls;
    private final Context context;

    // Constructor
    public ImageAdapter(Context context, List<String> imageUrls) {
        this.context = context;
        this.imageUrls = new ArrayList<>(imageUrls);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_image, parent, false); // Make sure to use a layout with just an ImageView
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String imageUrl = imageUrls.get(position);
        holder.bindImage(imageUrl);
    }

    @Override
    public int getItemCount() {
        return imageUrls.size();
    }

    // ViewHolder Class
    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageViewGalleryItem); // ImageView ID in item_image layout
        }

        public void bindImage(String imageUrl) {
            Glide.with(context)
                    .load(imageUrl)
                    .placeholder(R.drawable.tgarden) 
                    .error(R.drawable.tpark) 
                    .into(imageView);
        }
    }
}
    }
    
           