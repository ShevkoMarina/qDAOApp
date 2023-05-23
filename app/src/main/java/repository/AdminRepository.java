package repository;

import androidx.lifecycle.MutableLiveData;

import model.RawTransaction;
import remote.AdminClient;
import remote.NetworkService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import service.utils.Result;

public class AdminRepository {

    private final AdminClient client;
    private final MutableLiveData<Result<RawTransaction>> addPrincipalsTransaction = new MutableLiveData<>();

    public MutableLiveData<Result<RawTransaction>> getAddPrincipalsTransaction() {
        return addPrincipalsTransaction;
    }

    public AdminRepository(){
        client = NetworkService.getRetrofitClient().create(AdminClient.class);
    }

    public void addPrincipals(String userLogin, int requiredApprovals, int senderId){

        client.addPrincipal(userLogin, (short)requiredApprovals, senderId).enqueue(new Callback<RawTransaction>() {
            @Override
            public void onResponse(Call<RawTransaction> call, Response<RawTransaction> response) {
                if (response.isSuccessful()) {
                    addPrincipalsTransaction.setValue(Result.success(response.body()));
                } else {
                    addPrincipalsTransaction.setValue(Result.error("Ошибка добавления нового аудитора"));
                }
            }

            @Override
            public void onFailure(Call<RawTransaction> call, Throwable t) {
                addPrincipalsTransaction.setValue(Result.error("Ошибка подключения к серверу"));
            }
        });
    }
}
