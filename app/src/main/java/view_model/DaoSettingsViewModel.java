package view_model;

import static android.content.Context.MODE_PRIVATE;

import android.app.Application;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import model.RawTransaction;
import repository.QDAORepository;
import service.utils.Result;
import service.TransactionSender;
import service.TransactionSigner;

public class DaoSettingsViewModel extends AndroidViewModel {
    private final TransactionSigner transactionSigner;
    private final TransactionSender transactionSender;
    private final QDAORepository adminRepository;

    public DaoSettingsViewModel(@NonNull Application application) {
        super(application);

        adminRepository = new QDAORepository();
        transactionSender = new TransactionSender();
        transactionSigner = new TransactionSigner();
    }


    public void addPrincipals(String userLogin, String requiredApprovalsString){
        SharedPreferences sp = getApplication().getSharedPreferences("UserData", MODE_PRIVATE);
        int userId = sp.getInt("user_id", -1);

        int requiredApprovals = Integer.parseInt(requiredApprovalsString);
        adminRepository.addPrincipals(userLogin, requiredApprovals, userId);
    }

    public LiveData<Result<RawTransaction>> getAddPrincipalsTransaction(){
        return adminRepository.getAddPrincipalsTransaction();
    }

    public void transferTokens(String userLogin, String amountString){
        int amount = Integer.parseInt(amountString);
        SharedPreferences sp = getApplication().getSharedPreferences("UserData", MODE_PRIVATE);
        int userId = sp.getInt("user_id", -1);

        adminRepository.transferTokens(userId, userLogin, amount);
    }

    public LiveData<Result<RawTransaction>> getTransferTokensTransaction(){
        return adminRepository.getTransferTokensTransaction();
    }

    public void signAndSendTransferTokensTransaction(RawTransaction transaction){
        SharedPreferences sp = getApplication().getSharedPreferences("UserData", MODE_PRIVATE);
        String privateKey = sp.getString( "private_key", "");

        privateKey = "0xe263b4109e1e49e5d7e191c8f64aa9ec456b1768c96f9598d0a531814e252b72";
        String transactionHex = transactionSigner.SignTransaction(transaction, privateKey);
        transactionSender.sendSignedTransaction(transactionHex);
    }

    public void signAndSendAddPrincipalsTransaction(RawTransaction transaction){
        SharedPreferences sp = getApplication().getSharedPreferences("UserData", MODE_PRIVATE);
        String privateKey = sp.getString( "private_key", "");

        privateKey = "0xe263b4109e1e49e5d7e191c8f64aa9ec456b1768c96f9598d0a531814e252b72";
        String transactionHex = transactionSigner.SignTransaction(transaction, privateKey);
        transactionSender.sendSignedTransaction(transactionHex);
    }
}
