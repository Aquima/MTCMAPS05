package API.Service;

import API.Model.Districts;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


/**
 *
 * Created by raulquispe on 3/12/18.
 */

public interface ApiClient {

    @GET("/maps/api/geocode/json")
    Call<Districts> addressForDistrict(@Query("address") String address, @Query("key") String key);

}
