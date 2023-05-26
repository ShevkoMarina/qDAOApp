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
}
