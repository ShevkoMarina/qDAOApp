package repository;
import androidx.lifecycle.MutableLiveData;

import remote.NetworkService;
import remote.TransactionClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import service.utils.Result;

public class TransactionRespoitory {
    private final TransactionClient transactionClient;
    private final MutableLiveData<Result<Void>> sendTransactionResult = new MutableLiveData<>();

    public MutableLiveData<Result<Void>> getSendTransactionResult() {
        return sendTransactionResult;
    }

    public TransactionRespoitory (){
        transactionClient = NetworkService.getRetrofitClient().create(TransactionClient.class);
    }

    public void sendTransaction(String transactionHex) {
        transactionClient.sendTransaction(transactionHex).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    sendTransactionResult.postValue(Result.success());
                }
                else {
                    sendTransactionResult.postValue(Result.error("Ошибка отправки транзакции"));
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                sendTransactionResult.postValue(Result.error("Ошибка подключения к серверу"));
            }
        });
    }
}
