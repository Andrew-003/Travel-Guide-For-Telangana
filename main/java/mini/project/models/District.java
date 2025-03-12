package mini.project.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class District implements Parcelable {
    private int id;
    private String name;
    private double longitude;
    private double latitude;
    private String sub_description;
    private String main_description;
    private String imageurl;
    private String bgimageurl;
    private Transport transport;

    // Default constructor
    public District() {}

   

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getSubDescription() {
        return sub_description;
    }

    public void setSubDescription(String sub_description) {
        this.sub_description = sub_description;
    }

    public String getMainDescription() {
        return main_description;
    }

    public void setMainDescription(String main_description) {
        this.main_description = main_description;
    }

    public String getImageUrl() {
        return imageurl;
    }

    public void setImageUrl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getBgImageUrl() {
        return bgimageurl;
    }

    public void setBgImageUrl(String bgimageurl) {
        this.bgimageurl = bgimageurl;
    }

    public Transport getTransport() {
        return transport;
    }

    public void setTransport(Transport transport) {
        this.transport = transport;
    }

    // Parcelable implementation
    protected District(Parcel in) {
        id = in.readInt();
        name = in.readString();
        longitude = in.readDouble();
        latitude = in.readDouble();
        sub_description = in.readString();
        main_description = in.readString();
        imageurl = in.readString();
        bgimageurl = in.readString();
        transport = in.readParcelable(Transport.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeDouble(longitude);
        dest.writeDouble(latitude);
        dest.writeString(sub_description);
        dest.writeString(main_description);
        dest.writeString(imageurl);
        dest.writeString(bgimageurl);
        dest.writeParcelable(transport, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<District> CREATOR = new Creator<District>() {
        @Override
        public District createFromParcel(Parcel in) {
            return new District(in);
        }

        @Override
        public District[] newArray(int size) {
            return new District[size];
        }
    };

    // Transport inner class
    public static class Transport implements Parcelable {
        private Bus by_bus;
        private Train by_train;

        // Default constructor
        public Transport() {}

        // Parameterized constructor
        public Transport(Bus byBus, Train byTrain) {
            this.by_bus = byBus;
            this.by_train = byTrain;
        }

        public Bus getByBus() {
            return by_bus;
        }

        public void setByBus(Bus byBus) {
            this.by_bus = byBus;
        }

        public Train getByTrain() {
            return by_train;
        }

        public void setByTrain(Train byTrain) {
            this.by_train = byTrain;
        }

        // Parcelable implementation
        protected Transport(Parcel in) {
            by_bus = in.readParcelable(Bus.class.getClassLoader());
            by_train = in.readParcelable(Train.class.getClassLoader());
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeParcelable(by_bus, flags);
            dest.writeParcelable(by_train, flags);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<Transport> CREATOR = new Creator<Transport>() {
            @Override
            public Transport createFromParcel(Parcel in) {
                return new Transport(in);
            }

            @Override
            public Transport[] newArray(int size) {
                return new Transport[size];
            }
        };
    }

    // Bus inner class
    public static class Bus implements Parcelable {
        private String description;
        private BusDetails from_hyderabad;
        private List<String> from_nearby_cities;
        private String onlineBooking;

        // Default constructor
        public Bus() {}

        // Parameterized constructor
        public Bus(String description, BusDetails fromHyderabad, List<String> fromNearbyCities, String onlineBooking) {
            this.description = description;
            this.from_hyderabad = fromHyderabad;
            this.from_nearby_cities = fromNearbyCities;
            this.onlineBooking = onlineBooking;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public BusDetails getFromHyderabad() {
            return from_hyderabad;
        }

        public void setFromHyderabad(BusDetails fromHyderabad) {
            this.from_hyderabad = fromHyderabad;
        }

        public List<String> getFromNearbyCities() {
            return from_nearby_cities;
        }

        public void setFromNearbyCities(List<String> fromNearbyCities) {
            this.from_nearby_cities = fromNearbyCities;
        }

        public String getOnlineBooking() {
            return onlineBooking;
        }

        public void setOnlineBooking(String onlineBooking) {
            this.onlineBooking = onlineBooking;
        }

        // Parcelable implementation
        protected Bus(Parcel in) {
            description = in.readString();
            from_hyderabad = in.readParcelable(BusDetails.class.getClassLoader());
            from_nearby_cities = in.createStringArrayList();
            onlineBooking = in.readString();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(description);
            dest.writeParcelable(from_hyderabad, flags);
            dest.writeStringList(from_nearby_cities);
            dest.writeString(onlineBooking);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<Bus> CREATOR = new Creator<Bus>() {
            @Override
            public Bus createFromParcel(Parcel in) {
                return new Bus(in);
            }

            @Override
            public Bus[] newArray(int size) {
                return new Bus[size];
            }
        };
    }

    // BusDetails inner class
    public static class BusDetails implements Parcelable {
        private String operator;
        private List<String> pickup_points;
        private String journey_time;

        // Default constructor
        public BusDetails() {}

        // Parameterized constructor
        public BusDetails(String operator, List<String> pickupPoints, String journeyTime) {
            this.operator = operator;
            this.pickup_points = pickupPoints;
            this.journey_time = journeyTime;
        }

        public String getOperator() {
            return operator;
        }

        public void setOperator(String operator) {
            this.operator = operator;
        }

        public List<String> getPickupPoints() {
            return pickup_points;
        }

        public void setPickupPoints(List<String> pickupPoints) {
            this.pickup_points = pickupPoints;
        }

        public String getJourneyTime() {
            return journey_time;
        }

        public void setJourneyTime(String journeyTime) {
            this.journey_time = journeyTime;
        }

        // Parcelable implementation
        protected BusDetails(Parcel in) {
            operator = in.readString();
            pickup_points = in.createStringArrayList();
            journey_time = in.readString();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(operator);
            dest.writeStringList(pickup_points);
            dest.writeString(journey_time);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<BusDetails> CREATOR = new Creator<BusDetails>() {
            @Override
            public BusDetails createFromParcel(Parcel in) {
                return new BusDetails(in);
            }

            @Override
            public BusDetails[] newArray(int size) {
                return new BusDetails[size];
            }
        };
    }

    // Train inner class
    public static class Train implements Parcelable {
        private String description;
        private String station_name;
        private List<String> direct_trains;
        private String nearest_major_station;
        private String onlineBooking;

        // Default constructor
        public Train() {}

        // Parameterized constructor
        public Train(String description, String stationName, List<String> directTrains, String nearestMajorStation, String onlineBooking) {
            this.description = description;
            this.station_name = stationName;
            this.direct_trains = directTrains;
            this.nearest_major_station = nearestMajorStation;
            this.onlineBooking = onlineBooking;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getStationName() {
            return station_name;
        }

        public void setStationName(String stationName) {
            this.station_name = stationName;
        }

        public List<String> getDirectTrains() {
            return direct_trains;
        }

        public void setDirectTrains(List<String> directTrains) {
            this.direct_trains = directTrains;
        }

        public String getNearestMajorStation() {
            return nearest_major_station;
        }

        public void setNearestMajorStation(String nearestMajorStation) {
            this.nearest_major_station = nearestMajorStation;
        }

        public String getOnlineBooking() {
            return onlineBooking;
        }

        public void setOnlineBooking(String onlineBooking) {
            this.onlineBooking = onlineBooking;
        }

        // Parcelable implementation
        protected Train(Parcel in) {
            description = in.readString();
            station_name = in.readString();
            direct_trains = in.createStringArrayList();
            nearest_major_station = in.readString();
            onlineBooking = in.readString();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(description);
            dest.writeString(station_name);
            dest.writeStringList(direct_trains);
            dest.writeString(nearest_major_station);
            dest.writeString(onlineBooking);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<Train> CREATOR = new Creator<Train>() {
            @Override
            public Train createFromParcel(Parcel in) {
                return new Train(in);
            }

            @Override
            public Train[] newArray(int size) {
                return new Train[size];
            }
        };
    }
}
