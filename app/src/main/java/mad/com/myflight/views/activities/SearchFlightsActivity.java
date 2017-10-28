package mad.com.myflight.views.activities;

import android.app.DatePickerDialog;
import android.app.SearchManager;
import android.content.Context;
import android.database.Cursor;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CursorAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SearchView;
import android.view.View;
import android.widget.Button;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.google.api.services.qpxExpress.model.PassengerCounts;


import mad.com.myflight.R;
import mad.com.myflight.model.Airport;
import mad.com.myflight.model.UserQuery;
import mad.com.myflight.tasks.FetchSuggestionTask;
import mad.com.myflight.tasks.SearchTask;

/**
 * Displays a user interface for users to input flight searching parameters,
 * as well as handles the users events, such as button clicks.
 */

public class SearchFlightsActivity extends AppCompatActivity {

    private Button mSearchBtn;
    private SearchView mOriginSv;
    private SearchView mDestSv;
    private EditText mDepartureDateEt;
    private EditText mReturnDateEt;
    private Spinner mCabinclassSpn;
    private Spinner mAdultscountSpn;
    private Spinner mChildrencountSpn;
    private Spinner mInfantscountSpn;
    private CheckBox mSingleTripCb;

    private Calendar mCalendar;
    private SimpleDateFormat mDateformat;
    private PassengerCounts mPassengers;
    private String mCabinClass;
    private SearchManager mSearchManager;
    private CursorAdapter mOriginSuggestionAdapter, mDestSuggestionAdapter;

    private List<Airport> mSuggestionsList;

