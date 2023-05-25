package repository;

import androidx.lifecycle.MutableLiveData;

import model.ProposalInfo;

import java.util.List;

import model.CreateProposalDto;
import model.ProposalThin;
import model.ProposalType;
import model.RawTransaction;
import remote.NetworkService;
import remote.ProposalClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import service.utils.Result;


public class ProposalRepository {

    private final ProposalClient proposalClient;
    private final MutableLiveData<Result<RawTransaction>> createProposalTransaction = new MutableLiveData<>();
    private final MutableLiveData<Result<RawTransaction>> voteProposalTransaction = new MutableLiveData<>();
    private final MutableLiveData<Result<RawTransaction>> promoteProposalTransaction = new MutableLiveData<>();

    public MutableLiveData<Result<RawTransaction>> getCreateProposalTransaction() {
        return createProposalTransaction;
    }

    public MutableLiveData<Result<RawTransaction>> getVoteProposalTransaction() {
        return voteProposalTransaction;
    }

    public MutableLiveData<Result<RawTransaction>> getPromoteProposalTransaction() {
        return promoteProposalTransaction;
    }

    public ProposalRepository() {
        proposalClient = NetworkService.getRetrofitClient().create(ProposalClient.class);
    }

    public MutableLiveData<List<ProposalThin>> getProposals(int userId) {

        MutableLiveData<List<ProposalThin>> data = new MutableLiveData<>();

        proposalClient.getProposalsByUserId(userId).enqueue(new Callback<List<ProposalThin>>() {
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

    public void createProposal(
            String name,
            String description,
            ProposalType type,
            int newValue,
            int userId) {

        proposalClient.createProposal(new CreateProposalDto(
                name,
                description,
                type.ordinal(),
                userId,
                newValue

        )).enqueue(new Callback<RawTransaction>() {

            @Override
            public void onResponse(Call<RawTransaction> call, Response<RawTransaction> response) {
                if (response.isSuccessful()) {
                    createProposalTransaction.postValue(Result.success(response.body()));
                } else {
                    createProposalTransaction.postValue(Result.error("Не удалось получить данные с сервера"));
                }
            }

            @Override
            public void onFailure(Call<RawTransaction> call, Throwable t) {
                createProposalTransaction.postValue(Result.error("Ошибка подключения к серверу"));
            }
        });
    }

    public void voteProposal(long proposalId, int userId, boolean support){

        proposalClient.voteProposal(userId, proposalId, support).enqueue(new Callback<RawTransaction>() {
                    @Override
                    public void onResponse(Call<RawTransaction> call, Response<RawTransaction> response) {
                        if (response.isSuccessful()) {
                            voteProposalTransaction.postValue(Result.success(response.body()));
                        }
                        else {
                            voteProposalTransaction.postValue(Result.error("Не удалось получить данные с сервера"));
                        }
                    }

                    @Override
                    public void onFailure(Call<RawTransaction> call, Throwable t) {
                        voteProposalTransaction.postValue(Result.error("Ошибка подключения к серверу"));
                    }
                });
    }

    public MutableLiveData<Result<ProposalInfo>> getProposalInfoById(long proposalId){

        MutableLiveData<Result<ProposalInfo>> data = new MutableLiveData<>();

        proposalClient.getProposalInfoById(proposalId).enqueue(new Callback<ProposalInfo>() {
            @Override
            public void onResponse(Call<ProposalInfo> call, Response<ProposalInfo> response) {
                if (response.isSuccessful()) {
                    data.setValue(Result.success(response.body()));
                }
                else {
                    data.setValue(Result.error("Ошибка получения информации о предложении"));
                }
            }

            @Override
            public void onFailure(Call<ProposalInfo> call, Throwable t) {
                data.setValue(Result.error("Ошибка подключения к серверу"));
            }
        });

        return data;
    }

    public MutableLiveData<Result<List<ProposalThin>>> getProposalsActiveForVoting(){

        MutableLiveData<Result<List<ProposalThin>>> data = new MutableLiveData<>();

        proposalClient.getProposalsActiveForVoting().enqueue(new Callback<List<ProposalThin>>() {
            @Override
            public void onResponse(Call<List<ProposalThin>> call, Response<List<ProposalThin>> response) {
                if (response.isSuccessful()) {
                    data.setValue(Result.success(response.body()));
                }
                else {
                    data.setValue(Result.error("Не удалось получить предложения для голосования"));
                }
            }

            @Override
            public void onFailure(Call<List<ProposalThin>> call, Throwable t) {
                data.setValue(Result.error("Ошибка подключения к серверу"));
            }
        });

        return data;
    }

    public MutableLiveData<Result<List<ProposalThin>>> getProposalsForPromotion(){

        MutableLiveData<Result<List<ProposalThin>>> data = new MutableLiveData<>();

        proposalClient.getProposalsForPromotion().enqueue(new Callback<List<ProposalThin>>() {
            @Override
            public void onResponse(Call<List<ProposalThin>> call, Response<List<ProposalThin>> response) {
                if (response.isSuccessful()){
                    data.setValue(Result.success(response.body()));
                }
                else {
                    data.setValue(Result.error("Не удалось получить предложения для продвижения"));
                }
            }

            @Override
            public void onFailure(Call<List<ProposalThin>> call, Throwable t) {
                data.setValue(Result.error("Ошибка подключения к серверу"));
            }
        });

        return data;
    }

    public void queueProposal(long proposalId, int userId) {
        proposalClient.queueProposal(proposalId, userId).enqueue(new Callback<RawTransaction>() {
            @Override
            public void onResponse(Call<RawTransaction> call, Response<RawTransaction> response) {
                if (response.isSuccessful()) {
                    promoteProposalTransaction.postValue(Result.success(response.body()));
                } else {
                    promoteProposalTransaction.postValue(Result.error("Не удалось получить транзакцию для поставления предложения в очередь"));
                }
            }

            @Override
            public void onFailure(Call<RawTransaction> call, Throwable t) {
                promoteProposalTransaction.postValue(Result.error("Ошибка подключения к серверу"));
            }
        });
    }

    public void executeProposal(long proposalId, int userId) {

        proposalClient.executeProposal(proposalId, userId).enqueue(new Callback<RawTransaction>() {
            @Override
            public void onResponse(Call<RawTransaction> call, Response<RawTransaction> response) {
                if (response.isSuccessful()) {
                    promoteProposalTransaction.postValue(Result.success(response.body()));
                } else {
                    promoteProposalTransaction.postValue(Result.error("Не удалось получить транзакцию для выполнения предлоэения"));
                }
            }

            @Override
            public void onFailure(Call<RawTransaction> call, Throwable t) {
                promoteProposalTransaction.postValue(Result.error("Ошибка подключения к серверу"));
            }
        });
    }

    public void cancelProposal() {

    }

    public void approveProposal() {

    }
}
