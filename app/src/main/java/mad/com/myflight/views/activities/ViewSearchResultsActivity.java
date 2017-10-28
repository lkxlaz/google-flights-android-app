package mad.com.myflight.views.activities;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.api.services.qpxExpress.model.TripOption;
import com.google.api.services.qpxExpress.model.TripsSearchResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import mad.com.myflight.R;
import mad.com.myflight.adapter.TripAdapter;
import mad.com.myflight.model.Itinerary;
import mad.com.myflight.model.TripItem;
import mad.com.myflight.utils.ObjectWrapperForBinder;
import mad.com.myflight.utils.SearchResultsFiltering;
import mad.com.myflight.utils.SearchResultsSorting;
import mad.com.myflight.utils.TripItemsConverter;
import mad.com.myflight.views.fragments.SortFilterFragment;

/**
 * Presents the search results to users in a recycler view
 * handles the users interactions.
 */
public class ViewSearchResultsActivity extends AppCompatActivity {

    private RecyclerView mTripsRecyView;
    private TextView mSortFilterTv;
    private TripAdapter mTripAdapter;
    private TripItem mSelectedTripItem;
    private Itinerary mItinerary;

    private List<TripItem> mAttachedTrips;
    private List<TripItem> mOutboundTrips;
    private List<TripItem> mReturnTrips;

    private View.OnClickListener mRecyclerViewItemOnClickListener;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_search_results);

        mItinerary = new Itinerary();

        // attaching the layout manager to the recycler view
        mTripsRecyView = (RecyclerView) findViewById(R.id.view_trips_recyclerview);
        mTripsRecyView.setLayoutManager(new LinearLayoutManager(this));

        // attaching adapter to the recycler view
        // put trips data in and update the view
        final TripsSearchResponse res = (TripsSearchResponse) ((ObjectWrapperForBinder)getIntent().getExtras().getBinder("object_value")).getData();
        TripItemsConverter converter = new TripItemsConverter(res.getTrips().getTripOption());
        converter.convert();
        mOutboundTrips = converter.getTripsByType(TripItemsConverter.TYPE_OUTBOUND_TRIPS);
        mAttachedTrips = mOutboundTrips;
        if (converter.hasReturnTrips()) {
            mReturnTrips = converter.getTripsByType(TripItemsConverter.TYPE_RETURN_TRIPS);
        }
        // recycler view onclick
        mRecyclerViewItemOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSelectedTripItem = getSelectedTripItem(view);
                Toast.makeText(ViewSearchResultsActivity.this, "You selected a flight", Toast.LENGTH_SHORT).show();
                mItinerary.addTrip(mSelectedTripItem);
                clearTrips();
                if (mReturnTrips != null && mReturnTrips.size() > 0) {
                    showReturnTrips();
                }
            }
        };
        mTripAdapter = new TripAdapter(this, mAttachedTrips, mRecyclerViewItemOnClickListener);
        mTripsRecyView.setAdapter(mTripAdapter);

        // sort & filter
        mSortFilterTv = (TextView) findViewById(R.id.sort_filter_textview);
        mSortFilterTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSortFilterFragment();
            }
        });

    }

    /** Sorts the results set according to options that users specified. */
    public void sortResults (int option) {

        switch (option) {
            case 1:
                SearchResultsSorting.sortByPrice(mAttachedTrips, true);
                break;
            case 2:
                SearchResultsSorting.sortByPrice(mAttachedTrips, false);
                break;
            case 3:
                SearchResultsSorting.sortByDuration(mAttachedTrips, true);
                break;
            case 4: SearchResultsSorting.sortByDuration(mAttachedTrips, false);
                break;
            default:
                break;

        }

        mTripAdapter.notifyDataSetChanged();

    }

    /** Filters the results set according to options that users specified. */
    public void filterResults (int option) {

        switch (option) {
            case SearchResultsFiltering.FILTER_DIRECT_ONLY:
                SearchResultsFiltering.filterByDirectOnly(mAttachedTrips);
                break;
            default:
                break;
        }

        mTripAdapter.notifyDataSetChanged();
    }

    private void showSortFilterFragment() {
        FragmentManager fm = getSupportFragmentManager();
        SortFilterFragment f = SortFilterFragment.newInstance("blah", "blah");
        f.show(fm, "fragment_sort_filter");
    }

    private TripItem getSelectedTripItem(View view) {
        TripItem trip;
        int pos = mTripsRecyView.getChildLayoutPosition(view);
        trip = mAttachedTrips.get(pos);
        Log.d("SelectedTrip", trip.toString());
        return trip;
    }

    private void clearTrips() {
        mAttachedTrips.clear();
        mTripAdapter.notifyDataSetChanged();
    }

    private void showReturnTrips() {

        mAttachedTrips.addAll(mReturnTrips);
        mTripAdapter.notifyDataSetChanged();

    }
}
