package view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import model.RawTransaction;
import model.TokenInfo;
import repository.TokenRepository;
import service.utils.Result;

public class TokenViewModel extends AndroidViewModel {

    private final TokenRepository tokenRepository;

    public TokenViewModel(@NonNull Application application) {
        super(application);

        tokenRepository = new TokenRepository();
    }

    public LiveData<Result<TokenInfo>> getUserTokenInfo(){
        int userId = 1;

        return tokenRepository.getUserTokenInfo(userId);
    }

    public void delegateVotes(String delegateeLogin){
        int userId = 1;

        tokenRepository.delegateVotes(userId, delegateeLogin);
    }

    public LiveData<Result<RawTransaction>> getDelegateVotesTransactionResult(){
        return tokenRepository.getDelegateVotesTransactionResult();
    }
}
