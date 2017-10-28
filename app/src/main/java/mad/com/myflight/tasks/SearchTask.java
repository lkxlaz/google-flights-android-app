package mad.com.myflight.tasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.widget.ProgressBar;

import com.google.api.services.qpxExpress.model.TripsSearchResponse;

import java.io.IOException;

import mad.com.myflight.http.QpxExpressClient;
import mad.com.myflight.http.QpxExpressService;
import mad.com.myflight.model.UserQuery;
import mad.com.myflight.utils.ObjectWrapperForBinder;
import mad.com.myflight.views.activities.SearchFlightsActivity;
import mad.com.myflight.views.activities.ViewSearchResultsActivity;

import static android.support.v4.content.ContextCompat.startActivity;

/**
 * Created by liangze on 18/10/17.
 */

public class SearchTask extends AsyncTask<Void, Void, TripsSearchResponse> {

    private UserQuery mQuery;
    private Context mContext;
    private ProgressDialog mProgressDialog;

    public SearchTask(Context context, UserQuery query) {
        mContext = context;
        mQuery = query;
    }

    @Override
    protected void onPreExecute() {
        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Searching flights...");
        mProgressDialog.show();
    }

    @Override
    protected TripsSearchResponse doInBackground(Void... Void) {

        QpxExpressService service = new QpxExpressClient();

        TripsSearchResponse response = null;

        try {
            response = service.getTripResponse(mQuery);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    protected void onPostExecute(TripsSearchResponse response) {

        mProgressDialog.dismiss();

        final Bundle bundle = new Bundle();
        bundle.putBinder("object_value", new ObjectWrapperForBinder(response));
        startActivity(
                mContext,
                new Intent(mContext, ViewSearchResultsActivity.class).putExtras(bundle),
                null
        );

    }
}
