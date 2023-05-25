package view_model;

import static android.content.Context.MODE_PRIVATE;

import android.app.Application;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import model.ProposalInfo;

import model.RawTransaction;
import repository.ProposalRepository;
import service.utils.Result;
import service.TransactionSender;
import service.TransactionSigner;

public class ProposalPromotionDetailsViewModel extends AndroidViewModel {

    private final ProposalRepository proposalRepository;
    private final TransactionSender transactionSender;
    private final TransactionSigner transactionSigner;

    public ProposalPromotionDetailsViewModel(@NonNull Application application) {
        super(application);

        proposalRepository = new ProposalRepository();
        transactionSender = new TransactionSender();
        transactionSigner = new TransactionSigner();
    }

    public LiveData<Result<ProposalInfo>> getProposalInfo(long proposalId){
        return proposalRepository.getProposalInfoById(proposalId);
    }

    public void promoteProposal(ProposalInfo proposalInfo){
        int userId = 1;

        switch (proposalInfo.getStateNumber()){
            // Success
            case 4: proposalRepository.queueProposal(proposalInfo.getId(), userId);
            // Queued
            case 5: proposalRepository.executeProposal(proposalInfo.getId(), userId);
        }
    }

    public LiveData<Result<RawTransaction>> getPromotionTransactionResult() {
        return proposalRepository.getPromoteProposalTransaction();
    }

    public void sendPromotionTransaction(RawTransaction transaction) {
        SharedPreferences sp = getApplication().getSharedPreferences("UserData", MODE_PRIVATE);
        String privateKey = sp.getString( "private_key", "");

        String transactionHex = transactionSigner.SignTransaction(transaction, privateKey);
        transactionSender.sendSignedTransaction(transactionHex);
    }
}
