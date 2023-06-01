package remote;

import model.RawTransaction;
import model.UpdatableSettingsInfo;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface QDAOClient {

    @Headers("Content-type: application/json")
    @GET("qdao/add-principal")
    Call<RawTransaction> addPrincipal(
            @Query("userLogin") String userLogin,
            @Query("requiredApprovals") short requiredApprovals,
            @Query("senderId") int senderId);


    @Headers("Content-type: application/json")
    @GET("qdao/updatable-settings")
    Call<UpdatableSettingsInfo> getUpdatableSettings();

    @Headers("Content-type: application/json")
    @GET("qdao/transfer-tokens")
    Call<RawTransaction> transferTokens(
            @Query("userId") int userId,
            @Query("delefateeLogin") String delefateeLogin,
            @Query("amount") long amount);

    @Headers("Content-type: application/json")
    @GET("qdao/implementation-info")
    Call<String> getPendingAddress();

    @Headers("Content-type: application/json")
    @GET("qdao/set-pending")
    Call<RawTransaction> setPendingAddress(@Query("userId") int userId,
                                           @Query("address") String address);

    @Headers("Content-type: application/json")
    @GET("qdao/set-implementation")
    Call<RawTransaction> setImplementation(@Query("userId") int userId);

    @Headers("Content-type: application/json")
    @GET("qdao/approve-implementation")
    Call<RawTransaction> approveImplementation(@Query("userId") int userId);
}
