package view_model;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import remote.user_models.AuthorizeUserResponseDto;
import repository.ProposalRepository;
import repository.UserRepository;
import service.AccountCreator;
import service.Result;

public class AuthorizationViewModel extends AndroidViewModel {

    private final UserRepository userRepository;

    public AuthorizationViewModel(@NonNull Application application) {
        super(application);
        userRepository = new UserRepository();
    }

    public void OnCreateAccountBtnClicked(String login, String password) {
        AccountCreator accountCreator = new AccountCreator();
        try {
           // accountCreator.generateAndStoreInEcryptedSharedPreferances(getApplication());
            userRepository.register(login, password, "0x000000");
        }
        catch (Exception ignored) {}
    }

    public void OnLoginBtnClicked(String login, String password){
        userRepository.authorize(login, password);
    }

    public MutableLiveData<Result<AuthorizeUserResponseDto>> GetUserAuthorizationResult(){
        return userRepository.getAuthorizeUserResult();
    }

    public MutableLiveData<Result> GetUserRegistrationResult(){
        return userRepository.getRegisterUserResult();
    }
}
