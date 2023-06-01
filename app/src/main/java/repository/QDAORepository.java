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

    private final MutableLiveData<Result<RawTransaction>> transferTokensTransaction = new MutableLiveData<>();

    public MutableLiveData<Result<RawTransaction>> getTransferTokensTransaction() {
        return transferTokensTransaction;
    }
    private final MutableLiveData<Result<RawTransaction>> pendingTransaction = new MutableLiveData<>();

    public MutableLiveData<Result<RawTransaction>> getPendingTransaction() {
        return pendingTransaction;
    }

    private final MutableLiveData<Result<RawTransaction>> approveMigrationTransaction = new MutableLiveData<>();

    public MutableLiveData<Result<RawTransaction>> getApproveMigrationTransaction() {
        return approveMigrationTransaction;
    }

    private final MutableLiveData<Result<RawTransaction>> setMigration = new MutableLiveData<>();

    public MutableLiveData<Result<RawTransaction>> getSetMigration() {
        return setMigration;
    }

    public QDAORepository(String token) {
        client = NetworkService.getRetrofitWithToken(token).create(QDAOClient.class);
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

    public void transferTokens(int userId, String delegateeLogin, long amount) {

        client.transferTokens(userId, delegateeLogin, amount).enqueue(new Callback<RawTransaction>() {
            @Override
            public void onResponse(Call<RawTransaction> call, Response<RawTransaction> response) {
                if (response.isSuccessful()) {
                    transferTokensTransaction.postValue(Result.success(response.body()));
                } else {
                    transferTokensTransaction.postValue(Result.error("Ошибка получения данных по настройкам"));
                }
            }

            @Override
            public void onFailure(Call<RawTransaction> call, Throwable t) {
                transferTokensTransaction.postValue(Result.error("Ошибка подключения к серверу"));
            }
        });
    }

    public LiveData<Result<String>> getPendingAddress() {

        MutableLiveData<Result<String>> data = new MutableLiveData<>();
        client.getPendingAddress().enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    data.setValue(Result.success(response.body()));
                }
                else {
                    data.setValue(Result.error("Ошибка получения адреса предлагаемой миграции"));
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                data.setValue(Result.error("Ошибка подключения к серверу"));
            }
        });

        return data;
    }

    public void setPendingAddress(int userId, String address) {

        client.setPendingAddress(userId, address).enqueue(new Callback<RawTransaction>() {
            @Override
            public void onResponse(Call<RawTransaction> call, Response<RawTransaction> response) {
                if (response.isSuccessful()) {
                    pendingTransaction.postValue(Result.success(response.body()));
                } else {
                    pendingTransaction.postValue(Result.error("Ошибка получения данных по настройкам"));
                }
            }

            @Override
            public void onFailure(Call<RawTransaction> call, Throwable t) {
                pendingTransaction.postValue(Result.error("Ошибка подключения к серверу"));
            }
        });
    }

    public void approveMigration(int userId){
        client.approveImplementation(userId).enqueue(new Callback<RawTransaction>() {
            @Override
            public void onResponse(Call<RawTransaction> call, Response<RawTransaction> response) {
                if (response.isSuccessful()) {
                    approveMigrationTransaction.postValue(Result.success(response.body()));
                } else {
                    approveMigrationTransaction.postValue(Result.error("Ошибка получения транзакции для согласования миграции"));
                }
            }

            @Override
            public void onFailure(Call<RawTransaction> call, Throwable t) {
                approveMigrationTransaction.postValue(Result.error("Ошибка подключения к серверу"));
            }
        });
    }

    public void setMigration(int userId) {
        client.setImplementation(userId).enqueue(new Callback<RawTransaction>() {
            @Override
            public void onResponse(Call<RawTransaction> call, Response<RawTransaction> response) {
                if (response.isSuccessful()) {
                    setMigration.postValue(Result.success(response.body()));
                } else {
                    setMigration.postValue(Result.error("Ошибка получения транзакции для применения миграции"));
                }
            }

            @Override
            public void onFailure(Call<RawTransaction> call, Throwable t) {
                setMigration.postValue(Result.error("Ошибка подключения к серверу"));
            }
        });
    }
}
