package mini.project.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Attraction implements Parcelable {
    private String id;
    private String category;
    private List<String> subcategories;
    private String name;
    private String locationString;
    private String description;
    private String image;
    private int rankingPosition;
    private float rating;
    private String phone;
    private String address;
    private String localName;
    private String localAddress;
    private String email;
    private double latitude;
    private double longitude;
    private String webUrl;
    private String website;
    private String rankingString;
    private List<NeighborhoodLocation> neighborhoodLocations;
    private RatingHistogram ratingHistogram;
    private int numberOfReviews;
    private List<String> photos;

    // Constructor
    public Attraction() {}

    // Parcelable Implementation
    protected Attraction(Parcel in) {
        id = in.readString();
        category = in.readString();
        subcategories = in.createStringArrayList();
        name = in.readString();
        locationString = in.readString();
        description = in.readString();
        image = in.readString();
        rankingPosition = in.readInt();
        rating = in.readFloat();
        phone = in.readString();
        address = in.readString();
        localName = in.readString();
        localAddress = in.readString();
        email = in.readString();
        latitude = in.readDouble();
        longitude = in.readDouble();
        webUrl = in.readString();
        website = in.readString();
        rankingString = in.readString();
        neighborhoodLocations = in.createTypedArrayList(NeighborhoodLocation.CREATOR);
        ratingHistogram = in.readParcelable(RatingHistogram.class.getClassLoader());
        numberOfReviews = in.readInt();
        photos = in.createStringArrayList();
    }

    public static final Creator<Attraction> CREATOR = new Creator<Attraction>() {
        @Override
        public Attraction createFromParcel(Parcel in) {
            return new Attraction(in);
        }

        @Override
        public Attraction[] newArray(int size) {
            return new Attraction[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(category);
        dest.writeStringList(subcategories);
        dest.writeString(name);
        dest.writeString(locationString);
        dest.writeString(description);
        dest.writeString(image);
        dest.writeInt(rankingPosition);
        dest.writeFloat(rating);
        dest.writeString(phone);
        dest.writeString(address);
        dest.writeString(localName);
        dest.writeString(localAddress);
        dest.writeString(email);
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
        dest.writeString(webUrl);
        dest.writeString(website);
        dest.writeString(rankingString);
        dest.writeTypedList(neighborhoodLocations);
        dest.writeParcelable(ratingHistogram, flags);
        dest.writeInt(numberOfReviews);
        dest.writeStringList(photos);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    // Getters and Setters for all fields

    // Nested NeighborhoodLocation class
    public static class NeighborhoodLocation implements Parcelable {
        private String id;
        private String name;

        public NeighborhoodLocation() {}

        protected NeighborhoodLocation(Parcel in) {
            id = in.readString();
            name = in.readString();
        }

        public static final Creator<NeighborhoodLocation> CREATOR = new Creator<NeighborhoodLocation>() {
            @Override
            public NeighborhoodLocation createFromParcel(Parcel in) {
                return new NeighborhoodLocation(in);
            }

            @Override
            public NeighborhoodLocation[] newArray(int size) {
                return new NeighborhoodLocation[size];
            }
        };

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(id);
            dest.writeString(name);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        // Getters and Setters
        public String getId() { return id; }
        public void setId(String id) { this.id = id; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
    }

    // Nested RatingHistogram class
    public static class RatingHistogram implements Parcelable {
        private int count1;
        private int count2;
        private int count3;
        private int count4;
        private int count5;

        public RatingHistogram() {}

        protected RatingHistogram(Parcel in) {
            count1 = in.readInt();
            count2 = in.readInt();
            count3 = in.readInt();
            count4 = in.readInt();
            count5 = in.readInt();
        }

        public static final Creator<RatingHistogram> CREATOR = new Creator<RatingHistogram>() {
            @Override
            public RatingHistogram createFromParcel(Parcel in) {
                return new RatingHistogram(in);
            }

            @Override
            public RatingHistogram[] newArray(int size) {
                return new RatingHistogram[size];
            }
        };

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(count1);
            dest.writeInt(count2);
            dest.writeInt(count3);
            dest.writeInt(count4);
            dest.writeInt(count5);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        // Getters and Setters
        public int getCount1() { return count1; }
        public void setCount1(int count1) { this.count1 = count1; }
        public int getCount2() { return count2; }
        public void setCount2(int count2) { this.count2 = count2; }
        public int getCount3() { return count3; }
        public void setCount3(int count3) { this.count3 = count3; }
        public int getCount4() { return count4; }
        public void setCount4(int count4) { this.count4 = count4; }
        public int getCount5() { return count5; }
        public void setCount5(int count5) { this.count5 = count5; }
    }
    // Getters and Setters for Attraction Class
public String getId() {
    return id;
}

public void setId(String id) {
    this.id = id;
}

public String getCategory() {
    return category;
}

public void setCategory(String category) {
    this.category = category;
}

public List<String> getSubcategories() {
    return subcategories;
}

public void setSubcategories(List<String> subcategories) {
    this.subcategories = subcategories;
}

public String getName() {
    return name;
}

public void setName(String name) {
    this.name = name;
}

public String getLocationString() {
    return locationString;
}

public void setLocationString(String locationString) {
    this.locationString = locationString;
}

public String getDescription() {
    return description;
}

public void setDescription(String description) {
    this.description = description;
}

public String getImage() {
    return image;
}

public void setImage(String image) {
    this.image = image;
}

public int getRankingPosition() {
    return rankingPosition;
}

public void setRankingPosition(int rankingPosition) {
    this.rankingPosition = rankingPosition;
}

public float getRating() {
    return rating;
}

public void setRating(float rating) {
    this.rating = rating;
}

public String getPhone() {
    return phone;
}

public void setPhone(String phone) {
    this.phone = phone;
}

public String getAddress() {
    return address;
}

public void setAddress(String address) {
    this.address = address;
}

public String getLocalName() {
    return localName;
}

public void setLocalName(String localName) {
    this.localName = localName;
}

public String getLocalAddress() {
    return localAddress;
}

public void setLocalAddress(String localAddress) {
    this.localAddress = localAddress;
}

public String getEmail() {
    return email;
}

public void setEmail(String email) {
    this.email = email;
}

public double getLatitude() {
    return latitude;
}

public void setLatitude(double latitude) {
    this.latitude = latitude;
}

public double getLongitude() {
    return longitude;
}

public void setLongitude(double longitude) {
    this.longitude = longitude;
}

public String getWebUrl() {
    return webUrl;
}

public void setWebUrl(String webUrl) {
    this.webUrl = webUrl;
}

public String getWebsite() {
    return website;
}

public void setWebsite(String website) {
    this.website = website;
}

public String getRankingString() {
    return rankingString;
}

public void setRankingString(String rankingString) {
    this.rankingString = rankingString;
}

public List<NeighborhoodLocation> getNeighborhoodLocations() {
    return neighborhoodLocations;
}

public void setNeighborhoodLocations(List<NeighborhoodLocation> neighborhoodLocations) {
    this.neighborhoodLocations = neighborhoodLocations;
}

public RatingHistogram getRatingHistogram() {
    return ratingHistogram;
}

public void setRatingHistogram(RatingHistogram ratingHistogram) {
    this.ratingHistogram = ratingHistogram;
}

public int getNumberOfReviews() {
    return numberOfReviews;
}

public void setNumberOfReviews(int numberOfReviews) {
    this.numberOfReviews = numberOfReviews;
}

public List<String> getPhotos() {
    return photos;
}

public void setPhotos(List<String> photos) {
    this.photos = photos;
}

}