package mad.com.myflight.model;

import java.util.ArrayList;
import java.util.List;

/**
 * contains itinerary data
 */

public class Itinerary {

    private List<TripItem> mTrips;

    public Itinerary() {
        mTrips = new ArrayList<>();
    }

    public void addTrip (TripItem trip) {
        mTrips.add(trip);
    }

    public List<TripItem> getmTrips() {
        return mTrips;
    }
}
