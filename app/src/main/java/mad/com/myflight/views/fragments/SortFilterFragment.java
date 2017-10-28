package mad.com.myflight.views.fragments;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;

import mad.com.myflight.R;
import mad.com.myflight.utils.SearchResultsFiltering;
import mad.com.myflight.views.activities.ViewSearchResultsActivity;

/**
 * Displays a user interface for users to specify sorting and filtering options,
 * as well as handles the users interaction events, such as button clicks.
 */

public class SortFilterFragment extends DialogFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Spinner mSortingOptSpn;
    private CheckBox mDirectOnlyCheckbox;

    private ViewSearchResultsActivity mAttachedActivity;

    public SortFilterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SortFilterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SortFilterFragment newInstance(String param1, String param2) {
        SortFilterFragment fragment = new SortFilterFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        mAttachedActivity = (ViewSearchResultsActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_sort_filter, container, false);

        mSortingOptSpn = (Spinner) rootView.findViewById(R.id.sort_options_spinner);
        mSortingOptSpn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                Log.d("ItemSelected", item);
                if (item.equalsIgnoreCase("lowest price first")) {
                    mAttachedActivity.sortResults(1);
                }
                if (item.equalsIgnoreCase("shortest duration first")) {
                    mAttachedActivity.sortResults(3);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mDirectOnlyCheckbox = (CheckBox) rootView.findViewById(R.id.filter_direct_only_checkbox);
        mDirectOnlyCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    mAttachedActivity.filterResults(SearchResultsFiltering.FILTER_DIRECT_ONLY);
                }
            }
        });

        return rootView;
    }

    

}
