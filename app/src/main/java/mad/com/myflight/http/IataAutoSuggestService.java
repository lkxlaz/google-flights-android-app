package mad.com.myflight.http;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * takes query data, sends http request
 * and returns the response of airports suggestions
 */

public interface IataAutoSuggestService {

    /**
     * Returns a http ResponseBody that contains suggestions of airports
     *
     * @param apiKey api key.
     * @param query user query.
     */
    @GET("autocomplete")
    Call<ResponseBody> getSuggestPlaces (@Query("api_key") String apiKey, @Query("query") String query);
}
