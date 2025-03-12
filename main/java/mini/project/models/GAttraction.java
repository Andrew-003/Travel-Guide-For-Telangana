
package mini.project.models;



import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;
import java.util.Map;

public class GAttraction implements Parcelable {

    private String searchString;
    private int rank;
    private String searchPageUrl;
    private String searchPageLoadedUrl;
    private boolean isAdvertisement;
    private String title;
    private String subTitle;
    private String description;
    private String price;
    private String categoryName;
    private String address;
    private String neighborhood;
    private String street;
    private String city;
    private String postalCode;
    private String state;
    private String countryCode;
    private String website;
    private String phone;
    private String phoneUnformatted;
    private boolean claimThisBusiness;
    private Location location;
    private String locatedIn;
    private String plusCode;
    private String menu;
    private float totalScore;
    private boolean permanentlyClosed;
    private boolean temporarilyClosed;
    private String placeId;
    private List<String> categories;
    private String fid;
    private String cid;
    private int reviewsCount;
    private ReviewDistribution reviewsDistribution;
    private int imagesCount;
    private List<String> imageCategories;
    private String scrapedAt;
    private String reserveTableUrl;
    private String googleFoodUrl;
    private String hotelStars;
    private String hotelDescription;
    private String checkInDate;
    private String checkOutDate;
    
    
    
    
    
    
    
    private String url;
    private String imageUrl;
    
    private List<String> bookingLinks;
    private List<String> orderBy;
    private List<Image> images;
    private List<String> imageUrls;
    

    public GAttraction() {
        // Default constructor
    }

