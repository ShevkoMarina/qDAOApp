package repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import model.CreateProposalDto;
import model.ProposalThin;
import model.ProposalType;
import model.RawTransaction;
import remote.NetworkService;
import remote.ProposalService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProposalRepository {

    private final ProposalService proposalService;

    private final MutableLiveData<RawTransaction> rawTransaction = new MutableLiveData<>();

    public MutableLiveData<RawTransaction> getRawTransaction() {
        return rawTransaction;
    }

    public ProposalRepository() {
        proposalService = NetworkService.getRetrofitClient().create(ProposalService.class);
    }

    public MutableLiveData<List<ProposalThin>> getProposals(Long userId) {

        MutableLiveData<List<ProposalThin>> data = new MutableLiveData<>();

        proposalService.getProposalsByUserId(userId).enqueue(new Callback<List<ProposalThin>>() {
            @Override
            public void onResponse(Call<List<ProposalThin>> call, Response<List<ProposalThin>> response) {
                if (response.isSuccessful()) {
                    data.setValue(response.body());
                } else {
                    data.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<List<ProposalThin>> call, Throwable t) {
                data.setValue(null);
            }
        });

        return data;
    }

    public LiveData<RawTransaction> createProposal(
            String name,
            String description,
            ProposalType type,
            long newValue,
            long userId) {



        proposalService.createProposal(new CreateProposalDto(
                name,
                description,
                type.ordinal(),
                userId,
                newValue

        )).enqueue(new Callback<RawTransaction>() {

            @Override
            public void onResponse(Call<RawTransaction> call, Response<RawTransaction> response) {
                if (response.isSuccessful()) {
                    rawTransaction.postValue(response.body());
                } else {
                    rawTransaction.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<RawTransaction> call, Throwable t) {
                rawTransaction.postValue(null);
            }
        });

        return rawTransaction;
    }
}
