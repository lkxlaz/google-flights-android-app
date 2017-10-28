package mad.com.myflight.http;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import mad.com.myflight.model.Airport;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Query;

/**
 * takes query data, sends http request
 * and returns the response of airports suggestions
 */

public class IataorgClient {

    private static final String BASE_URL = "http://iatacodes.org/api/v6/";
    private static final String API_KEY = "7a0a4a4b-2abb-4921-ad81-38cbc243598a";
    private IataAutoSuggestService mService;

    public IataorgClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        this.mService = retrofit.create(IataAutoSuggestService.class);
    }

    public List<Airport> getSuggestions (String query) {
        List<Airport> airportsList = new ArrayList<>();
        Call<ResponseBody> res = mService.getSuggestPlaces(API_KEY, query);
        String places = null;
        try {
            places = res.execute().body().string();
            Log.d("SuggestedPlacesJson", places);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (places != null) {
            JsonObject obj = new JsonParser().parse(places).getAsJsonObject();
            if (obj != null) {
                JsonArray airports = obj.get("response").getAsJsonObject().get("airports").getAsJsonArray();
                for (int i = 0; i < airports.size(); i++) {
                    Airport airport = new Airport();
                    airport.setCode(airports.get(i).getAsJsonObject().get("code").getAsString());
                    airport.setCountryName(airports.get(i).getAsJsonObject().get("country_name").getAsString());
                    airport.setName(airports.get(i).getAsJsonObject().get("name").getAsString());
                    airportsList.add(airport);
                }
            }
        }

        Log.d("SuggestedAirports", airportsList.toString());

        return airportsList;
    }


}
