package mad.com.myflight.tasks;

import android.app.SearchManager;
import android.database.MatrixCursor;
import android.os.AsyncTask;
import android.provider.BaseColumns;
import android.widget.CursorAdapter;

import java.util.List;

import mad.com.myflight.http.IataorgClient;
import mad.com.myflight.model.Airport;

/**
 * Created by liangze on 20/10/17.
 */

public class FetchSuggestionTask extends AsyncTask<String, Void, List<Airport>> {

    private CursorAdapter mAdapter;
    private List<Airport> mSuggestions;

    public FetchSuggestionTask(CursorAdapter adapter, List<Airport> suggestions) {
        mAdapter = adapter;
        mSuggestions = suggestions;
    }

    @Override
    protected List<Airport> doInBackground(String... strings) {
        String query = strings[0];
        IataorgClient client = new IataorgClient();
        mSuggestions = client.getSuggestions(query);
        return mSuggestions;
    }

    @Override
    protected void onPostExecute(List<Airport> airports) {
        String[] columns = { BaseColumns._ID,
                SearchManager.SUGGEST_COLUMN_TEXT_1,
                SearchManager.SUGGEST_COLUMN_INTENT_DATA,
        };
        MatrixCursor cursor = new MatrixCursor(columns);
        for (int i = 0; i < airports.size(); i++) {
            String[] tmp = {Integer.toString(i),airports.get(i).toString(),airports.get(i).toString()};
            cursor.addRow(tmp);
        }
        mAdapter.swapCursor(cursor);

    }
}