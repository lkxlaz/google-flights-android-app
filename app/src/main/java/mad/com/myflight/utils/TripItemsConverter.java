package mad.com.myflight.utils;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.api.services.qpxExpress.model.LegInfo;
import com.google.api.services.qpxExpress.model.SegmentInfo;
import com.google.api.services.qpxExpress.model.SliceInfo;
import com.google.api.services.qpxExpress.model.TripOption;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import mad.com.myflight.model.TripItem;

/**
 * converts tripoptions to tripitems
 */

public class TripItemsConverter {

    public static final int TYPE_OUTBOUND_TRIPS = 0 ;
    public static final int TYPE_RETURN_TRIPS = 1 ;

    private List<TripItem> mOutboundTrips;
    private List<TripItem> mReturnTrips;
    private List<TripOption> mTripOptions;

    /**
     * constructs a converter
     *
     * @param tripOptions trip options that to be converted.
     */
    public TripItemsConverter(List<TripOption> tripOptions) {
        mTripOptions = tripOptions;
        mReturnTrips = new ArrayList<TripItem>();
        mOutboundTrips = new ArrayList<TripItem>();
    }

    /**
     * returns trip items
     *
     * @param type type.
     */
    public List<TripItem> getTripsByType(int type) {
        if (type == TYPE_OUTBOUND_TRIPS && mOutboundTrips != null) {
            return mOutboundTrips;
        }
        if (type == TYPE_RETURN_TRIPS && mOutboundTrips != null) {
            return mReturnTrips;
        } else {
            return null;
        }
    }

    /**
     * converts trip options to trip items
     *
     */
    public void convert() {

        if (mTripOptions == null) {
            return;
        }

        for (TripOption option : mTripOptions) {
            String price = option.getSaleTotal();

            List<SliceInfo> slices = option.getSlice();
            for (int i = 0; i < slices.size(); i++) {
                SliceInfo slice = slices.get(i);

                if (slice == null) {
                    return;
                }

                TripItem tripItem = new TripItem();

                tripItem.setOriginPlace(getOrigin(slice));
                tripItem.setDestination(getDestination(slice));
                tripItem.setDepartureTime(extractTime( getDepartureTime(slice) ));
                tripItem.setArrivalTime(extractTime( getArrivalTime(slice) ));
                tripItem.setDuration(getDuration(slice));
                tripItem.setPrice(price);
                tripItem.setmAirlineLogoUrl(getAirlineLogoUrl(slice));
                tripItem.setDirect(isNonStop(slice));
                if (! isNonStop(slice)) {
                    tripItem.setStops(getStops(slice));
                }

                // check if its round trip
                // if yes, build both outbound and return trips
                // or not, build only outbound trips
                if (isRoundTrip(option) && i == 1) {
                    mReturnTrips.add(tripItem);
                } else {
                    mOutboundTrips.add(tripItem);
                }
            }

        }
    }

    public boolean hasReturnTrips() {
        return mReturnTrips.size() > 0;
    }

    private boolean isRoundTrip (TripOption trip) {
        return (trip.getSlice().size() > 1);
    }


    private String getOrigin(SliceInfo slice) {
        return slice.getSegment().get(0).getLeg().get(0).getOrigin();
    }

    private String getDestination(SliceInfo slice) {
        List<SegmentInfo> segments = slice.getSegment();
        List<LegInfo> legs = segments.get(segments.size()-1).getLeg();
        return legs.get(legs.size()-1).getDestination();
    }

    private String getDepartureTime(SliceInfo slice) {
        return slice.getSegment().get(0).getLeg().get(0).getDepartureTime();
    }

    private String getArrivalTime(SliceInfo slice) {
        List<SegmentInfo> segments = slice.getSegment();
        List<LegInfo> legs = segments.get(segments.size()-1).getLeg();
        return legs.get(legs.size()-1).getArrivalTime();
    }


    private String getDuration(@NonNull SliceInfo slice) {
        return String.valueOf(slice.getDuration() / 60);
    }

    private String getAirlineLogoUrl(SliceInfo slice) {
        final String BASE_URL = "https://daisycon.io/images/airline/?width=120&height=40&color=ffffff&iata=";
        String carrier = slice.getSegment().get(0).getFlight().getCarrier();
        return BASE_URL + carrier;

    }

    private boolean isNonStop(SliceInfo slice) {
        int segNum = slice.getSegment().size();
        int legNum = 0;
        if (segNum == 1) {
            SegmentInfo seg = slice.getSegment().get(0);
            if (seg.getLeg().size() == 1) {
                return true;
            }
        }

        return false;
    }

    private List<String> getStops(SliceInfo slice) {

        List<String> stops = new ArrayList<>();

        String origin = getOrigin(slice);
        String dest = getDestination(slice);
        Set<String> locations = new TreeSet<String>();
        locations.add(origin);
        locations.add(dest);

        List<SegmentInfo> segs = slice.getSegment();
        for (SegmentInfo seg: segs) {
            List<LegInfo> legs = seg.getLeg();
            for (LegInfo leg: legs) {
                if (! locations.contains(leg.getOrigin())) {
                    if (! stops.contains(leg.getOrigin())) {
                        stops.add(leg.getOrigin());
                    }
                }
                if (! locations.contains(leg.getDestination())) {
                    if (! stops.contains(leg.getDestination())) {
                        stops.add(leg.getDestination());
                    }
                }
            }
        }

        Log.d("stops", stops.size()+"：" + stops);

        return stops;
    }

    private String extractTime(@NonNull String str) {
        int indexStart, indexEnd = -1;
        indexStart = str.indexOf("T") + 1;
        if (str.indexOf("+") > 0) {
            indexEnd = str.indexOf("+");
        }
        if (str.indexOf("+") == -1 && str.indexOf("-") > 0) {
            indexEnd = str.lastIndexOf("-");
        }

        return str.substring(indexStart, indexEnd);
    }


}
