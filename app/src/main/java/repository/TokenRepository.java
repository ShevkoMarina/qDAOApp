package repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import remote.NetworkService;
import remote.TokenService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TokenRepository {

    private final TokenService tokenService;

    public TokenRepository (){
        tokenService = NetworkService.getRetrofitClient().create(TokenService.class);
    }

    public LiveData<Long> getBalance(String address) {
        final MutableLiveData<Long> data = new MutableLiveData<>();

        tokenService.getBalance(address).enqueue(new Callback<Long>() {
            @Override
            public void onResponse(Call<Long> call, Response<Long> response) {
                if (response.isSuccessful()) {
                    data.setValue(response.body());
                } else {
                    data.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<Long> call, Throwable t) {
                data.setValue(null);
            }
        });

        return data;
    }
}
