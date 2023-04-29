package remote;

import java.util.List;

import model.CreateProposalDto;
import model.ProposalThin;
import model.RawTransaction;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ProposalService {

    @Headers("Content-type: application/json")
    @GET("proposal/get-by-user")
    Call<List<ProposalThin>> getProposalsByUserId(@Query("userId") long userId);

    @Headers("Content-type: application/json")
    @POST("proposal/create")
    Call<RawTransaction> createProposal(@Body CreateProposalDto request);
}
