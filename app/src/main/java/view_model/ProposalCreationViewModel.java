package view_model;

import static android.content.Context.MODE_PRIVATE;

import android.app.Application;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import model.ProposalType;
import model.RawTransaction;
import repository.ProposalRepository;
import service.utils.Result;
import service.TransactionSender;
import service.TransactionSigner;

public class ProposalCreationViewModel extends AndroidViewModel {
    private final ProposalRepository proposalRepository;
    private final TransactionSender transactionSender;
    private final TransactionSigner transactionSigner;


    public ProposalCreationViewModel(@NonNull Application application){
        super(application);

        proposalRepository = new ProposalRepository();
        transactionSender = new TransactionSender();
        transactionSigner = new TransactionSigner();
    }


    public void createProposal(String name, String description, String proposalType, String newValue) {
        SharedPreferences sp = getApplication().getSharedPreferences("UserData", MODE_PRIVATE);
        int userId = sp.getInt("user_id", -1);
        int value = Integer.parseInt(newValue);

        proposalRepository.createProposal(name, description, Map(proposalType), value, userId);
    }

    public LiveData<Result<RawTransaction>> getProposalTransaction() {
        return proposalRepository.getCreateProposalTransaction();
    }

    public void signAndSendCreateProposalTransaction(RawTransaction transaction){
        SharedPreferences sp = getApplication().getSharedPreferences("UserData", MODE_PRIVATE);
        String privateKey = sp.getString( "private_key", "");

        privateKey = "0x2c72f5cc094ff6beb9c48e8ce90f2fa894473ee097f715b39d5428e493f46963";
        String transactionHex = transactionSigner.SignTransaction(transaction, privateKey);
        transactionSender.sendSignedTransaction(transactionHex);
    }

    public LiveData<Result<Void>> getProposalTransactionResult(){
        return transactionSender.getSendTransactionResult();
    }

    private ProposalType Map(String proposalType){
        switch (proposalType){
            case "Изменить период голосования":
                return ProposalType.UpdateVotingPeriod;
            case "Изменить кворум":
                return ProposalType.UpdateQuorum;
            default:
                return ProposalType.Unknown;
        }
    }
}
