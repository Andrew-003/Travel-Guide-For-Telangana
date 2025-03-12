package mini.project.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Restaurant implements Parcelable {

    
    private String id;
    private String type;
    private String category;
    private List<String> subcategories;
    private String name;
    private String locationString;
    private String description;
    private String image;
    private int photoCount;
    private String rankingPosition;
    private float rating;
    
    private String phone;
    private String address;
    
    private String localName;
    private String localAddress;
    private String localLangCode;
    private String email;
    private double latitude;
    private double longitude;
    private String webUrl;
    private String website;
    private String rankingString;
    private Integer rankingDenominator;
    
 private int numberOfReviews;
    private boolean isClosed;
    private boolean isLongClosed;
    private String openNowText;
    
    private List<String> dishes;
    
    private String menuWebUrl;
    
    private boolean isNearbyResult;
    private String priceRange;
    private List<String> photos;
    private List<String> cuisines;
    private List<String> dietaryRestrictions;
    private List<String> establishmentTypes;
    private List<String> amenities;
    private List<String> mealTypes;

    // Constructor
    public Restaurant() {
    }

    // Parcelable constructor
    protected Restaurant(Parcel in) {
    id = in.readString();
    type = in.readString();
    category = in.readString();
    subcategories = in.createStringArrayList();
    name = in.readString();
    locationString = in.readString();
    description = in.readString();
    image = in.readString();
    photoCount = in.readInt();
    rankingPosition = in.readString();
    rating = in.readFloat();
    phone = in.readString();
    address = in.readString();
    localName = in.readString();
    localAddress = in.readString();
    localLangCode = in.readString();
    email = in.readString();
    latitude = in.readDouble();
    longitude = in.readDouble();
    webUrl = in.readString();
    website = in.readString();
    rankingString = in.readString();
    rankingDenominator = (Integer) in.readValue(Integer.class.getClassLoader());
    numberOfReviews = in.readInt();
    isClosed = in.readByte() != 0;
    isLongClosed = in.readByte() != 0;
    openNowText = in.readString();
    dishes = in.createStringArrayList();
    menuWebUrl = in.readString();
    isNearbyResult = in.readByte() != 0;
    priceRange = in.readString();
    cuisines = in.createStringArrayList();
    dietaryRestrictions = in.createStringArrayList();
    establishmentTypes = in.createStringArrayList();
    amenities = in.createStringArrayList();
    mealTypes = in.createStringArrayList();
    photos = in.createStringArrayList();
}

    public static final Creator<Restaurant> CREATOR = new Creator<Restaurant>() {
        @Override
        public Restaurant createFromParcel(Parcel in) {
            return new Restaurant(in);
        }

        @Override
        public Restaurant[] newArray(int size) {
            return new Restaurant[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

  @Override
public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(id);
    dest.writeString(type);
    dest.writeString(category);
    dest.writeStringList(subcategories);
    dest.writeString(name);
    dest.writeString(locationString);
    dest.writeString(description);
    dest.writeString(image);
    dest.writeInt(photoCount);
    dest.writeString(rankingPosition);
    dest.writeFloat(rating);
    dest.writeString(phone);
    dest.writeString(address);
    dest.writeString(localName);
    dest.writeString(localAddress);
    dest.writeString(localLangCode);
    dest.writeString(email);
    dest.writeDouble(latitude);
    dest.writeDouble(longitude);
    dest.writeString(webUrl);
    dest.writeString(website);
    dest.writeString(rankingString);
    dest.writeValue(rankingDenominator);
    dest.writeInt(numberOfReviews);
    dest.writeByte((byte) (isClosed ? 1 : 0));
    dest.writeByte((byte) (isLongClosed ? 1 : 0));
    dest.writeString(openNowText);
    dest.writeStringList(dishes);
    dest.writeString(menuWebUrl);
    dest.writeByte((byte) (isNearbyResult ? 1 : 0));
    dest.writeString(priceRange);
    dest.writeStringList(cuisines);
    dest.writeStringList(dietaryRestrictions);
    dest.writeStringList(establishmentTypes);
    dest.writeStringList(amenities);
    dest.writeStringList(mealTypes);
    dest.writeStringList(photos);
}

    // Getters and Setters
    public List<String> getPhotos() {
        return photos;
    }

    public void setPhotos(List<String> photos) {
        this.photos= photos;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public int getPhotoCount() {
        return photoCount;
    }

    public void setPhotoCount(int photoCount) {
        this.photoCount = photoCount;
    }

    public String getRankingPosition() {
        return rankingPosition;
    }

    public void setRankingPosition(String rankingPosition) {
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

    public String getLocalLangCode() {
        return localLangCode;
    }

    public void setLocalLangCode(String localLangCode) {
        this.localLangCode = localLangCode;
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

    public Integer getRankingDenominator() {
        return rankingDenominator;
    }

    public void setRankingDenominator(Integer rankingDenominator) {
        this.rankingDenominator = rankingDenominator;
    }

    
  

    public int getNumberOfReviews() {
        return numberOfReviews;
    }

    public void setNumberOfReviews(int numberOfReviews) {
        this.numberOfReviews = numberOfReviews;
    }

    public boolean isClosed() {
        return isClosed;
    }

    public void setClosed(boolean closed) {
        isClosed = closed;
    }

    public boolean isLongClosed() {
        return isLongClosed;
    }

    public void setLongClosed(boolean longClosed) {
        isLongClosed = longClosed;
    }

    public String getOpenNowText() {
        return openNowText;
    }

    public void setOpenNowText(String openNowText) {
        this.openNowText = openNowText;
    }

    
    public List<String> getDishes() {
        return dishes;
    }

    public void setDishes(List<String> dishes) {
        this.dishes = dishes;
    }

    

    public String getMenuWebUrl() {
        return menuWebUrl;
    }

    public void setMenuWebUrl(String menuWebUrl) {
        this.menuWebUrl = menuWebUrl;
    }

    

    public boolean isNearbyResult() {
        return isNearbyResult;
    }

    public void setNearbyResult(boolean nearbyResult) {
        isNearbyResult = nearbyResult;
    }

    public String getPriceRange() {
        return priceRange;
    }

    public void setPriceRange(String priceRange) {
        this.priceRange = priceRange;
    }

    
    

    public List<String> getCuisines() {
        return cuisines;
    }

    public void setCuisines(List<String> cuisines) {
        this.cuisines = cuisines;
    }

    public List<String> getDietaryRestrictions() {
        return dietaryRestrictions;
    }

    public void setDietaryRestrictions(List<String> dietaryRestrictions) {
        this.dietaryRestrictions = dietaryRestrictions;
    }

    public List<String> getEstablishmentTypes() {
        return establishmentTypes;
    }

    public void setEstablishmentTypes(List<String> establishmentTypes) {
        this.establishmentTypes = establishmentTypes;
    }

    public List<String> getAmenities() {
        return amenities;
    }

    public void setAmenities(List<String> amenities) {
        this.amenities = amenities;
    }

    public List<String> getMealTypes() {
        return mealTypes;
    }

    public void setMealTypes(List<String> mealTypes) {
        this.mealTypes = mealTypes;
    }
}