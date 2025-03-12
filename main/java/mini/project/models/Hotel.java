package mini.project.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Hotel implements Parcelable {

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
    private RatingHistogram ratingHistogram;
    private int numberOfReviews;
    private String hotelClass;
    private List<String> amenities;
    private String priceRange;
    private String checkInDate;
    private String checkOutDate;
    private String numberOfRooms;
    private List<CategoryReviewScore> categoryReviewScores;
    private List<String> photos;

    // Constructors
    public Hotel() {}

    protected Hotel(Parcel in) {
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
        ratingHistogram = in.readParcelable(RatingHistogram.class.getClassLoader());
        numberOfReviews = in.readInt();
        hotelClass = in.readString();
        amenities = in.createStringArrayList();
        priceRange = in.readString();
        checkInDate = in.readString();
        checkOutDate = in.readString();
        numberOfRooms = in.readString();
        categoryReviewScores = in.createTypedArrayList(CategoryReviewScore.CREATOR);
        photos = in.createStringArrayList();
    }

    // Parcelable methods
    @Override
    public int describeContents() {
        return 0;
    }

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
        dest.writeParcelable(ratingHistogram, flags);
        dest.writeInt(numberOfReviews);
        dest.writeString(hotelClass);
        dest.writeStringList(amenities);
        dest.writeString(priceRange);
        dest.writeString(checkInDate);
        dest.writeString(checkOutDate);
        dest.writeString(numberOfRooms);
        dest.writeTypedList(categoryReviewScores);
        dest.writeStringList(photos);
    }

    public static final Creator<Hotel> CREATOR = new Creator<Hotel>() {
        @Override
        public Hotel createFromParcel(Parcel in) {
            return new Hotel(in);
        }

        @Override
        public Hotel[] newArray(int size) {
            return new Hotel[size];
        }
    };

    // Getters and Setters
    // ... Add getters and setters for all properties
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

public RatingHistogram getRatingHistogram() {
    return ratingHistogram;
}
    public int getNumberOfReviews() {
    return numberOfReviews;
}

public void setNumberOfReviews(int numberOfReviews) {
    this.numberOfReviews = numberOfReviews;
}

public String getHotelClass() {
    return hotelClass;
}

public void setHotelClass(String hotelClass) {
    this.hotelClass = hotelClass;
}

public List<String> getAmenities() {
    return amenities;
}

public void setAmenities(List<String> amenities) {
    this.amenities = amenities;
}

public String getPriceRange() {
    return priceRange;
}

public void setPriceRange(String priceRange) {
    this.priceRange = priceRange;
}

public String getCheckInDate() {
    return checkInDate;
}

public void setCheckInDate(String checkInDate) {
    this.checkInDate = checkInDate;
}

public String getCheckOutDate() {
    return checkOutDate;
}

public void setCheckOutDate(String checkOutDate) {
    this.checkOutDate = checkOutDate;
}

public String getNumberOfRooms() {
    return numberOfRooms;
}

public void setNumberOfRooms(String numberOfRooms) {
    this.numberOfRooms = numberOfRooms;
}
    public void setRatingHistogram(RatingHistogram ratingHistogram) {
    this.ratingHistogram = ratingHistogram;
}



public List<CategoryReviewScore> getCategoryReviewScores() {
    return categoryReviewScores;
}

public void setCategoryReviewScores(List<CategoryReviewScore> categoryReviewScores) {
    this.categoryReviewScores = categoryReviewScores;
}
public List<String> getPhotos() {
    return photos;
}

public void setPhotos(List<String> photos) {
    this.photos = photos;
}

    // Nested RatingHistogram class
    public static class RatingHistogram implements Parcelable {
        private int oneStar;
        private int twoStars;
        private int threeStars;
        private int fourStars;
        private int fiveStars;

        // Constructors
        public RatingHistogram() {}

        protected RatingHistogram(Parcel in) {
            oneStar = in.readInt();
            twoStars = in.readInt();
            threeStars = in.readInt();
            fourStars = in.readInt();
            fiveStars = in.readInt();
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(oneStar);
            dest.writeInt(twoStars);
            dest.writeInt(threeStars);
            dest.writeInt(fourStars);
            dest.writeInt(fiveStars);
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

        // Getters and Setters
        // ... Add getters and setters for all properties
        public int getOneStar() {
    return oneStar;
}

public void setOneStar(int oneStar) {
    this.oneStar = oneStar;
}

public int getTwoStars() {
    return twoStars;
}

public void setTwoStars(int twoStars) {
    this.twoStars = twoStars;
}

public int getThreeStars() {
    return threeStars;
}

public void setThreeStars(int threeStars) {
    this.threeStars = threeStars;
}

public int getFourStars() {
    return fourStars;
}

public void setFourStars(int fourStars) {
    this.fourStars = fourStars;
}

public int getFiveStars() {
    return fiveStars;
}

public void setFiveStars(int fiveStars) {
    this.fiveStars = fiveStars;
}
    }

    // Nested CategoryReviewScore class
    public static class CategoryReviewScore implements Parcelable {
        private String categoryName;
        private double score;

        // Constructors
        public CategoryReviewScore() {}

        protected CategoryReviewScore(Parcel in) {
            categoryName = in.readString();
            score = in.readDouble();
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(categoryName);
            dest.writeDouble(score);
        }

        public static final Creator<CategoryReviewScore> CREATOR = new Creator<CategoryReviewScore>() {
            @Override
            public CategoryReviewScore createFromParcel(Parcel in) {
                return new CategoryReviewScore(in);
            }

            @Override
            public CategoryReviewScore[] newArray(int size) {
                return new CategoryReviewScore[size];
            }
        };

        // Getters and Setters
        // ... Add getters and setters for all properties
        public String getCategoryName() {
    return categoryName;
}

public void setCategoryName(String categoryName) {
    this.categoryName = categoryName;
}

public double getScore() {
    return score;
}

public void setScore(double score) {
    this.score = score;
}
                

    }
}