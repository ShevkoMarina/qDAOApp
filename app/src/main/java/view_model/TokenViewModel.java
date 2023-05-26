package view_model;

import static android.content.Context.MODE_PRIVATE;

import android.app.Application;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import model.RawTransaction;
import model.TokenInfo;
import repository.TokenRepository;
import service.TransactionSender;
import service.TransactionSigner;
import service.utils.Result;

public class TokenViewModel extends AndroidViewModel {

    private final TokenRepository tokenRepository;
    private final TransactionSigner transactionSigner;
    private final TransactionSender transactionSender;

    public TokenViewModel(@NonNull Application application) {
        super(application);

        tokenRepository = new TokenRepository();
        transactionSender = new TransactionSender();
        transactionSigner = new TransactionSigner();
    }

    public LiveData<Result<TokenInfo>> getUserTokenInfo(){
        SharedPreferences sp = getApplication().getSharedPreferences("UserData", MODE_PRIVATE);
        int userId = sp.getInt("user_id", -1);

        return tokenRepository.getUserTokenInfo(userId);
    }

    public void generateDelegateVotesTransaction(String delegateeLogin){
        SharedPreferences sp = getApplication().getSharedPreferences("UserData", MODE_PRIVATE);
        int userId = sp.getInt("user_id", -1);

        tokenRepository.generateDelegateVotesTransaction(userId, delegateeLogin);
    }

    public LiveData<Result<RawTransaction>> getDelegateVotesTransaction(){
        return tokenRepository.getDelegateVotesTransaction();
    }

    public void delegateVotes(RawTransaction transaction){
        SharedPreferences sp = getApplication().getSharedPreferences("UserData", MODE_PRIVATE);
        String privateKey = sp.getString( "private_key", "");

        privateKey = "0x2c72f5cc094ff6beb9c48e8ce90f2fa894473ee097f715b39d5428e493f46963";
        String transactionHex = transactionSigner.SignTransaction(transaction, privateKey);
        transactionSender.sendSignedTransaction(transactionHex);
    }
}
