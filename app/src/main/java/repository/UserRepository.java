package repository;

import androidx.lifecycle.MutableLiveData;

import remote.NetworkService;
import remote.UserClient;
import remote.user_models.AddUserDto;
import remote.user_models.AuthorizeUserRequestDto;
import remote.user_models.AuthorizeUserResponseDto;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import service.utils.Result;

public class UserRepository {
    private final UserClient userClient;
    private final MutableLiveData<Result<AuthorizeUserResponseDto>> authorizeUserResult = new MutableLiveData<>();
    private final MutableLiveData<Result> registerUserResult = new MutableLiveData<>();

    public UserRepository() {
        userClient = NetworkService.getRetrofitClient().create(UserClient.class);
    }

    public void authorize(String login, String password) {

        AuthorizeUserRequestDto request = new AuthorizeUserRequestDto(login, password);

        userClient.authorize(request).enqueue(new Callback<AuthorizeUserResponseDto>() {
            @Override
            public void onResponse(Call<AuthorizeUserResponseDto> call, Response<AuthorizeUserResponseDto> response) {
                if (response.isSuccessful()) {
                    authorizeUserResult.postValue(Result.success(response.body()));
                } else {
                    authorizeUserResult.postValue(Result.error("Не удалось авторизоваться"));
                }
            }

            @Override
            public void onFailure(Call<AuthorizeUserResponseDto> call, Throwable t) {
                authorizeUserResult.postValue(Result.error("Ошибка подключения к серверу"));
            }
        });
    }

    public void register(String login, String password, String account){

        AddUserDto request = new AddUserDto(login, password, account);

        userClient.register(request).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()){
                    registerUserResult.postValue(Result.success(null));
                }
                else {
                    registerUserResult.postValue(Result.error("Ошибка регистрации пользователя"));
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                registerUserResult.postValue(Result.error("Ошибка подключения к серверу"));
            }
        });
    }

    public MutableLiveData<Result<AuthorizeUserResponseDto>> getAuthorizeUserResult() {
        return authorizeUserResult;
    }

    public MutableLiveData<Result> getRegisterUserResult() {
        return registerUserResult;
    }
}
