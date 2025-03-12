package mini.project.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class GHotel implements Parcelable {

    private int rank;
    private String searchPageUrl;
    private String title;
    private String subTitle;
    private String categoryName;
    private String address;
    private String postalCode;
    private String phone;
    private Location location;
    private Object menu;
    private double totalScore;
    private boolean temporarilyClosed;
    private ReviewsDistribution reviewsDistribution;
    private String hotelStars;
    private Object hotelDescription;
    private List<SimilarHotel> similarHotelsNearby;
    private AdditionalInfo additionalInfo;
    private String url;
    private String imageUrl;
    private List<Image> images;
    private List<String> imageUrls;
    private List<Review> reviews;

    public GHotel() {
    }

    protected GHotel(Parcel in) {
        rank = in.readInt();
        searchPageUrl = in.readString();
        title = in.readString();
        subTitle = in.readString();
        categoryName = in.readString();
        address = in.readString();
        postalCode = in.readString();
        phone = in.readString();
        location = in.readParcelable(Location.class.getClassLoader());
        menu = in.readValue(Object.class.getClassLoader());
        totalScore = in.readDouble();
        temporarilyClosed = in.readByte() != 0;
        reviewsDistribution = in.readParcelable(ReviewsDistribution.class.getClassLoader());
        hotelStars = in.readString();
        hotelDescription = in.readValue(Object.class.getClassLoader());
        similarHotelsNearby = in.createTypedArrayList(SimilarHotel.CREATOR);
        additionalInfo = in.readParcelable(AdditionalInfo.class.getClassLoader());
        url = in.readString();
        imageUrl = in.readString();
        images = in.createTypedArrayList(Image.CREATOR);
        imageUrls = in.createStringArrayList();
        reviews = in.createTypedArrayList(Review.CREATOR);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(rank);
        dest.writeString(searchPageUrl);
        dest.writeString(title);
        dest.writeString(subTitle);
        dest.writeString(categoryName);
        dest.writeString(address);
        dest.writeString(postalCode);
        dest.writeString(phone);
        dest.writeParcelable(location, flags);
        dest.writeValue(menu);
        dest.writeDouble(totalScore);
        dest.writeByte((byte) (temporarilyClosed ? 1 : 0));
        dest.writeParcelable(reviewsDistribution, flags);
        dest.writeString(hotelStars);
        dest.writeValue(hotelDescription);
        dest.writeTypedList(similarHotelsNearby);
        dest.writeParcelable(additionalInfo, flags);
        dest.writeString(url);
        dest.writeString(imageUrl);
        dest.writeTypedList(images);
        dest.writeStringList(imageUrls);
        dest.writeTypedList(reviews);
    }

    public static final Creator<GHotel> CREATOR = new Creator<GHotel>() {
        @Override
        public GHotel createFromParcel(Parcel in) {
            return new GHotel(in);
        }

        @Override
        public GHotel[] newArray(int size) {
            return new GHotel[size];
        }
    };

    // Getters and Setters
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

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
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

    public Object getMenu() {
        return menu;
    }

    public void setMenu(Object menu) {
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

    public ReviewsDistribution getReviewsDistribution() {
        return reviewsDistribution;
    }

    public void setReviewsDistribution(ReviewsDistribution reviewsDistribution) {
        this.reviewsDistribution = reviewsDistribution;
    }

    public String getHotelStars() {
        return hotelStars;
    }

    public void setHotelStars(String hotelStars) {
        this.hotelStars = hotelStars;
    }

    public Object getHotelDescription() {
        return hotelDescription;
    }

    public void setHotelDescription(Object hotelDescription) {
        this.hotelDescription = hotelDescription;
    }

    public List<SimilarHotel> getSimilarHotelsNearby() {
        return similarHotelsNearby;
    }

    public void setSimilarHotelsNearby(List<SimilarHotel> similarHotelsNearby) {
        this.similarHotelsNearby = similarHotelsNearby;
    }

    public AdditionalInfo getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(AdditionalInfo additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    // Nested Parcelable classes

    public static class Location implements Parcelable {
        private double lat;
        private double lng;

        protected Location(Parcel in) {
            lat = in.readDouble();
            lng = in.readDouble();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeDouble(lat);
            dest.writeDouble(lng);
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

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public double getLng() {
            return lng;
        }

        public void setLng(double lng) {
            this.lng = lng;
        }
    }

    public static class ReviewsDistribution implements Parcelable {
        private int oneStar;
        private int twoStar;
        private int threeStar;
        private int fourStar;
        private int fiveStar;

        protected ReviewsDistribution(Parcel in) {
            oneStar = in.readInt();
            twoStar = in.readInt();
            threeStar = in.readInt();
            fourStar = in.readInt();
            fiveStar = in.readInt();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(oneStar);
            dest.writeInt(twoStar);
            dest.writeInt(threeStar);
            dest.writeInt(fourStar);
            dest.writeInt(fiveStar);
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

        public int getOneStar() {
            return oneStar;
        }

        public void setOneStar(int oneStar) {
            this.oneStar = oneStar;
        }

        public void setTwoStar(int twoStar) {
            this.twoStar = twoStar;
        }

        public int getThreeStar() {
            return threeStar;
        }

        public void setThreeStar(int threeStar) {
            this.threeStar = threeStar;
        }

        public int getFourStar() {
            return fourStar;
        }

        public void setFourStar(int fourStar) {
            this.fourStar = fourStar;
        }

        public int getFiveStar() {
            return fiveStar;
        }

        public void setFiveStar(int fiveStar) {
            this.fiveStar = fiveStar;
        }
    }

    public static class SimilarHotel implements Parcelable {
        private String title;
        private String address;
        private String imageUrl;

        protected SimilarHotel(Parcel in) {
            title = in.readString();
            address = in.readString();
            imageUrl = in.readString();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(title);
            dest.writeString(address);
            dest.writeString(imageUrl);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<SimilarHotel> CREATOR = new Creator<SimilarHotel>() {
            @Override
            public SimilarHotel createFromParcel(Parcel in) {
                return new SimilarHotel(in);
            }

            @Override
            public SimilarHotel[] newArray(int size) {
                return new SimilarHotel[size];
            }
        };

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }
    }

    public static class AdditionalInfo implements Parcelable {
        private String checkInTime;
        private String checkOutTime;
        private String cancellationPolicy;

        protected AdditionalInfo(Parcel in) {
            checkInTime = in.readString();
            checkOutTime = in.readString();
            cancellationPolicy = in.readString();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(checkInTime);
            dest.writeString(checkOutTime);
            dest.writeString(cancellationPolicy);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<AdditionalInfo> CREATOR = new Creator<AdditionalInfo>() {
            @Override
            public AdditionalInfo createFromParcel(Parcel in) {
                return new AdditionalInfo(in);
            }

            @Override
            public AdditionalInfo[] newArray(int size) {
                return new AdditionalInfo[size];
            }
        };

        public String getCheckInTime() {
            return checkInTime;
        }

        public void setCheckInTime(String checkInTime) {
            this.checkInTime = checkInTime;
        }

        public String getCheckOutTime() {
            return checkOutTime;
        }

        public void setCheckOutTime(String checkOutTime) {
            this.checkOutTime = checkOutTime;
        }

        public String getCancellationPolicy() {
            return cancellationPolicy;
        }

        public void setCancellationPolicy(String cancellationPolicy) {
            this.cancellationPolicy = cancellationPolicy;
        }
    }

    public static class Image implements Parcelable {
        private String imageUrl;
        private String caption;

        protected Image(Parcel in) {
            imageUrl = in.readString();
            caption = in.readString();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(imageUrl);
            dest.writeString(caption);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<Image> CREATOR = new Creator<Image>() {
            @Override
            public Image createFromParcel(Parcel in) {
                return new Image(in);
            }

            @Override
            public Image[] newArray(int size) {
                return new Image[size];
            }
        };

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public String getCaption() {
            return caption;
        }

        public void setCaption(String caption) {
            this.caption = caption;
        }
    }

    public static class Review implements Parcelable {
        private String reviewerName;
        private String reviewText;
        private double rating;
        private String reviewDate;

        protected Review(Parcel in) {
            reviewerName = in.readString();
            reviewText = in.readString();
            rating = in.readDouble();
            reviewDate = in.readString();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(reviewerName);
            dest.writeString(reviewText);
            dest.writeDouble(rating);
            dest.writeString(reviewDate);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<Review> CREATOR = new Creator<Review>() {
            @Override
            public Review createFromParcel(Parcel in) {
                return new Review(in);
            }

            @Override
            public Review[] newArray(int size) {
                return new Review[size];
            }
        };

        public String getReviewerName() {
            return reviewerName;
        }

        public void setReviewerName(String reviewerName) {
            this.reviewerName = reviewerName;
        }

        public String getReviewText() {
            return reviewText;
        }

        public void setReviewText(String reviewText) {
            this.reviewText = reviewText;
        }

        public double getRating() {
            return rating;
        }

        public void setRating(double rating) {
            this.rating = rating;
        }

        public String getReviewDate() {
            return reviewDate;
        }

        public void setReviewDate(String reviewDate) {
            this.reviewDate = reviewDate;
        }
    }
}