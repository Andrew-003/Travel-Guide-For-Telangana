package mini.project.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class GRestaurant implements Parcelable {
    private int rank;
    private String searchPageUrl;
    private String title;
    private String subTitle;
    private String description;
    private String price;
    private String address;
    private String postalCode;
    private String website;
    private String phone;
    private Location location;
    private String menu;
    private double totalScore;
    private boolean temporarilyClosed;
    private List<String> categories;
    private int reviewsCount;
    private ReviewsDistribution reviewsDistribution;
    private int imagesCount;
    
    private String imageUrl;
    private String similarHotelsNearby;
    private String hotelReviewSummary;
    private String popularTimesLiveText;

    // Default constructor
    public GRestaurant() {}

    protected GRestaurant(Parcel in) {
        rank = in.readInt();
        searchPageUrl = in.readString();
        title = in.readString();
        subTitle = in.readString();
        imageUrl=in.readString();
        description = in.readString();
        price = in.readString();
        address = in.readString();
        postalCode = in.readString();
        website = in.readString();
        phone = in.readString();
        location = in.readParcelable(Location.class.getClassLoader());
        menu = in.readString();
        totalScore = in.readDouble();
        temporarilyClosed = in.readByte() != 0;
        categories = in.createStringArrayList();
        reviewsCount = in.readInt();
        reviewsDistribution = in.readParcelable(ReviewsDistribution.class.getClassLoader());
        
        imagesCount = in.readInt();
        similarHotelsNearby = in.readString();
        hotelReviewSummary = in.readString();
        popularTimesLiveText = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(rank);
        dest.writeString(searchPageUrl);
        dest.writeString(title);
        dest.writeString(subTitle);
        dest.writeString(imageUrl);
        dest.writeString(description);
        dest.writeString(price);
        dest.writeString(address);
        dest.writeString(postalCode);
        dest.writeString(website);
        dest.writeString(phone);
        dest.writeParcelable(location, flags);
        dest.writeString(menu);
        dest.writeDouble(totalScore);
        dest.writeByte((byte) (temporarilyClosed ? 1 : 0));
        dest.writeStringList(categories);
        dest.writeInt(reviewsCount);
        dest.writeParcelable(reviewsDistribution, flags);
        
        dest.writeInt(imagesCount);
        dest.writeString(similarHotelsNearby);
        dest.writeString(hotelReviewSummary);
        dest.writeString(popularTimesLiveText);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<GRestaurant> CREATOR = new Creator<GRestaurant>() {
        @Override
        public GRestaurant createFromParcel(Parcel in) {
            return new GRestaurant(in);
        }

        @Override
        public GRestaurant[] newArray(int size) {
            return new GRestaurant[size];
        }
    };

    // Getters and setters
    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getSearchPageUrl() {
        return searchPageUrl;
    }

    public void setSearchPageUrl(String searchPageUrl) {
        this.searchPageUrl = searchPageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public String getImageUrl() {
        return imageUrl;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public double getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(double totalScore) {
        this.totalScore = totalScore;
    }

    public boolean isTemporarilyClosed() {
        return temporarilyClosed;
    }

    public void setTemporarilyClosed(boolean temporarilyClosed) {
        this.temporarilyClosed = temporarilyClosed;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public int getReviewsCount() {
        return reviewsCount;
    }

    public void setReviewsCount(int reviewsCount) {
        this.reviewsCount = reviewsCount;
    }

    public ReviewsDistribution getReviewsDistribution() {
        return reviewsDistribution;
    }

    public void setReviewsDistribution(ReviewsDistribution reviewsDistribution) {
        this.reviewsDistribution = reviewsDistribution;
    }

    public int getImagesCount() {
        return imagesCount;
    }

    public void setImagesCount(int imagesCount) {
        this.imagesCount = imagesCount;
    }

    public String getSimilarHotelsNearby() {
        return similarHotelsNearby;
    }

    public void setSimilarHotelsNearby(String similarHotelsNearby) {
        this.similarHotelsNearby = similarHotelsNearby;
    }

    public String getHotelReviewSummary() {
        return hotelReviewSummary;
    }

    public void setHotelReviewSummary(String hotelReviewSummary) {
        this.hotelReviewSummary = hotelReviewSummary;
    }

    public String getPopularTimesLiveText() {
        return popularTimesLiveText;
    }

    public void setPopularTimesLiveText(String popularTimesLiveText) {
        this.popularTimesLiveText = popularTimesLiveText;
    }

    // Nested Parcelable Location class
    public static class Location implements Parcelable {
        private double latitude;
        private double longitude;

        public Location() {}

        protected Location(Parcel in) {
            latitude = in.readDouble();
            longitude = in.readDouble();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeDouble(latitude);
            dest.writeDouble(longitude);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<Location> CREATOR = new Creator<Location>() {
            @Override
            public Location createFromParcel(Parcel in) {
                return new Location(in);
            }

            @Override
            public Location[] newArray(int size) {
                return new Location[size];
            }
        };

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
    }

    // Nested Parcelable ReviewsDistribution class
    public static class ReviewsDistribution implements Parcelable {
        private int fiveStars;
        private int fourStars;
        private int threeStars;
        private int twoStars;
        private int oneStar;

        public ReviewsDistribution() {}

        protected ReviewsDistribution(Parcel in) {
            fiveStars = in.readInt();
            fourStars = in.readInt();
            threeStars = in.readInt();
            twoStars = in.readInt();
            oneStar = in.readInt();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(fiveStars);
            dest.writeInt(fourStars);
            dest.writeInt(threeStars);
            dest.writeInt(twoStars);
            dest.writeInt(oneStar);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<ReviewsDistribution> CREATOR = new Creator<ReviewsDistribution>() {
            @Override
            public ReviewsDistribution createFromParcel(Parcel in) {
                return new ReviewsDistribution(in);
            }

            @Override
            public ReviewsDistribution[] newArray(int size) {
                return new ReviewsDistribution[size];
            }
        };

        public int getFiveStars() {
            return fiveStars;
        }

        public void setFiveStars(int fiveStars) {
            this.fiveStars = fiveStars;
        }

        public int getFourStars() {
            return fourStars;
        }

        public void setFourStars(int fourStars) {
            this.fourStars = fourStars;
        }

        public int getThreeStars() {
            return threeStars;
        }

        public void setThreeStars(int threeStars) {
            this.threeStars = threeStars;
        }

        public int getTwoStars() {
            return twoStars;
        }

        public void setTwoStars(int twoStars) {
            this.twoStars = twoStars;
        }

        public int getOneStar() {
            return oneStar;
        }

        public void setOneStar(int oneStar) {
            this.oneStar = oneStar;
        }
    }
    


        
}
