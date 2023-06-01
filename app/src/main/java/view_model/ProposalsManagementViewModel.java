package view_model;

import static android.content.Context.MODE_PRIVATE;

import android.app.Application;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import model.ProposalThin;
import model.RawTransaction;
import repository.ProposalRepository;
import repository.QDAORepository;
import service.TransactionSender;
import service.TransactionSigner;
import service.utils.Result;

public class ProposalsManagementViewModel extends AndroidViewModel {

    private final ProposalRepository proposalRepository;
    private final QDAORepository qdaoRepository;
    private final TransactionSigner transactionSigner;
    private final TransactionSender transactionSender;

    public ProposalsManagementViewModel(@NonNull Application application) {
        super(application);

        SharedPreferences sp = getApplication().getSharedPreferences("UserData", MODE_PRIVATE);
        String token = sp.getString("user_token", "");

        proposalRepository = new ProposalRepository(token);
        qdaoRepository = new QDAORepository(token);
        transactionSender = new TransactionSender(token);
        transactionSigner = new TransactionSigner();
    }

    public LiveData<Result<List<ProposalThin>>> getProposalsForPromotion() {
       return proposalRepository.getProposalsForPromotion();
    }

    public LiveData<Result<String>> getPendingMigrationAddress() {
        return qdaoRepository.getPendingAddress();
    }

    public void setPendingAddressTransaction(String address) {
        SharedPreferences sp = getApplication().getSharedPreferences("UserData", MODE_PRIVATE);
        int userId = sp.getInt("user_id", -1);

        qdaoRepository.setPendingAddress(userId, address);
    }

    public LiveData<Result<RawTransaction>> getPendingTransaction() {
        return qdaoRepository.getPendingTransaction();
    }

    public void signAndSendPendingMigrationTransaction(RawTransaction transaction) {
        SharedPreferences sp = getApplication().getSharedPreferences("UserData", MODE_PRIVATE);
        String privateKey = sp.getString( "private_key", "");
        privateKey = "0xe263b4109e1e49e5d7e191c8f64aa9ec456b1768c96f9598d0a531814e252b72";

        String transactionHex = transactionSigner.SignTransaction(transaction, privateKey);
        transactionSender.sendSignedTransaction(transactionHex);
    }

    public void approveMigrationTransaction() {
        SharedPreferences sp = getApplication().getSharedPreferences("UserData", MODE_PRIVATE);
        int userId = sp.getInt("user_id", -1);

        qdaoRepository.approveMigration(userId);
    }

    public LiveData<Result<RawTransaction>> getApproveMigrationTransaction() {
        return qdaoRepository.getApproveMigrationTransaction();
    }

    public void signAndSendApproveMigrationTransaction(RawTransaction transaction) {
        SharedPreferences sp = getApplication().getSharedPreferences("UserData", MODE_PRIVATE);
        String privateKey = sp.getString( "private_key", "");
        privateKey = "0x5822bf1c7ede62de46bb07d36720d4ae163acf5063cd1f8bd62287e67b30a3de";

        String transactionHex = transactionSigner.SignTransaction(transaction, privateKey);
        transactionSender.sendSignedTransaction(transactionHex);
    }


    public void setMigration() {
        SharedPreferences sp = getApplication().getSharedPreferences("UserData", MODE_PRIVATE);
        int userId = sp.getInt("user_id", -1);

        qdaoRepository.setMigration(userId);
    }

    public LiveData<Result<RawTransaction>> getSetMigrationTransaction() {
        return qdaoRepository.getSetMigration();
    }

    public void signAndSendSetMigrationTransaction(RawTransaction transaction) {
        SharedPreferences sp = getApplication().getSharedPreferences("UserData", MODE_PRIVATE);
        String privateKey = sp.getString( "private_key", "");
        privateKey = "0xe263b4109e1e49e5d7e191c8f64aa9ec456b1768c96f9598d0a531814e252b72";

        String transactionHex = transactionSigner.SignTransaction(transaction, privateKey);
        transactionSender.sendSignedTransaction(transactionHex);
    }
}
