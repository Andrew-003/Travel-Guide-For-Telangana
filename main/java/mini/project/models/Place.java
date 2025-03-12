package mini.project.models;

public class Place {
    public static final int TYPE_HOTEL = 0;
    public static final int TYPE_RESTAURANT = 1;
    public static final int TYPE_ATTRACTION = 2;

    private int type;
    private String name;
    private String rating; 
    private String imageUrl;

    public Place(int type, String name, String rating, String imageUrl) {
        this.type = type;
        this.name = name;
        this.rating = rating;
        this.imageUrl = imageUrl;
    }

    public int getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getRating() {
        return rating;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}