    private Spinner.OnItemSelectedListener mAdultsCountListener, mChildrenCountListener, mInfantsCountListener;
    private Spinner.OnItemSelectedListener mCabinClassListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_flights);

        mOriginSv = (SearchView) findViewById(R.id.origin_searchview);
        mDestSv = (SearchView) findViewById(R.id.destination_searchview);
        mDepartureDateEt = (EditText) findViewById(R.id.departureDateEt);
        mReturnDateEt = (EditText) findViewById(R.id.returnDateEt);
        mCabinclassSpn = (Spinner) findViewById(R.id.cabin_class);
        mAdultscountSpn = (Spinner) findViewById(R.id.adult_number);
        mChildrencountSpn = (Spinner) findViewById(R.id.child_number);
        mInfantscountSpn = (Spinner) findViewById(R.id.infant_number);
        mSingleTripCb = (CheckBox) findViewById(R.id.single_trip_checkBox);
        mSearchBtn = (Button) findViewById(R.id.search_flight_btn);

        setSpinnerDefaultValues();

        setDateChangeHandler();
        setSearchButtonHandler();
        setSpinnersHandler();
        setCheckBoxHandler();

        // Enble auto suggeations
        mSuggestionsList = new ArrayList<>();
        mSearchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        mOriginSuggestionAdapter = new SimpleCursorAdapter(this,
                android.R.layout.simple_list_item_1,
                null,
                new String[]{SearchManager.SUGGEST_COLUMN_TEXT_1},
                new int[]{android.R.id.text1},
                0);
        mDestSuggestionAdapter = new SimpleCursorAdapter(this,
                android.R.layout.simple_list_item_1,
                null,
                new String[]{SearchManager.SUGGEST_COLUMN_TEXT_1},
                new int[]{android.R.id.text1},
                0);

        enableAutoSuggestions(mOriginSv);
        enableAutoSuggestions(mDestSv);

    }

    private void setSpinnerDefaultValues() {

        mAdultscountSpn.setSelection(1);
        mChildrencountSpn.setSelection(0);
        mInfantscountSpn.setSelection(0);
        mCabinclassSpn.setSelection(0);
    }

    private void setCheckBoxHandler() {
        mSingleTripCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                // hide return date if checked
                if (b == true) {
                    mReturnDateEt.setVisibility(View.INVISIBLE);
                } else {
                    mReturnDateEt.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void setSpinnersHandler() {

        setPassengerCountsListener();
        mAdultscountSpn.setOnItemSelectedListener(mAdultsCountListener);
        mChildrencountSpn.setOnItemSelectedListener(mChildrenCountListener);
        mInfantscountSpn.setOnItemSelectedListener(mInfantsCountListener);

        setCabinClassListener();
        mCabinclassSpn.setOnItemSelectedListener(mCabinClassListener);
    }

    private void setPassengerCountsListener() {

        mPassengers = new PassengerCounts();

        mAdultsCountListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mPassengers.setAdultCount(Integer.parseInt(adapterView.getItemAtPosition(i).toString()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        };
        mChildrenCountListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mPassengers.setChildCount(Integer.parseInt(adapterView.getItemAtPosition(i).toString()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        };
        mInfantsCountListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mPassengers.setInfantInSeatCount(Integer.parseInt(adapterView.getItemAtPosition(i).toString()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        };
    }

    private void setCabinClassListener() {

        mCabinClassListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mCabinClass = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        };
    }


    private void setDateChangeHandler() {

        mCalendar = Calendar.getInstance();
        mDateformat = new SimpleDateFormat("yyyy-MM-dd");

        final DatePickerDialog.OnDateSetListener departureDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                mCalendar.set(Calendar.YEAR, year);
                mCalendar.set(Calendar.MONTH, monthOfYear);
                mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDepartureDateField();
            }
        };

        final DatePickerDialog.OnDateSetListener returnDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                mCalendar.set(Calendar.YEAR, year);
                mCalendar.set(Calendar.MONTH, monthOfYear);
                mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateReturnDateField();
            }
        };

        mDepartureDateEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(SearchFlightsActivity.this,
                        departureDate,
                        mCalendar.get(Calendar.YEAR),
                        mCalendar.get(Calendar.MONTH),
                        mCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        mReturnDateEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(SearchFlightsActivity.this,
                        returnDate,
                        mCalendar.get(Calendar.YEAR),
                        mCalendar.get(Calendar.MONTH),
                        mCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void setSearchButtonHandler() {

        mSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                UserQuery query = getUserQuery();

                Log.d("UserQuery", query.toString());

                new SearchTask(SearchFlightsActivity.this, query).execute();
            }
        });
    }

    private void enableAutoSuggestions(final SearchView sv) {

        sv.setSearchableInfo(mSearchManager.getSearchableInfo(getComponentName()));
        if (sv.getId() == R.id.origin_searchview) {
            sv.setSuggestionsAdapter(mOriginSuggestionAdapter);
        }
        if (sv.getId() == R.id.destination_searchview) {
            sv.setSuggestionsAdapter(mDestSuggestionAdapter);
        }

        sv.setOnSuggestionListener(new SearchView.OnSuggestionListener() {
            @Override
            public boolean onSuggestionSelect(int i) {
                return false;
            }

            @Override
            public boolean onSuggestionClick(int i) {
                Cursor cursor = sv.getSuggestionsAdapter().getCursor();
                String str = cursor.getString(cursor.getColumnIndex(SearchManager.SUGGEST_COLUMN_TEXT_1));
                Log.d("SuggestionClicked", str);
                sv.setQuery(str, false);
                return false;
            }
        });
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(final String s) {
                if (s.length() > 2) {
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            new FetchSuggestionTask(sv.getSuggestionsAdapter(), mSuggestionsList).execute(s);
                        }
                    }, 500);

                }
                return false;
            }
        });
    }

    private UserQuery getUserQuery() {

        UserQuery query = new UserQuery();

        query.setCountry("AU");
        query.setCurrency("AUD");
        query.setLocale("en-AU");

        query.setOriginPlace(extractCode(mOriginSv.getQuery().toString()));
        query.setDestinationPlace(extractCode(mDestSv.getQuery().toString()));

        query.setOutboundDate(mDepartureDateEt.getText().toString());
        query.setInboundDate(mReturnDateEt.getText().toString());
        query.setCabinClass(mCabinClass);
        query.setNumOfAdults(mPassengers.getAdultCount());
        query.setNumOfChildren(mPassengers.getChildCount());
        query.setNumOfInfants(mPassengers.getInfantInSeatCount());

        query.setSingleTrip(mSingleTripCb.isChecked());
        query.setRoundTrip(! mSingleTripCb.isChecked());

        return query;
    }

    private void updateDepartureDateField() {
        mDepartureDateEt.setText(mDateformat.format(mCalendar.getTime()));
    }

    private void updateReturnDateField() {
        mReturnDateEt.setText(mDateformat.format(mCalendar.getTime()));
    }

    private String extractCode(String str) {
        int beginIndex = str.indexOf("[");
        int endIndex = str.lastIndexOf("]");
        return str.substring(beginIndex + 1, endIndex);
    }

}
