package mad.com.myflight.http;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import mad.com.myflight.model.Place;

/**
 * wraps places data returned by IATA service
 */

public class GetAutoSuggestResponse {

    @SerializedName("Places")
    @Expose
    private List<Place> places = null;

    public List<Place> getPlaces() {
            return places;
        }

    public void setPlaces(List<Place> places) {
            this.places = places;
        }
}
