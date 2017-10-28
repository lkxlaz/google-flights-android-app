package mad.com.myflight.http;

import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.qpxExpress.QPXExpress;
import com.google.api.services.qpxExpress.QPXExpressRequestInitializer;
import com.google.api.services.qpxExpress.model.PassengerCounts;
import com.google.api.services.qpxExpress.model.SliceInput;
import com.google.api.services.qpxExpress.model.TripOption;
import com.google.api.services.qpxExpress.model.TripOptionsRequest;
import com.google.api.services.qpxExpress.model.TripsSearchRequest;
import com.google.api.services.qpxExpress.model.TripsSearchResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import mad.com.myflight.model.UserQuery;

/**
 * implements the QpxExpressService interface
 */

public class QpxExpressClient implements QpxExpressService {

    private static final String APPLICATION_NAME = "MyFlight";
    private static final String API_KEY = "AIzaSyCRy_biKvsWSVhlrctZtrg-C4p5BZcjZUI";

    private int mSolutionLimit = 100;

    public QpxExpressClient() {}

    @Override
    public TripsSearchResponse getTripResponse(UserQuery query) throws IOException {

        HttpTransport httpTransport = new NetHttpTransport();
        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
        TripsSearchResponse tripSearchResults = null;

        PassengerCounts passengers = getPassengerCounts(query.getNumOfAdults(),
                query.getNumOfChildren(),
                query.getNumOfInfants());

        // set slice data
        List<SliceInput> slices = getSlices(query);

        // build request
        TripOptionsRequest request = new TripOptionsRequest();
        request.setSolutions(mSolutionLimit);
        request.setPassengers(passengers);
        request.setSlice(slices);

        TripsSearchRequest parameters = new TripsSearchRequest();
        parameters.setRequest(request);

        // get response back
        QPXExpress qpXExpress = new QPXExpress.Builder(httpTransport, jsonFactory, null).setApplicationName(APPLICATION_NAME)
                .setGoogleClientRequestInitializer(new QPXExpressRequestInitializer(API_KEY)).build();

        tripSearchResults = qpXExpress.trips().search(parameters).execute();

        return tripSearchResults;
    }

    private PassengerCounts getPassengerCounts(int adults, int children, int infants) {

        PassengerCounts passengers = new PassengerCounts();

        if (adults > 0) {
            passengers.setAdultCount(adults);
        }
        if (children > 0) {
            passengers.setChildCount(children);
        }
        if (infants > 0) {
            passengers.setInfantInSeatCount(infants);
        }

        return passengers;
    }

    private List<SliceInput> getSlices(UserQuery query) {

        List<SliceInput> slices = new ArrayList<SliceInput>();
        int sliceSize = 0;

        if (query.isSingleTrip()) {
            sliceSize = 1;
        }

        if (query.isRoundTrip()) {
            sliceSize = 2;
        }

        String origin, dest, temp;
        String date;

        for (int i = 0; i < sliceSize; i++) {

            origin = query.getOriginPlace();
            dest = query.getDestinationPlace();
            date = query.getOutboundDate();

            if (i == 1 && query.isRoundTrip()) {

                // swap origin and destination
                temp = origin;
                origin = dest;
                dest = temp;

                date = query.getInboundDate();
            }

            slices.add(
                    new SliceInput()
                            .setOrigin(origin)
                            .setDestination(dest)
                            .setDate(date)
            );
        }

        return slices;
    }

}
