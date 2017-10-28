package mad.com.myflight.http;

import com.google.api.services.qpxExpress.model.TripsSearchResponse;

import java.io.IOException;

import mad.com.myflight.model.UserQuery;

/**
 * takes query data, sends http request
 * and returns the response of flights search
 */

public interface QpxExpressService {

    /**
     * Returns a trip search response
     *
     * @param query user query.
     * @throws IOException
     */
    TripsSearchResponse getTripResponse(UserQuery query)
            throws IOException;

}
