package remote;

import model.RawTransaction;
import model.TokenInfo;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface TokenClient {

    @Headers("Content-type: application/json")
    @GET("token/user-info")
    Call<TokenInfo> getUserTokenInfo(@Query("userId") int userId);

    @Headers("Content-type: application/json")
    @GET("token/user-info")
    Call<RawTransaction> delegateVotes(@Query("userId") int userId, @Query("delegateeLogin") String delegateeLogin);
}
