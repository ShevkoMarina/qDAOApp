package remote;

import java.util.List;

import model.ProposalThin;
import model.RawTransaction;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface AdminClient {

    @Headers("Content-type: application/json")
    @GET("qdao/add-principal")
    Call<RawTransaction> addPrincipal(
            @Query("userLogin") String userLogin,
            @Query("requiredApprovals") short requiredApprovals,
            @Query("senderId") int senderId);
}
