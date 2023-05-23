package view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import remote.user_models.AuthorizeUserResponseDto;
import repository.UserRepository;
import service.AccountCreator;
import service.utils.Result;

public class AuthorizationViewModel extends AndroidViewModel {

    private final UserRepository userRepository;
    private final AccountCreator accountCreator;

    public AuthorizationViewModel(@NonNull Application application) {
        super(application);
        userRepository = new UserRepository();
        accountCreator = new AccountCreator();
    }

    public Result<Void> registerUser(String login, String password) {
        Result<String> accountCreationResult = accountCreator.generateAndStoreInSharedPreferances(getApplication());

        if (!accountCreationResult.isSuccess()) {
            return Result.error(accountCreationResult.getErrorMessage());
        }
        userRepository.register(login, password, accountCreationResult.getData());
        return Result.success();
    }

    public void authUser(String login, String password){
        userRepository.authorize(login, password);
    }

    public MutableLiveData<Result<AuthorizeUserResponseDto>> getUserAuthResult(){
        return userRepository.getAuthorizeUserResult();
    }

    public MutableLiveData<Result> getUserRegistrationResult(){
        return userRepository.getRegisterUserResult();
    }
}
