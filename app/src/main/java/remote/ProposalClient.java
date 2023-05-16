package remote;

import model.ProposalInfo;

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

public interface ProposalClient {
    @Headers("Content-type: application/json")
    @GET("proposal/get-by-user")
    Call<List<ProposalThin>> getProposalsByUserId(@Query("userId") long userId);

    @Headers("Content-type: application/json")
    @POST("proposal/create")
    Call<RawTransaction> createProposal(@Body CreateProposalDto request);

    @Headers("Content-type: application/json")
    @GET("proposal/vote")
    Call<RawTransaction> voteProposal(@Query("userId") int userId, @Query("proposalId") long proposalId, @Query("support") boolean support);

    @Headers("Content-type: application/json")
    @GET("proposal/info")
    Call<ProposalInfo> getProposalInfoById(@Query("proposalId") long proposalId);

    @Headers("Content-type: application/json")
    @GET("proposal/active")
    Call<List<ProposalThin>> getProposalsActiveForVoting();

    @Headers("Content-type: application/json")
    @GET("proposal/promotion")
    Call<List<ProposalThin>> getProposalsForPromotion();

    @Headers("Content-type: application/json")
    @POST("proposal/queue")
    Call<RawTransaction> queueProposal(@Query("proposalId") long proposalId, @Query("userId") int userId);

    @Headers("Content-type: application/json")
    @POST("proposal/execute")
    Call<RawTransaction> executeProposal(@Query("proposalId") long proposalId, @Query("userId") int userId);
}
