package remote;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface TokenService {

    @Headers("Content-type: application/json")
    @GET("token/balance")
    Call<Long> getBalance(@Query("signer") String signer);
}
