package view_model;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import service.AccountCreator;

public class AuthorizationViewModel extends AndroidViewModel {

    public AuthorizationViewModel(@NonNull Application application) {
        super(application);
    }

    public void OnCreateAccountBtnClicked() {
        AccountCreator accountCreator = new AccountCreator();
        try {
            accountCreator.generateAndStoreInEcryptedSharedPreferances(getApplication());
        }
        catch (Exception ignored) {}
    }
}
