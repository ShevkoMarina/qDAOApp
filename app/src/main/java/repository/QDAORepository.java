package repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import model.RawTransaction;
import model.UpdatableSettingsInfo;
import remote.QDAOClient;
import remote.NetworkService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import service.utils.Result;

public class QDAORepository {

    private final QDAOClient client;
    private final MutableLiveData<Result<RawTransaction>> addPrincipalsTransaction = new MutableLiveData<>();

    public MutableLiveData<Result<RawTransaction>> getAddPrincipalsTransaction() {
        return addPrincipalsTransaction;
    }

    public QDAORepository(){
        client = NetworkService.getRetrofitClient().create(QDAOClient.class);
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

    public LiveData<Result<UpdatableSettingsInfo>> getUpdatableSettings(){

        MutableLiveData<Result<UpdatableSettingsInfo>> data = new MutableLiveData<>();
        client.getUpdatableSettings().enqueue(new Callback<UpdatableSettingsInfo>() {
            @Override
            public void onResponse(Call<UpdatableSettingsInfo> call, Response<UpdatableSettingsInfo> response) {
                if (response.isSuccessful()) {
                    data.setValue(Result.success(response.body()));
                } else {
                    data.setValue(Result.error("Ошибка получения данных по настройкам"));
                }
            }

            @Override
            public void onFailure(Call<UpdatableSettingsInfo> call, Throwable t) {
                data.setValue(Result.error("Ошибка подключения к серверу"));
            }
        });

        return data;
    }
}
