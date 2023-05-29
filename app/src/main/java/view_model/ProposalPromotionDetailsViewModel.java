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

    public void generatePromotionTransaction(ProposalInfo proposalInfo){
        SharedPreferences sp = getApplication().getSharedPreferences("UserData", MODE_PRIVATE);
        int userId = sp.getInt("user_id", -1);

        switch (proposalInfo.getStateNumber()){
            // No Quorm
            // Succeeded
            case 5: proposalRepository.queueProposal(proposalInfo.getId(), userId);
            // Queued
            case 7: proposalRepository.executeProposal(proposalInfo.getId(), userId);
        }
    }

    public LiveData<Result<RawTransaction>> getPromotionTransaction() {
        return proposalRepository.getPromoteProposalTransaction();
    }

    public void sendPromotionTransaction(RawTransaction transaction) {
        SharedPreferences sp = getApplication().getSharedPreferences("UserData", MODE_PRIVATE);
        String privateKey = sp.getString( "private_key", "");
        privateKey = "0xe263b4109e1e49e5d7e191c8f64aa9ec456b1768c96f9598d0a531814e252b72";

        String transactionHex = transactionSigner.SignTransaction(transaction, privateKey);
        transactionSender.sendSignedTransaction(transactionHex);
    }


    public void generateApproveProposalTransaction(long proposalId){
        SharedPreferences sp = getApplication().getSharedPreferences("UserData", MODE_PRIVATE);
        int userId = sp.getInt("user_id", -1);

        proposalRepository.approveProposal(proposalId, userId);
    }

    public LiveData<Result<RawTransaction>> getApproveProposalTransaction(){
        return proposalRepository.getApproveProposalTransaction();
    }

    public void approveProposal(RawTransaction transaction){
        SharedPreferences sp = getApplication().getSharedPreferences("UserData", MODE_PRIVATE);
        String privateKey = sp.getString( "private_key", "");
        privateKey = "0x2c72f5cc094ff6beb9c48e8ce90f2fa894473ee097f715b39d5428e493f46963";

        String transactionHex = transactionSigner.SignTransaction(transaction, privateKey);
        transactionSender.sendSignedTransaction(transactionHex);
    }
}