    protected GAttraction(Parcel in) {
        searchString = in.readString();
        rank = in.readInt();
        searchPageUrl = in.readString();
        searchPageLoadedUrl = in.readString();
        isAdvertisement = in.readByte() != 0;
        title = in.readString();
        subTitle = in.readString();
        description = in.readString();
        price = in.readString();
        categoryName = in.readString();
        address = in.readString();
        neighborhood = in.readString();
        street = in.readString();
        city = in.readString();
        postalCode = in.readString();
        state = in.readString();
        countryCode = in.readString();
        website = in.readString();
        phone = in.readString();
        phoneUnformatted = in.readString();
        claimThisBusiness = in.readByte() != 0;
        location = in.readParcelable(Location.class.getClassLoader());
        locatedIn = in.readString();
        plusCode = in.readString();
        menu = in.readString();
        totalScore = in.readFloat();
        permanentlyClosed = in.readByte() != 0;
        temporarilyClosed = in.readByte() != 0;
        placeId = in.readString();
        categories = in.createStringArrayList();
        fid = in.readString();
        cid = in.readString();
        reviewsCount = in.readInt();
        reviewsDistribution = in.readParcelable(ReviewDistribution.class.getClassLoader());
        imagesCount = in.readInt();
        imageCategories = in.createStringArrayList();
        scrapedAt = in.readString();
        reserveTableUrl = in.readString();
        googleFoodUrl = in.readString();
        hotelStars = in.readString();
        hotelDescription = in.readString();
        checkInDate = in.readString();
        checkOutDate = in.readString();
        
        
        
        
        
        url = in.readString();
        imageUrl = in.readString();
        
        bookingLinks = in.createStringArrayList();
        orderBy = in.createStringArrayList();
        images = in.createTypedArrayList(Image.CREATOR);
        imageUrls = in.createStringArrayList();
        
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(searchString);
        dest.writeInt(rank);
        dest.writeString(searchPageUrl);
        dest.writeString(searchPageLoadedUrl);
        dest.writeByte((byte) (isAdvertisement ? 1 : 0));
        dest.writeString(title);
        dest.writeString(subTitle);
        dest.writeString(description);
        dest.writeString(price);
        dest.writeString(categoryName);
        dest.writeString(address);
        dest.writeString(neighborhood);
        dest.writeString(street);
        dest.writeString(city);
        dest.writeString(postalCode);
        dest.writeString(state);
        dest.writeString(countryCode);
        dest.writeString(website);
        dest.writeString(phone);
        dest.writeString(phoneUnformatted);
        dest.writeByte((byte) (claimThisBusiness ? 1 : 0));
        dest.writeParcelable(location, flags);
        dest.writeString(locatedIn);
        dest.writeString(plusCode);
        dest.writeString(menu);
        dest.writeFloat(totalScore);
        dest.writeByte((byte) (permanentlyClosed ? 1 : 0));
        dest.writeByte((byte) (temporarilyClosed ? 1 : 0));
        dest.writeString(placeId);
        dest.writeStringList(categories);
        dest.writeString(fid);
        dest.writeString(cid);
dest.writeInt(reviewsCount);
        dest.writeParcelable(reviewsDistribution, flags);
        dest.writeInt(imagesCount);
        dest.writeStringList(imageCategories);
        dest.writeString(scrapedAt);
        dest.writeString(reserveTableUrl);
        dest.writeString(googleFoodUrl);
        dest.writeString(hotelStars);
        dest.writeString(hotelDescription);
        dest.writeString(checkInDate);
        dest.writeString(checkOutDate);
        
        
        
        
        
        
        
        dest.writeString(url);
        dest.writeString(imageUrl);
      
        dest.writeStringList(bookingLinks);
        dest.writeStringList(orderBy);
        dest.writeTypedList(images);
        dest.writeStringList(imageUrls);
        
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<GAttraction> CREATOR = new Creator<GAttraction>() {
        @Override
        public GAttraction createFromParcel(Parcel in) {
            return new GAttraction(in);
        }

        @Override
        public GAttraction[] newArray(int size) {
            return new GAttraction[size];
        }
    };

    // Getters
    public String getSearchString() { return searchString; }
    public int getRank() { return rank; }
    public String getSearchPageUrl() { return searchPageUrl; }
    public String getSearchPageLoadedUrl() { return searchPageLoadedUrl; }
    public boolean isAdvertisement() { return isAdvertisement; }
    public String getTitle() { return title; }
    public String getSubTitle() { return subTitle; }
    public String getDescription() { return description; }
    public String getPrice() { return price; }
    public String getCategoryName() { return categoryName; }
    public String getAddress() { return address; }
    public String getNeighborhood() { return neighborhood; }
    public String getStreet() { return street; }
    public String getCity() { return city; }
    public String getPostalCode() { return postalCode; }
    public String getState() { return state; }
    public String getCountryCode() { return countryCode; }
    public String getWebsite() { return website; }
    public String getPhone() { return phone; }
    public String getPhoneUnformatted() { return phoneUnformatted; }
    public boolean isClaimThisBusiness() { return claimThisBusiness; }
    public Location getLocation() { return location; }
    public String getLocatedIn() { return locatedIn; }
    public String getPlusCode() { return plusCode; }
    public String getMenu() { return menu; }
    public float getTotalScore() { return totalScore; }
    public boolean isPermanentlyClosed() { return permanentlyClosed; }
    public boolean isTemporarilyClosed() { return temporarilyClosed; }
    public String getPlaceId() { return placeId; }
    public List<String> getCategories() { return categories; }
    public String getFid() { return fid; }
    public String getCid() { return cid; }
    public int getReviewsCount() { return reviewsCount; }
    public ReviewDistribution getReviewsDistribution() { return reviewsDistribution; }
    public int getImagesCount() { return imagesCount; }
    public List<String> getImageCategories() { return imageCategories; }
    public String getScrapedAt() { return scrapedAt; }
    public String getReserveTableUrl() { return reserveTableUrl; }
    public String getGoogleFoodUrl() { return googleFoodUrl; }
    public String getHotelStars() { return hotelStars; }
    public String getHotelDescription() { return hotelDescription; }
    public String getCheckInDate() { return checkInDate; }
    public String getCheckOutDate() { return checkOutDate; }
    
   
    
    
    
    public String getUrl() { return url; }
    public String getImageUrl() { return imageUrl; }
    
    public List<String> getBookingLinks() { return bookingLinks; }
    public List<String> getOrderBy() { return orderBy; }
    public List<Image> getImages() { return images; }
    public List<String> getImageUrls() { return imageUrls; }
    
    
    public static class Image implements Parcelable {
    private String imageUrl, authorName, authorUrl, uploadedAt;

    // Default constructor
    public Image() {
    }

    // Constructor with all values
    public Image(String imageUrl, String authorName, String authorUrl, String uploadedAt) {
        this.imageUrl = imageUrl;
        this.authorName = authorName;
        this.authorUrl = authorUrl;
        this.uploadedAt = uploadedAt;
    }

    // Parcelable implementation
    protected Image(Parcel in) {
        imageUrl = in.readString();
        authorName = in.readString();
        authorUrl = in.readString();
        uploadedAt = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(imageUrl);
        dest.writeString(authorName);
        dest.writeString(authorUrl);
        dest.writeString(uploadedAt);
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

    // Getters
    public String getImageUrl() {
        return imageUrl;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getAuthorUrl() {
        return authorUrl;
    }

    public String getUploadedAt() {
        return uploadedAt;
    }
}
    public static class ReviewDistribution implements Parcelable {
    private int oneStar;
    private int twoStar;
    private int threeStar;
    private int fourStar;
    private int fiveStar;

    // Default constructor
    public ReviewDistribution() {
    }

    // Constructor with all values
    public ReviewDistribution(int oneStar, int twoStar, int threeStar, int fourStar, int fiveStar) {
        this.oneStar = oneStar;
        this.twoStar = twoStar;
        this.threeStar = threeStar;
        this.fourStar = fourStar;
        this.fiveStar = fiveStar;
    }

    // Parcelable implementation
    protected ReviewDistribution(Parcel in) {
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

    public static final Creator<ReviewDistribution> CREATOR = new Creator<ReviewDistribution>() {
        @Override
        public ReviewDistribution createFromParcel(Parcel in) {
            return new ReviewDistribution(in);
        }

        @Override
        public ReviewDistribution[] newArray(int size) {
            return new ReviewDistribution[size];
        }
    };

    // Getters
    public int getOneStar() {
        return oneStar;
    }

    public int getTwoStar() {
        return twoStar;
    }

    public int getThreeStar() {
        return threeStar;
    }

    public int getFourStar() {
        return fourStar;
    }

    public int getFiveStar() {
        return fiveStar;
    }
}
}