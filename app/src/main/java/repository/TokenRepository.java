package repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import model.RawTransaction;
import model.TokenInfo;
import remote.NetworkService;
import remote.TokenClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import service.utils.Result;

public class TokenRepository {

    private final TokenClient tokenClient;
    private final MutableLiveData<Result<RawTransaction>> delegateVotesTransactionResult = new MutableLiveData<>();

    public MutableLiveData<Result<RawTransaction>> getDelegateVotesTransaction() {
        return delegateVotesTransactionResult;
    }

    public TokenRepository (String token){
        tokenClient = NetworkService.getRetrofitWithToken(token).create(TokenClient.class);
    }

    public LiveData<Result<TokenInfo>> getUserTokenInfo(int userId) {

        MutableLiveData<Result<TokenInfo>> data = new MutableLiveData<>();

        tokenClient.getUserTokenInfo(userId).enqueue(new Callback<TokenInfo>() {
            @Override
            public void onResponse(Call<TokenInfo> call, Response<TokenInfo> response) {
                if (response.isSuccessful()) {
                    data.setValue(Result.success(response.body()));
                } else {
                    data.setValue(Result.error("Ошибка получения данных о токене"));
                }
            }

            @Override
            public void onFailure(Call<TokenInfo> call, Throwable t) {
                data.setValue(Result.error("Ошибка подключения к серверу"));
            }
        });

        return data;
    }

    public void generateDelegateVotesTransaction(int userId, String delegateeLogin){
        tokenClient.delegateVotes(userId, delegateeLogin).enqueue(new Callback<RawTransaction>() {
            @Override
            public void onResponse(Call<RawTransaction> call, Response<RawTransaction> response) {
                if (response.isSuccessful()) {
                    delegateVotesTransactionResult.postValue(Result.success(response.body()));
                } else {
                    delegateVotesTransactionResult.postValue(Result.error("Ошибка создания транзакции"));
                }
            }

            @Override
            public void onFailure(Call<RawTransaction> call, Throwable t) {
                delegateVotesTransactionResult.postValue(Result.error("Ошибка подключения к серверу"));
            }
        });
    }
}
