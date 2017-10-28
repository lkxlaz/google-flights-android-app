package mad.com.myflight.utils;

import java.util.Iterator;
import java.util.List;

import mad.com.myflight.model.TripItem;

/**
 * filters a resuls set
 */

public class SearchResultsFiltering {

    public static final int FILTER_DIRECT_ONLY = 1;

    /**
     * filters a result set by Direct only
     *
     * @param trips result set that to be filterd.
     */
    public static void filterByDirectOnly(List<TripItem> trips) {
        Iterator iterator = trips.iterator();
        while (iterator.hasNext()) {
            TripItem trip = (TripItem) iterator.next();
            if (! trip.isDirect()) {
                iterator.remove();
            }

        }
    }
}
