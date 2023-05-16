package remote;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface TransactionClient {

    @Headers("Content-type: application/json")
    @POST("transaction/execute")
    Call<Void> sendTransaction(@Body String request);
}
