package mad.com.myflight.model;

import java.util.List;

/**
 * contains trips data.
 */


public class TripItem {

    private String mOriginPlace;
    private String mDestination;
    private String mDepartureTime;
    private String mArrivalTime;
    private String mPrice;
    private String mDuration;
    private String mAirlineLogoUrl;
    private boolean mIsDirect;

    private List<String> mStops;
    private List<TripItem> trips;

    public TripItem() {}

    public TripItem(String mOriginPlace, String mDestination, String mDepartureTime, String mArrivalTime, String mPrice, String mDuration, String mAirlineLogoUrl) {
        this.mOriginPlace = mOriginPlace;
        this.mDestination = mDestination;
        this.mDepartureTime = mDepartureTime;
        this.mArrivalTime = mArrivalTime;
        this.mPrice = mPrice;
        this.mDuration = mDuration;
        this.mAirlineLogoUrl = mAirlineLogoUrl;
    }

    public String getOriginPlace() {
        return mOriginPlace;
    }

    public void setOriginPlace(String originPlace) {
        this.mOriginPlace = originPlace;
    }

    public String getDestination() {
        return mDestination;
    }

    public void setDestination(String destination) {
        this.mDestination = destination;
    }

    public String getmDepartureTime() {
        return mDepartureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.mDepartureTime = departureTime;
    }

    public String getArrivalTime() {
        return mArrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.mArrivalTime = arrivalTime;
    }

    public String getPrice() {
        return mPrice;
    }

    public void setPrice(String price) {
        this.mPrice = price;
    }

    public String getDuration() {
        return mDuration;
    }

    public void setDuration(String duration) {
        this.mDuration = duration;
    }

    public String getAirlineLogoUrl() {
        return mAirlineLogoUrl;
    }

    public void setmAirlineLogoUrl(String airlineLogoUrl) {
        this.mAirlineLogoUrl = airlineLogoUrl;
    }

    public List<TripItem> getTrips() {
        return trips;
    }

    public void setTrips(List<TripItem> trips) {
        this.trips = trips;
    }

    public boolean isDirect() {
        return mIsDirect;
    }

    public void setDirect(boolean direct) {
        mIsDirect = direct;
    }

    public List<String> getStops() {
        return mStops;
    }

    public TripItem setStops(List<String> stops) {
        this.mStops = stops;
        return this;
    }

    @Override
    public String toString() {
        return "TripItem{" +
                "mOriginPlace='" + mOriginPlace + '\'' +
                ", mDestination='" + mDestination + '\'' +
                ", mDepartureTime='" + mDepartureTime + '\'' +
                ", mArrivalTime='" + mArrivalTime + '\'' +
                ", mPrice='" + mPrice + '\'' +
                ", mDuration='" + mDuration + '\'' +
                ", mAirlineLogoUrl='" + mAirlineLogoUrl + '\'' +
                ", mIsDirect=" + mIsDirect +
                ", mStops=" + mStops +
                ", trips=" + trips +
                '}';
    }
}
