package mad.com.myflight.utils;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import mad.com.myflight.model.TripItem;

/**
 * sorts a resuls set
 */

public class SearchResultsSorting {

    public SearchResultsSorting() {}

    /**
     * sorts the result set by price
     *
     * @param trips result set that to be sorted.
     * @param isLowestPriceFirst Lowest Price First.
     */
    public static void sortByPrice(List<TripItem> trips, boolean isLowestPriceFirst) {

        if (trips == null) {
            return;
        }

        Comparator<TripItem> lowestPriceFirstComparator = new Comparator<TripItem>() {
            @Override
            public int compare(TripItem t0, TripItem t1) {
                int price0, price1;
                price0 = Integer.parseInt(t0.getPrice().replaceAll("\\D+",""));
                price1 = Integer.parseInt(t1.getPrice().replaceAll("\\D+",""));
                if (price0 < price1) {
                    return -1;
                } else if (price0 > price1) {
                    return 1;
                } else {
                    return 0;
                }
            }
        };

        Comparator<TripItem> lowestPriceLastComparator = new Comparator<TripItem>() {
            @Override
            public int compare(TripItem t0, TripItem t1) {

                int price0, price1;

                price0 = Integer.parseInt(t0.getPrice().replaceAll("\\D+",""));
                price1 = Integer.parseInt(t1.getPrice().replaceAll("\\D+",""));

                if (price0 < price1) {
                    return 1;
                } else if (price0 > price1) {
                    return -1;
                } else {
                    return 0;
                }
            }
        };

        if (isLowestPriceFirst) {
            Collections.sort(trips, lowestPriceFirstComparator);
        } else {
            Collections.sort(trips, lowestPriceLastComparator);
        }

    }
    /**
     * sorts the result set by duration
     *
     * @param trips result set that to be sorted.
     * @param shortestDurationFirst shortest Duration First.
     */
    public static void sortByDuration(List<TripItem> trips, boolean shortestDurationFirst) {

        if (trips == null) {
            return;
        }

        Comparator<TripItem> shortestDurationFirstComparator = new Comparator<TripItem>() {
            @Override
            public int compare(TripItem t0, TripItem t1) {

                int duration0, duration1;

                duration0 = Integer.parseInt(t0.getDuration());
                duration1 = Integer.parseInt(t1.getDuration());

                if (duration0 < duration1) {
                    return -1;
                } else if (duration0 < duration1) {
                    return 1;
                } else {
                    return 0;
                }

            }
        };

        Comparator<TripItem> shortestDurationLastComparator = new Comparator<TripItem>() {
            @Override
            public int compare(TripItem t0, TripItem t1) {

                int duration0, duration1;

                duration0 = Integer.parseInt(t0.getDuration());
                duration1 = Integer.parseInt(t1.getDuration());

                if (duration0 < duration1) {
                    return -1;
                } else if (duration0 < duration1) {
                    return 1;
                } else {
                    return 0;
                }

            }
        };

        if (shortestDurationFirst) {
            Collections.sort(trips, shortestDurationFirstComparator);
        } else {
            Collections.sort(trips, shortestDurationLastComparator);
        }

    }

}
