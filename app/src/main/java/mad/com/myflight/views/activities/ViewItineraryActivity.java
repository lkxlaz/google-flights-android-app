package mad.com.myflight.views.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import mad.com.myflight.R;

/**
 * Displays the overall itinerary that consists of both ourbound
 * and return trips, together with other details.
 */

public class ViewItineraryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_itinerary);
    }
}
