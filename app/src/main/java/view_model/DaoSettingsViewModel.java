package view_model;

import android.app.Application;

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
        int senderId = 1;
        int requiredApprovals = Integer.parseInt(requiredApprovalsString);
        adminRepository.addPrincipals(userLogin, requiredApprovals, senderId);
    }

    public LiveData<Result<RawTransaction>> getAddPrincipalsTransaction(){
        return adminRepository.getAddPrincipalsTransaction();
    }
}
