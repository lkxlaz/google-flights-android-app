package mad.com.myflight.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import mad.com.myflight.R;
import mad.com.myflight.model.TripItem;

import static android.media.CamcorderProfile.get;

/**
 * Created by liangze on 11/10/17.
 */

public class TripAdapter extends RecyclerView.Adapter<TripAdapter.ViewHolder> {

    private List<TripItem> mTrips;
    private Context mContext;

    private final View.OnClickListener mOnClickListener;

    public TripAdapter(Context context, List<TripItem> trips, View.OnClickListener onClickLisnter) {
        this.mContext = context;
        this.mTrips = trips;
        mOnClickListener = onClickLisnter;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.trip_item, parent, false);
        return new ViewHolder(itemView, mOnClickListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final String TIME_UNIT = "h";

        TripItem trip = mTrips.get(position);

        holder.mOriginTv.setText(trip.getOriginPlace());
        holder.mDestinationTv.setText(trip.getDestination());
        holder.mDepartureTimeTv.setText(trip.getmDepartureTime());
        holder.mArrivalTimeTv.setText(trip.getArrivalTime());
        holder.mTotalPriceTv.setText(trip.getPrice());
        holder.mDurationTv.setText(trip.getDuration() + TIME_UNIT);
        holder.mStopsTv.setText(getTripStopsInfo(trip));
        Picasso.with(mContext)
                .load(trip.getAirlineLogoUrl())
                .into(holder.mAirlineLogoIv);
    }

    @Override
    public int getItemCount() {
        return mTrips.size();
    }


    /**
     * class
     *
     * */
    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mTotalPriceTv;
        private TextView mStopsTv;
        private TextView mDepartureTimeTv;
        private TextView mArrivalTimeTv;
        private TextView mOriginTv;
        private TextView mDestinationTv;
        private TextView mDurationTv;

        private ImageView mAirlineLogoIv;

        public ViewHolder(View itemView, View.OnClickListener listener) {
            super(itemView);

            mTotalPriceTv = (TextView) itemView.findViewById(R.id.trip_item_price);
            mStopsTv = (TextView) itemView.findViewById(R.id.trip_item_stops);
            mDepartureTimeTv = (TextView) itemView.findViewById(R.id.trip_item_departure_time);
            mArrivalTimeTv = (TextView) itemView.findViewById(R.id.trip_item_arrival_time);
            mOriginTv = (TextView) itemView.findViewById(R.id.trip_item_origin);
            mDestinationTv = (TextView) itemView.findViewById(R.id.trip_item_destination);
            mDurationTv = (TextView) itemView.findViewById(R.id.trip_item_duration);
            mAirlineLogoIv = (ImageView) itemView.findViewById(R.id.trip_item_airline_logo);

            itemView.setOnClickListener(listener);
        }
    }

    private String getTripStopsInfo(TripItem trip) {

        if (trip.isDirect()) {
            return "Non-stop";
        }

        List<String> stops = trip.getStops();
        int stopNum = stops.size();

        StringBuilder str = new StringBuilder(stopNum + " stop ");
        for (int i = 0; i < stops.size(); i++) {
            String stop = stops.get(i);
            str.append(stop);
            if (i != (stops.size() -1) ) {
                str.append("|");
            }
        }

        return str.toString();
    }


}
